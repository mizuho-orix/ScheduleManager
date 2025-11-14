package model;

public class Schedule {
	private int id;
	private String schedule_Date;
	private String Comment;
	
	public Schedule(int id, String schedule_Date, String comment) {
		this.id = id;
		this.schedule_Date = schedule_Date;
		this.Comment = comment;
	}

	public int getId() {
		return id;
	}

	public String getSchedule_Date() {
		return schedule_Date;
	}

	public String getComment() {
		return Comment;
	}
	
}
