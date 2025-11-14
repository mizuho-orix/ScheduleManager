package model;

import dao.MuttersDAO;

public class PostMutterLogic {
	public void execute(Mutter mutter) {
		//MuttersDAOクラス「dao」を生成
		MuttersDAO dao = new MuttersDAO();
		
		//生成したクラスのcreateメソッドを実行
		dao.create(mutter);
	}
}
