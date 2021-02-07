package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            result = new Post(name, text, author, stringToDate(date), link);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result;
    }

    public static LocalDateTime stringToDate(String date) {
        String[] firstSplit = date.split(", ");
        if (firstSplit.length != 2) {
            throw new IllegalArgumentException();
        }
        String[] hhmm = firstSplit[1].split(":");
        int hour = Integer.parseInt(hhmm[0]);
        int minute = Integer.parseInt(hhmm[1]);
        if ("сегодня".equals(firstSplit[0])) {
            return LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
        }
        if ("вчера".equals(firstSplit[0])) {
            return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(hour, minute));
        }
        String[] ddmmyy = firstSplit[0].split(" ");
        String[] months = {"янв", "фев", "мар", "апр", "май",
                "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        int year = Integer.parseInt("20" + ddmmyy[2]);
        int month = 0;
        int day = Integer.parseInt(ddmmyy[0]);
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(ddmmyy[1])) {
                month = i + 1;
                break;
            }
        }
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse();
        List<Post> list = sqlRuParse.list("https://www.sql.ru/forum/job-offers");
        for (Post post : list) {
            System.out.println(post);
        }
    }
}
