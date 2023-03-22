package com.search.multi.data.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.search.multi.data.dto.search.BlogResponseDto;
import com.search.multi.data.entity.BlogSearchEntity;
import com.search.multi.data.entity.QBlogSearchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.search.multi.data.entity.QBlogSearchEntity.blogSearchEntity;

@RequiredArgsConstructor
@Repository
public class BlogSearchRepositoryImpl implements BlogSearchRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    @Description("인기 검색어 조회")
    public List<BlogResponseDto> findBlogSearchEntityForRank(int cnt) {
        return queryFactory
                .select(Projections.fields(BlogResponseDto.class,
                        blogSearchEntity.searchWord.coalesce(blogSearchEntity.longSearchWord).as("query"),
                        blogSearchEntity.cnt.as("cnt")))
                .from(blogSearchEntity)
                .orderBy(blogSearchEntity.cnt.desc())
                .limit(cnt)
                .fetch();
    }

    @Override
    @Description("검색어 조회")
    public List<BlogSearchEntity> findBlogSearchEntityByQuery(String query) {
        return queryFactory
                .selectFrom(blogSearchEntity)
                .where(blogSearchEntity.searchWord.eq(query).or(blogSearchEntity.longSearchWord.eq(query)))
                .limit(1)
                .fetch();
    }
}
