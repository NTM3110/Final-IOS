package rmit.rmitsb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rmit.rmitsb.model.ArticleModel;


public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {

}
