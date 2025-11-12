package model;

import java.util.List;

import dao.MuttersDAO;

public class GetMutterListLogic {
	
	//つぶやき一覧をリストで受け取る
	public List<Mutter> execute() {
		MuttersDAO dao = new MuttersDAO();
		List<Mutter> mutterList = dao.findAll();
		return mutterList;
	}
}
