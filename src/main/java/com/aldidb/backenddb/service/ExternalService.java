package com.aldidb.backenddb.service;

import java.time.Duration;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aldidb.backenddb.exception.CustomGenericException;

import org.springframework.http.*;

@Service
public class ExternalService {
	
	 private static final Logger logger = LoggerFactory.getLogger(ExternalService.class);
	
	private final RestTemplate restTemplate;
	
    public ExternalService(RestTemplateBuilder restTemplateBuilder) {
        // set connection and read timeouts
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }
	
    @Cacheable("province")
	public ResponseEntity<Object> getAllProvince(String key) {
		
		String url = "https://api.rajaongkir.com/starter/province";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("key", key);

        
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
        	logger.warn("----Sleep for 4 Secs.. backend call.-----");
            Thread.sleep(1000 * 4);
            
        	ResponseEntity<Object> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class, 1);
            if (response.getStatusCode() == HttpStatus.OK) {
            	//TODO CACHE
            	logger.info("----Get data from api.-----");
                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	throw new CustomGenericException("98", HttpStatus.BAD_REQUEST.value(),"Failed get data");
		}
		return null;
		
	}

}
