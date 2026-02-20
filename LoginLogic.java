package Logic;

import java.sql.Connection;
import java.sql.SQLException;

import DAO.LoginDAO;
import DAO.UserDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.User;

public class LoginLogic {

	public User login(int userId, String password) throws UserBusinessException,UserSystemException{
		Connection con = null;
		User user = null;
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			LoginDAO loginDAO = new LoginDAO(con);
			boolean result = loginDAO.findById(userId, password);
			//戻り値がfalseの場合
			if(result == false) {
				throw new UserBusinessException("ログインに失敗しました。");
			}
			
			UserDAO userDAO = new UserDAO(con);
			user=userDAO.findUserId(userId);
			if(user == null) {
				throw new UserBusinessException("ログインに失敗しました。");
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
		return user;
	}
}
