package ru.job4j.grabber;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        /* cnn = DriverManager.getConnection(...); */
        try {
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("login"),
                    cfg.getProperty("password")
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement preparedStatement = cnn.prepareStatement(
                "insert into post(name, author, text, link, created) values (?, ?, ?, ?, ?)")) {
                preparedStatement.setString(1, post.getName());
                preparedStatement.setString(2, post.getAuthor());
                preparedStatement.setString(3, post.getText());
                preparedStatement.setString(4, post.getUrl());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
                preparedStatement.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = cnn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("author"),
                            resultSet.getString("text"),
                            resultSet.getTimestamp("created").toLocalDateTime(),
                            resultSet.getString("link")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post result = null;
        try (PreparedStatement preparedStatement =
                     cnn.prepareStatement("select * from post where id = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                result = new Post(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("author"),
                        resultSet.getString("text"),
                        resultSet.getTimestamp("created").toLocalDateTime(),
                        resultSet.getString("link")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        try (InputStream in = PsqlStore.class.getClassLoader()
                .getResourceAsStream("grabber.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            PsqlStore psqlStore = new PsqlStore(properties);
            psqlStore.save(new Post("vac1", "au1", "text1", LocalDateTime.now(), "link1"));
            psqlStore.save(new Post("vac2", "au2", "text2", LocalDateTime.now(), "link2"));
            psqlStore.save(new Post("vac3", "au3", "text3", LocalDateTime.now(), "link3"));
            Post rpost = psqlStore.findById("1");
            System.out.println(rpost);
            List<Post> posts = psqlStore.getAll();
            System.out.println("All posts");
            for (Post p : posts) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}