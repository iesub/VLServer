package virtual.library.vl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class MainPageController {

    @GetMapping("/")
    @ResponseBody
    public String successLogin(Principal principal){
        return "Login success";
    }

}
