package com.db.shops.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.shops.model.Shop;
import com.db.shops.model.ShopAddress;
import com.google.gson.Gson;

import io.restassured.http.ContentType;

/**
 * Integration TEST with Rest-assured
 * 
 * Application is started using SpringBoot Runner in a random port Using
 * Rest-Assured library, the Shops API is tested in a isolated testing
 * environment.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiRestAssuredIT {

	private static final String API_PATH = "/shops";

	@LocalServerPort
	int port;

	private List<Shop> testData;

	/**
	 * Setup A list of shops are created
	 * 
	 * <pre>
	 *  API PATH: /shops
	 *  HTTP Method: POST
	 *  Body: Shop JSON object
	 *  Expected HTTP code: 200
	 * </pre>
	 * 
	 * @throws JSONException
	 */
	@Before
	public void setUp() throws JSONException {
		testData = Arrays.asList(new Shop("testShop1", new ShopAddress("1111", "2222")),
				new Shop("testShop2", new ShopAddress("1111", "2222")),
				new Shop("testShop3", new ShopAddress("1111", "2222")));

		for (Shop shop : testData) {
			// Add initial shops
			given().port(port).contentType(ContentType.JSON).body(new Gson().toJson(shop)).post(API_PATH).then()
					.statusCode(HttpStatus.SC_CREATED); // HTTP code 201
		}
	}

	/**
	 * tearDown The shops are deleted
	 * 
	 * <pre>
	 *  API PATH: /shops
	 *  HTTP Method: DELETE
	 *  Expected HTTP code: 200
	 * </pre>
	 * 
	 * @throws JSONException
	 */
	@After
	public void tearDown() throws JSONException {
		// delete shops
		given().port(port).delete(API_PATH).then().statusCode(HttpStatus.SC_OK);
	}

	/**
	 * Test all shops created in the setup() method are listed
	 * 
	 * <pre>
	 *  API PATH: /shops
	 *  HTTP Method: GET
	 *  Expected HTTP code: 200
	 *  Expected body: [{<shop1JSON>},{<shop2JSON>},{<shop3JSON>}]
	 * </pre>
	 */
	@Test
	public void shopsListing() {
		List<String> shopNames = new ArrayList<>();
		for (Shop shop : testData) {
			shopNames.add(shop.getShopName());
		}
		given().port(port).get(API_PATH).then().body("shopName", hasItems(shopNames.toArray()));
	}

	/**
	 * Test the case a new shop version is created. In this case, it returns the
	 * previous shop representation.
	 * 
	 * <pre>
	 *  API PATH: /shops
	 *  HTTP Method: POST
	 *  Body: <modifiedShopJSON>
	 *  Expected HTTP code: 200
	 *  Expected body: {<originalShopJSON>}
	 * </pre>
	 */
	@Test
	public void previousVersionsTest() {
		for (Shop originalShop : testData) {

			// Attempt to create a shop with the same name and different
			// shopAddress
			Shop modifiedShop = new Shop(originalShop.getShopName(), new ShopAddress("9999", "6666"));

			// Expected HTTP 200 and previous version representation
			given().port(port).contentType(ContentType.JSON).body(new Gson().toJson(modifiedShop)).post(API_PATH).then()
					.statusCode(HttpStatus.SC_OK).body("shopAddress.number",
							equalTo(originalShop.getShopAddress().getNumber()), "shopAddress.postCode",
							equalTo(originalShop.getShopAddress().getPostCode()));

		}

	}

}
