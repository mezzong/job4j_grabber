package ru.job4j.grabber;

import java.time.LocalDateTime;

public class Post {
    private final String name;
    private final String text;
    private final String author;
    private final LocalDateTime created;
    private final String link;

    public Post(String name, String text, String author, LocalDateTime created, String link) {
        this.name = name;
        this.text = text;
        this.author = author;
        this.created = created;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getUrl() {
        return link;
    }

    @Override
    public String toString() {
        return "Post{"
                + "name='" + name + '\''
                + ", text='" + text + '\''
                + ", author='" + author + '\''
                + ", created=" + created
                + ", link='" + link + '\''
                + '}';
    }
}
