package com.search.multi.data.entity;

import jdk.jfr.Description;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import reactor.core.publisher.Mono;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
@Table(name = "BLOG_SEARCH")
public class BlogSearchEntity {

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="blog_search_id", nullable = false)
    private Long blogSearchId;

    /** 검색어 */
    @Column(name="search_word", unique = true)
    private String searchWord;

    /** 긴 검색어 */
    @Column(columnDefinition = "TEXT", name="long_search_word", unique = true)
    private String longSearchWord;

    /** 검색수 */
    @ColumnDefault("1")
    private Long cnt;

    /** 저장일 */
    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    /** 수정일 */
    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    @Builder
    public BlogSearchEntity(Long blogSearchId, String searchWord, String longSearchWord, Long cnt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.blogSearchId = blogSearchId;
        this.searchWord = searchWord;
        this.longSearchWord = longSearchWord;
        this.cnt = cnt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * 검색수 증가
     */
    public void increaseCnt(){
        this.cnt += 1;
    }
}
