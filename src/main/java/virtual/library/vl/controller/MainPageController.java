package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import virtual.library.vl.entity.Role;
import virtual.library.vl.repository.RoleRepository;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/")
    @ResponseBody
    public List<Role> successLogin(){
        return roleRepository.findAll();
    }

    @GetMapping("/roles")
    @ResponseBody
    public List<Role> test(){
        return roleRepository.findAll();
    }
}
