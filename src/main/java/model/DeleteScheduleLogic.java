package model;

import dao.SchedulesDAO;

public class DeleteScheduleLogic {

	public boolean execute(int taskId) {
		// SCHEDULESテーブルに接続するクラスScheDulesDAOを生成
		SchedulesDAO dao = new SchedulesDAO();
		
		// 削除するスケジュールIDが格納されたtaskIdを渡して
		// データベースから予定を削除するメソッドを呼び出す
		return dao.delete(taskId);
	}
}
