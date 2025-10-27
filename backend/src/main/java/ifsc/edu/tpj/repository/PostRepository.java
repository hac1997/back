package ifsc.edu.tpj.repository;

import ifsc.edu.tpj.model.PostSorting;
import org.springframework.data.jpa.repository.JpaRepository;

import ifsc.edu.tpj.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // nem fudendo que eu conseguiria fazer essa query, por isso
    // que eu queria usar algo como QueryBuilder ou ElasticSearch
    @Query("""
        SELECT p FROM Post p
        LEFT JOIN p.comments c
        LEFT JOIN Vote v ON v.post = p
        LEFT JOIN p.tags t
        WHERE (:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(p.body) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR EXISTS (SELECT tt FROM p.tags tt WHERE LOWER(tt.name) LIKE LOWER(CONCAT('%', :keyword, '%'))))
          AND (:createdAfter IS NULL OR p.createdAt >= :createdAfter)
          AND (:createdBefore IS NULL OR p.createdAt <= :createdBefore)
          AND (:tags IS NULL OR t.name IN :tags)
        GROUP BY p
        HAVING (:minComments IS NULL OR COUNT(DISTINCT c) >= :minComments)
           AND (:maxComments IS NULL OR COUNT(DISTINCT c) <= :maxComments)
           AND (:tags IS NULL OR COUNT(DISTINCT t) = :tagCount)
        ORDER BY
            CASE
                WHEN :sorting = 'MOST_VOTED' THEN COALESCE(SUM(CASE WHEN v.vote = true THEN 1 ELSE -1 END), 0)
            END DESC,
            CASE
                WHEN :sorting = 'MOST_COMMENTS' THEN COUNT(DISTINCT c)
            END DESC,
            CASE
                WHEN :sorting = 'YOUNGEST' THEN p.createdAt
            END DESC,
            CASE
                WHEN :sorting = 'OLDEST' THEN p.createdAt
            END ASC
    """)
    List<Post> searchAdvanced(
            @Param("keyword") String keyword,
            @Param("tags") List<String> tags,
            @Param("createdAfter") LocalDateTime createdAfter,
            @Param("createdBefore") LocalDateTime createdBefore,
            @Param("minComments") Integer minComments,
            @Param("maxComments") Integer maxComments,
            @Param("sorting") PostSorting sorting,
            @Param("tagCount") Long tagCount
    );

}
