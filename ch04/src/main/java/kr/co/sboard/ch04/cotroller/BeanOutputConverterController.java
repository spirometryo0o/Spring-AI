package kr.co.sboard.ch04.cotroller;

import kr.co.sboard.ch04.DTO.HotelDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BeanOutputConverterController {


    @GetMapping("/ai/bean-output-converter")
    public String beanOutputConverter() {
        return "/bean-output-converter";
    }

    @ResponseBody
    @PostMapping("/ai/bean-output-converter")
    public HotelDTO beanOutputConverter(@RequestParam("city") String city) {

        return null;
    }

}
