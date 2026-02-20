package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.AssetsDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Assets;

public class AssetsCategoryTotalAmountLogic {
	public ArrayList<Assets> AssetsCategoryTotalAmount(int userId)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		ArrayList<Assets> result = new ArrayList<>();

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			AssetsDAO assetsDAO = new AssetsDAO(con);
			result = assetsDAO.assetsCategoryTotalAmount(userId);

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
