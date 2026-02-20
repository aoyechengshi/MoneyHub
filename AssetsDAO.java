package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Assets;

public class AssetsDAO {

	private Connection con;

	// DB接続
	public AssetsDAO(Connection connection) {
		this.con = connection;
	}

	// 固定費合計
	public Assets amountGetFixedExpense(int userId, Date date) throws SQLException {
		String sql = "SELECT "
				+ "	COALESCE(SUM(e.amount), 0) AS fixed_total "
				+ "FROM expenses e "
				+ "JOIN categories c "
				+ "	ON e.category_id = c.category_id "
				+ "WHERE e.user_id = ? "
				+ "AND c.type = '固定費' "
				+ "AND DATE_FORMAT(e.date, '%Y-%m') = DATE_FORMAT(?, '%Y-%m') ";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Assets assets = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setDate(2, date);
			res = stmt.executeQuery();

			while (res.next()) {
				assets = new Assets();
				assets.setFixedExpense(res.getInt("fixed_total"));
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return assets;
	}

	// 変動費合計
	public Assets amountGetVariableExpense(int userId, Date date) throws SQLException {
		String sql = "SELECT "
				+ "	COALESCE(SUM(e.amount), 0) AS variable_total "
				+ "FROM expenses e "
				+ "JOIN categories c "
				+ "	ON e.category_id = c.category_id "
				+ "WHERE e.user_id = ? "
				+ "AND c.type = '変動費' "
				+ "AND DATE_FORMAT(e.date, '%Y-%m') = DATE_FORMAT(?, '%Y-%m') ";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Assets assets = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setDate(2, date);
			res = stmt.executeQuery();

			while (res.next()) {
				assets = new Assets();
				assets.setVariableExpense(res.getInt("variable_total"));
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return assets;
	}

	// 変動費カテゴリ別集計
	public ArrayList<Assets> categoryGetVariableExpense(int userId, Date date) throws SQLException {
		String sql = "SELECT "
				+ "	c.category_id, "
				+ "	c.name, "
				+ "	COALESCE(SUM(e.amount), 0) AS total_amount "
				+ "FROM categories c "
				+ "LEFT JOIN expenses e "
				+ "	ON c.category_id = e.category_id "
				+ "	AND DATE_FORMAT(e.date, '%Y-%m') = DATE_FORMAT(?, '%Y-%m') "
				+ "	AND e.user_id = ? "
				+ "WHERE c.user_id = ? "
				+ "AND c.type = '変動費' "
				+ "GROUP BY c.category_id, c.name "
				+ "ORDER BY c.category_id; ";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Assets assets = null;
		ArrayList<Assets> List = new ArrayList<>();

		try {
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, date);
			stmt.setInt(2, userId);
			stmt.setInt(3, userId);
			res = stmt.executeQuery();

			while (res.next()) {
				assets = new Assets();
				assets.setCategoryVariableAmount(res.getInt("total_amount"));
				assets.setCategoryName(res.getString("name"));
				List.add(assets);
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return List;
	}

	// 収入合計
	public Assets sumIncomeAmount(int userId, Date date) throws SQLException {
		String sql = "SELECT "
				+ "  COALESCE(SUM(amount), 0) AS income_total "
				+ "FROM income "
				+ "WHERE user_id = ? "
				+ "AND DATE_FORMAT(income_date, '%Y-%m') = DATE_FORMAT(?, '%Y-%m') ";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Assets assets = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setDate(2, date);
			res = stmt.executeQuery();

			while (res.next()) {
				assets = new Assets();
				assets.setSumIncomeAmount(res.getInt("income_total"));
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return assets;
	}

	// 総資産カテゴリ別取得
	public ArrayList<Assets> assetsCategoryTotalAmount(int userId) throws SQLException {
		String sql = "SELECT "
				+ "    c.assets_category_id, "
				+ "    c.assets_name, "
				+ "    SUM(a.amount) AS total_amount "
				+ "FROM assets a "
				+ "JOIN assets_category c "
				+ "  ON a.assets_category_id = c.assets_category_id "
				+ "WHERE a.user_id = ? "
				+ "GROUP BY "
				+ "    c.assets_category_id, "
				+ "    c.assets_name "
				+ "ORDER BY "
				+ "    c.assets_category_id;";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Assets assets = null;
		ArrayList<Assets> List = new ArrayList<>();

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			res = stmt.executeQuery();

			while (res.next()) {
				assets = new Assets();
				assets.setAssetsCategoryId(res.getInt("assets_category_id"));
				assets.setCategoryName(res.getString("assets_name"));
				assets.setAmount(res.getInt("total_amount"));
				List.add(assets);
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return List;
	}

	// 総資産を記録するメソッド
	public boolean recordAssets(int userId, int assetsCategoryId, int amount, Date date) throws SQLException {
		String sql = "INSERT INTO assets (assets_category_id, user_id, amount, record_date) " +
				"VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = null;
		boolean result = false;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, assetsCategoryId);
			stmt.setInt(2, userId);
			stmt.setInt(3, amount);
			stmt.setDate(4, date);
			int rows = stmt.executeUpdate();

			if (rows > 0) {
				result = true;
			}

		} finally {

			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}

	// 総資産カテゴリ別名前取得
	public ArrayList<Assets> FindAssetsCategory(int userId) throws SQLException {
		String sql = "SELECT "
				+ "                assets_category_id, "
				+ "                assets_name "
				+ "            FROM assets_category "
				+ "            WHERE user_id = ? "
				+ "            ORDER BY assets_category_id";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Assets assets = null;
		ArrayList<Assets> List = new ArrayList<>();

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			res = stmt.executeQuery();

			while (res.next()) {
				assets = new Assets();
				assets.setAssetsCategoryId(res.getInt("assets_category_id"));
				assets.setCategoryName(res.getString("assets_name"));
				List.add(assets);
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return List;
	}

	// 総資産項目を増やすメソッド
	public boolean InsertAssets(int userId, String categoryName) throws SQLException {
		String sql = "INSERT INTO assets_category (user_id, assets_name) " +
				"VALUES (?, ?)";
		PreparedStatement stmt = null;
		boolean result = false;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setString(2, categoryName);
			int rows = stmt.executeUpdate();

			if (rows > 0) {
				result = true;
			}

		} finally {

			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}
}
