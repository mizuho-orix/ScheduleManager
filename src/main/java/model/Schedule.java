package model;

import java.time.LocalDate;

public class Schedule {
	private int id;
	private LocalDate schedule_Date;
	private String schedule_Name;
	private String Comment;
	
	public Schedule(int id, LocalDate schedule_Date, String schedule_Name, String comment) {
		this.id = id;
		this.schedule_Date = schedule_Date;
		this.schedule_Name = schedule_Name;
		this.Comment = comment;
	}

	public int getId() {
		return id;
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
