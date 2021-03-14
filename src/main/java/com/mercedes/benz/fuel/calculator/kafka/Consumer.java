package com.mercedes.benz.fuel.calculator.kafka;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.benz.fuel.calculator.DTO.FuelCalculatorRequestDTO;
import com.mercedes.benz.fuel.calculator.service.FuelCalculatorService;

@Service
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@Autowired
	private FuelCalculatorService fuelCalculatorService;

	@KafkaListener(topics = "fuelEvent", groupId = "group_id")
	public void consume(String message) throws Exception {
		logger.info(String.format("#### -> Consumed message -> %s", message));

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(message);

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
				DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

		FuelCalculatorRequestDTO fuelCalculatorRequestDTO = mapper.convertValue(jsonObject,
				FuelCalculatorRequestDTO.class);

		fuelCalculatorService.processFuelCalculation(fuelCalculatorRequestDTO);
	}
}