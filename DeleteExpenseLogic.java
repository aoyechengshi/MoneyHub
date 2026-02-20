package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ExpensesDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Expenses;

public class DeleteExpenseLogic {
	public ArrayList<Expenses> deleteExpense(int userId, int expenseId)throws UserBusinessException,UserSystemException{
		Connection con = null;
		ArrayList<Expenses>List = new ArrayList<>();
		
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			ExpensesDAO expensesDAO = new ExpensesDAO(con);
			expensesDAO.deleteExpenses(expenseId);
			//全出費の取得
			List = expensesDAO.findExpenses(userId);
			
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
		return List;
	}
}
