package com.slv.slv_api.users;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.slv.slv_api.core.AbstractTest;
import com.slv.slv_api.exceptions.SLVTestsException;
import com.slv.slv_api.services.JsonDiffResult;

/**
 * @author cmarchive
 * 
 *         Test cases for users part
 */
public class UsersTest extends AbstractTest {

	/**
	 * File name of UserProfil API Inputs
	 */
	private final static String INPUT_FILE = "json/users/input.json";

	/**
	 * File name of UserProfil API Outputs
	 */
	private final static String OUTPUT_FILE = "json/users/output.json";

	@Test
	public void createUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.CREATE_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}

	@Test
	public void retrieveCurrentUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.GET_CURRENT_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void updateUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void getAllUsers() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.GET_ALL_USERS.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void getUsersGeozone() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.GET_USERS_GEOZONE.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void deleteUser() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.DELETE_USER.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void updatePassword() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_PASSWORD.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void updateUserProperties() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.UPDATE_USER_PROPERTIES.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void recoverPassword() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.RECOVER_PASSWORD.getUrl());

		// VERIFY
		Assert.assertTrue(result.isEquals(), result.getErrorMessage());
	}
	
	@Test
	public void verifyPassword() throws SLVTestsException {
		JsonDiffResult result = retrieveResult(UsersMethods.VERIFY_PASSWORD.getUrl());

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

	public static void main(String[] args) {
		UsersTest test = new UsersTest();
		try {
			test.beforeTest("http://5.196.91.118:8080/celad/api/", "celad", "Celad20!6");
			test.retrieveCurrentUser();
		} catch (SLVTestsException e) {
			e.printStackTrace();
		}
	}

}
