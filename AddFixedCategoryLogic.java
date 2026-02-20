package Logic;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.CategoryDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;

public class AddFixedCategoryLogic {
	public boolean addFixedCategory(int userId, String name, int budget)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		boolean result = false;

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			CategoryDAO categoryDAO = new CategoryDAO(con);
			result = categoryDAO.insertFixedCategoryCategories(userId, name, budget);

			if (result == false) {
				throw new UserBusinessException("追加できませんでした。");
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
