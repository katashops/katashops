package com.db.shops.api;

import java.util.Collection;

import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.db.shops.configuration.HazelcastConfig;
import com.db.shops.model.Shop;
import com.db.shops.utils.UtilsShop;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@RestController
public class ShopController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastConfig.class);

	@Autowired
	HazelcastInstance hazelcastInstance;

	@RequestMapping(value = "/shops", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Shop> addShop(@RequestBody final Shop shop) {
		LOGGER.info("Adding shop: " + shop);

		UtilsShop.addPositionInfo(shop);

		Shop oldShop = getShopData().put(shop.getShopName(), shop);
		LOGGER.info("Added shop: " + shop + " replacing: " + oldShop);

		if (oldShop != null) {
			return ResponseEntity.ok().body(oldShop);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(oldShop);
	}

	@RequestMapping(value = "/shops", method = RequestMethod.DELETE)
	public void reset() {
		getShopData().clear();
	}

	@RequestMapping(value = "/shops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Shop> getAll() {
		return getShopData().values();
	}

	@RequestMapping(value = "/distance", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Shop distance(@QueryParam("longitude") final String longitude,
			@QueryParam("latitude") final String latitude) {
		LOGGER.info("Calculating nearest city from latitude: " + latitude + ", and longitude: " + longitude);

		Shop nearestShop = UtilsShop.nearestShop(Double.parseDouble(latitude), Double.parseDouble(longitude), getShopData());
		
		LOGGER.info("Nearest shop is: " + nearestShop);
		return nearestShop;
	}

	private IMap<String, Shop> getShopData() {
		IMap<String, Shop> map = hazelcastInstance.getMap("shops");
		return map;
	}

}
