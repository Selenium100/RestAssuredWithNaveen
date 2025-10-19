package GoRestApis;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class GoRestApiTesting {
	
	@Test
	public void getUser() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		Response res = given().header("Authorization", "Bearer 120c4667fe92fd1b40733ae5aa40f9435c0653d197693bb2e885ac1748ea8bf7")
		.when().get("/public/v2/users/8145422");
		
		res.prettyPrint();
		
		System.out.println(res.statusCode());
		System.out.println(res.statusLine());
		
		Assert.assertEquals(res.statusCode(), 200);
		
		JsonPath js =  res.jsonPath();
		System.out.println(js.getInt("id")); // changed in tag based
	}

}
