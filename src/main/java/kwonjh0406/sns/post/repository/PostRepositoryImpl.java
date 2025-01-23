package kwonjh0406.sns.post.repository;

import kwonjh0406.sns.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final DSLContext dslContext;

    @Override
    public int updateReplies(Post post) {
        return dslContext.update(DSL.table("Post"))
                .set(DSL.field("replies", Integer.class), DSL.field("replies", Integer.class).add(1))  // replies 컬럼을 1 증가시킴
                .set(DSL.field("lastCommentedAt"), DSL.val(new Timestamp(System.currentTimeMillis())))  // last_replied_at 값 갱신
                .where(DSL.field("id").eq(post.getId()))  // id 컬럼을 기준으로 찾음
                .and(DSL.field("lastCommentedAt").eq(post.getLastCommentedAt()))  // last_replied_at 값이 일치할 때만
                .execute();
    }
}
