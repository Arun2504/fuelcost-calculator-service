package com.mercedes.benz.fuel.calculator.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FuelCalculatorResponseDTO {
	
	@JsonProperty(value = "totalFuelRefilled(In Liters)")
	Long totalFuelRefilled;
	@JsonProperty(value = "totalTimeTaken(In Minutes)")
	Long totalTimeTaken;
	@JsonProperty(value = "totalAmount(In Rupees)")
	Double totalAmount;

}
