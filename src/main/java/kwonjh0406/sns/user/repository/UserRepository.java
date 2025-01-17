package kwonjh0406.sns.user.repository;

import kwonjh0406.sns.user.dto.ProfileEditResponse;
import kwonjh0406.sns.user.dto.SearchUserResponse;
import kwonjh0406.sns.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query("SELECT new kwonjh0406.sns.user.dto.SearchUserResponse(u.username, u.name, u.profileImageUrl, u.bio) " +
            "FROM User u")
    List<SearchUserResponse> findAllUsers();
    @Query("SELECT new kwonjh0406.sns.user.dto.ProfileEditResponse(" +
            "u.username, u.name, u.profileImageUrl, u.bio) " +
            "FROM User u WHERE u.id = :userId")
    ProfileEditResponse findProfileByUserId(Long userId);

}
