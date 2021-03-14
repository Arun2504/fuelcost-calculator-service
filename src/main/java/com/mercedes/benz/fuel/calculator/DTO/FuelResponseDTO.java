package com.mercedes.benz.fuel.calculator.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FuelResponseDTO {
	public String dieselChange;
	public int isActive;
	public String priceDate;
	public String cityState;
	public String petrolPrice;
	public String petrolChange;
	@JsonProperty("ID")
	public int id;
	public String origin;
	public String type;
	public String dieselPrice;
	public String seoname;
	public String createdDate;
}
