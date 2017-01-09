package com.slv.slv_api.services;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for {@link JsonDiffService}
 * @author atran
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JsonDiffService.class)
public class JsonDiffServiceTest {
	
	@InjectMocks
	private JsonDiffService jsonDiffService;

	@Test
	public void prepareForCompare() throws Exception {
		// INIT
		ObjectMapper jacksonMapper = new ObjectMapper();
		JsonNode json = jacksonMapper.readTree("{ "
				+ "\"key1\": \"value1\","
				+ "\"key2\": ["
				+ "		{\"subKey1\": \"subValue1\"},"
				+ "		{\"subKey2\": \"subValue2\"},"
				+ "		{\"subKey3\": \"subValue3\"},"
				+ "		{\"subKey4\": \"subValue4\"}"
				+ "	]"
				+ "}");
		
		// CALL
		JsonNode preparedJson = Whitebox.invokeMethod(jsonDiffService, "prepareForCompare", json);
		
		// VERIFY
		Assert.assertNotNull(preparedJson);
		Iterator<JsonNode> elements = preparedJson.elements();
		Assert.assertTrue(elements.hasNext());
		boolean allArraySizeEqualsOne = true;
		allArraySizeEqualsOne = checkArraySize(preparedJson);
		Assert.assertTrue(allArraySizeEqualsOne);
	}
	
	/**
	 * Check a {@link JsonNode} value and verify for each of the arrays that its length is at most 1 element
	 * @param value the {@link JsonNode} to check
	 * @return <code>true</code> if OK, <code>false</code> otherwise
	 */
	private boolean checkArraySize(JsonNode value) {
		boolean correctSize = true;
		if(value != null) {
			for (Iterator<JsonNode> iterator = value.elements(); iterator.hasNext();) {
				JsonNode node = (JsonNode) iterator.next();
				if(node.isObject()) {
					correctSize = checkArraySize(node);
				} else if(node.isArray())  {
					correctSize = correctSize && hasAtMostOneElement(node.elements()); 
				}
			}
		}
		return correctSize;
	}
	
	/**
	 * Indicates if this {@link Iterator} has at most one element
	 * @param iterator the {@link Iterator} to check
	 * @return <code>true</code> if there is only one element, <code>false</code> otherwise
	 */
	private boolean hasAtMostOneElement(Iterator<?> iterator) {
		int i = 0;
		while(iterator.hasNext()) {
			iterator.next();
			i++;
		}
		return i <= 1;
	}

}
