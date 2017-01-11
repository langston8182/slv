package com.slv.slv_api.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.flipkart.zjsonpatch.JsonDiff;
import com.slv.slv_api.common.MessageHelper;
import com.slv.slv_api.entities.Add;
import com.slv.slv_api.entities.Remove;
import com.slv.slv_api.exceptions.ExceptionCode;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.exceptions.UnsupportedOperationException;

/**
 * Used to compare two JSON {@link String}. Interface to make a comparison of
 * two items according to their data format.
 * 
 * @author cmarchive
 */
public class JsonDiffService {

	/**
	 * Logger log4j
	 */
	private Logger logger = Logger.getLogger(JsonDiffService.class);
	
	/**
	 * No changes message.
	 */
	private static final String NO_CHANGES = "no changes";
	
	/**
	 * Operation move.
	 */
	private static final String OP_MOVE = "move";
	
	/**
	 * Operation add.
	 */
	private static final String OP_ADD = "add";
	
	/**
	 * Operation remove.
	 */
	private static final String OP_REMOVE = "remove";
	
	/**
	 * Node op.
	 */
	private static final String NODE_OP = "op";
	
	/**
	 * Node path.
	 */
	private static final String NODE_PATH = "path";
	
	/**
	 * Node from.
	 */
	private static final String NODE_FROM = "from";

	/**
	 * Empty tab suffix.
	 */
	private static final String EMPTY_TAB_SUFFIX = "/0";

	/**
	 * Unique instance of JsonDiffService
	 */
	private static JsonDiffService INSTANCE = null;

	/**
	 * Get the unique instance of JsonDiffService.
	 * 
	 * @return The unique instance of JsonDiffService.
	 */
	public static JsonDiffService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JsonDiffService();
		}
		return INSTANCE;
	}

	/**
	 * Private default constructor to avoid creating several JsonDiffService objects.
	 */
	private JsonDiffService() {

	}

	/**
	 * Method that verifies if two {@link String} representing two Json streams
	 * are equals in terms of attributes.
	 * 
	 * @return {@link JsonDiffResult} an object that indicates the result of the
	 *         comparison and an error message when needed
	 * @throws SLVTestsException
	 * @throws UnsupportedOperationException
	 */
	public JsonDiffResult diff(String toVerify, String target)
			throws UnsupportedOperationException, SLVTestsException {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode patch = null;
		try {
			patch = JsonDiff.asJson(
					prepareForCompare(mapper.readTree(target)),
					prepareForCompare(mapper.readTree(toVerify)));
		} catch (IOException ex) {
			String message = MessageHelper.getMessage(ex.getMessage());
			logger.error(message);
			throw new SLVTestsException(ExceptionCode.DIFF_METHOD_EXECUTION, message, ex);
		}

		boolean jsonEquals = true;

		// If there's no difference, return true
		if (patch.size() == 0) {
			return new JsonDiffResult(jsonEquals, NO_CHANGES);
		}

		// Otherwise, compute add and remove list
		List<Add> adds = new ArrayList<>();
		List<Remove> removes = new ArrayList<>();

		for (JsonNode opNode : patch) {
			String operationType = opNode.get(NODE_OP).asText();
			String path = opNode.get(NODE_PATH).asText();

			switch (operationType) {
			case OP_MOVE:
				removes.add(new Remove(opNode.get(NODE_FROM).asText()));
				adds.add(new Add(path));
				break;
				
			case OP_ADD:
				adds.add(new Add(path));
				break;
				
			case OP_REMOVE:
				// Don't remove empty tabs
				if (!path.endsWith(EMPTY_TAB_SUFFIX)) {
					removes.add(new Remove(path));
				}
				break;
			}
		}

		// If no adds and no removes, then there's no difference in format
		jsonEquals = adds.isEmpty() && removes.isEmpty();
		
		return new JsonDiffResult(jsonEquals, generateErrorMessage(removes, adds));
	}

	/**
	 * Method that generates error Message.
	 * 
	 * @param removes List of elements removed.
	 * @param adds List of elements added
	 * 
	 * @return The error message.
	 */
	private String generateErrorMessage(List<Remove> removes, List<Add> adds) {
		StringBuilder errorMessage = new StringBuilder();
		for (Remove remove : removes) {
			String message = MessageHelper.getMessage("core.service.diff.message.remove", remove.getFrom());
			errorMessage.append(message);
		}
		for (Add add : adds) {
			String message = MessageHelper.getMessage("core.service.diff.message.add", add.getPath());
			errorMessage.append(message);
		}

		return errorMessage.toString();
	}

	/**
	 * If needed, modify the {@link JsonNode} value to be compliant with the
	 * comparison method :
	 * <ul>
	 * <li>Keep only one item in each list</li>
	 * </ul>
	 * 
	 * @param value
	 *            the {@link JsonNode} to prepare
	 *            
	 * @return the {@link JsonNode} modified if needed
	 * 
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public JsonNode prepareForCompare(JsonNode value)
			throws JsonProcessingException, IOException {
		if (value != null) {
			return convertArrayToOneElement(value);
		}

		return value;
	}

	/**
	 * Read the {@link JsonNode} and for each {@link JsonNodeType#ARRAY}, keeps only one value.
	 * 
	 * @param value the {@link JsonNode} to read
	 * 
	 * @return the {@link JsonNode} modified
	 */
	private JsonNode convertArrayToOneElement(JsonNode value) {
		if(value != null) {
			// If it's an array, keep only one element et read the kept element recursively
			if(value.isArray()) {
				Iterator<JsonNode> elements = value.elements();
				if(elements.hasNext()) {
					convertArrayToOneElement(elements.next());
					while (elements.hasNext()) {
						elements.next();
						elements.remove();
					}
				}
			} else if(value.isObject()) {
				// If it's an object, read it recursively
				Iterator<JsonNode> elements = value.elements();
				while (elements.hasNext()) {
					convertArrayToOneElement(elements.next());
				}
			}
		}
		return value;
	}
}
