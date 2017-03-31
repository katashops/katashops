package com.db.shops.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.shops.model.*;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class UtilsShop {

	private static final Logger log = LoggerFactory.getLogger(UtilsShop.class);
	static final String YOUR_API_KEY = "AIzaSyBciQ0h0KlwGtLoG9my9u2xmiaUBiCmg0w";
	private static final String template = "The nearest shop adress is %s, with Latitude %s and Longitude %s";

	public static Shop addPositionInfo(Shop shop) {
		GeoApiContext context = new GeoApiContext().setApiKey(YOUR_API_KEY);
		GeocodingResult[] results;

		try {
			results = GeocodingApi.geocode(context, shop.getShopAddress().getPostCode()).await();
			
			Position p = new Position();
			p.setLatitude(results[0].geometry.location.lat);
			p.setLongitude(results[0].geometry.location.lng);
			p.setFormattedAddress(results[0].formattedAddress);
			shop.setPosition(p);

		} catch (ApiException e) {
			log.error("ApiException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		} catch (InterruptedException e) {
			log.error("InterruptedException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		} catch (IOException e) {
			log.error("IOException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		}
		return shop;

	}
	
	public static Shop nearestShop(final double latitude, final double longitude,
			final Map<String, Shop> shopList) {
		return (findTheNearestShop(latitude, longitude, shopList));
	}

	private static Shop findTheNearestShop(double latitude, double longitude, Map<String, Shop> shopList) {
		String shopKeyFinal = null;
		double distance;
		Double minimunDistanceBetweenShops = null;

		Iterator<Entry<String, Shop>> iterator = shopList.entrySet().iterator();
		while (iterator.hasNext()) {

			Entry<String, Shop> entry = iterator.next();
			Shop shop = (Shop) entry.getValue();

			distance = calculateDistance(latitude, longitude, shop);
			boolean isNotDefinedDistance = minimunDistanceBetweenShops == null;
			if (isNotDefinedDistance) {
				minimunDistanceBetweenShops = distance;
				shopKeyFinal = entry.getKey();
			} else if (distance < minimunDistanceBetweenShops) {
				minimunDistanceBetweenShops = distance;
				shopKeyFinal = entry.getKey();
			}
		}

		return shopList.get(shopKeyFinal);

	}

	private static double calculateDistance(double latitude, double longitude, Shop shop) {
		return Haversine.distance(latitude, longitude,
				shop.getPosition().getLatitude(),
				shop.getPosition().getLongitude());
	}
}
