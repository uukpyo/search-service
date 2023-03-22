package com.search.multi.service;

import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.entity.BlogSearchEntity;
import com.search.multi.data.repository.BlogSearchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

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
            blogRequestDto.setQuery("테스트");

            // mocking
            lenient().when(blogSearchRepository.save(any())).thenReturn(blogSearchEntity);
            lenient().when(blogSearchRepository.findById(any())).thenReturn(Optional.ofNullable(blogSearchEntity));

            // when
            blogSearchService.insertByBlogSearchEntity(blogRequestDto);

            // then
            Optional<BlogSearchEntity> findBlogSearchEntity = blogSearchRepository.findById(fakeBlogId);

            assertThat(findBlogSearchEntity).isNotNull();
            assertEquals(findBlogSearchEntity.get().getCnt(), blogSearchEntity.getCnt());
            assertEquals(findBlogSearchEntity.get().getSearchWord(), blogSearchEntity.getSearchWord());
        }

        @Test
        void updateByBlogSearchEntity() {
            // given
            BlogSearchEntity blogSearchEntity = BlogSearchEntity.builder()
                    .cnt(1l)
                    .searchWord("검색어")
                    .build();

            // mocking
            lenient().when(blogSearchRepository.save(any())).thenReturn(blogSearchEntity);
            lenient().when(blogSearchRepository.findById(any())).thenReturn(Optional.of(blogSearchEntity));

            // when
            blogSearchService.updateByBlogSearchEntity(blogSearchEntity);

            // then
            Optional<BlogSearchEntity> findBlogSearchEntity = blogSearchRepository.findById(fakeBlogId);

            assertThat(findBlogSearchEntity).isNotNull();
            assertEquals(findBlogSearchEntity.get().getCnt(), 2l);
            assertEquals(findBlogSearchEntity.get().getSearchWord(), blogSearchEntity.getSearchWord());
        }
    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        void insertByBlogSearchEntity() {
            // given
            BlogRequestDto blogRequestDto = new BlogRequestDto();
            blogRequestDto.setQuery("테스트");

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
            blogSearchService.updateByBlogSearchEntity(blogSearchEntity);

            // then
            Optional<BlogSearchEntity> findBlogSearchEntity = blogSearchRepository.findById(fakeBlogId);
            assertThat(findBlogSearchEntity).isNull();
        }
    }
}