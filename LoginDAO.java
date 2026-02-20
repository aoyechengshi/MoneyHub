package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private Connection connection;

	// DB接続
	public LoginDAO(Connection connection) {
		this.connection = connection;
	}
	// ユーザー照合メソッド
		public boolean findById(int userId, String password) throws SQLException {
			String sql = "SELECT user_id FROM Login WHERE user_id = ? and password= ?";
			// SQL文をDBに送るためのオブジェクトを保存する変数
			PreparedStatement pstmt = null;
			// SQLの実行結果を保持する変数
			ResultSet rs = null;
			boolean result = false;

			try {
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, userId);
				pstmt.setString(2, password);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					result = true;
				}

				// クローズ処理
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			}

			return result;
		}
}

