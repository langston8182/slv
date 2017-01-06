package com.slv.pojos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.slv.slv_api.services.JsonDiffResult;

public class SlvJsonComparator {

	private static final String MOVE = "move";
	private static final String ADD = "add";
	private static final String ADD_MESSAGE = "The key %s has been added.\n";
	private static final  String REMOVE_MESSAGE = "The key %s has been removed.\n";

	public SlvJsonComparator() {
	}

	public JsonDiffResult compare(String source, String target)
			throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode patch = JsonDiff.asJson(mapper.readTree(source),
				mapper.readTree(target));

		Iterator<JsonNode> opIterator = patch.elements();
		if (!opIterator.hasNext())
			return new JsonDiffResult(false, "no changes");
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

			}

		}
		return new JsonDiffResult(true, generateErrorMessage(removes, adds));

	}

	private String generateErrorMessage(List<Remove> removes, List<Add> adds) {
		StringBuilder errorMessage = new StringBuilder();
		for (Remove remove : removes)
			errorMessage.append(String.format(REMOVE_MESSAGE, remove.getFrom()));
		for (Add add : adds)
			errorMessage.append(String.format(ADD_MESSAGE, add.getPath()));

		return errorMessage.toString();

	}

}
