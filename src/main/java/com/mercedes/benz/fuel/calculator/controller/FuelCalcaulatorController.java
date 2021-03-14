package com.mercedes.benz.fuel.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mercedes.benz.fuel.calculator.DTO.FuelCalculatorRequestDTO;
import com.mercedes.benz.fuel.calculator.DTO.FuelCalculatorResponseDTO;
import com.mercedes.benz.fuel.calculator.service.FuelCalculatorService;

@Controller
@RequestMapping("fuelCalculator")
public class FuelCalcaulatorController {

	@Autowired
	private FuelCalculatorService fuelCalculatorService;

	@PostMapping(path = "/calculate")
	public ResponseEntity<FuelCalculatorResponseDTO> calculateFuel(
			@RequestBody final FuelCalculatorRequestDTO fuelCalculatorRequestDTO) throws Exception {
		return new ResponseEntity<FuelCalculatorResponseDTO>(
				fuelCalculatorService.processFuelCalculation(fuelCalculatorRequestDTO), HttpStatus.OK);

	}

}
