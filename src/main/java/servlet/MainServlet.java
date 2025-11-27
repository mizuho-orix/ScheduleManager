package servlet;

import java.io.IOException;
import java.time.YearMonth;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.GetScheduleLogic;
import model.Schedule;
import model.User;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログインユーザーを取得するセッションスコープの生成
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			// loginUserが存在していなければログイン画面へ
			response.sendRedirect("Login");
			return;
		}
		
		// スケジュールリスト取得を担当するGetScheduleLogicクラスを生成
		GetScheduleLogic scheduleBo = new GetScheduleLogic();

		// 現在の年月を取得
		YearMonth currentYearMonth = YearMonth.now();
		int userId = loginUser.getId(); // ログイン中のユーザーID

		// 引数として現在の年月を渡して、データベースから
		// 引数の期間のスケジュールリストを取得するexecuteメソッドを呼び出す		
		List<Schedule> scheduleList = scheduleBo.execute(currentYearMonth, userId);

		// 取得したユーザーIDをセッションスコープに保存
		session.setAttribute("userId", userId);

		// 取得したリストをセッションスコープに保存
		session.setAttribute("scheduleList", scheduleList);			

		// メイン画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
