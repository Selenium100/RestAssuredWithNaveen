package petApiUsingLombok;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import petApiUsingLombok.PetLombok.Category;
import petApiUsingLombok.PetLombok.Tags;
import static io.restassured.RestAssured.*;

public class PetApis {

	@Test
	public void CreatePet() {

		PetLombok.Tags tag1 = new PetLombok.Tags(1, "doggie");
		PetLombok.Tags tag2 = new PetLombok.Tags(1, "mogggie");
		List<Tags> tagList = new ArrayList<>();
		tagList.add(tag1);
		tagList.add(tag2);

		List<String> photoUrls = new ArrayList<>();
		photoUrls.add("https://amazon.com");
		photoUrls.add("https://flipkart.com");

		PetLombok.Category category = new PetLombok.Category(0, "tommy");

		PetLombok petLombok = new PetLombok.PetLombokBuilder().id(0).category(category).name("Nitya")
				.photoUrls(photoUrls).tags(tagList).status("Active").build();

		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		Long id = given().log().all().contentType(ContentType.JSON).body(petLombok).when().post("/pet").then()
				.assertThat().statusCode(200).extract().path("id");

		System.out.println("Created id is : " + id); // good

	}
	
	@Test
	public void createPetWithMoreAssertions() {
		PetLombok.Tags tag1 = new PetLombok.Tags(0, "doggie");
		PetLombok.Tags tag2 = new PetLombok.Tags(0, "mogggie");
		List<Tags> tagList = new ArrayList<>();
		tagList.add(tag1);
		tagList.add(tag2);

		List<String> photoUrls = new ArrayList<>();
		photoUrls.add("https://amazon.com");
		photoUrls.add("https://flipkart.com");

		PetLombok.Category category = new PetLombok.Category(1, "tommy");

		PetLombok petLombok = new PetLombok.PetLombokBuilder().id(1).category(category).name("Nitya")
				.photoUrls(photoUrls).tags(tagList).status("Active").build();

		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		Response res = given().log().all().contentType(ContentType.JSON).body(petLombok).when().post("/pet");
		
		Assert.assertEquals(res.statusCode(), 200);
		JsonPath js =  res.jsonPath();
		
		Assert.assertEquals(js.getInt("id"), petLombok.getId());
		Assert.assertEquals(js.getString("name"), petLombok.getName());
		Assert.assertEquals(js.getString("status"), petLombok.getStatus());
		
		Assert.assertEquals(js.getInt("category.id"), category.getId());
		Assert.assertEquals(js.getString("category.name"), category.getName());
		
		Assert.assertEquals(js.getList("photoUrls"), petLombok.getPhotoUrls());
		
		for(int i=0;i<tagList.size();i++) {
			Assert.assertEquals(js.getInt("tags["+i+"].id"), petLombok.getTags().get(i).getId());
			Assert.assertEquals(js.getString("tags["+i+"].name"), petLombok.getTags().get(i).getName());
		}
		
	}

}
