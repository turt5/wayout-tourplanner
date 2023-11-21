package wayout.files.Dashboard;

import java.sql.*;

public class DatabaseUtils {
    public int getRowCount(String tableName) throws Exception {
        int rowCount = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String url = "jdbc:mysql://127.0.0.1/wayout";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);

            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return rowCount;
    }
}
