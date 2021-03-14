package com.mercedes.benz.fuel.calculator.service;

import com.mercedes.benz.fuel.calculator.DTO.FuelCalculatorRequestDTO;
import com.mercedes.benz.fuel.calculator.DTO.FuelCalculatorResponseDTO;

public interface FuelCalculatorService {

	/**
	 * @param fuelCalculatorRequestDTO
	 * @return FuelCalculatorResponseDTO
	 * @throws Exception
	 */
	FuelCalculatorResponseDTO processFuelCalculation(final FuelCalculatorRequestDTO fuelCalculatorRequestDTO)
			throws Exception;

}
