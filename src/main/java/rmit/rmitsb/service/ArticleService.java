package rmit.rmitsb.service;

import org.springframework.data.domain.Pageable;
import rmit.rmitsb.model.ArticleModel;

import java.util.List;

public interface ArticleService {


    public void saveArticle(ArticleModel article);

    public List<ArticleModel> getAllArticles(String category);
    public List<ArticleModel> getAllArticles(Pageable pageable, String category);
    public ArticleModel getArticle(Long id);

    public ArticleModel deleteArticle(Long id);

    public void deleteAll();

}
