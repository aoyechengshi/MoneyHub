package ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// データベース接続
public class ConnectionManager {

    // データベース接続情報
    private static final String URL = "jdbc:mysql://localhost:3306/money?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "mysql";

    // Connection を取得するメソッド
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            // ← ここを追加
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバが見つかりません");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return con;
    }
}


