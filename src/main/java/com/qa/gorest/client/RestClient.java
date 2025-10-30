package com.qa.gorest.client;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.frameworkexception.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "5eacf1500a6f560ce889fce62cb41c691cb38ed175df154db2310d1663f856e1";

	private static RequestSpecBuilder specBuilder;
    private Properties prop;
    private String baseURI;
    
    private boolean isAuthorizationAdded=false;
    
	public RestClient(Properties prop, String baseURI)
	{
		specBuilder=new RequestSpecBuilder();
		
		this.prop=prop;
		this.baseURI=baseURI;
	}
	
// ****************************Common Re-usuable Methods****************	
	public void addAuthorization() {
		if(!isAuthorizationAdded)
		{
		specBuilder.addHeader("Authorization", "Bearer" + " " + prop.getProperty("tokenId"));
		isAuthorizationAdded=true;
		}
	}

	private void setRequestContentType(String contentType) {
		System.out.println("Content Type is: " + contentType);

		switch (contentType.toLowerCase().trim()) {
		case "json":

			specBuilder.setContentType(ContentType.JSON);

			break;
		case "text":

			specBuilder.setContentType(ContentType.TEXT);

			break;
		case "xml":

			specBuilder.setContentType(ContentType.XML);

			break;
		case "html":

			specBuilder.setContentType(ContentType.HTML);

			break;
		case "multipart":

			specBuilder.setContentType(ContentType.MULTIPART);

			break;

		default:

			System.out.println("Content Type is Not Supported");
			throw new APIFrameworkException("UNSUPPORTED CONTENT TYPE");

		}

	}

// ******************* Request Specification Methods***************************	
	
	public RequestSpecification createRequestSpec(boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}
		return specBuilder.build();

	}

	/*
	 * Overloading the method with Headers Map
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap,boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}

	/*
	 * Overloading the method with Headers Map and with Query Params
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}

		if (queryMap != null) {
			specBuilder.addQueryParams(queryMap);
		}
		return specBuilder.build();
	}

	/*
	 * Overloading the method for the post request expecting some Json Data, or POJO
	 * class along with ContentType
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}
		setRequestContentType(contentType);
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	/*
	 * Overloading the method for the post request expecting some Json Data, or POJO
	 * class along with ContentType and some Headers also
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType,
			Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorization();
		}
		setRequestContentType(contentType);
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}

		return specBuilder.build();
	}

// *******************HTTP GET Methods to be called via Test Cases ********************
	
	/*
	 *  Only Plain get call with service Url thats it
	 */
	public Response getRequest(String serviceUrl,boolean includeAuth, boolean log)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()
		           .spec(createRequestSpec(includeAuth))
		           .when()
		           .get(serviceUrl);
		return res;
		}
		else
		{
			Response res=RestAssured.given()
			           .spec(createRequestSpec(includeAuth))
			           .when()
			           .get(serviceUrl);
			
			return res;
			
		}
	}
	
	/*
	 *  Over loading the Get Call with service Url and some headers if I have thats it
	 */
	public Response getRequest(String serviceUrl,Map<String, String> headersMap, boolean includeAuth,boolean log)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()
		           .spec(createRequestSpec(headersMap,includeAuth))
		           .when()
		           .get(serviceUrl);
		return res;
		}
		else
		{
			Response res=RestAssured.given()
			           .spec(createRequestSpec(headersMap,includeAuth))
			           .when()
			           .get(serviceUrl);
			
			return res;
			
		}
	}
	
	/*
	 *  Over loading the Get Call where I have serviceUrl, headers , query Params and logs
	 */
	public Response getRequest(String serviceUrl,Map<String, String> headersMap,Map<String, String> queryParams,boolean includeAuth, boolean log)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()
		           .spec(createRequestSpec(headersMap,queryParams,includeAuth))
		           .when()
		           .get(serviceUrl);
		return res;
		}
		else
		{
			Response res=RestAssured.given()
			           .spec(createRequestSpec(headersMap,queryParams,includeAuth))
			           .when()
			           .get(serviceUrl);
			
			return res;
			
		}
	}
	
