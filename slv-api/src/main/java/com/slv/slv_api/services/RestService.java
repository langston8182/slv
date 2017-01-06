package com.slv.slv_api.services;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

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

	public String get(String methoUrl) {
		return get(methoUrl, null);
	}
	
	public String get(String methoUrl, Map<String, Object> params) {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(login, password));

		WebResource webResource = client.resource(url + methoUrl);
		
		if(params != null) {
			MultivaluedMap<String, String> map = convert(params);
			webResource = webResource.queryParams(map);
		}

		ClientResponse response = webResource.accept(JSON_SCHEMA).get(ClientResponse.class);
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : Http error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);
		return output;
	}

	/**
	 * Convert from {@link Map} to {@link MultivaluedMap}
	 * @param params the {@link Map}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MultivaluedMap<String, String> convert(Map<String, Object> params) {
		MultivaluedMap<String, String> map = null;
		if(params != null && !params.isEmpty()) {
			map = new MultivaluedMapImpl();
			for (Entry<String, Object> param : params.entrySet()) {
				if(param.getValue() instanceof List<?>) {
					for (Object paramIter : (List<Object>)param.getValue()) {
						map.add(param.getKey(), paramIter.toString());
					}
				} else {
					map.add(param.getKey(), param.getValue().toString());
				}
			}
		}
		return map;
	}

}
