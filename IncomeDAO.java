package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Income;

public class IncomeDAO {
	private Connection con;

	// DB接続
	public IncomeDAO(Connection connection) {
		this.con = connection;
	}

	//収入取得メソッド
	public ArrayList<Income> findIncome(int userId) throws SQLException {
		String sql = "SELECT i.income_id, i.amount, i.income_date, i.note, t.category FROM income i JOIN incometype t ON i.categoryincome_id = t.categoryincome_id WHERE i.user_id = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Income> List = new ArrayList<>();
		Income income = null;

		try {
			// PreparedStatement
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				income = new Income();
				income.setIncomeId(rs.getInt("income_id"));
				income.setCategory(rs.getString("category"));
				income.setAmount(rs.getInt("amount"));
				income.setDate(rs.getDate("income_date"));
				income.setNote(rs.getString("note"));
				List.add(income);
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}

		}
		return List;
	}
	
	//収入記録メソッド
		public boolean recordIncome(int categoryIncomeId, int userId, int amount, String note, java.sql.Date date)
				throws SQLException {
			String sql = "INSERT INTO income (user_id, categoryincome_id, amount, income_date, note ) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = null;
			ResultSet rs = null;
			boolean result = false;

			try {
				// PreparedStatement 作成（自動採番取得可能にする）
				stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, userId);
				stmt.setInt(2, categoryIncomeId);
				stmt.setInt(3, amount);
				stmt.setDate(4, date);
				stmt.setString(5, note);

				int rows = stmt.executeUpdate();

				if (rows > 0) {
					result = true;
				}
			} finally {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			}

			return result;
		}
		
		//収入変更メソッド
		public boolean updateIncome(int incomeId, int userId, int amount, String note, java.sql.Date date)
				throws SQLException {
			String sql = "UPDATE income SET amount = ? ,note = ?,income_date = ? WHERE income_id = ? AND user_id = ?";
			PreparedStatement stmt = null;
			boolean result = false;

			try {
				// PreparedStatement 作成（自動採番取得可能にする）
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, amount);
				stmt.setString(2, note);
				stmt.setDate(3, date);
				stmt.setInt(4, incomeId);
				stmt.setInt(5, userId);

				int rows = stmt.executeUpdate();

				if (rows > 0) {
					result = true;
				}
			} finally {
				if (stmt != null)
					stmt.close();
			}

			return result;
		}
		
		//収入削除メソッド　
		public boolean deleteIncome(int incomeId)
				throws SQLException {
			String sql = "DELETE FROM income WHERE income_id = ? ";
			PreparedStatement stmt = null;
			boolean result = false;

			try {
				// PreparedStatement 作成（自動採番取得可能にする）
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, incomeId);
				
				int rows = stmt.executeUpdate();

				if (rows > 0) {
					result = true;
				}
			} finally {
				if (stmt != null)
					stmt.close();
			}

			return result;
		}
}
