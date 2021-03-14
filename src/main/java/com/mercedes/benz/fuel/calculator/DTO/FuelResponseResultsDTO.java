package com.mercedes.benz.fuel.calculator.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelResponseResultsDTO{
    public List<FuelResponseDTO> results;
}

