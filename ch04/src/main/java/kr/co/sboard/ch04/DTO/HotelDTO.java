package kr.co.sboard.ch04.DTO;

import lombok.Data;

import java.util.List;

@Data
public class HotelDTO {

    private String city;

    private List<String> names;
}
