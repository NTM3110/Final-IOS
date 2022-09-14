package rmit.rmitsb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rmit.rmitsb.model.ArticleModel;

import java.util.List;


public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {
    List<ArticleModel> findAllByCategory(String category);
    Page<ArticleModel> findAll(Pageable pageable);
}
