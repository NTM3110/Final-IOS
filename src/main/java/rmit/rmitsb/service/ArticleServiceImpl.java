package rmit.rmitsb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.rmitsb.model.ArticleModel;
import rmit.rmitsb.repository.ArticleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void saveArticle(ArticleModel article){
        this.articleRepository.save(article);
    }

    public List<ArticleModel> getAllArticles(){
        return this.articleRepository.findAll();
    }

    public ArticleModel getArticle(Long id){
        ArticleModel article = null;
        try {
            article = this.articleRepository.findById(id)
                    .orElseThrow(() -> new Exception("Article not found for this id :: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(article.getTitle());
        return article;
    }

    public ArticleModel deleteArticle(Long id){

        ArticleModel article = getArticle(id);
        this.articleRepository.delete(article);
        return article;
    }



}
