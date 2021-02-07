package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Post post = postDetail("https://www.sql.ru/forum/1325330/"
               + "lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        System.out.println(post);
        /*int i = 1;
        while (i < 6) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers" + "/" + i).get();
            Elements row = doc.select(".postslisttopic");
            Elements dates = doc.select(".altCol");
            int index = 1;
            for (Element td : row) {
                Element href = td.child(0);
                Element date = dates.get(index);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(stringToDate(date.text()));
                index += 2;
            }
            i++;
        }*/
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

    public static Post postDetail(String url) {
        Post result = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements header = doc.select(".messageHeader");
            Elements body = doc.select(".msgBody");
            Elements footer = doc.select(".msgFooter");
            String name = header.get(0).text();
            String text = body.get(1).text();
            String author = body.get(0).child(0).text();
            String date =  footer.get(0).text().split("\\s\\[")[0];
            result = new Post(name, text, author, stringToDate(date));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result;
    }
}
