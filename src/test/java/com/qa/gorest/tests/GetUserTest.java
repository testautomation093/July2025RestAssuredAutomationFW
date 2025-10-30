package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.XMLPathValidator;

import io.restassured.response.Response;

public class GetUserTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup() {

		resClient = new RestClient(prop, baseURI);
	}

	
	  @Test(priority=3, enabled=false) 
	  public void getAllUsersTest() {
	  resClient.getRequest(GOREST_ENDPOINT, true, true)
	  .then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	  
	  } 
	  // Commenting these two methods because we will solve the problem of double authentication later on
	  
	  @Test(priority=2) public void getSingleUserTest() {
	  resClient.getRequest(GOREST_ENDPOINT+"8190370", true, true)
	  .then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode())
	  .and().body("id", equalTo(8190370));
	  
	  }
	  
	  @Test(priority=1) public void getUserWithQueryParamsTest() {
	  
	  Map<String, String> map = new HashMap<String, String>(); map.put("name",
	  "Nishant"); map.put("status", "active");
	  
	  resClient.getRequest(GOREST_ENDPOINT, null, map, true, true)
	  .then().log().all() .assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	  
	  }
	  
	 
	@Test(priority = 4)
	public void getAllUsersXMLTest() {
		Response res = resClient.getRequest(GOREST_ENDPOINT_XML, true, true);

		int statusCode = res.statusCode();
		Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());

		XMLPathValidator xml = new XMLPathValidator();
		List<String> listNames = xml.readList(res, "objects.object.name");

		System.out.println("Print all Names:" + listNames);

		Assert.assertTrue(listNames.contains("Lai Shukla"));

	}

}
