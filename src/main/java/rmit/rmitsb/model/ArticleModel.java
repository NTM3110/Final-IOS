package rmit.rmitsb.model;

import javax.persistence.*;

@Entity
public class ArticleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private String imgSrc;

    @Column
    private String directUrl;

    @Column
    private String category;

    @Column
    private String content;

    @Column
    private String time;

    @Column
    private String author;

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

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getImgSrc(){return imgSrc;}

    public String getTitle() { return title; }

    public String getCategory() {return category;}

    public String getTime() {
        return time;
    }

    public void setCategory(String category) {this.category = category;}
    public void setTime(String time){this.time = time;}
    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}
}
