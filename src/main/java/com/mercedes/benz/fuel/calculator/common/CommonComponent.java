package com.mercedes.benz.fuel.calculator.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommonComponent {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	CacheManager cacheManager;


	@Cacheable(cacheNames="fuelDetailCache")
	public ResponseEntity<String> getCityWiseFuelDetails() {
		
		String url = environment.getProperty(ServiceConstants.FUEL_DATA_INDIA_URL_PROP);
		return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
	}
	
	@Scheduled(cron = "0 0 * * * ?")
	public void evictAllcachesAtIntervals() {
	    evictAllCaches();
	}
	
	public void evictAllCaches() {
	    cacheManager.getCacheNames().stream()
	      .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}
	
}
