package rmit.rmitsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.rmitsb.model.ArticleModel;
import rmit.rmitsb.service.ArticleService;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(path = "/students", method = RequestMethod.GET)
    public List<ArticleModel> getAllArticles(){
        return articleService.getAllArticles();
    }

    @RequestMapping(path = "/students", method = RequestMethod.POST)
    public void addArticle(@RequestBody ArticleModel  article){
         articleService.saveArticle(article);
    }

    @RequestMapping(path = "/students", method = RequestMethod.PUT)
    public void updateArticle(@RequestBody ArticleModel article){
        articleService.saveArticle(article);
    }

    @RequestMapping(path = "/students/{id}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable(value = "id") Long id){
        articleService.deleteArticle(id);
    }

    @RequestMapping(path = "/students/{id}", method = RequestMethod.GET)
    public ArticleModel getArticle(@PathVariable(value = "id") Long id){
        return articleService.getArticle(id);
    }
}
