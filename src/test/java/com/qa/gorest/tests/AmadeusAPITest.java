package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class AmadeusAPITest extends BaseTest {

	private String accessToken;
	
	@Parameters({"grantType","clientId","clientSecret"})
	@BeforeMethod
	public void getAccessTokenSetup(String grantType, String clientId, String clientSecret )
	{
		resClient=new RestClient(prop, baseURI);
		accessToken=resClient.getAccessTokenOauth(AMADEUS_TOKEN_ENDPOINT, grantType, clientId, clientSecret);
		
	}
	
	@Test
	public void getFlightDetails()
	{
		RestClient restClient2=new RestClient(prop, baseURI);
		
		Map<String, String> queryMap=new HashMap<String, String>();
		queryMap.put("origin", "PAR");
		queryMap.put("maxPrice","200");
		
		Map<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("Authorization", "Bearer "+accessToken);
		
		
		restClient2.getRequest(AMADEUS_FLIGHTBOOKING_ENDPOINT, headerMap, queryMap, false, true)
					.then().log().all()
					.assertThat()
					.statusCode(APIHttpStatus.OK_200.getCode())
					.and()
					.assertThat()
					.body("data[0].price.total", equalTo("108.68"));
	
	}
	
	
	
	
}
