package com.mercedes.benz.fuel.calculator.serviceImpl;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.benz.fuel.calculator.DTO.FuelCalculatorRequestDTO;
import com.mercedes.benz.fuel.calculator.DTO.FuelCalculatorResponseDTO;
import com.mercedes.benz.fuel.calculator.DTO.FuelResponseDTO;
import com.mercedes.benz.fuel.calculator.DTO.FuelResponseResultsDTO;
import com.mercedes.benz.fuel.calculator.common.CommonComponent;
import com.mercedes.benz.fuel.calculator.common.ServiceConstants;
import com.mercedes.benz.fuel.calculator.service.FuelCalculatorService;

@Service
public class FuelCalcaulatorServiceImpl implements FuelCalculatorService {

	private Logger logger = LoggerFactory.getLogger(FuelCalcaulatorServiceImpl.class);


	@Autowired
	private CommonComponent commonComponent;

	@Override
	public FuelCalculatorResponseDTO processFuelCalculation(final FuelCalculatorRequestDTO fuelCalculatorRequestDTO)
			throws Exception {

		Instant currentTime = Instant.now();

		Boolean fuelId = fuelCalculatorRequestDTO.getFuelId();
		String city = fuelCalculatorRequestDTO.getCity();

		logger.info("Request received to calculate fuel [fuelId : " + fuelId + ", city : " + city + "]");
		FuelCalculatorResponseDTO fuelCalculatorResponseDTO = null;

		if (fuelCalculatorRequestDTO.getFuelId()) {
			logger.info("started fuel calculation for city : " + city);

			ResponseEntity<String> serviceResponse = null;

			try {
				serviceResponse = commonComponent.getCityWiseFuelDetails();
			} catch (Exception ex) {

				throw new RestClientException("Error in getting fuel prices", ex);
			}

				if (null != serviceResponse && serviceResponse.hasBody()) {

				JSONParser parser = new JSONParser();
				JSONObject jsonObject = (JSONObject) parser.parse(serviceResponse.getBody());

				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
						DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
						.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
								DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);

				FuelResponseResultsDTO fuelResponseResultsDTO = mapper.convertValue(jsonObject,
						FuelResponseResultsDTO.class);
				FuelResponseDTO fuelResponseDTO = fuelResponseResultsDTO.getResults().stream()
						.filter(fuelResponse -> fuelResponse.getCityState().equals(city)).collect(Collectors.toList())
						.get(0);

				Long timeRequiredToFillTank = fuelCalculatorRequestDTO.getFuelRequired()
						* ServiceConstants.TIME_REQUIRED_PER_LITERS_IN_SECONDS;

				Instant finishedTime = currentTime
						.plusMillis(timeRequiredToFillTank * ServiceConstants.SECONDS_TO_MILISECONDS);

				Double totalAmount = Double
						.parseDouble(fuelCalculatorRequestDTO.getFuelType().equals(ServiceConstants.PETROL)
								? fuelResponseDTO.getPetrolPrice()
								: fuelResponseDTO.getDieselPrice())
						* fuelCalculatorRequestDTO.getFuelRequired();

				fuelCalculatorResponseDTO = new FuelCalculatorResponseDTO();
				fuelCalculatorResponseDTO.setTotalAmount(totalAmount);
				fuelCalculatorResponseDTO.setTotalTimeTaken(Duration.between(currentTime, finishedTime).toMinutes());
				fuelCalculatorResponseDTO.setTotalFuelRefilled(fuelCalculatorRequestDTO.getFuelRequired());
			}

		}

		return fuelCalculatorResponseDTO;
	}

};