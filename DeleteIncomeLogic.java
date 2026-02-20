package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.IncomeDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Income;

public class DeleteIncomeLogic {

	public ArrayList<Income>deleteIncome(int incomeId, int userId) throws UserBusinessException, UserSystemException {
		Connection con = null;
		ArrayList<Income> incomeList = null;

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			IncomeDAO incomeDAO = new IncomeDAO(con);
			incomeDAO.deleteIncome(incomeId);
			
			//全収入を取得
			incomeList = incomeDAO.findIncome(userId);

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
		return incomeList;

	}
}
