package rmit.rmitsb.service;

import rmit.rmitsb.model.ArticleModel;

import java.util.List;

public interface ArticleService {


    public void saveArticle(ArticleModel article);

    public List<ArticleModel> getAllArticles(String category);
    public ArticleModel getArticle(Long id);

    public ArticleModel deleteArticle(Long id);

    public void deleteAll();

}
