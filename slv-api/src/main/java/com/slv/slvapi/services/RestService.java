package com.slv.slvapi.services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Provides an implementation of RestService. This class implements calls to a
 * Rest API using GET Http verb.
 * 
 * @author cmarchive
 */
public class RestService {

  /**
   * Json schema.
   */
  private static final String JSON_SCHEMA = "application/json";

  /**
   * Unique instance of RestService.
   */
  private static RestService INSTANCE = null;

  /**
   * The URL to connect to the Rest API.
   */
  private String url;

  /**
   * Login to connect to the Rest API.
   */
  private String login;

  /**
   * Password to connect to the Rest API.
   */
  private String password;

  /**
   * Default constructor. Not accessible out of this class. Provide only one
   * instance of RestService as Singleton.
   * 
   * @param url
   *          The url to connect to Rest API.
   * @param login
   *          The login to connect to Rest API.
   * @param password
   *          The password to connect to Rerst API.
   */
  private RestService(String url, String login, String password) {
    this.url = url;
    this.login = login;
    this.password = password;
  }

  /**
   * Private default constructor to avoid creating several RestService objects.
   */
  private RestService() {

  }

  /**
   * Get the unique instance of RestService.
   * 
   * @param url
   *          The url to connect to Rest API.
   * @param login
   *          The login to connect to Rest API.
   * @param password
   *          The password to connect to Rerst API.
   * 
   * @return The unique instance of RestService.
   */
  public static RestService getInstance(String url, String login, String password) {
    if (INSTANCE == null) {
      INSTANCE = new RestService(url, login, password);
    }

    return INSTANCE;
  }

  /**
   * Call to the Rest API with GET http verb.
   * 
   * @param methoUrl
   *          The uri.
   * 
   * @return The response of the Rest API.
   */
  public String get(String methoUrl) {
    return get(methoUrl, null);
  }

  /**
   * Call to the Rest API with GET http verb with parameters.
   * 
   * @param methoUrl
   *          The uri.
   * @param params
   *          The parameters of the request.
   * 
   * @return The response of the Rest API.
   */
  public String get(String methoUrl, Map<String, Object> params) {
    Client client = Client.create();
    client.addFilter(new HTTPBasicAuthFilter(login, password));

    WebResource webResource = client.resource(url + methoUrl);

    if (params != null) {
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
   * 
   * @param params
   *          the {@link Map}
   * @return the new created map.
   */
  @SuppressWarnings("unchecked")
  private MultivaluedMap<String, String> convert(Map<String, Object> params) {
    MultivaluedMap<String, String> map = null;
    if (params != null && !params.isEmpty()) {
      map = new MultivaluedMapImpl();
      for (Entry<String, Object> param : params.entrySet()) {
        if (param.getValue() instanceof List<?>) {
          for (Object paramIter : (List<Object>) param.getValue()) {
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
