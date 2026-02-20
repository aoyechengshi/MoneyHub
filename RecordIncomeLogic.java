package Logic;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.IncomeDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Income;

public class RecordIncomeLogic {

	public Income RecordIncome(int incomeId, int userId, int amount, String note, java.sql.Date date)throws UserBusinessException,UserSystemException{
		Connection con = null;
		Income income = null;
		boolean result = false;
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			IncomeDAO incomeDAO = new IncomeDAO(con);
			result = incomeDAO.recordIncome(incomeId, userId, amount, note, date);
			if(result == false) {
				throw new UserBusinessException("登録できませんでした。");
			}
			
			
		}catch(SQLException e) {
			//データベースエラーの場合
			e.printStackTrace();
			throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
		}finally {
			try {
				if(con!= null) {
					con.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
				throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
			}
		}
		return income;	
	}
}
