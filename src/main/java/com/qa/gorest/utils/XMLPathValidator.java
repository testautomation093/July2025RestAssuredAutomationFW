package com.qa.gorest.utils;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XMLPathValidator {

	private String getXMLResponseAsString(Response response) {
		String res = response.getBody().asString();

		return res;
	}

	public <T> T read(Response response, String path) {

		String res = getXMLResponseAsString(response);

		XmlPath xmlPath = new XmlPath(res);
		return xmlPath.get(path);

	}

	public <T> List<T> readList(Response response, String path) {

		String res = getXMLResponseAsString(response);

		XmlPath xmlPath = new XmlPath(res);
		return xmlPath.getList(path);

	}

}
