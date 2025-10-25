package jwayJsonPathPractice;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class fakeStoreApiTest {

	@Test
	public void getAllUsers_withJsonPathQuery() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response res = given().when().get("/products");

		Assert.assertEquals(res.statusCode(), 200);

		String body = res.getBody().asString();

		ReadContext cxt = JsonPath.parse(body);
		ArrayList<Number> prices = cxt.read("$.[?(@.price>50)].price");

		System.out.println(prices);
		System.out.println("Size of prices is: " + prices.size());

		System.out.println("-----------------------------------------------");

		// contains - /.*Value.*/i

		ArrayList<Integer> ids = cxt.read("$.[?(@.title=~/.*Backpack.*/i)].id");
		System.out.println(ids);
		
		System.out.println("-----------------------------------------------");
		
		//Starts with - /^Value.*/i
		ArrayList<Integer> startsWithids = cxt.read("$.[?(@.title=~/^Fjallraven.*/i)].price");
		System.out.println(startsWithids);
		
		System.out.println("-----------------------------------------------");
		
		//Ends With - /.*Value$/i
		ArrayList<Integer> endsWithids = cxt.read("$.[?(@.title=~/.*Laptops$/i)].id");
		System.out.println(endsWithids);

	}
	
	@Test
	public void getUsers_withComplexJsonPathQueries() {
		RestAssured.baseURI = "https://fakestoreapi.com";

		Response res = given().when().get("/products");

		Assert.assertEquals(res.statusCode(), 200);

		String body = res.getBody().asString();

		ReadContext cxt = JsonPath.parse(body);
		
		//fetch all id and title
		List<Map<String, Object>> twoAttrList = cxt.read("$[*].[\"id\",\"title\"]");
		System.out.println(twoAttrList);
		
		
		System.out.println("-----------------------------------------------");
		
		//Multiple conditions
		//fetch the ids which havinh categoty as jewellary and price<10
		List<Integer> twoConditionIdsList = cxt.read("$[?((@.category==='jewelery') && (@.price<10))].id");
		System.out.println(twoConditionIdsList);
		
	}

}
