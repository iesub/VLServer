package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virtual.library.vl.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long aLong);
}
