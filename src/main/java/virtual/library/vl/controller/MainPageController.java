package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import virtual.library.vl.entity.Role;
import virtual.library.vl.repository.RoleRepository;

import java.security.Principal;
import java.util.List;

@Controller
public class MainPageController {

    @GetMapping("/")
    @ResponseBody
    public String successLogin(Principal principal){
        return "Login success";
    }

}
