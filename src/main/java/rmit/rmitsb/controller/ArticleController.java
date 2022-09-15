package rmit.rmitsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmit.rmitsb.model.ArticleModel;
import rmit.rmitsb.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(path = "/articles", method = RequestMethod.GET)
    public List<ArticleModel> getAllArticles(Pageable pageable, @RequestParam(value = "category", required = false) String category){
        return articleService.getAllArticles(pageable, category);
    }

    @RequestMapping(path = "/articles", method = RequestMethod.POST)
    public void addArticle(@RequestBody ArticleModel  article){
         articleService.saveArticle(article);
    }

    @RequestMapping(path = "/articles", method = RequestMethod.PUT)
    public void updateArticle(@RequestBody ArticleModel article){
        articleService.saveArticle(article);
    }

    @RequestMapping (path = "/articles/{id}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable(value = "id") Long id){
        articleService.deleteArticle(id);
    }

    @RequestMapping (path = "/articles/", method = RequestMethod.DELETE)
    public void deleteArticles(){
        articleService.deleteAll();
    }

    @RequestMapping(path = "/articles/{id}", method = RequestMethod.GET)
    public ArticleModel getArticle(@PathVariable(value = "id") Long id){
        return articleService.getArticle(id);
    }

    @GetMapping(path = "/categories")
    public List<String> getCategories(){
        ArrayList<String> categories = new ArrayList<String>() {
            {
                add("Entertainment");
                add("Covid-19");
                add("Newest");
                add("Politic");
                add("Publisher");
                add("Sport");
                add("Technology");
                add("World");
            }
        };
        return categories;
    }

}
