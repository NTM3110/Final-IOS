package rmit.rmitsb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ArticleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String imgSrc;
    private String directUrl;

    public ArticleModel(){

    }
    public ArticleModel(String title, String directUrl) {
        this.title = title;
        this.directUrl = directUrl;
    }
    public String getDirectUrl() {
        return directUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }

    public String getImgSrc(){return imgSrc;}
    public String getTitle() { return title; }
}
