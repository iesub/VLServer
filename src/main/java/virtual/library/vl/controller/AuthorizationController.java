package virtual.library.vl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import virtual.library.vl.dto.AuthorizationDTO;
import virtual.library.vl.dto.RegistrationDTO;
import virtual.library.vl.entity.User;
import virtual.library.vl.service.UserService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthorizationController {
    @Autowired
    UserService userService;

    @GetMapping("/ifAuthenticated")
    public String checkAuthentication(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthorizationDTO data = new AuthorizationDTO();
        if (!authentication.getPrincipal().equals("anonymousUser")){
            User user = (User) authentication.getPrincipal();
            data.setAuthenticated(true);
            data.setMail(user.getUsername());
            data.setNickname(user.getNickname());
            data.setAuthorities(authentication.getAuthorities());
            model.addAttribute("userInfo", data);
        } else {
            data.setAuthenticated(false);
            model.addAttribute("userInfo", data);
        }
        return "jsonTemplate";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        AuthorizationDTO data = new AuthorizationDTO();
        data.setAuthenticated(true);
        data.setMail(user.getUsername());
        data.setNickname(user.getNickname());
        data.setAuthorities(authentication.getAuthorities());
        model.addAttribute("userInfo", data);
        return "jsonTemplate";
    }

    @PostMapping("/registration")
    public String registration(User user, Model model){

        String emailPattern =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(user.getUsername());
        RegistrationDTO data = new RegistrationDTO();


        if (user.getUsername() == ""){
            data.setMailEmpty(true);
            data.setGotError(true);
        } else {
            if(!matcher.matches()){
                data.setMailCorrect(false);
                data.setGotError(true);
            }
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            data.setPasswordsCorrect(false);
            data.setGotError(true);
        }
        if (user.getNickname() == ""){
            data.setNicknameEmpty(true);
            data.setGotError(true);
        }
        if (user.getPassword() == ""){
            data.setPasswordEmpty(true);
            data.setGotError(true);
        }
        if (data.isGotError()){
            model.addAttribute("registrationData", data);
            return "jsonTemplate";
        }
        if (userService.registerUser(user)){
            data.setMailExist(false);
            model.addAttribute("registrationData", data);
            return "jsonTemplate";
        } else {
            data.setMailExist(true);
            model.addAttribute("registrationData", data);
            return "jsonTemplate";
        }
    }
}
