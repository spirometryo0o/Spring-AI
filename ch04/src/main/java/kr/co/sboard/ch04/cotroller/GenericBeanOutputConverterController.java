package kr.co.sboard.ch04.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenericBeanOutputConverterController {


    @GetMapping("/ai/generic-bean-output-converter")
    public String genericBeanOutputConverter() {
        return "/generic-bean-output-converter";
    }

    @PostMapping("/ai/generic-bean-output-converter")
    public String genericBeanOutputConverter(@RequestParam("cities")String cities) {

        return "/generic-bean-output-converter";
    }


}
