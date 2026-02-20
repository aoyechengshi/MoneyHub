package Logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ExpensesDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Expenses;

public class UpdateExpenseLogic {

	public ArrayList<Expenses> updeteExpense(int expenseId,int amount,Date date, String memo,int userId) throws UserBusinessException, UserSystemException {
		Connection con = null;
		ArrayList<Expenses>List = new ArrayList<>();
		
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			//変更
			ExpensesDAO expensesDAO = new ExpensesDAO(con);
			expensesDAO.updateExpenses(expenseId, amount, date, memo, userId);
			
			//全出費検索
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

