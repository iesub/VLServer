package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtual.library.vl.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
