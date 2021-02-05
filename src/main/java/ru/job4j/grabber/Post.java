package ru.job4j.grabber;

import java.time.LocalDateTime;

public class Post {
    private final String name;
    private final String text;
    private final String author;
    private final LocalDateTime created;

    public Post(String name, String text, String author, LocalDateTime created) {
        this.name = name;
        this.text = text;
        this.author = author;
        this.created = created;
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
}
