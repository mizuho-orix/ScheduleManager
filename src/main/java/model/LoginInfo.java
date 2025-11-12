package model;

// ログイン時に入力したユーザーIDと
// パスワードを格納する為のクラス

public class LoginInfo {
	private String userId;
	private String pass;
	
	public LoginInfo(String userId, String pass) {

		//入力したユーザーID
		this.userId = userId;
		
		//入力したパスワード
		this.pass = pass;
	}

	public String getUserId() {
		return userId;
	}

	public String getPass() {
		return pass;
	}	
}
