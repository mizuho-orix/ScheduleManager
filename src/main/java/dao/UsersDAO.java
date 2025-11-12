package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.LoginInfo;
import model.Mutter;
import model.User;

public class UsersDAO {
	//データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:h2:~/desktop/h2DB/H2/scheduleManager";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	//ログイン時の入力情報 LoginInfoクラスを受け取って
	//データベースのユーザーIDとパスワードと照合するメソッド
	public User findByLogin(LoginInfo loginInfo) {
		User user = null;
		
		//JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		//データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			//SELECT文の準備
			//入力したユーザーIDとパスワードが合致しているデータのID、名字、名前を取得
			String sql = "SELECT ID, NAME_SEI, NAME_MEI FROM USERS WHERE LOGIN_ID = ? AND PASSWORD = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// LOGIN_ID = ? に入力したユーザーIDを挿入
			pStmt.setString(1, loginInfo.getUserId());

			// PASSWORD = ? に入力したパスワードを挿入
			pStmt.setString(2, loginInfo.getPass());
			
			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();
			
			//SELECT文の結果、ユーザーが存在していれば
			//ログイン中のユーザーとしてクラスuserに
			//ID、名字、名前を格納して生成する
			if (rs.next()) {
				int id = rs.getInt("ID");
				String userName_Sei = rs.getString("NAME_SEI");
				String userName_Mei = rs.getString("NAME_MEI");
				user = new User(id, userName_Sei, userName_Mei);
			}
		} catch (SQLException e) {
			//接続失敗時、エラー内容を出力してクラス内容はnullを返す
			e.printStackTrace();
			return null;
		}
		
		//接続成功時は生成されたクラスuserを返す
		return user;
	}
	
	public boolean create(Mutter mutter) {
		// 新規ユーザー登録の処理をここに記述する
		
		
		
		
		
		//JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		//データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			//INSERT文の準備
			String sql = "INSERT INTO MUTTERS(NAME, TEXT) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//INSERT文中の「?」に使用する値を設定してSQL文を完成
			pStmt.setString(1, mutter.getUserName());
			pStmt.setString(2, mutter.getText());
			
			//INSERT文を実行（resultには追加された行数が代入される）
			int result =pStmt.executeUpdate();

			//追加された行数が1行でなければ追加に失敗している
			if (result != 1) {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		//追加が成功していればtrueを返す
		return true;
	}
}
