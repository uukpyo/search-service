package com.search.multi.service;

import com.search.multi.data.dto.search.BlogRequestDto;
import com.search.multi.data.dto.search.BlogResponseDto;
import com.search.multi.data.entity.BlogSearchEntity;
import com.search.multi.data.repository.BlogSearchRepository;
import com.search.multi.service.Interface.BlogSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class BlogSearchServiceImpl implements BlogSearchService {

    private final BlogSearchRepository blogSearchRepository;

    @Override
    @Transactional
    public void insertOrUpdateByBlogSearchEntity(BlogRequestDto dto) {
        List<BlogSearchEntity> blogSearchEntity = blogSearchRepository.findBlogSearchEntityByQuery(dto.getQuery());
        if(!blogSearchEntity.isEmpty()){
             updateByBlogSearchEntity(blogSearchEntity.get(0));
        }else{
             insertByBlogSearchEntity(dto);
        }
    }

    @Override
    @Transactional
    public void insertByBlogSearchEntity(BlogRequestDto dto) {
        int size = dto.getQuery().length();
        BlogSearchEntity blogSearchEntity;

        log.info("STRING BYTE : "+size);
        if(size > 255) blogSearchEntity = BlogSearchEntity.builder().longSearchWord(dto.getQuery()).build();
        else blogSearchEntity = BlogSearchEntity.builder().searchWord(dto.getQuery()).build();
        blogSearchRepository.save(blogSearchEntity);
    }

    @Override
    @Transactional
    public void updateByBlogSearchEntity(BlogSearchEntity blogSearchEntity) {
        blogSearchEntity.increaseCnt();
    }

    @Override
    public List<BlogResponseDto> getBlogSearchEntityCntRank() {
        int size = 10;
        return blogSearchRepository.findBlogSearchEntityForRank(size);
    }
}
