package com.slv.slv_api.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.slv.slv_api.entities.Add;
import com.slv.slv_api.entities.Remove;

/**
 * Used to compare two JSON {@link String}.
 * Interface to make a comparison of two items according to their data format.
 * 
 * @author cmarchive
 * 
 * @return 
 */
public class JsonDiffService  {
	

	private static final String MOVE = "move";
	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String ADD_MESSAGE = "The key %s has been added.\n";
	private static final  String REMOVE_MESSAGE = "The key %s has been removed.\n";

	/**
	 * Instance of {@link JsonDiffService}
	 */
	private static JsonDiffService service = null;
	
	/**
	 * @return instance of {@link JsonDiffService} as a Singleton
	 */
	public static JsonDiffService getInstance() {
		if(service == null) {
			service = new JsonDiffService();
		}
		return service;
	}
	
	/**
	 * Method that verifies if two {@link String} representing two Json streams are equals in terms of attributes
	 * @return {@link JsonDiffResult} an object that indicates the result of the comparison and an error message when needed
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public JsonDiffResult diff(String toVerify, String target) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patch = JsonDiff.asJson(mapper.readTree(target),
				mapper.readTree(toVerify));

		Iterator<JsonNode> opIterator = patch.elements();
		if (!opIterator.hasNext())
			return new JsonDiffResult(true, "no changes");
		List<Add> adds = new ArrayList<>();
		List<Remove> removes = new ArrayList<>();

		while (opIterator.hasNext()) {
			JsonNode opNode = opIterator.next();
			String operationType = opNode.get("op").asText();
			String path = opNode.get("path").asText();
			switch (operationType) {
			case MOVE:
				removes.add(new Remove(opNode.get("from").asText()));
				adds.add(new Add(path));
				break;
			case ADD:
				adds.add(new Add(path));
			case REMOVE:
				removes.add(new Remove(path));
			}

		}
		return new JsonDiffResult(false, generateErrorMessage(removes, adds));

	}
	
	/**
	 * Method that generates error Message 
	 */
	private String generateErrorMessage(List<Remove> removes, List<Add> adds) {
		StringBuilder errorMessage = new StringBuilder();
		for (Remove remove : removes)
			errorMessage.append(String.format(REMOVE_MESSAGE, remove.getFrom()));
		for (Add add : adds)
			errorMessage.append(String.format(ADD_MESSAGE, add.getPath()));

		return errorMessage.toString();

	}

	/**
	 * If needed, modify the {@link JsonNode} value to be compliant with the comparison method :
	 * <ul>
	 * <li>Keep only one item in each list</li>
	 * </ul>
	 * @param value the {@link JsonNode} to prepare
	 * @return the {@link JsonNode} modified if needed
	 */
	public JsonNode prepareForCompare(JsonNode value) {
		return value;
	}

}
