package Logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.YearMonth;

import DAO.ExpensesDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;

public class SetBudgetLogic {
	public boolean setBudget(int userId)throws UserBusinessException,UserSystemException{
		Connection con = null;
		boolean result = false;
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			// ② 今月の YearMonth を取得（例：2025-10）
            YearMonth now = YearMonth.now();
            // ③ 今月の「月初日」を Date に変換（2025-10-01）
            Date targetMonth = Date.valueOf(now.atDay(1));
            
			//DAOを生成
			ExpensesDAO expensesDAO = new ExpensesDAO(con);
			result = expensesDAO.setBudget(userId, targetMonth);
			
			
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
		return result;	
	}
}
