package virtual.library.vl.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import virtual.library.vl.entity.Role;
import virtual.library.vl.repository.RoleRepository;
import virtual.library.vl.service.UserService;

@Component
public class RoleCreation implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void run(String... strings) throws Exception{
        roleRepository.save(new Role(1L, "ROLE_USER"));
        roleRepository.save(new Role(2L, "ROLE_ADMINISTRATOR"));
    }

}
