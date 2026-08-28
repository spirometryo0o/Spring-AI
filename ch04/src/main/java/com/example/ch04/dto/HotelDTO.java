package com.example.ch04.dto;

import java.util.List;

import lombok.Data;

@Data
public class HotelDTO {
	
	private String city;
	
	private List<String> names;
}
