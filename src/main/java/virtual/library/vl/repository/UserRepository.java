package virtual.library.vl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import virtual.library.vl.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);

    @Query(value="select * from user WHERE activated = 0 ORDER by id asc", nativeQuery = true)
    List<User> findNonActivatedUser();
}
