package com.search.multi.data.repository;

import com.search.multi.data.entity.BlogSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BlogSearchRepository extends JpaRepository<BlogSearchEntity,Long>, BlogSearchRepositoryCustom {
}
