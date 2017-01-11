package com.slv.slvapi.providers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slv.slvapi.common.MessageHelper;
import com.slv.slvapi.core.AbstractTest;
import com.slv.slvapi.exceptions.SlvTestsException;
import com.slv.slvapi.services.JsonDiffResult;
import com.slv.slvapi.utils.TestConstants;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for the rest api URLs of a provider.
 * 
 * @author yromdhane
 *
 */
public class ProvidersTest extends AbstractTest {

  /**
   * File name of Providers API Inputs.
   */
  private static final String INPUT_FILE = "json/providers/input.json";

  /**
   * File name of Providers API Outputs.
   */
  private static final String OUTPUT_FILE = "json/providers/output.json";

  /**
   * Map to store the identifiers of the created providers in order to delete
   * them at the end of the tests The key is the method name and the value is
   * the provider identifier.
   */
  private Map<String, Integer> createdProviderIdMap = new HashMap<String, Integer>();

  /**
   * Json format test of API Service "asset/getAllProviders"
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  @Test(groups = { "providers-format" }, priority = 1)
  public void getAllProviders() throws SlvTestsException {
    // CALL
    JsonDiffResult result = retrieveResult(ProvidersMethods.GET_ALL_PROVIDERS.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  // ----------- Begin Functional test : Create - Update - Delete -------------
  /**
   * Creates a Provider and assert its existence and equality.
   * 
   * @throws SlvTestsException
   *           When a SlvTestException is thrown.
   * @throws IOException
   *           When an IOException is thrown.
   * @throws JsonMappingException
   *           When a JsonMappingException is thrown.
   * @throws JsonParseException
   *           When a JsonParserException is thrown.
   */
  @Test(groups = { "providers-createUpdateDeleteProvider" }, priority = 2)
  public void createProvider() throws SlvTestsException, 
      JsonParseException, JsonMappingException, IOException {
    // CALL
    JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl());

