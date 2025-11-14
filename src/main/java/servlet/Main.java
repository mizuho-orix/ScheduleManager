package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//つぶやき一覧を取得してリストに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute();
		
		//リストをリクエストスコープに保存
		request.setAttribute("mutterList", mutterList);
		
		// ログインしているか確認するため
		// セッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		
		if (loginUser == null) { //ログインしていない場合
			//リダイレクト
			response.sendRedirect("index.jsp");
		} else {
			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
		}	
	}

	//つぶやきを投稿した時に呼び出されるメソッド
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");
		
		//入力値チェック
		//textの内容がnullか文章の長さが0か判定
		if (text != null && text.length() !=0) {			
			//セッションスコープに保存されたユーザー情報を取得
			HttpSession session = request.getSession();
			User loginUser = (User)session.getAttribute("loginUser");
			
			//つぶやきを作成してつぶやきリストに追加
			//セッションスコープからユーザー名を取得し、textの変数からつぶやき内容を取得
			Mutter mutter = new Mutter(loginUser.getName_Sei(), text);
			PostMutterLogic postMutterLogic = new PostMutterLogic();
			
			// 引数としてつぶやき内容を渡し、
			// データベース接続→登録するexecuteメソッドを呼び出す
			postMutterLogic.execute(mutter);
			
		} else {
			//エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "つぶやきが入力されていません");
		}

		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute();
		request.setAttribute("mutterList", mutterList);
		
		//メイン画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

}
