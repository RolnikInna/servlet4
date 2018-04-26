package ru.mirea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26.04.2018.
 */
public class DataBaseTodoModel implements TodoModel {
    @Override
    public List<TodoItem> getItems() {
        List<TodoItem> items = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/todo")) {
            String sql = "select id, text from todo order by id";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                    {
                        int id = rs.getInt(1);
                        String text = rs.getString(2);
                        items.add(new TodoItem(id, text));

                    }
                }


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    @Override
    public void add(String text) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/todo")) {
            String sql = "insert into todo (text) values (?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, text);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/todo")) {
            String sql = "delete from todo where id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/todo")) {
            String sql = "create table todo (ID int auto_increment primary key, Text varchar (100))";
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private class TodoItems {
    }
}
