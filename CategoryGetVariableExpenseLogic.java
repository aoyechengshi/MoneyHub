package Logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.AssetsDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Assets;

public class CategoryGetVariableExpenseLogic {
	public ArrayList<Assets> AnalysisExpense(int userId, Date date)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		ArrayList<Assets> List = new ArrayList<>();

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			AssetsDAO assetsDAO = new AssetsDAO(con);
			List = assetsDAO.categoryGetVariableExpense(userId, date);

		} catch (SQLException e) {
			//データベースエラーの場合
			e.printStackTrace();
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
		return List;
	}
}
