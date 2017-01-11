package com.slv.slvapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slv.slvapi.common.MessageHelper;
import com.slv.slvapi.exceptions.SlvTestsException;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;

/**
 * Unit tests for {@link JsonDiffService}.
 * 
 * @author atran
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JsonDiffService.class)
public class JsonDiffServiceTest {

  @InjectMocks
  private JsonDiffService jsonDiffService;

  @Test
  public void diffWithOnlyReplacements() throws IOException {
    // INIT
    String toVerify = "{"
        + " \"key1\": null,"
        + " \"key2\": \"value2\","
        + " \"key3\": \"value4\""
        + "}";
    String expected = "{"
        + " \"key1\": \"value1\","
        + " \"key2\": null,"
        + " \"key3\": \"value3\""
        + "}";

    // CALL
    JsonDiffResult result = null;
    try {
      result = jsonDiffService.diff(toVerify, expected);
    } catch (SlvTestsException ex) {
      Assert.assertTrue(false);
    }

    // VERIFY
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isEquals());
  }

  @Test
  public void prepareForCompareWithRootAsObject() throws Exception {
    // INIT
    ObjectMapper jacksonMapper = new ObjectMapper();
    JsonNode json = jacksonMapper.readTree("{ "
        + "\"key1\": \"value1\","
        + "\"key2\": ["
        + "     {\"subKey1\": \"subValue1.1\"},"
        + "     {\"subKey1\": \"subValue1.2\"},"
        + "     {\"subKey1\": \"subValue1.3\"},"
        + "     {\"subKey1\": \"subValue1.4\"}"
        + " ],"
        + "\"key3\": {"
        + "     \"subKey1\": \"subValue1\","
        + "     \"subKey2\": ["
        + "         {"
        + "             \"subKey2\": ["
        + "                 {\"subKey2\": \"subValue2.1\"},"
        + "                 {\"subKey2\": \"subValue2.2\"}"
        + "             ]"
        + "         },"
        + "         {"
        + "             \"subKey2\": ["
        + "                 {\"subKey2\": \"subValue2.1\"},"
        + "                 {\"subKey2\": \"subValue2.2\"},"
        + "                 {\"subKey2\": \"subValue2.3\"}"
        + "             ]"
        + "         }"
        + "     ]"
        + " }"
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

  @Test
  public void prepareForCompareWithRootAsArray() throws Exception {
    // INIT
    ObjectMapper jacksonMapper = new ObjectMapper();
    JsonNode json = jacksonMapper.readTree("["
        + "     {"
        + "         \"key1\": \"value1.1\","
        + "         \"key2\": \"value2.2\","
        + "         \"key3\": ["
        + "             {"
        + "                 \"key1\": \"value1\""
        + "             },"
        + "             {"
        + "                 \"key2\": \"value2\""
        + "             }"
        + "         ]"
        + "     },"
        + "     {"
        + "         \"key1\": \"value1.2\","
        + "         \"key2\": \"value2.2\","
        + "         \"key3\": ["
        + "             {"
        + "                 \"key1\": \"value1\""
        + "             },"
        + "             {"
        + "                 \"key2\": \"value2\""
        + "             }"
        + "         ]"
        + "     },"
        + "     {"
        + "         \"key1\": \"value1.3\","
        + "         \"key2\": \"value2.3\","
        + "         \"key3\": ["
        + "             {"
        + "                 \"key1\": \"value1\""
        + "             },"
        + "             {"
        + "                 \"key2\": \"value2\""
        + "             },"
        + "             {"
        + "                 \"key3\": \"value3\""
        + "             },"
        + "             {"
        + "                 \"key4\": \"value4\""
        + "             }"
        + "         ]"
        + "     }"
        + "]");

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

  @Test
  public void prepareForCompareWithRootAsArrayAndFirstArrayEmpty() throws Exception {
    // INIT
    ObjectMapper jacksonMapper = new ObjectMapper();
    JsonNode json = jacksonMapper.readTree("["
        + "     {"
        + "         \"key1\": \"value1.1\","
        + "         \"key2\": \"value2.2\","
        + "         \"key3\": []"
        + "     },"
        + "     {"
        + "         \"key1\": \"value1.2\","
        + "         \"key2\": \"value2.2\","
        + "         \"key3\": ["
        + "             {"
        + "                 \"key1\": \"value1\""
        + "             },"
        + "             {"
        + "                 \"key2\": \"value2\""
        + "             }"
        + "         ]"
        + "     },"
        + "     {"
        + "         \"key1\": \"value1.3\","
        + "         \"key2\": \"value2.3\","
        + "         \"key3\": ["
        + "             {"
        + "                 \"key1\": \"value1\""
        + "             },"
        + "             {"
        + "                 \"key2\": \"value2\""
        + "             },"
        + "             {"
        + "                 \"key3\": \"value3\""
        + "             },"
        + "             {"
        + "                 \"key4\": \"value4\""
        + "             }"
        + "         ]"
        + "     }"
        + "]");

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
   * Response contains an error indicator.
   * 
   * @throws JsonProcessingException When a JsonProcessingException is thrown.
   * @throws IOException When an IOException is thrown.
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void isErrorResponseYes() throws JsonProcessingException, IOException, SlvTestsException {
    // INIT
    String jsonErrorResponse = "{"
        + "         \"errorCode\": \"613\","
        + "         \"errorCodeLabel\": \"Item not found\","
        + "         \"message\": \"Provider #null not found!\","
        + "         \"status\": \"ERROR\","
        + "         \"statusError\": true,"
        + "         \"statusOk\": false,"
        + "         \"value\": null"
        + "     }";
    Assert.assertTrue(jsonDiffService.isErrorResponse(jsonErrorResponse),
        MessageHelper.getMessage("not.an.error.response"));
  }

  /**
   * Response doesn't contain an error indicator.
   * 
   * @throws JsonProcessingException When a JsonProcessingException is thrown.
   * @throws IOException When an IOException is thrown.
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void isErrorResponseNo() throws JsonProcessingException, IOException, SlvTestsException {
    // INIT
    String jsonOkResponse = "{"
        + "         \"errorCode\": \"0\","
        + "         \"errorCodeLabel\": null,"
        + "         \"message\": null,"
        + "         \"status\": \"OK\","
        + "         \"statusError\": false,"
        + "         \"statusOk\": true,"
        + "         \"value\": null"
        + "     }";
    Assert.assertFalse(jsonDiffService.isErrorResponse(jsonOkResponse),
        MessageHelper.getMessage("not.an.ok.response"));
  }

  /**
   * Check a {@link JsonNode} value and verify for 
   * each of the arrays that its length is at most 1 element.
   * @param value the {@link JsonNode} to check
   * @return <code>true</code> if OK, <code>false</code> otherwise
   */
  private boolean checkArraySize(JsonNode value) {
    boolean correctSize = true;
    if (value != null) {
      // If the node is an array, verify size
      if (value.isArray()) {
        correctSize = correctSize && hasAtMostOneElement(value.iterator());
        // Read the rest
        if (correctSize) {
          for (JsonNode jsonNode : value) {
            correctSize = checkArraySize(jsonNode);
          }
        }
      } else if (value.isObject()) {
        // If it's an object, recursive call on each attributes
        for (JsonNode jsonNode : value) {
          correctSize = checkArraySize(jsonNode);
        }
      }
    }
    return correctSize;
  }

  /**
   * Indicates if this {@link Iterator} has at most one element.
   * 
   * @param iterator the {@link Iterator} to check
   * 
   * @return <code>true</code> if there is only one element, <code>false</code> otherwise
   */
  private boolean hasAtMostOneElement(Iterator<?> iterator) {
    int index = 0;
    while (iterator.hasNext()) {
      iterator.next();
      index++;
    }
    return index <= 1;
  }

}
