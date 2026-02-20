package Logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import DAO.AssetsDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;

public class RecordAssetsLogic {
	public boolean RecordAssets(int userId, int assetsCategoryId, int amount, Date date)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		boolean result = false;

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			AssetsDAO assetsDAO = new AssetsDAO(con);
			result = assetsDAO.recordAssets(userId, assetsCategoryId, amount, date);

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
