package fakeStoreUsingLombok;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FakeStorePojoUsingLombok {
	
	private String email;
	private String username;
	private String password;
	private String phone;
	private Name name;
	private Address address;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Name{
		private String firstname;
		private String lastname;
	}
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Address{
		private String city;
		private String street;
		private Integer number;
		private String zipcode;
		private GeoLocation location;
		
		
		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		@Builder
		public static class GeoLocation{
			private String lat;
			@JsonProperty("long")
			private String longitude;
		}
	}
	

}
