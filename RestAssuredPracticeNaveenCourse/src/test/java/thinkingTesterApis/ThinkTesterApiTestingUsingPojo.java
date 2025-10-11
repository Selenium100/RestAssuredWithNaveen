package thinkingTesterApis;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ThinkTesterApiTestingUsingPojo {

	private static String AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODkzNzkyN2UxMDg2NTAwMTVkZThlNjEiLCJpYXQiOjE3NTk4NTE3NjJ9.P-2G-AZDXf7p_qBrjs6J0j5IvszVvevkRw27XB7j-EU";

	public String randomEmail() {
		return "automationtesting" + System.currentTimeMillis() + "@maillinator.com";
	}

	@Test
public void createUserUsingLombokBuider() {

		ThinkTesterPojoWithLombok userObj = new ThinkTesterPojoWithLombok.ThinkTesterPojoWithLombokBuilder()
				.firstName("Amit").lastName("Parchari").birthdate("1970-01-01").email(randomEmail()).phone("7303709376")
				.street1("Kalinga Nagar").street2("Bhubaneswar").city("Bhubaeswar").stateProvince("Orissa")
				.postalCode("751005").country("India").build();

		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		String id = given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).body(userObj)
				.when().log().all().post("/contacts").then().assertThat().statusCode(201).extract().path("_id");

		System.out.println("Created User id is: " + id);
	}

	@Test
	public void createUser() {

		System.out.println("---------------------- 1.POST CALL --------------------------------------");

		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

		String id = given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON)
				.body(new ThinkTesterPojoWithLombok("Nitya", "Mohanty", "1970-01-01", randomEmail(), "7303709376",
						"Kalingnagar", "Bhubaneswar", "Cuttack", "Orissa", "751003", "India"))
				.when().post("/contacts").then().assertThat().statusCode(201).extract().path("_id");

		System.out.println("The USER id created is : " + id);

		System.out.println("---------------------- 2.GET CALL --------------------------------------");

		given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2ODkzNzkyN2UxMDg2NTAwMTVkZThlNjEiLCJpYXQiOjE3NTk2NzgzODh9.yZgrEdC-lYw7Vyo8O7VDgCYPYZZFUERzl0doYMl0v2s")
				.contentType(ContentType.JSON).when().log().all().get("/contacts/" + id).then().assertThat()
				.statusCode(200);

	}

	@Test
	public void updateUser() {

		System.out.println("---------------------- 1.POST CALL --------------------------------------");

		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		ThinkTesterPojoWithLombok userObj = new ThinkTesterPojoWithLombok("Nitya", "Ranjan", "1970-01-01",
				randomEmail(), "7303709376", "Kalingnagar", "Bhubaneswar", "Cuttack", "Orissa", "751003", "India");

		String id = given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).body(userObj)
				.when().post("/contacts").then().assertThat().statusCode(201).extract().path("_id");

		System.out.println("The USER id created is : " + id);

		System.out.println("---------------------- 2.GET CALL --------------------------------------");

		given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).when().log().all()
				.get("/contacts/" + id).then().assertThat().statusCode(200)
				.body("firstName", equalTo(userObj.getFirstName())).body("lastName", equalTo(userObj.getLastName()));
		;

		System.out.println("---------------------- 3. PUT CALL --------------------------------------");

		userObj.setFirstName("Anubhav");
		userObj.setLastName("Mohanty");

		given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).body(userObj).when().log()
				.all().put("/contacts/" + id).then().log().all().statusCode(200);

		System.out.println("---------------------- 2.GET CALL --------------------------------------");

		given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).when().log().all()
				.get("/contacts/" + id).then().assertThat().statusCode(200)
				.body("firstName", equalTo(userObj.getFirstName())).body("lastName", equalTo(userObj.getLastName()));

	}

	@Test
	public void deleteUser() {

		System.out.println("---------------------- 1.POST CALL --------------------------------------");

		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		ThinkTesterPojoWithLombok userObj = new ThinkTesterPojoWithLombok("Nitya", "Ranjan", "1970-01-01",
				randomEmail(), "7303709376", "Kalingnagar", "Bhubaneswar", "Cuttack", "Orissa", "751003", "India");

		String id = given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).body(userObj)
				.when().post("/contacts").then().assertThat().statusCode(201).extract().path("_id");

		System.out.println("The USER id created is : " + id);

		System.out.println("---------------------- 2.GET CALL --------------------------------------");

		given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).when().log().all()
				.get("/contacts/" + id).then().assertThat().statusCode(200)
				.body("firstName", equalTo(userObj.getFirstName())).body("lastName", equalTo(userObj.getLastName()));
		;

		System.out.println("---------------------- 3.DELETE CALL --------------------------------------");

		Response res = given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).when()
				.log().all().delete("/contacts/" + id);

		String resBody = res.getBody().asString();
		System.out.println("Response Body is: " + resBody);
		Assert.assertEquals(resBody, "Contact deleted");

		res.then().assertThat().statusCode(200);

		System.out.println("---------------------- 2.GET CALL --------------------------------------");

		given().log().all().header("Authorization", AUTH_TOKEN).contentType(ContentType.JSON).when().log().all()
				.get("/contacts/" + id).then().assertThat().statusCode(404);

	}

}
