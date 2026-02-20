package Logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import DAO.AssetsDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Assets;

public class AnalysisIncomeExpenseLogic {
	public Assets AnalysisIncomeExpense(int userId, Date date)
			throws UserBusinessException, UserSystemException {
		Connection con = null;
		Assets result = new Assets();

		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();

			//DAOを生成
			AssetsDAO assetsDAO = new AssetsDAO(con);
			Assets fixedExpense = assetsDAO.amountGetFixedExpense(userId, date);
			Assets variableExpense = assetsDAO.amountGetVariableExpense(userId, date);

			result.setFixedExpense(fixedExpense.getFixedExpense());
			result.setVariableExpense(variableExpense.getVariableExpense());

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