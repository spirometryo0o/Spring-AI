package kr.co.sboard.ch04.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapOutputConverterController {


    @GetMapping("/ai/map-output-converter")
    public String mapOutputConverter() {
        return "/map-output-converter";
    }


}
