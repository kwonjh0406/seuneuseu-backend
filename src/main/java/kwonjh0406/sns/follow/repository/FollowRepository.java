package kwonjh0406.sns.follow.repository;

import kwonjh0406.sns.follow.entity.Follow;
import kwonjh0406.sns.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(User follower, User following);
    void deleteByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollower(User user);
}
