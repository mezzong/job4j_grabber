package ru.job4j.grabber;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private final String name;
    private final String text;
    private final String author;
    private final LocalDateTime created;
    private final String link;

    public Post(int id, String name, String text, String author,
                LocalDateTime created, String link) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.author = author;
        this.created = created;
        this.link = link;
    }

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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", text='" + text + '\''
                + ", author='" + author + '\''
                + ", created=" + created
                + ", link='" + link + '\''
                + '}';
    }
}
