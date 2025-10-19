package deserializationWithGetCall;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ThinkTesterApiTesting {

	public String randomEmail() {
		return "automation" + System.currentTimeMillis() + "@maillinator.com";
	}

	@Test
	public void createUser() {
		
		System.out.println("---------------------------- CREATE USER ------------------------------------------");

		ThinkTesterPojoWithLombok user = new ThinkTesterPojoWithLombok.ThinkTesterPojoWithLombokBuilder()._id(null)
				.firstName("Nitya").lastName("Ranjan").birthdate("1970-01-01").email(randomEmail()).phone("8005555555")
				.street1("1 Main St.").street2("Apartment A").city("bhubaneswar").stateProvince("orissa")
				.postalCode("751005").country("india").owner(null).__v(null).build(); 

		String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODkzNzkyN2UxMDg2NTAwMTVkZThlNjEiLCJpYXQiOjE3NjA4ODYzNDl9.NoNkN2rNttRyKKJHosFEWYlm8fF80QCQ-BNRNlasyqI";

		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		String id = given().log().all().header("Authorization", token).contentType(ContentType.JSON).body(user).when().log().all().post("/contacts").then().log().all()
				.assertThat().statusCode(201).extract().path("_id");
		System.out.println("Created new user id is : " + id);
		
		System.out.println("---------------------------- GET USER ------------------------------------------");
		
	Response res =	 given().log().all().header("Authorization", token).contentType(ContentType.JSON).when().log().all().get("/contacts/"+id);
	
	//Deserialization:  from json response to Java object using Jackson ObjectMapper class
	ObjectMapper mapper = new ObjectMapper();
	try {
		ThinkTesterPojoWithLombok userObj =  mapper.readValue(res.getBody().asString(), ThinkTesterPojoWithLombok.class);
		System.out.println(userObj);
		Assert.assertEquals(userObj.getFirstName(), user.getFirstName());
		Assert.assertEquals(userObj.getLastName(), user.getLastName());
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
	
		
	}

}
