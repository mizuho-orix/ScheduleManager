package dao;

// 新規予定を追加した時にデータベースの
// SCHEDULESテーブルに接続するクラス

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;
import model.Schedule;

public class SchedulesDAO {
	//データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:h2:~/desktop/h2DB/H2/scheduleManager";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";
	
	// 引数として受け取った年月のスケジュールを
	// SCHEDULESテーブルから取得するメソッド
	public List<Schedule> findSchedule(YearMonth yearMonth, int userId) {
		// 取得したスケジュールを格納するリストを作成
		List<Schedule> ScheduleList = new ArrayList<>();
		
		// 引数の年月の月初を取得
		LocalDate start = yearMonth.atDay(1);
		
		// 引数の年月の月末を取得
		LocalDate end = yearMonth.atEndOfMonth();
		
		//JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		//データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			//SELECT文の準備
			String sql = "SELECT ID, SCHEDULE_DATE, SCHEDULE, COMMENT FROM SCHEDULES WHERE USER_ID = ? AND (SCHEDULE_DATE BETWEEN ? AND ?) ORDER BY SCHEDULE_DATE";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, userId);
			pStmt.setDate(2, java.sql.Date.valueOf(start));
			pStmt.setDate(3, java.sql.Date.valueOf(end));
			
			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();
			
			//SELECT文の結果をArrayListに格納
			while (rs.next()) {
				int id = rs.getInt("ID");
				
				// 11/14ここまで
				// 取得したSQLからスケジュールリストを作成するところから続き
				// 取得した予定をjsonにできるか…？
				
				
				String userName = rs.getString("NAME");
				String text = rs.getString("TEXT");
				Mutter mutter = new Mutter(id, userName, text);
				
				
				mutterList.add(mutter);
			}
		} catch (SQLException e) {
			//エラー内容を出力する
			e.printStackTrace();
			return null;
		}
		
		return mutterList;

	}
	
	
	// 新規予定を登録するメソッド
	public boolean create(Schedule addSchedule) {
		// JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			
			// INSERT文の準備
			String sql = "INSERT INTO SCHEDULES(USER_ID, SCHEDULE_DATE, SCHEDULE, COMMENT) VALUES(?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			// INSERT文中の「?」に使用する値を設定してSQL文を完成
			pStmt.setInt(1, addSchedule.getId()); // ID
			
			// LocalDate型はsetDateの引数として使用できないので
			// 一度変数に入れた後、java.sql.Date型に変換し直して引数にする
			LocalDate localDate = addSchedule.getSchedule_Date();
			java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
			
			pStmt.setDate(2, sqlDate); // 日付
			pStmt.setString(3, addSchedule.getSchedule_Name()); // 予定内容
			pStmt.setString(4, addSchedule.getComment()); // 備考
			
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
