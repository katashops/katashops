package com.db.shops.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableCaching
public class HazelcastConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastConfig.class);

	    @Bean
	    CacheManager cacheManager() {
	        return new HazelcastCacheManager(hazelcastInstance());
	    }
	   

	    @Bean
	    HazelcastInstance hazelcastInstance() {
	         return Hazelcast.newHazelcastInstance();
	    }
	
}
