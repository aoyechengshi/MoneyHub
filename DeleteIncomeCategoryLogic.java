package Logic;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.IncomeCategoryDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;

public class DeleteIncomeCategoryLogic {
	public boolean deleteIncomeCategory(int userId, String categoryId)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		boolean result = false;

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			IncomeCategoryDAO incomecategoryDAO = new IncomeCategoryDAO(con);
			result = incomecategoryDAO.deleteIncomeCategory(userId, categoryId);

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
