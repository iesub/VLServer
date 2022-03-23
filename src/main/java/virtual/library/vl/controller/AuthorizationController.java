package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import virtual.library.vl.dto.LoginInfoDTO;
import virtual.library.vl.service.UserService;

@Controller
public class AuthorizationController {
    @Autowired
    UserService userService;

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model){
        model.addAttribute("loginInfo", new LoginInfoDTO(true));
        return "jsonTemplate";
    }

    @GetMapping("/loginFailure")
    public String loginFailure(Model model){
        model.addAttribute("loginInfo", new LoginInfoDTO(false));
        return "jsonTemplate";
    }
}
