package com.mercedes.benz.fuel.calculator.DTO;

import lombok.Data;

@Data
public class FuelCalculatorRequestDTO {
	
	
	Boolean fuelId;
	String city;
	String fuelType;
	Long fuelRequired;

}
