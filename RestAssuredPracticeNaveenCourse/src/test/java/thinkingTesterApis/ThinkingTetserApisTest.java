package thinkingTesterApis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ThinkingTetserApisTest {
	
	public String getRandomEmailIds() {
		return "apiautomation"+System.currentTimeMillis()+"@malinator.com";
	}

	@Test
	public void getAllUsers() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		RequestSpecification request = RestAssured.given();
		request.header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODkzNzkyN2UxMDg2NTAwMTVkZThlNjEiLCJpYXQiOjE3NTkwMzg0NTR9.KCtxCQzfFPkYWALSDJahpm1TDR_xrv-nzLki5z7hsdo");

		Response res = request.get("/contacts");

		int statusCode = res.statusCode();
		String statusLine = res.statusLine();

		System.out.println("Status Code is : " + statusCode);
		System.out.println("Status Line is : " + statusLine);

		Assert.assertEquals(statusCode, 200);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

		// res.prettyPrint();

		List<Header> headerList = res.headers().asList();
		System.out.println("The total number of headers are: " + headerList.size());

		for (Header e : headerList) {
			String headerName = e.getName();
			String headerValue = e.getValue();

			System.out.println(headerName + " : " + headerValue);
		}

	}

	@Test
	public void createUser() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		given().log().all().contentType(ContentType.JSON).header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODkzNzkyN2UxMDg2NTAwMTVkZThlNjEiLCJpYXQiOjE3NTkwNDI2MTl9.vGuyEzPx6Oj3yvmjX96RRSi_garGI4K8hEcbKY6hezw")
				.body(new File("./src/test/resources/jsons/user.json")).when().log().all().post("/contacts").then()
				.log().all().assertThat().statusCode(201);
	}

	@Test
	// This approach is very important
	public void createUserWithRandomEmailFromFile() {
		String randomEmailId = getRandomEmailIds();
		String jsonContent = "";
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		
		try {
			 jsonContent = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String replacedJsonContent = jsonContent.replace("{{email}}", randomEmailId);

		String id = given().log().all().contentType(ContentType.JSON).header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODkzNzkyN2UxMDg2NTAwMTVkZThlNjEiLCJpYXQiOjE3NTkwNDI2MTl9.vGuyEzPx6Oj3yvmjX96RRSi_garGI4K8hEcbKY6hezw")
				.body(replacedJsonContent).when().log().all().post("/contacts").then()
				.log().all().assertThat().statusCode(201).extract().path("_id");
		
		System.out.println("New User Id is :" + id);
	}

}
