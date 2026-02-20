package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.User;

public class UserDAO {
	private Connection con;

	// DB接続
	public UserDAO(Connection connection) {
		this.con = connection;
	}

	// 検索メソッド
	public User findUserId(int userId) throws SQLException {
		String sql = "SELECT user_id,user_name FROM users WHERE user_id=?";
		PreparedStatement stmt = null;
		ResultSet res = null;
		User user = null;

		try {
			//PreparedStatementの設定
			stmt = con.prepareStatement(sql);
			//パラメータの設定
			stmt.setInt(1, userId);
			//SQLの実行
			res = stmt.executeQuery();

			//結果セットから情報を取り出す。
			if (res.next()) {
				user = new User(res.getInt("user_id"),
						res.getString("user_name"));
			}
			// クローズ処理
		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		return user;
	}
}
