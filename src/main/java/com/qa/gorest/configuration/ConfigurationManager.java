package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameworkException;

public class ConfigurationManager {

	private Properties prop;
	private FileInputStream fp;

	public Properties initProperties() {
		prop = new Properties();

		String envName = System.getProperty("env");

		System.out.println("Environment Name is : " + envName);

		try {

			if (envName == null) {
				System.out.println("Since the envName is null Hence Runningt the test suite on default env.");
				fp = new FileInputStream("src/test/resources/config/config.properties");

			}
			else {

				switch (envName.toLowerCase()) {
				case "uat":

					fp = new FileInputStream("src/test/resources/config/config_uat.properties");

					break;

				case "stage":
					fp = new FileInputStream("src/test/resources/config/config_stage.properties");
					break;

				case "prod":
					fp = new FileInputStream("src/test/resources/config/config_prod.properties");
					break;

				default:
					throw new APIFrameworkException("INVALID ENVIRONEMENT NAME");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		try {
			prop.load(fp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}

}
