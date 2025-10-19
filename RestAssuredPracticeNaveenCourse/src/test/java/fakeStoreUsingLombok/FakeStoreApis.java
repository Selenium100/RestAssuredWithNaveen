package fakeStoreUsingLombok;

import org.testng.annotations.Test;

import fakeStoreUsingLombok.FakeStorePojoUsingLombok.Address;


public class FakeStoreApis {
	
	
	@Test
	public void createFakeStore() {
		
		Address.GeoLocation location = new Address.GeoLocation("-37.189", "38.271");
		FakeStorePojoUsingLombok.Address address = new FakeStorePojoUsingLombok.Address("Banglore", "new rode street", 9090, "751005",location);
		FakeStorePojoUsingLombok.Name name = new FakeStorePojoUsingLombok.Name("Nitya", "Ranjan");
		FakeStorePojoUsingLombok fakeObj = new FakeStorePojoUsingLombok("nitya@maillinator.com", "nitya_100", "test1233", "7303709376", name, address);
		
		
	}

}
