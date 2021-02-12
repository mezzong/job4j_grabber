package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String link) {
        List<Post> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                result.add(detail(href.attr("href")));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result;
    }

    @Override
    public Post detail(String link) {
        Post result = null;
        try {
            Document doc = Jsoup.connect(link).get();
            Elements header = doc.select(".messageHeader");
            Elements body = doc.select(".msgBody");
            Elements footer = doc.select(".msgFooter");
            String name = header.get(0).text();
            String text = body.get(1).text();
            String author = body.get(0).child(0).text();
            String date =  footer.get(0).text().split("\\s\\[")[0];
            result = new Post(name, text, author, Converter.stringToDate(date), link);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse();
        List<Post> list = sqlRuParse.list("https://www.sql.ru/forum/job-offers");
        for (Post post : list) {
            System.out.println(post);
        }
    }
}
