package com.slv.slv_api.users;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;

/**
 * Test cases for users part
 * 
 * @author cmarchive
 */
public class UsersTest extends AbstractTest {

	/**
	 * File name of Users API Inputs
	 */
	private final static String INPUT_FILE = "json/users/input.json";

	/**
	 * File name of Users API Outputs
	 */
	private final static String OUTPUT_FILE = "json/users/output.json";


	/**
	 * Json format test of API Service "userprofile/createUser"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void createUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.CREATE_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/getCurrentUser"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void retrieveCurrentUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.GET_CURRENT_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/updateUser"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void updateUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/getAllUsers"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void getAllUsers() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.GET_ALL_USERS.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/getGeoZoneUsers"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void getUsersGeozone() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.GET_USERS_GEOZONE.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/deleteUser"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void deleteUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.DELETE_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/changePassword"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void updatePassword() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_PASSWORD.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/updateUserProperty"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void updateUserProperties() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_USER_PROPERTIES.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "userprofile/checkPassword"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void verifyPassword() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.VERIFY_PASSWORD.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	/**
	 * Json format test of API Service "publicconfig/sendResetPasswordRequestByMail"
	 * 
	 * @throws SLVTestsException
	 */
	@Test
	public void recoverPassword() throws SLVTestsException {
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