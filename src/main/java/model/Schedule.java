package model;

import java.time.LocalDate;

public class Schedule {
	private int id;						// ユーザーID
	private int schedule_Id;			// スケジュールID
	private LocalDate schedule_Date; 	// 予定日
	private String schedule_Name;		// 予定名
	private String Comment;				// 備考
	
	// 予定追加時のコンストラクタ
	public Schedule(int id, LocalDate schedule_Date, String schedule_Name, String comment) {
		this.id = id;
		this.schedule_Date = schedule_Date;
		this.schedule_Name = schedule_Name;
		this.Comment = comment;
	}
	
	// 予定修正時のコンストラクタ
	public Schedule(int id, int schedule_Id, LocalDate schedule_Date, String schedule_Name, String comment) {
		this.id = id;
		this.schedule_Id = schedule_Id;
		this.schedule_Date = schedule_Date;
		this.schedule_Name = schedule_Name;
		this.Comment = comment;
	}


	public int getId() {
		return id;
	}
	
	public int getSchedule_Id() {
		return schedule_Id;
	}

	public String getSchedule_Name() {
		return schedule_Name;
	}

	public LocalDate getSchedule_Date() {
		return schedule_Date;
	}

	public String getComment() {
		return Comment;
	}
	
}