// *******************HTTP POST Methods to be called via Test Cases ********************
	
	/*
	 * Post Call with Service Url, Content Type, Request Body and Logs 
	 */
	public Response postRequest(String serviceUrl, String contentType,Object requestBody,boolean includeAuth, boolean log)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()
		           .spec(createRequestSpec(requestBody,contentType,includeAuth))
		           .when()
		           .post(serviceUrl);
		return res;
		}
		else
		{
			Response res=RestAssured.given()
			           .spec(createRequestSpec(requestBody,contentType,includeAuth))
			           .when()
			           .post(serviceUrl);
			
			return res;
			
		}
		
	}
	/*
	 * Post Call with Service Url, Content Type, Request Body, Headers and Logs 
	 */
	public Response postRequest(String serviceUrl, String contentType,Object requestBody,Map<String, String> headersMap,boolean includeAuth, boolean log)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()
		           .spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
		           .when()
		           .post(serviceUrl);
		return res;
		}
		else
		{
			Response res=RestAssured.given()
			           .spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
			           .when()
			           .post(serviceUrl);
			
			return res;
			
		}
		
	}

// *******************HTTP PUT Methods to be called via Test Cases ********************
	
	
	/*
	 * PUT Call with Service Url, Content Type, Request Body and Logs 
	 */
	public Response putRequest(String serviceUrl, String contentType,Object requestBody,boolean includeAuth, boolean log)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()
		           .spec(createRequestSpec(requestBody,contentType,includeAuth))
		           .when()
		           .put(serviceUrl);
		return res;
		}
		else
		{
			Response res=RestAssured.given()
			           .spec(createRequestSpec(requestBody,contentType,includeAuth))
			           .when()
			           .put(serviceUrl);
			
			return res;
			
		}
		
	}
	/*
	 * PUT Call with Service Url, Content Type, Request Body, Headers and Logs 
	 */
	public Response putRequest(String serviceUrl, String contentType,Object requestBody,Map<String, String> headersMap,boolean includeAuth, boolean log)
	{
		if(log)
		{
		Response res=RestAssured.given().log().all()
		           .spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
		           .when()
		           .put(serviceUrl);
		return res;
		}
		else
		{
			Response res=RestAssured.given()
			           .spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
			           .when()
			           .put(serviceUrl);
			
			return res;
			
		}
		
	}
	
// *******************HTTP PATCH Methods to be called via Test Cases ********************
	
	
		/*
		 * PATCH Call with Service Url, Content Type, Request Body and Logs 
		 */
		public Response patchRequest(String serviceUrl, String contentType,Object requestBody,boolean includeAuth, boolean log)
		{
			if(log)
			{
			Response res=RestAssured.given().log().all()
			           .spec(createRequestSpec(requestBody,contentType,includeAuth))
			           .when()
			           .patch(serviceUrl);
			return res;
			}
			else
			{
				Response res=RestAssured.given()
				           .spec(createRequestSpec(requestBody,contentType,includeAuth))
				           .when()
				           .patch(serviceUrl);
				
				return res;
				
			}
			
		}
		/*
		 * PATCH Call with Service Url, Content Type, Request Body, Headers and Logs 
		 */
		public Response patchRequest(String serviceUrl, String contentType,Object requestBody,Map<String, String> headersMap,boolean includeAuth, boolean log)
		{
			if(log)
			{
			Response res=RestAssured.given().log().all()
			           .spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
			           .when()
			           .patch(serviceUrl);
			return res;
			}
			else
			{
				Response res=RestAssured.given()
				           .spec(createRequestSpec(requestBody,contentType,headersMap,includeAuth))
				           .when()
				           .patch(serviceUrl);
				
				return res;
				
			}
			
		}	
		
// *******************HTTP DELETE Methods to be called via Test Cases ********************
		public Response deleteRequest(String serviceUrl,boolean includeAuth,boolean log)
		{
			if(log)
			{
			Response res=RestAssured.given().log().all()
			           .spec(createRequestSpec(includeAuth))
			           .when()
			           .delete(serviceUrl);
			return res;
			}
			else
			{
				Response res=RestAssured.given()
				           .spec(createRequestSpec(includeAuth))
				           .when()
				           .delete(serviceUrl);
				
				return res;
			}
			
		}
		
// Method for OAuth2.0 
		
		public String getAccessTokenOauth(String serviceUrl, String grantType, String clientId, String clientSecret)
		{
			
			RestAssured.baseURI="https://test.api.amadeus.com/";
		
			// 1. Post Request for Fetching the Token
			String token=given().log().all()
			.contentType(ContentType.URLENC)
			.formParam("grant_type", grantType)
			.formParam("client_id", clientId)
			.formParam("client_secret", clientSecret)
			.when()
			.post(serviceUrl)
			.then()
			.assertThat()
			.statusCode(APIHttpStatus.OK_200.getCode())
			.extract().body().path("access_token");
			
			System.out.println("OAuth 2.0 Token is : "+token);
            return token;
			
}
}