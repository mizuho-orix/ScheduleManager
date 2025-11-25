package model;

import dao.SchedulesDAO;

public class UpdateScheduleLogic {

	public void excecute(Schedule updateSchedule) {
		// SCHEDULESテーブルに接続するクラスSchedulesDAOを生成
		SchedulesDAO dao = new SchedulesDAO();
		
		// 修正する予定内容が格納されたupdateScheduleを渡して
		// データベースに予定を追加するメソッドを呼び出す
		dao.update(updateSchedule);
	}
}
