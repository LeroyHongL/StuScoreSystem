package stuscoresystem;

import stuscoresystem.mysql.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main2 {
    public static void main(String[] args) {
        try {
            Connection connection = DBUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM students";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
            // 关闭资源
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}