    // Verify that the provider is created
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());

    // Extract the id of the created Provider
    Map<String, Object> map = convert(result.getResponse());
    createdProviderIdMap.put(ProviderTestMethod.CREATE_PROVIDER.getName(),
        (Integer) map.get(TestConstants.CREATE_PROVIDER_OUTPUT_ID_KEY));
  }

  /**
   * Updates a Provider and assert that the modification has been done.<br/>
   * Depends on {@link #createProvider()}
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  @Test(groups = { "providers-createUpdateDeleteProvider" }, 
      dependsOnMethods = { "createProvider" }, priority = 2)
  public void updateProvider() throws SlvTestsException {
    // INIT
    JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl()).deepCopy();
    ((ObjectNode) parameters).put(TestConstants.UPDATE_PROVIDER_INPUT_ID_KEY,
        createdProviderIdMap.get(ProviderTestMethod.CREATE_PROVIDER.getName()));

    // CALL
    JsonDiffResult result = retrieveResult(ProvidersMethods.UPDATE_PROVIDER.getUrl(), parameters);

    // Verify that the provider is updated
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Deletes a Provider and assert that it does not exist anymore.<br/>
   * Depends on {@link #updateProvider()}
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  @Test(groups = { "providers-createUpdateDeleteProvider" }, 
      dependsOnMethods = { "updateProvider" }, priority = 2)
  public void deleteProvider() throws SlvTestsException {
    // INIT
    JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl()).deepCopy();
    ((ObjectNode) parameters).put(TestConstants.DELETE_PROVIDER_INPUT_ID_KEY,
        createdProviderIdMap.get(ProviderTestMethod.CREATE_PROVIDER.getName()));

    // CALL
    JsonDiffResult result = retrieveResult(ProvidersMethods.DELETE_PROVIDER.getUrl(), parameters);

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  // ----------- End Functional test : Create - Update - Delete -------------

  /**
   * Fails to create a Provider without specifying a name.
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   * @throws IOException
   *           When a IOException is thrown.
   * @throws JsonMappingException
   *           When a JsonMappingException is thrown.
   * @throws JsonParseException
   *           When a JsonParserException is thrown.
   */
  @Test(groups = { "providers-createProviderWithMissingAttributes" }, dependsOnMethods = {
      "deleteProvider" }, priority = 3)
  public void createProviderKoMissedName()
      throws SlvTestsException, JsonParseException, JsonMappingException, IOException {
    // INIT
    JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()).deepCopy();
    // Remove the name from the request input
    ((ObjectNode) parameters).remove(TestConstants.CREATE_PROVIDER_INPUT_NAME_KEY);

    // CALL
    String response = call(ProvidersMethods.CREATE_PROVIDER.getUrl(), parameters);

    // Verify that the response represents an error
    Assert.assertTrue(isErrorResponse(response), MessageHelper.getMessage("not.an.error.response"));

    // Extract the error message from the response
    Map<String, Object> map = convert(response);
    String errorCode = (String) map.get(TestConstants.RESPONSE_ERROR_CODE_KEY);
    String errorMessage = (String) map.get(TestConstants.RESPONSE_MESSAGE_KEY);

    // Verify that the error belongs to the name
    Assert.assertTrue(TestConstants.INTERNAL_ERROR_CODE.equals(errorCode),
        MessageHelper.getMessage("providers.error.code.not.recognized"));
    Assert.assertTrue(errorMessage.contains("streetlight.data.Provider.name"),
        MessageHelper.getMessage("providers.error.message.not.recognized"));
  }

  /**
   * Creates a Provider without specifying a pollution rate.
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   * @throws IOException
   *           When a IOException is thrown.
   * @throws JsonMappingException
   *           When a JsonMappingException is thrown.
   * @throws JsonParseException
   *           When a JsonParserException is thrown.
   */
  @Test(groups = { "providers-createProviderWithMissingAttributes" }, dependsOnMethods = {
      "deleteProvider" }, priority = 3)
  public void createProviderMissedPollutionRate()
      throws SlvTestsException, JsonParseException, JsonMappingException, IOException {
    // INIT
    JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()).deepCopy();
    // Remove the pollution rate from the request input
    ((ObjectNode) parameters).remove(TestConstants.CREATE_PROVIDER_INPUT_POLLUTIONRATE_KEY);

    // CALL
    JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl(), parameters);

    // Verify that the provider is created
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());

    // Extract the id of the created Provider
    Map<String, Object> map = convert(result.getResponse());
    createdProviderIdMap.put(ProviderTestMethod.CREATE_PROVIDER_MISSED_POLLUTION_RATE.getName(),
        (Integer) map.get(TestConstants.CREATE_PROVIDER_OUTPUT_ID_KEY));
    deleteCreatedProviderWithMissingAttributes(
        ProviderTestMethod.CREATE_PROVIDER_MISSED_POLLUTION_RATE.getName());
  }

  /**
   * Creates a Provider without specifying a time.
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   * @throws IOException
   *           When a IOException is thrown.
   * @throws JsonMappingException
   *           When a JsonMappingException is thrown.
   * @throws JsonParseException
   *           When a JsonParserException is thrown.
   */
  @Test(groups = { "providers-createProviderWithMissingAttributes" }, dependsOnMethods = {
      "deleteProvider" }, priority = 3)
  public void createProviderMissedTime()
      throws SlvTestsException, JsonParseException, JsonMappingException, IOException {
    // INIT
    JsonNode parameters = getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()).deepCopy();
    // Remove the time from the request input
    ((ObjectNode) parameters).remove(TestConstants.CREATE_PROVIDER_INPUT_TIME_KEY);

    // CALL
    JsonDiffResult result = retrieveResult(ProvidersMethods.CREATE_PROVIDER.getUrl(), parameters);

    // Verify that the provider is created
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());

    // Extract the id of the created Provider
    Map<String, Object> map = convert(result.getResponse());
    createdProviderIdMap.put(ProviderTestMethod.CREATE_PROVIDER_MISSED_TIME.getName(),
        (Integer) map.get(TestConstants.CREATE_PROVIDER_OUTPUT_ID_KEY));
    deleteCreatedProviderWithMissingAttributes(
        ProviderTestMethod.CREATE_PROVIDER_MISSED_TIME.getName());
  }

  /**
   * Initialize a provider for update and delete operations test.
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  @Test(groups = { "providers-updateProviderWithMissingAttributes" }, dependsOnGroups = {
      "providers-createProviderWithMissingAttributes" }, priority = 3)
  public void initProviderOfUpdateTests() throws SlvTestsException {
    // Create a provider
    String response = call(ProvidersMethods.CREATE_PROVIDER.getUrl(),
        getInputs().get(ProvidersMethods.CREATE_PROVIDER.getUrl()));
    // Extract the id of the created Provider
    Map<String, Object> map = convert(response);
    createdProviderIdMap.put(ProviderTestMethod.INIT_PROVIDER_OF_UPDATE_TESTS.getName(),
        (Integer) map.get(TestConstants.CREATE_PROVIDER_OUTPUT_ID_KEY));
  }

  /**
   * Fails to update a Provider without specifying a new name.
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  @Test(groups = { "providers-updateProviderWithMissingAttributes" }, dependsOnMethods = {
      "initProviderOfUpdateTests" }, priority = 3)
  public void updateProviderMissedNewName() throws SlvTestsException {
    // INIT
    JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl()).deepCopy();
    // Remove the name from the request input
    ((ObjectNode) parameters).remove(TestConstants.UPDATE_PROVIDER_INPUT_NEW_NAME_KEY);
    ((ObjectNode) parameters).put(TestConstants.UPDATE_PROVIDER_INPUT_ID_KEY,
        createdProviderIdMap.get(ProviderTestMethod.INIT_PROVIDER_OF_UPDATE_TESTS.getName()));

    // CALL
    JsonDiffResult result = retrieveResult(ProvidersMethods.UPDATE_PROVIDER.getUrl(), parameters);

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Fails to update a Provider without specifying a new name.
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  @Test(groups = { "providers-updateProviderWithMissingAttributes" }, dependsOnMethods = {
      "initProviderOfUpdateTests" }, priority = 3)
  public void updateProviderMissedProviderId() throws SlvTestsException {
    // INIT
    JsonNode parameters = getInputs().get(ProvidersMethods.UPDATE_PROVIDER.getUrl()).deepCopy();
    // Remove the provider id from the request input
    ((ObjectNode) parameters).remove(TestConstants.UPDATE_PROVIDER_INPUT_ID_KEY);

    // CALL
    String response = call(ProvidersMethods.UPDATE_PROVIDER.getUrl(), parameters);

    // Extract the error code from the response
    Map<String, Object> map = convert(response);
    String errorCode = (String) map.get(TestConstants.RESPONSE_ERROR_CODE_KEY);

    // Verify that the error belongs to the name
    Assert.assertTrue(TestConstants.ITEM_NOT_FOUND_ERROR_CODE.equals(errorCode),
        MessageHelper.getMessage("providers.error.code.not.recognized"));
  }

  /**
   * Initialize a provider for update and delete operations test.
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  @Test(groups = { "providers-updateProviderWithMissingAttributes" }, dependsOnMethods = {
      "updateProviderMissedNewName", "updateProviderMissedProviderId" }, priority = 3)
  public void clearProviderOfUpdateTests() throws SlvTestsException {
    deleteCreatedProviderWithMissingAttributes("initProviderOfUpdateTests");
  }

  /**
   * Deletes a created provider.
   * 
   * @param methodName
   * 
   * @throws SlvTestsException
   *           When a SlvTestsException is thrown.
   */
  public void deleteCreatedProviderWithMissingAttributes(String methodName)
      throws SlvTestsException {
    if (createdProviderIdMap.get(methodName) != null) {
      JsonNode parameters = getInputs().get(ProvidersMethods.DELETE_PROVIDER.getUrl());
      ((ObjectNode) parameters).put(
          TestConstants.DELETE_PROVIDER_INPUT_ID_KEY, createdProviderIdMap.get(methodName));
      call(ProvidersMethods.DELETE_PROVIDER.getUrl(), parameters);
    }
  }

  @Override
  protected String getInputFile() {
    return INPUT_FILE;
  }

  @Override
  protected String getOutputFile() {
    return OUTPUT_FILE;
  }
}