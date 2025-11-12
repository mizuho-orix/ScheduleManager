package model;

import java.io.Serializable;

//ログイン中のユーザーを格納する為のクラス

public class User implements Serializable {
	private int id;		 		//ID
	private String name_Sei;	//名字
	private String name_Mei;	//名前
	
	public User() {
		
	}
		
	public User(int id, String name_Sei, String name_Mei) {
		this.id = id;
		this.name_Sei = name_Sei;
		this.name_Mei = name_Mei;
	}
	
	public int getId() {
		return id;
	}

	public String getName_Sei() {
		return name_Sei;
	}

	public String getName_Mei() {
		return name_Mei;
	}
}
