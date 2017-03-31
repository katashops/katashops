package com.db.shops.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.shops.model.Position;
import com.db.shops.model.Shop;
import com.db.shops.model.ShopAddress;

public class UtilShopTest {

	private static final String THE_NEAREST_SHOP = "Barlow, Kentucky 42024, EE. UU.";
	private static final Logger log = LoggerFactory.getLogger(UtilShopTest.class);
	static final String SHOP_ID = "shop1";
	static final String SHOP_ID2 = "shop2";
	static final String SHOP_ID3 = "shop3";
	static final String SHOP_NUMBER = "1";
	static final String SHOP_NUMBER2 = "2";
	static final String SHOP_NUMBER89 = "89";
	static final String SHOP_POST_CODE = "46022";
	static final String SHOP_POST_CODE2 = "46023";
	static final String SHOP_POST_CODE3 = "46021";
	static final double INITIAL_LAT = 39.4729669;
	static final double INITIAL_LNG = -0.3536548;
	static final double FINAL_LAT = 39.4471372;
	static final double FINAL_LNG = -0.3348372;
	static final double OTHER_LAT = 37.149022;
	static final double OTHER_LNG = -88.943163;
	static final double OTHER_LAT2 = 36.9245629;
	static final double OTHER_LNG2 = -88.6144839;
	static final String ADRESS = "Barlow, Kentucky 42024, EE. UU.";

	@Test
	public void findTheNearestShopTest() {
		HashMap<String, Shop> shopList = new HashMap<>();

		ShopAddress initialshopAddress = new ShopAddress(SHOP_NUMBER, SHOP_POST_CODE);
		Shop initialShop = new Shop(SHOP_ID, initialshopAddress);
		Position geoResultSHop = new Position(OTHER_LAT2, OTHER_LNG2, ADRESS);
		initialShop.setPosition(geoResultSHop);
		shopList.put(SHOP_ID, initialShop);

		ShopAddress shopAddress2 = new ShopAddress(SHOP_NUMBER2, SHOP_POST_CODE);
		Shop shop2 = new Shop(SHOP_ID2, shopAddress2);
		Position geoResultSHop2 = new Position(FINAL_LAT, FINAL_LNG, ADRESS);
		shop2.setPosition(geoResultSHop2);
		shopList.put(SHOP_ID2, shop2);

		ShopAddress shopAddress3 = new ShopAddress(SHOP_NUMBER89, SHOP_POST_CODE);
		Shop shop3 = new Shop(SHOP_ID3, shopAddress3);
		Position geoResultSHop3 = new Position(OTHER_LAT, OTHER_LNG, ADRESS);
		shop3.setPosition(geoResultSHop3);
		shopList.put(SHOP_ID3, shop3);

		Shop nearestShop = UtilsShop.nearestShop(INITIAL_LAT, INITIAL_LNG, shopList);

		assertEquals(nearestShop.getPosition().getFormattedAddress(), THE_NEAREST_SHOP);

	}

}
