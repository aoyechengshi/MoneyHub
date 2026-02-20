package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.IncomeCategoryDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Income;

public class IncomeCategoryLogic {

	
	public ArrayList<Income> IncomeCategory(int userId) throws UserBusinessException,UserSystemException{
		Connection con = null;
		ArrayList<Income>List = new ArrayList<>();
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			IncomeCategoryDAO IncomeCategoryDAO = new IncomeCategoryDAO(con);
			List = IncomeCategoryDAO.getIncomeCategory(userId);
			
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
