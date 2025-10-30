package com.qa.gorest.utils;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JsonPathValidator {

	private String getJsonResponseAsString(Response response) {
		String res = response.getBody().asString();

		return res;
	}

	public <T> T read(Response response, String path) {

		String res=getJsonResponseAsString(response);
		return JsonPath.read(res, path);

	}

	public  <T> List<T> readList(Response response, String path) {

		String res=getJsonResponseAsString(response);
		return JsonPath.read(res, path);

	}

}
