package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtil;

public class APISchemaValidationTest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetup() {

		resClient = new RestClient(prop, baseURI);
	}
	
	
	@Test
	public void checkSchemValidationSingleUser()
	{
        
		User user=new User("Piku", StringUtil.generateEmail(),"active","female");	
		
		resClient.postRequest(GOREST_ENDPOINT, "json",user , true,true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
		.and()
		.assertThat().body(matchesJsonSchemaInClasspath("createUserSchema.json"));

}
}
