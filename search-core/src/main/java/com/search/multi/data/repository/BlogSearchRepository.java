package com.search.multi.data.repository;

import com.search.multi.data.entity.BlogSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogSearchRepository extends JpaRepository<BlogSearchEntity, Long>, BlogSearchRepositoryCustom {
}
