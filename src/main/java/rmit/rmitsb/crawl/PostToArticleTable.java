package rmit.rmitsb.crawl;

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
        String SQL = "INSERT INTO article_model(title,img_src) "
                + "VALUES(?,?)";

        long id = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            ArticleModel articleModel = article;
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getImgSrc());

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
        return id;
    }

    public static void main(String[] args) throws IOException, ParseException {
        CrawlModelManager crawlModelManager  = new CrawlModelManager() ;
        List<ArticleModel> articleModel = new ArrayList<>();
        articleModel = crawlModelManager.crawlRSS("Entertainment");
        System.out.println(articleModel.get(0).getTitle());
        PostToArticleTable  pta = new PostToArticleTable();
        for (int i = 0 ;i< articleModel.size();i++) {
            pta.insertArticle(articleModel.get(i));
        }
    }
}