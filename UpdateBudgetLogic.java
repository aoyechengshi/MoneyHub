package Logic;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.CategoryDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;

public class UpdateBudgetLogic {

	//categoriesテーブルのbudget設定メソッド
	public boolean updateCategoriesBudget(int userId, int categoryId, int budget)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		boolean result = false;

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			CategoryDAO categoryDAO = new CategoryDAO(con);
			result = categoryDAO.updateCategoriesBudget(userId, categoryId, budget);

			if (result == false) {
				throw new UserBusinessException("更新に失敗しました。");
			}
		} catch (SQLException e) {
			//データベースエラーの場合
			throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
			}
		}
		return result;

	}
	
	public boolean updateMonthlyBudget(int userId, int categoryId, int budget)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		boolean result = false;

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			CategoryDAO categoryDAO = new CategoryDAO(con);
			result = categoryDAO.updateMonthlyBudget(userId, categoryId, budget);

			if (result == false) {
				throw new UserBusinessException("更新に失敗しました。");
			}
		} catch (SQLException e) {
			//データベースエラーの場合
			throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
			}
		}
		return result;

	}
}
