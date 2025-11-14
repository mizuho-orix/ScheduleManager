package model;

import dao.SchedulesDAO;

public class AddScheduleLogic {

	public void execute(Schedule addSchedule) {
		// SCHEDULESテーブルに接続するクラスSchedulesDAOを生成
		SchedulesDAO dao = new SchedulesDAO();
		
		// 追加する予定内容が格納されたaddScheduleを渡して
		// データベースに予定を追加するメソッドを呼び出す
		dao.create(addSchedule);
	}
}
