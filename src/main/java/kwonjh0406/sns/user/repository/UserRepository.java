package kwonjh0406.sns.user.repository;

import kwonjh0406.sns.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Boolean existsByUsername(String username);
    User findByUsername(String username);
}
