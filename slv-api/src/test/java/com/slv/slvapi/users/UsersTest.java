package com.slv.slvapi.users;

import com.slv.slvapi.core.AbstractTest;
import com.slv.slvapi.exceptions.SlvTestsException;
import com.slv.slvapi.services.JsonDiffResult;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for users part.
 * 
 * @author cmarchive
 */
public class UsersTest extends AbstractTest {

  /**
   * File name of Users API Inputs.
   */
  private static final String INPUT_FILE = "json/users/input.json";

  /**
   * File name of Users API Outputs.
   */
  private static final String OUTPUT_FILE = "json/users/output.json";

  /**
   * Json format test of API Service "userprofile/createUser".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void createUser() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.CREATE_USER.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/getCurrentUser".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void retrieveCurrentUser() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.GET_CURRENT_USER.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/updateUser".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void updateUser() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_USER.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/getAllUsers".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void getAllUsers() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.GET_ALL_USERS.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/getGeoZoneUsers".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void getUsersGeozone() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.GET_USERS_GEOZONE.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/deleteUser".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void deleteUser() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.DELETE_USER.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/changePassword".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void updatePassword() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_PASSWORD.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/updateUserProperty".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void updateUserProperties() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_USER_PROPERTIES.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service "userprofile/checkPassword".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void verifyPassword() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.VERIFY_PASSWORD.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
  }

  /**
   * Json format test of API Service
   * "publicconfig/sendResetPasswordRequestByMail".
   * 
   * @throws SlvTestsException When a SlvTestsException is thrown.
   */
  @Test
  public void recoverPassword() throws SlvTestsException {
    JsonDiffResult result = retrieveResult(UsersMethods.RECOVER_PASSWORD.getUrl());

    // VERIFY
    Assert.assertTrue(result.isEquals(), result.getErrorMessage());
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