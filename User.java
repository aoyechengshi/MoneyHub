package entity;

public class User {

	private int userId;
	private String userName;

	//引数なしのコンストラクタ
		public User() {		
		}
	
	//IDと名前を引数にもつコンストラクタ
		public User(int userId,String userName) {
			this.userId=userId;
			this.userName=userName;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
}
