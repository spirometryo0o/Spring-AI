package kr.co.sboard.ch04.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemMessageController {


    @GetMapping("/ai/system-Message")
    public String systemMessageConverter() {
        return "/system-Message";
    }


}
