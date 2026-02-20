package Logic;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.ExpensesDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Expenses;

public class RecordExpenseLogic {

	public Expenses RecordExpense(int categoryId, int userId, int amount, String memo, java.sql.Date date)throws UserBusinessException,UserSystemException{
		Connection con = null;
		Expenses expenses = null;
		boolean result = false;
		
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			ExpensesDAO expensesDAO = new ExpensesDAO(con);
			result = expensesDAO.recordExpenses(categoryId, userId, amount, memo, date);
			if(result == false) {
				throw new UserBusinessException("登録できませんでした。");
			}
			
			
		}catch(SQLException e) {
			//データベースエラーの場合
			throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
		}finally {
			try {
				if(con!= null) {
					con.close();
				}
			}catch(SQLException e) {
				throw new UserSystemException("システムエラーが発生しました。管理者に連絡してください。");
			}
		}
		return expenses;
	
	}
}
