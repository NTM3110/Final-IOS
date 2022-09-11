package rmit.rmitsb.crawl;

import rmit.rmitsb.model.ArticleModel;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CrawlModel {
    String crawlArticleContent(String url);
    List<ArticleModel> crawlArticleList(String instruction) throws IOException, ParseException;
}
