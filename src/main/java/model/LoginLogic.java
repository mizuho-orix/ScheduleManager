package model;

import dao.UsersDAO;

public class LoginLogic {
	
	public User execute(LoginInfo loginInfo) {
		// USERSテーブルに接続するクラスUsersDAOを生成
		UsersDAO usersDao = new UsersDAO();
		
		// ログイン時に入力したユーザーIDとパスワードが格納された
		// クラスloginInfoを渡して、照合するユーザーがいるかを確認するメソッド
		// ユーザーが確認できればログインユーザーとしてクラスuserを生成
		User user = usersDao.findByLogin(loginInfo);		
		
		// クラスuserがnullかどうかを真偽値で返す
		// 「user != null」がtrueかfalseを判定する部分
		return user;
	}
}
