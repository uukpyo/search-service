package com.search.multi.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.dto.search.BlogResponseDto;
import com.search.multi.data.entity.BlogSearchEntity;
import com.search.multi.data.repository.BlogSearchRepository;
import com.search.multi.data.repository.BlogSearchRepositoryCustom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogSearchServiceImplTest {

    @InjectMocks
    private BlogSearchServiceImpl blogSearchService;

    @Mock
    private BlogSearchRepository blogSearchRepository;

    private Long fakeBlogId = 1L;

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase {
        @Test
        void insertByBlogSearchEntity() {
            // given
            BlogSearchEntity blogSearchEntity = BlogSearchEntity.builder()
                    .cnt(1l)
                    .searchWord("검색어")
                    .build();
            BlogRequestDto blogRequestDto = new BlogRequestDto();

            // mocking
            lenient().when(blogSearchRepository.save(any())).thenReturn(blogSearchEntity);
            lenient().when(blogSearchRepository.findById(any())).thenReturn(Optional.ofNullable(blogSearchEntity));

            // when
            blogSearchService.insertByBlogSearchEntity(blogRequestDto);

            // then
            BlogSearchEntity findBlogSearchEntity = blogSearchRepository.findById(fakeBlogId).get();

            assertThat(findBlogSearchEntity).isNotNull();
            assertEquals(findBlogSearchEntity.getCnt(), blogSearchEntity.getCnt());
            assertEquals(findBlogSearchEntity.getSearchWord(), blogSearchEntity.getSearchWord());
        }

        @Test
        void updateByBlogSearchEntity() {
            // given
            BlogSearchEntity blogSearchEntity = BlogSearchEntity.builder()
                    .cnt(1l)
                    .searchWord("검색어")
                    .build();
            BlogRequestDto blogRequestDto = new BlogRequestDto();

            // mocking
            lenient().when(blogSearchRepository.save(any())).thenReturn(blogSearchEntity);
            lenient().when(blogSearchRepository.findById(any())).thenReturn(Optional.ofNullable(blogSearchEntity));
            blogSearchEntity.increaseCnt();

            // when
            blogSearchService.updateByBlogSearchEntity(blogRequestDto);

            // then
            BlogSearchEntity findBlogSearchEntity = blogSearchRepository.findById(fakeBlogId).get();

            assertThat(findBlogSearchEntity).isNotNull();
            assertEquals(findBlogSearchEntity.getCnt(), 2l);
            assertEquals(findBlogSearchEntity.getSearchWord(), blogSearchEntity.getSearchWord());
        }
    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        void insertByBlogSearchEntity() {
            // given
            BlogRequestDto blogRequestDto = new BlogRequestDto();

            // mocking
            lenient().when(blogSearchRepository.save(any())).thenReturn(null);
            lenient().when(blogSearchRepository.findById(any())).thenReturn(null);

            // when
            blogSearchService.insertByBlogSearchEntity(blogRequestDto);

            // then
            Optional<BlogSearchEntity> findBlogSearchEntity = blogSearchRepository.findById(fakeBlogId);
            assertThat(findBlogSearchEntity).isNull();
        }

        @Test
        void updateByBlogSearchEntity() {
            // given
            BlogSearchEntity blogSearchEntity = BlogSearchEntity.builder()
                    .cnt(1l)
                    .searchWord("검색어")
                    .build();
            BlogRequestDto blogRequestDto = new BlogRequestDto();

            // mocking
            lenient().when(blogSearchRepository.save(any())).thenReturn(null);
            lenient().when(blogSearchRepository.findById(any())).thenReturn(null);
            blogSearchEntity.increaseCnt();

            // when
            blogSearchService.updateByBlogSearchEntity(blogRequestDto);

            // then
            Optional<BlogSearchEntity> findBlogSearchEntity = blogSearchRepository.findById(fakeBlogId);

            assertThat(findBlogSearchEntity).isNull();
        }
    }
}