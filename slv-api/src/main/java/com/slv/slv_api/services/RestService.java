package com.slv.slv_api.services;
import com.slv.slv_api.Methods;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class RestService {

	private static final String JSON_SCHEMA = "application/json";

	private static RestService INSTANCE = null;
	private String url;
	private String login;
	private String password;
	
	private RestService(String url, String login, String password) {
		this.url = url;
		this.login = login;
		this.password = password;
	}
	
	private RestService() {
		
	}
	
	public static RestService getInstance(String url, String login, String password) {
		if (INSTANCE == null) {
			INSTANCE = new RestService(url, login, password);
		}
		
		return INSTANCE;
	}
	
	public String get(Methods method) {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(login, password));

		WebResource webResource = client.resource(url + method);

		ClientResponse response = webResource.accept(JSON_SCHEMA).get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : Http error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);
		return output;
	}

}
