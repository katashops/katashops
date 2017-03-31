package com.db.shops.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void distanceNoData() throws Exception {
		String latitude = "39.475448";
		String longitude = "-0.3842619";
		this.mockMvc
				.perform(get("/distance").contentType(MediaType.APPLICATION_JSON_VALUE).param("latitude", latitude)
						.param("longitude", longitude))
				.andDo(print()).andExpect(status().isOk()).andExpect(status().isOk());
	}

	@Test
	public void addShop() throws Exception {
		String jsonMessage = "{\"shopName\": \"ONE\", \"shopAddress\" : { \"number\": \"123\", \"postCode\": \"54321\" }}";
		// First time is 201.
		this.mockMvc.perform(post("/shops").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonMessage))
				.andDo(print()).andExpect(status().isCreated());
		// Second time is 200.
		this.mockMvc.perform(post("/shops").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonMessage))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void distance() throws Exception {
		String latitude = "39.475448";
		String longitude = "-0.3842619";
		this.mockMvc
				.perform(get("/distance").contentType(MediaType.APPLICATION_JSON_VALUE).param("latitude", latitude)
						.param("longitude", longitude))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.shopName").value("ONE"));
	}

}
