package petApiUsingLombok;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import petApiUsingLombok.PetLombok.Tags;
import static io.restassured.RestAssured.*;

public class PetApis {

	@Test
	public void CreatePet() {

		PetLombok.Tags tag1 = new PetLombok.Tags(0, "doggie");
		PetLombok.Tags tag2 = new PetLombok.Tags(0, "mogggie");
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
	Long id =	given().log().all().contentType(ContentType.JSON).body(petLombok).when().post("/pet").then()
				.assertThat().statusCode(200).extract().path("id");
	
	System.out.println("Created id is : " + id); //good
	
	

		

	}

}
