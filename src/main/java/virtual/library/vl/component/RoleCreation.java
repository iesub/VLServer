package virtual.library.vl.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import virtual.library.vl.entity.Role;
import virtual.library.vl.entity.User;
import virtual.library.vl.repository.RoleRepository;
import virtual.library.vl.service.UserService;

import java.util.Set;

@Component
public class RoleCreation implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... strings) throws Exception{
        roleRepository.save(new Role(1L, "ROLE_USER"));
        roleRepository.save(new Role(2L, "ROLE_ADMINISTRATOR"));
        User user = new User();
        user.setId(1L);
        user.setUsername("iesubbotin@gmail.com");
        user.setNickname("admin");
        user.setPassword("123");
        user.setActive(true);
        userService.registerUser(user);
        userService.loadUserByUsername(user.getUsername());
        userService.saveUser(user);
    }

}
