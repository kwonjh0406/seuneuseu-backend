package kwonjh0406.sns.follow.repository;

import kwonjh0406.sns.follow.entity.Follow;
import kwonjh0406.sns.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(User follower, User following);
}
