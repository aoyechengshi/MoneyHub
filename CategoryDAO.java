package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Category;

public class CategoryDAO {

	private Connection con;

	// DB接続
	public CategoryDAO(Connection connection) {
		this.con = connection;
	}

	// 変動費カテゴリ取得
	public ArrayList<Category> getVariableCategories(int userId) throws SQLException {
		ArrayList<Category> list = new ArrayList<>();
		String sql = "SELECT category_id, type, name, budget FROM categories WHERE user_id = ? AND type='変動費'";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Category category = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId); // user_idをセット
			res = stmt.executeQuery();

			while (res.next()) {
				category = new Category();
				category.setCategoryId(res.getInt("category_id"));
				category.setType(res.getString("type"));
				category.setName(res.getString("name"));
				category.setBudget(res.getInt("budget"));
				list.add(category);
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}

	// 固定費カテゴリ取得
	public ArrayList<Category> getFixedCategoryCategories(int userId) throws SQLException {
		ArrayList<Category> list = new ArrayList<>();
		String sql = "SELECT category_id, type, name, budget FROM categories WHERE user_id = ? AND type='固定費'";
		PreparedStatement stmt = null;
		ResultSet res = null;
		Category category = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId); // user_idをセット
			res = stmt.executeQuery();

			while (res.next()) {
				category = new Category();
				category.setCategoryId(res.getInt("category_id"));
				category.setType(res.getString("type"));
				category.setName(res.getString("name"));
				category.setBudget(res.getInt("budget"));
				list.add(category);
			}

		} finally {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}

	// 固定費追加カテゴリ
	public boolean insertFixedCategoryCategories(int userId, String name, int budget) throws SQLException {
		String sql = "INSERT INTO categories (user_id,type,name,budget) VALUES (?,?,?,?)";
		PreparedStatement stmt = null;
		boolean result = false;
		String type = "固定費";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId); //
			stmt.setString(2, type); //
			stmt.setString(3, name); //
			stmt.setInt(4, budget); //
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

	// カテゴリ削除
	public boolean deleteFixedCategoryCategories(int userId, int categoryId) throws SQLException {
		String sql = "DELETE FROM categories WHERE category_id =? AND user_id =?";
		PreparedStatement stmt = null;
		boolean result = false;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, categoryId); //
			stmt.setInt(2, userId); //
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

	// 変動費追加カテゴリ
	public boolean insertVariableCategoryCategories(int userId, String name, int budget) throws SQLException {
		String sql = "INSERT INTO categories (user_id,type,name,budget) VALUES (?,?,?,?)";
		PreparedStatement stmt = null;
		boolean result = false;
		String type = "変動費";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, userId); //
			stmt.setString(2, type); //
			stmt.setString(3, name); //
			stmt.setInt(4, budget); //
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

	//予算の更新(Categoriesテーブル）
	public boolean updateCategoriesBudget(int userId, int categoryId, int budget)
			throws SQLException {
		boolean result = false;
		String sql = "UPDATE categories "
				+ "SET budget = ? "
				+ "WHERE category_id = ? "
				+ "AND user_id = ? ";

		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, budget);
			stmt.setInt(2, categoryId);
			stmt.setInt(3, userId);

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

	//予算の更新(monthlyテーブル）
	public boolean updateMonthlyBudget(int userId, int categoryId, int budget)
			throws SQLException {
		boolean result = false;
		String sql = "UPDATE monthly_budget "
				+ "SET budget = ? "
				+ "WHERE user_id = ? "
				+ "AND category_id = ? "
				+ "AND DATE_FORMAT(target_month, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m');";

		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, budget);
			stmt.setInt(2, userId);
			stmt.setInt(3, categoryId);

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
