package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;

public class GetProductAPITest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetup() {

		resClient = new RestClient(prop, baseURI);
	}
	
	@Test
	public void getAllProductInfo()
	{
	   
		Response response=resClient.getRequest(PRODUCT_ENDPOINT, false, true);
//		.then().log().all()
//		.assertThat()
//		.statusCode(APIHttpStatus.OK_200.getCode());
		
	   int statusCode=response.statusCode();
	   Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());
		
		JsonPathValidator js=new JsonPathValidator();
		List<Float> rateList=js.readList(response, "$..[?(@.rate>3)].rate");
		
		System.out.println("Rate List is : "+rateList);
		
		Assert.assertTrue(rateList.contains(3.9));
		
		
		List<String> titleList=js.readList(response,"$..title");
		System.out.println("Title and Category :" +titleList);
		
		Assert.assertTrue(titleList.contains("Mens Cotton Jacket"));


}

}
