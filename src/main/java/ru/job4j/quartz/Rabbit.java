package ru.job4j.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.*;

public class Rabbit implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("store");
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("insert into rabbit(created_date) values (?)")) {
            preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
