package Logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.CategoryDAO;
import Exception.UserBusinessException;
import Exception.UserSystemException;
import entity.Category;

public class CategoryDisplayiLogic {
	
	public ArrayList<Category> CategoryDisplayHendouhi(int userId)throws UserBusinessException,UserSystemException{
		Connection con = null;
		ArrayList<Category> categoryList = null;
		
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			CategoryDAO categoryDAO = new CategoryDAO(con);
			categoryList = categoryDAO.getVariableCategories(userId);
			
			
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
		return categoryList;
	
	}
	
	public ArrayList<Category> CategoryDisplayKoteihi(int userId)throws UserBusinessException,UserSystemException{
		Connection con = null;
		ArrayList<Category> categoryList = null;
		
		try {
			//データベースの接続
			con = ConnectionManager.ConnectionManager.getConnection();
			
			//DAOを生成
			CategoryDAO categoryDAO = new CategoryDAO(con);
			categoryList = categoryDAO.getFixedCategoryCategories(userId);
			
			
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
		return categoryList;
	
	}
}
