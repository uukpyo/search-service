package com.search.multi.data.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.search.multi.config.QuerydslConfig;
import com.search.multi.data.dto.search.BlogResponseDto;
import com.search.multi.data.entity.BlogSearchEntity;
import com.search.multi.data.entity.QBlogSearchEntity;
import com.search.multi.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
@Log4j2
@Import(QuerydslConfig.class)
class BlogSearchRepositoryTest {

    @Autowired
    private BlogSearchRepository blogSearchRepository;
    private BlogSearchEntity blogSearchEntity;
    @Autowired
    private JPAQueryFactory queryFactory;

    private String defaultWord = "테스트검색어";
    private String longDefaultWord = "긴테스트검색어";
    private Long defaultCnt = 1l;

    //    초기화
    @BeforeEach
    public void setUp() {
        blogSearchEntity = blogSearchEntity.builder()
                .searchWord(defaultWord)
                .longSearchWord(longDefaultWord)
                .cnt(defaultCnt)
                .build();
    }

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase{
        @Test
        public void 블로그검색엔티티_저장(){
            // given
            BlogSearchEntity nextSearchEntity = BlogSearchEntity.builder()
                    .searchWord("두번째검색어")
                    .longSearchWord("두번째긴검색어")
                    .cnt(10l)
                    .build();

            //when
            BlogSearchEntity saveSearch = blogSearchRepository.save(blogSearchEntity);
            BlogSearchEntity nextSaveSearch = blogSearchRepository.save(nextSearchEntity);

            // then
            assertThat(saveSearch.getSearchWord()).isEqualTo(defaultWord);
            assertThat(saveSearch.getLongSearchWord()).isEqualTo(longDefaultWord);
            assertThat(saveSearch.getCnt()).isEqualTo(defaultCnt);

            assertThat(nextSaveSearch.getSearchWord()).isEqualTo("두번째검색어");
            assertThat(nextSaveSearch.getLongSearchWord()).isEqualTo("두번째긴검색어");
            assertThat(nextSaveSearch.getCnt()).isEqualTo(10l);
        }
        @Test
        public void 블로그검색엔티티_검색수_증가(){
            // given
            BlogSearchEntity nextSearchEntity = BlogSearchEntity.builder()
                    .searchWord("두번째검색어")
                    .longSearchWord("두번째긴검색어")
                    .cnt(10l)
                    .build();

            // when
            BlogSearchEntity saveSearch = blogSearchRepository.save(blogSearchEntity);
            saveSearch.increaseCnt();

            BlogSearchEntity nextSaveSearch = blogSearchRepository.save(nextSearchEntity);
            nextSaveSearch.increaseCnt();

            // then
            assertThat(saveSearch.getCnt()).isNotNull();
            assertThat(saveSearch.getCnt()).isEqualTo(2l);

            assertThat(nextSaveSearch.getCnt()).isNotNull();
            assertThat(nextSaveSearch.getCnt()).isEqualTo(11l);
        }
        @Test
        public void 블로그검색엔티티_조회_아이디(){
            // given
            BlogSearchEntity nextSearchEntity = BlogSearchEntity.builder()
                    .searchWord("두번째검색어")
                    .longSearchWord("두번째긴검색어")
                    .cnt(10l)
                    .build();

            BlogSearchEntity saveSearch = blogSearchRepository.save(blogSearchEntity);
            BlogSearchEntity nextSaveSearch = blogSearchRepository.save(nextSearchEntity);

            //when
            Optional<BlogSearchEntity> search = Optional
                    .ofNullable(blogSearchRepository.findById(saveSearch.getBlogSearchId()))
                    .orElseThrow(() -> new NotFoundException("not found search"));

            Optional<BlogSearchEntity> nextSearch = Optional
                    .ofNullable(blogSearchRepository.findById(nextSaveSearch.getBlogSearchId()))
                    .orElseThrow(() -> new NotFoundException("not found search"));

            // then
            assertThat(search.get().getSearchWord()).isEqualTo(saveSearch.getSearchWord());
            assertThat(search.get().getLongSearchWord()).isEqualTo(saveSearch.getLongSearchWord());
            assertThat(search.get().getCnt()).isEqualTo(saveSearch.getCnt());

            assertThat(nextSearch.get().getSearchWord()).isEqualTo(nextSaveSearch.getSearchWord());
            assertThat(nextSearch.get().getLongSearchWord()).isEqualTo(nextSaveSearch.getLongSearchWord());
            assertThat(nextSearch.get().getCnt()).isEqualTo(nextSaveSearch.getCnt());
        }
        @Test
        public void 블로그검색엔티티_조회_최다검색어(){
            // given
            BlogSearchEntity saveSearch = blogSearchRepository.save(blogSearchEntity);
            //8번의 cnt 증가
            for (int i=0; i<9; i++) saveSearch.increaseCnt();

            BlogSearchEntity nextSearchEntity = BlogSearchEntity.builder()
                    .searchWord("두번째검색어")
                    .longSearchWord("두번째긴검색어")
                    .cnt(1l)
                    .build();
            BlogSearchEntity nextSaveSearch = blogSearchRepository.save(nextSearchEntity);

            //9번의 cnt 증가
            for (int i=0; i<10; i++) nextSaveSearch.increaseCnt();

            BlogSearchEntity thirdSearchEntity = BlogSearchEntity.builder()
                    .searchWord("세번째검색어")
                    .longSearchWord("세번째긴검색어")
                    .cnt(1l)
                    .build();
            BlogSearchEntity thirdSaveSearch = blogSearchRepository.save(thirdSearchEntity);

            //10번의 cnt 증가
            for (int i=0; i<11; i++) thirdSaveSearch.increaseCnt();

            //when
            List<BlogResponseDto> rank = queryFactory
                    .select(Projections.fields(BlogResponseDto.class,
                            QBlogSearchEntity.blogSearchEntity.searchWord.coalesce(QBlogSearchEntity.blogSearchEntity.longSearchWord).as("query"),
                            QBlogSearchEntity.blogSearchEntity.cnt.as("cnt")))
                    .from(QBlogSearchEntity.blogSearchEntity)
                    .orderBy(QBlogSearchEntity.blogSearchEntity.cnt.desc())
                    .limit(10)
                    .fetch();

            // then
            assertThat(rank.size()).isEqualTo(3);
            assertThat(rank.get(0).getQuery()).isEqualTo(thirdSaveSearch.getSearchWord());
            assertThat(rank.get(0).getCnt()).isEqualTo(thirdSaveSearch.getCnt());

            assertThat(rank.get(1).getQuery()).isEqualTo(nextSearchEntity.getSearchWord());
            assertThat(rank.get(1).getCnt()).isEqualTo(nextSearchEntity.getCnt());

            assertThat(rank.get(2).getQuery()).isEqualTo(saveSearch.getSearchWord());
            assertThat(rank.get(2).getCnt()).isEqualTo(saveSearch.getCnt());
        }
    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        public void 블로그검색엔티티_cnt_nullable_false(){
            // given
            BlogSearchEntity nextSearchEntity = BlogSearchEntity.builder()
                    .searchWord("두번째검색어")
                    .longSearchWord("두번째긴검색어")
                    //.cnt(10l)
                    .build();

            //when & then
            Assertions.assertThatThrownBy(() -> blogSearchRepository.save(nextSearchEntity)).isInstanceOf(DataIntegrityViolationException.class);
        }
        @Test
        public void 블로그검색엔티티_searchWord_unique_true(){
            // given
            BlogSearchEntity searchEntity = BlogSearchEntity.builder()
                    .searchWord("동일한검색어")
                    .cnt(1l)
                    .build();
            blogSearchRepository.save(searchEntity);

            BlogSearchEntity nextSearchEntity = BlogSearchEntity.builder()
                    .searchWord("동일한검색어")
                    .cnt(1l)
                    .build();

            //when & then
            Assertions.assertThatThrownBy(() -> blogSearchRepository.save(nextSearchEntity)).isInstanceOf(DataIntegrityViolationException.class);
        }
        @Test
        public void 블로그검색엔티티_longSearchWord_unique_true(){
            // given
            BlogSearchEntity searchEntity = BlogSearchEntity.builder()
                    .longSearchWord("동일한검색어")
                    .cnt(1l)
                    .build();
            blogSearchRepository.save(searchEntity);

            BlogSearchEntity nextSearchEntity = BlogSearchEntity.builder()
                    .longSearchWord("동일한검색어")
                    .cnt(1l)
                    .build();

            //when & then
            Assertions.assertThatThrownBy(() -> blogSearchRepository.save(nextSearchEntity)).isInstanceOf(DataIntegrityViolationException.class);
        }
    }
}