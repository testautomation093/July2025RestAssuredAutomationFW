package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtil;


public class CreateUserTest extends BaseTest {
	
	//RestClient rsPost;
	RestClient rsGet;
	//Properties prop;
	
	@BeforeMethod
	public void getUserSetup() {

		resClient = new RestClient(prop, baseURI);
	}
	
	
//	@DataProvider
//	public Object[][] getUserTestData()
//	{
//		Object obj[][]= {{"Mohan","male","active"},{"Meena","female","active"},{"Raja","male","inactive"}};
//	    return obj;
//		
//	}
	
	@DataProvider
	public Object[][] getUserTestSheetData()
	{

		Object ob[][]=ExcelUtil.getTestDataFromSheet(APIConstants.TEST_SHEET_NAME);
		
		return ob;
		
	}

	@Test(dataProvider ="getUserTestSheetData")
	public void createUserTest(String name, String gender, String status)
	{
        
// 1. Creating a new User:		
		User user=new User(name, StringUtil.generateEmail(),status,gender);		
		//resClient=new RestClient(prop, prop.getProperty("baseUri"));
		
		Integer userId=resClient.postRequest(GOREST_ENDPOINT, "json",user , true,true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
		.extract().path("id");
		
		System.out.println("User id is : "+userId);

// 2. Fetching the created user details:
		
		rsGet=new RestClient(prop, prop.getProperty("baseUri"));
		
		rsGet.getRequest(GOREST_ENDPOINT+userId, true,true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
		.and()
		.body("id", equalTo(userId));		
		
	}
	
	
}
