package rmit.rmitsb.crawl;/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2021B
  Assessment: Final Project
  Author: Chill Coders
  Acknowledgement:
  https://rmit.instructure.com/courses/88207/assignments/594559
*/

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import rmit.rmitsb.model.ArticleModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CrawlModelManager implements CrawlModel {
    private int bound = 50;
//    enum category {
//        BUSINESS,
//        COVID19,
//        ENTERTAINMENT,
//        NEWEST,
//        POLITIC,
//        PUBLISHER,
//        SPORT,
//        TECHNOLOGY,
//        WORLD
//    }
//    private final String[][] source = new String[9][20]{
//        {"https://vnexpress.net/rss/kinh-doanh.rss", "https://tuoitre.vn/rss/kinh-doanh.rss",
//    }

    @Override
    // This functions take in URL of an article to return cleaned HTML
    public String crawlArticleContent(String url) {
        Document doc;
        String dir = " @CrawlModelManager/crawl";

        // Get HTML with JSoup
        try {
            doc = Jsoup.connect(url).get();
            System.out.println(url + "connection looks find " + dir);
        } catch (IOException e) {
            System.out.println(" URL not found" + dir);
            throw new RuntimeException(e);
        }

        // Remove unnecessary tags
        String[] toBeRemoved = {
                "div.form-comment", "script", "footer", "noscript", "div.details__morenews", "div.wrapPopup",
                "div.details__author__meta", "div.breadcrumbs", "div.modal", "div.simplePopup", "div.site-header__grid",
                "div.col_right_news", "div.bread-crumbs", "div.content-left", "p.title-time-home", "div.menu-box",
                "div.social", "ul.social", "section.zone", "textarea", "div.float-ads floatbot", "div.social-signin",
                "div.banner", "input", "div.as-content", "div.network", "div.content-bottom-detail", "div[id=stickyrm1bot]",
                "table.video", "div.box_embed_video_parent", "videolist", "videoitem", "[type*=RelatedOneNews]",
                "div.tab-moreview", "style", "div.title-content clearfix", "meta", "link", "ul.parent", "span", "button",
                "div[id=myvne_taskbar_tmp]", "div[id=ctl00_main_divSidebar]", "div[id=abde]", "div[type='RelatedNews']",
                "div.tagandnetwork", "div.footer_vne", "div.section_footer", "ul.breadcrumb", "section[id='news-latest']",
                "div.nd_header", "div.header_link", "div.nd_header_menu", "div.dropdown-menu", "ul.pager", "div.panel-default",
                "div.cdn_top2", "details__meta", "div.native-ad", "div.col_footer", "div.details__bottombanner",
                "div[data-view='formcomment']", "p[data-view='nocomment']", "div.relate-container", "div.topbar",
                "div.box_input", "div.section_menu", "div.banner-ads", "div.pull-right", "div.btn-group", "ul",
                "ul.list-news", "ul.row", "div.z-widget-corona", "div[id='innerarticle']", "div.tags-scroller",
                "section.recommendation", "div[id='pushed_popup']", "div.firefox-warning", "p.the-article-tags",
                "section.sidebar", "svg", "nav", "iframe", "div.box_search_topbar", "ul.social_left", "div.util-box",
        };

        for (String cssQuery: toBeRemoved) {
            for (Element e: doc.select(cssQuery)) e.remove();
        }

        for (Element element : doc.select("picture")) {
            assert element.parent() != null;
            element.parent().removeAttr("fig-picture").removeAttr("style");
            String img = element.select("[data-srcset]").attr("data-srcset").split(" 1x")[0];
            Element in = new Element("div").attr("class", "fig-picture");
            in.appendElement("img").attr("src", img).attr("style", "width:60%;height:60%");
            element.replaceWith(in);
        }

        for (Element element : doc.select("img")) {
            String img = element.select("[data-src]").attr("data-src").isEmpty() ? element.select("[src]").attr("src") : element.select("[data-src]").attr("data-src");
            Element in = new Element("div").attr("class", "fig-picture");
            in.appendElement("img").attr("src", img).attr("style", "width:60%;height:60%");
            element.replaceWith(in);
        }

        for (Element element : doc.select("a")) {
            element.removeAttr("href");
        }
        for (Element element : doc.select("div.block_thumb_slide_show")) {
            element.removeAttr("style");
        }
        for (Element element : doc.select("div.row")) {
            element.removeAttr("style");
        }
        for (Element element : doc.select("a")) {
            if (element.select("[src]").isEmpty()) element.remove();
        }

        for (Element element : doc.getAllElements()) {
            // System.out.println("--"+element.html());
            element.removeAttr("class").removeAttr("id").removeAttr("muted");
            element.attr("autoplay", true).attr("controls", true).attr("muted", true).attr("allowfullscreen", true);
        }

        for (Element element : doc.select("p")) {
            element.attr("style", "font-size: 28px; font-family:sans-serif;");
        }
        for (Element element : doc.select("div[iarticleModelrop='articleBody']")) {
            element.select("div").attr("style", "font-size: 28px; font-family:sans-serif;");
        }
        for (Element element : doc.select("div")) {
            element.select("div").attr("style", "font-size: 28px; font-family:sans-serif;");
        }

        for (Element element : doc.select("h1")) {
            element.attr("style", "font-size: 28px;font-family:sans-serif;");
        }
        for (Element element : doc.select("h2")) {
            element.attr("style", "font-size: 28px;font-family:sans-serif;");
        }
        for (Element element : doc.select("a")) {
            if (element.select("img").isEmpty()) element.remove();
        }

        Objects.requireNonNull(doc.select("body").last()).after("<div style='height:100px;'>");
        Objects.requireNonNull(doc.select("body").first()).attr("style", "width:80%; text-align: justify; text-justify: inter-word;");

        String HTML = doc.html();
        System.out.println("Return HTML successfully" + dir);
        return HTML;
    }

    public List<ArticleModel> crawlArticleList(String instruction) throws IOException, ParseException {
        String dir = " @CrawlModelManager/Crawl(instruction)";
        bound = 50000;
        if (instruction.contains("Crawl") && instruction.split(" ").length > 1) {
            return crawlRSS(instruction.split(" ")[1]);
        }

        System.out.println("Wrong instruction format " + "'" + instruction + "'" + " - show this instruction to the author " + dir);
        System.out.println("Return null List<ArticleModel>" + dir);
        return null;
    }

    private ArticleModel setUpArticleModel(Element e, String provider) throws IOException, ParseException {
        // Initialize with article's title and URL.
        ArticleModel articleModel = provider.contains("Zing") ?
                new ArticleModel(e.select("image|caption").text(), e.select("loc").text())
                : new ArticleModel(e.select("title").text(), e.select("link").text());

        String[] s1;
        //Extract thumbnail
        if (provider.contains("Zing"))
            //Zing's thumbnail
            articleModel.setImgSrc(e.select("image|loc").text());
        if (!provider.contains("Nhandan") && !provider.contains("Zing"))
            //Tuoitre's, Vnexpress', and Thanhnien's thumbnail
            if (e.text().contains("src")) {
                s1 = e.select("description").text().split("src");
                articleModel.setImgSrc(s1[1].split(Character.toString((char) 34))[1]);
            }
        if (provider.contains("Nhandan")) {
            //Nhandan's thumbnail
            articleModel.setImgSrc(e.select("enclosure").attr("url"));
            articleModel.setImgSrc("https://i.ibb.co/tJNKY1Y/placeholder.png");
        }
        if (articleModel.getImgSrc() == null) {
            //Cannot find any thumbnails
            articleModel.setImgSrc("https://i.ibb.co/tJNKY1Y/placeholder.png");
        }

        // Get current date for Calendar
        String time = e.select("pubDate").text() + e.select("lastmod").text();
        Date dt;
        if (e.select("pubDate").text().isEmpty()) {
            dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH).parse(time);
        } else {
            if (e.select("pubDate").text().contains("GMT")) {
                dt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH).parse(time.split("GMT")[0]);
            } else {
                dt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ssZ").parse(time);
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);

        // Process time format
        long ms = System.currentTimeMillis() - calendar.getTimeInMillis();
        long seconds = ms / 1000;
        long minutes = seconds / 60;
        long hours = seconds / (60 * 60);
//        if (minutes <= 1) articleModel.setTime(minutes + " minute ago");
//        else if (hours < 1) articleModel.setTime(minutes + " minutes ago");
//        else if (hours == 1) articleModel.setTime(hours + " hour ago");
//        else if (hours <= 48) articleModel.setTime(hours + " hours ago");
//        else articleModel.setTime((hours / 24) + " days ago");
//        articleModel.setT(seconds);

//        // Logo for different papers
//        if (provider.contains("Vnexpress")) articleModel.setPublisherImage("https://i.ibb.co/vmNV6Zv/vnexpress.png");
//        else if (provider.contains("Zing")) articleModel.setPublisherImage("https://i.ibb.co/J2C429c/Zing.png");
//        else if (provider.contains("TuoiTre")) articleModel.setPublisherImage("https://i.ibb.co/w4zbYX9/tuoitre.png");
//        else if (provider.contains("Thanhnien")) articleModel.setPublisherImage("https://i.ibb.co/tCzZ1Y0/thanhnien.png");
//        else if (provider.contains("Nhandan")) articleModel.setPublisherImage("https://i.ibb.co/PC70NVZ/nhandan.png");

        // Add new article
        return articleModel;
    }

    // Publisher.txt is one of this function's input
    public List<ArticleModel> crawlRSS(String file) throws IOException, ParseException {
        String dir = " @CrawlModelManager/Crawl1(file)";
        List<ArticleModel> articles = new ArrayList<>();

        Scanner sc;
        File path = new File("src/main/java/rmit/rmitsb/text/"+file+".txt");
        sc = new Scanner(path);
        // Process all URLs in text file
        while (sc.hasNext()) {
            Document doc;
            String st = sc.next();

            System.out.println(st);

            // Split by ','
            String[] s = st.split(",");
            String URL = s[2];
            String provider = s[0];

            // Connect to get content with JSoup
            try {
                doc = Jsoup.connect(URL).get();
                System.out.println(URL + " connection looks fine" + dir);
            } catch (IOException e) {
                System.out.println("cannot get Jsoup connect" + dir);
                System.out.println("Return null List<ArticleModel>" + dir);
                return null;
            }

            // Get all articles in RSS
            Elements pageContent = provider.contains("Zing") ?
                    doc.getElementsByTag("url") : doc.getElementsByTag("item");
            for (Element e : pageContent) {
                bound = bound - 1;
                if (bound == 0 || bound % 20 == 0) break;
                articles.add(setUpArticleModel(e, provider));
            }
        }

        sc.close();  // close the scanner
        return articles;
    }
}
