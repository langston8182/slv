package com.slv.slv_api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Test class to implement a simple rest client
 * 
 * @author cmarchive
 */
public class JerseyClient {

	private static final String URL = "http://5.196.91.118:8080/celad/api/";
	
	public static void main(String[] args) {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("celad", "Celad20!6"));
		
		WebResource webResource = client.resource(URL + Methods.RETRIEVE_CURRENT_PROFILE);
		
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : Http error code : " + response.getStatus());
		}
		
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
}
