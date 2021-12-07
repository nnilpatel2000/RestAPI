package com.employeeapi.testCases;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.employeeapi.utilities.XLUtility;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DataDrivenTesting {

	@Test(dataProvider="LoginData")
	public void addNewData(String ename, String ejob) {

		// Specify base URI
		RestAssured.baseURI = "https://reqres.in/api";

		// Request object
		RequestSpecification httpRequest = RestAssured.given();

		// Request paylaod sending along with post request
		JSONObject requestParams = new JSONObject();

		requestParams.put("name", ename);
		requestParams.put("job", ejob);

		httpRequest.header("Content-Type", "application/json");

		httpRequest.body(requestParams.toJSONString()); // attach above data to the request

		// Response object
		Response response = httpRequest.request(Method.POST, "/users");

		// print response in console window

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is:" + responseBody);
		
		Assert.assertEquals(responseBody.contains(ename), true);
		Assert.assertEquals(responseBody.contains(ejob), true);
		
		// status code validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);
		Assert.assertEquals(statusCode, 201);

	}

	@DataProvider(name="LoginData")
	String [][] getEmpData() throws IOException
	{
	
		
		//get the data from excel
		String path="C:\\Users\\Nnilp\\eclipse-workspace\\RestassuredAPITesting_Employee_Project\\src\\test\\java\\com\\employeeapi\\testData\\ApiDataDriven.xlsx";
		XLUtility xlutil=new XLUtility(path);
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);	
				
		String loginData[][]=new String[totalrows][totalcols];
			
		
		for(int i=1;i<=totalrows;i++) //1
		{
			for(int j=0;j<totalcols;j++) //0
			{
				loginData[i-1][j]=xlutil.getCellData("Sheet1", i, j);
			}
				
		}
	
//		
//		String loginData[][]= {
//				{"admin@yourstore.com","admin"},
//				{"admin@yourstore.com","adm"},
//				{"adm@yourstore.com","admin"},
//				{"adm@yourstore.com","adm"}
//			};
		return(loginData);
		
	}
	
	
}
