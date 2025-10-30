package com.qa.gorest.base;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {
	
	
	// Service Urls :
	
	public final static String GOREST_ENDPOINT="/public/v2/users/";
	public final static String REQRES_ENDPOINT="/api/users/";
	public final static String AMADEUS_TOKEN_ENDPOINT="v1/security/oauth2/token";
	public final static String AMADEUS_FLIGHTBOOKING_ENDPOINT="v1/shopping/flight-destinations";
    public final static String PRODUCT_ENDPOINT="/products";
    public final static String GOREST_ENDPOINT_XML="/public/v2/users.xml";
	
	public Properties prop;
	public ConfigurationManager cm;
	public RestClient resClient;
	public String baseURI;
	
	@Parameters({"baseURI"})
	@BeforeTest
	public void setUp(String baseUri)
	{
		RestAssured.filters(new AllureRestAssured());
		cm=new ConfigurationManager();
		prop=cm.initProperties();
		this.baseURI=baseUri;
		//resClient=new RestClient(prop, baseUri);
	}
}
