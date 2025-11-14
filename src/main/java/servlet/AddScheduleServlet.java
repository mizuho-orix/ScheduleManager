package servlet;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.AddScheduleLogic;
import model.Schedule;
import model.User;

/**
 * Servlet implementation class AddSchedeluServlet
 */
@WebServlet("/AddScheduleServlet")
public class AddScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの取得
		// main.jspの新規予定作成フォームに入力された値を取得
		request.setCharacterEncoding("UTF-8");
		
		// フォームから送られた日付を取得（文字列）
		String dateStr = request.getParameter("add-Date");
		
		// 文字列を日付に変換
		LocalDate scheduleDate = LocalDate.parse(dateStr);
		
		// 予定内容を取得
		String scheduleName = request.getParameter("add-ScheduleName");

		// 備考を取得
		String comment = request.getParameter("add-Comment");
		
		// ログイン中のユーザーIDを取得（文字列で）
		String idStr = request.getParameter("add-UserId");
		
		// 文字列で格納されたユーザーIDをint型に変換
		int id = Integer.parseInt(idStr);
		
		// 追加するスケジュールを格納するScheduleクラスを生成
		Schedule addSchedule = new Schedule(id, scheduleDate, scheduleName, comment);
		
		// 予定を追加する処理を担当するAddScheduleLogicクラスを生成
		AddScheduleLogic bo = new AddScheduleLogic();
		
		// 引数として予定内容を渡し、
		// データベース接続→登録するexecuteメソッドを呼び出す
		bo.execute(addSchedule);

		//メインメニューにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
