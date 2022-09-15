package rmit.rmitsb.crawl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rmit.rmitsb.model.ArticleModel;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PostToArticleTable {
    private final String url = "jdbc:postgresql://localhost:5432/ios";
    private final String user = "postgres";
    private final String password = "Nhatuthien3110";



    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public long insertArticle(ArticleModel article) {
        String SQL = "INSERT INTO article_model(title,img_src,direct_url,category,content,time,author)"
                + "VALUES(?,?,?,?,?,?,?)";

        long id = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            ArticleModel articleModel = article;
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getImgSrc());
            pstmt.setString(3, article.getDirectUrl());
            pstmt.setString(4, article.getCategory());
            pstmt.setString(5, article.getContent());
            pstmt.setString(6,article.getTime());
            pstmt.setString(7,article.getAuthor());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Connect: "+ ex.getMessage());
        }

        System.out.println("Inserted " + id);
        return id;
    }

    //@Scheduled(fixedDelay = 1000 * 60 * 5)
    public void task() throws IOException, ParseException{
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

        CrawlModelManager crawlModelManager  = new CrawlModelManager() ;

        for (String category: categories) {
            List<ArticleModel> articleModel = new ArrayList<>();
            articleModel = crawlModelManager.crawlRSS(category);

            if (articleModel == null) continue;

            System.out.println(articleModel.get(0).getTitle());
            PostToArticleTable  pta = new PostToArticleTable();
            System.out.println(articleModel.size());

            for (int i = 0 ;i< articleModel.size();i++) {
                pta.insertArticle(articleModel.get(i));
            }
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
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

        CrawlModelManager crawlModelManager  = new CrawlModelManager() ;

        for (String category: categories) {
            List<ArticleModel> articleModel = new ArrayList<>();
            articleModel = crawlModelManager.crawlRSS(category);

            if (articleModel == null) continue;

            System.out.println(articleModel.get(0).getTitle());
            PostToArticleTable  pta = new PostToArticleTable();
            System.out.println(articleModel.size());

            for (int i = 0 ;i< articleModel.size();i++) {
                pta.insertArticle(articleModel.get(i));
            }
        }
    }
}