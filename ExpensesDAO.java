package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;

import entity.Expenses;

public class ExpensesDAO {
	private Connection con;

	// DB接続
	public ExpensesDAO(Connection connection) {
		this.con = connection;
	}

	//出費記録メソッド
	public boolean recordExpenses(int categoryId, int userId, int amount, String memo, java.sql.Date date)
			throws SQLException {
		String sql = "INSERT INTO expenses (user_id, category_id, amount, memo, date) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = null;
		ResultSet generatedKeys = null;
		boolean result = false;

		try {
			// PreparedStatement 作成（自動採番取得可能にする）
			stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, userId);
			stmt.setInt(2, categoryId);
			stmt.setInt(3, amount);
			stmt.setString(4, memo);
			stmt.setDate(5, date);

			int rows = stmt.executeUpdate();

			if (rows > 0) {
				result = true;
			}
		} finally {
			if (generatedKeys != null)
				generatedKeys.close();
			if (stmt != null)
				stmt.close();
		}

		return result;
	}

	//出費検索メソッド
	public ArrayList<Expenses> findExpenses(int userId)
			throws SQLException {

		String sql = "SELECT e.expense_id, "
				+ "       e.category_id, "
				+ "       e.amount, "
				+ "       e.date, "
				+ "       e.memo, "
				+ "       c.name AS category_name, "
				+ "       c.type AS category_type "
				+ "FROM expenses e "
				+ "JOIN categories c "
				+ "  ON e.category_id = c.category_id "
				+ "WHERE e.user_id = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Expenses> list = new ArrayList<>();

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Expenses expense = new Expenses();
				expense.setExpenseId(rs.getInt("expense_id"));
				expense.setCategoryId(rs.getInt("category_id"));
				expense.setAmount(rs.getInt("amount"));
				expense.setDate(rs.getDate("date"));
				expense.setMemo(rs.getString("memo"));
				expense.setName(rs.getString("category_name"));
				expense.setType(rs.getString("category_type"));
				list.add(expense);
			}

		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}

		return list;
	}

	//出費記録変更メソッド
	public boolean updateExpenses(int expenseId, int amount, Date date, String memo, int userId)
			throws SQLException {
		String sql = "UPDATE expenses SET amount = ?, date = ?, memo = ?  WHERE user_id = ? AND expense_id = ?";
		PreparedStatement stmt = null;

		boolean result = false;

		try {
			// PreparedStatement 作成（自動採番取得可能にする）
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, amount);
			stmt.setDate(2, date);
			stmt.setString(3, memo);
			stmt.setInt(4, userId);
			stmt.setInt(5, expenseId);

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

	//出費削除メソッド
	public boolean deleteExpenses(int expenseId)
			throws SQLException {
		String sql = "DELETE FROM expenses WHERE expense_id = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			// PreparedStatement 作成（自動採番取得可能にする）
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, expenseId);

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

	public ArrayList<Expenses> analysisExpenses(int userId, Date date)
			throws SQLException {
		ArrayList<Expenses> list = new ArrayList<>();

		String sql = "SELECT c.name, mb.budget, COALESCE(SUM(e.amount), 0) AS actual " +
				"FROM categories c " +
				"JOIN monthly_budget mb " +
				"  ON c.category_id = mb.category_id AND mb.target_month = ? " +
				"LEFT JOIN expenses e " +
				"  ON c.category_id = e.category_id " +
				"  AND DATE_FORMAT(e.date, '%Y-%m') = DATE_FORMAT(?, '%Y-%m') " +
				"WHERE c.user_id = ? " +
				"AND c.type = '変動費' " +
				"GROUP BY c.category_id, c.name, mb.budget;";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, date);
			stmt.setDate(2, date);
			stmt.setInt(3, userId);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Expenses expense = new Expenses();
				expense.setName(rs.getString("name"));
				expense.setBudget(rs.getInt("budget"));
				expense.setAmount(rs.getInt("actual"));
				list.add(expense);
			}

		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}

		return list;
	}

	//月の初めに、先月の予算を今月の予算にコピーする
	public boolean setBudget(int userId, Date targetMonth)
			throws SQLException {
		boolean result = false;
		YearMonth target = YearMonth.from(targetMonth.toLocalDate());

		Date toMonth = Date.valueOf(target.atDay(1));

		String sql = "INSERT INTO monthly_budget ("
				+ "    user_id, "
				+ "    category_id, "
				+ "    target_month, "
				+ "    budget "
				+ ") "
				+ "SELECT "
				+ "    c.user_id, "
				+ "    c.category_id, "
				+ "    ?, "
				+ "    c.budget "
				+ "FROM categories c "
				+ "WHERE c.user_id = ? "
				+ "AND c.type = '変動費' "
				+ "ON DUPLICATE KEY UPDATE "
				+ "    budget = VALUES(budget); ";

		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, toMonth); // INSERT 用
			stmt.setInt(2, userId); // WHERE

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
