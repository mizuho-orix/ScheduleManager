package model;

import java.time.YearMonth;
import java.util.List;

import dao.SchedulesDAO;

public class GetScheduleLogic {

	// 引数として受け取った年月とユーザーIDに合致する
	// スケジュールリストを取得して返す
	public List<Schedule> execute(YearMonth yearMonth, int userId) {
		SchedulesDAO dao = new SchedulesDAO();
		List<Schedule> scheduleList = dao.findSchedule(yearMonth, userId);
		return scheduleList;
	}

}
