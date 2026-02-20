package Logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import DAO.AssetsDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Assets;

public class sumIncomeAmountLogic {
	public Assets sumIncomeAmount(int userId, Date date)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		Assets result = new Assets();

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			AssetsDAO assetsDAO = new AssetsDAO(con);
			result = assetsDAO.sumIncomeAmount(userId, date);

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
		return result;
	}
}
