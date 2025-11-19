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
import model.LoginInfo;
import model.LoginLogic;
import model.Schedule;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン失敗時はdoPostからdoGetに飛んできて
		// ログインメニューにリダイレクトする
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの取得
		// index.jspからフォームに入力されたnameとpassを取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		
		// ↓ここからログイン処理↓
		// 入力したユーザーIDとパスワードを格納するLoginInfoを生成
		LoginInfo loginInfo = new LoginInfo(name, pass);
		
		// ログインの照合処理を担当するLoginLogicクラスを生成
		LoginLogic loginBo = new LoginLogic();
		
		// ログイン情報を渡してデータベースから
		// ユーザーIDとパスワードが合致するユーザーがいるかを照合する
		// executeメソッドを呼び出す
		User user = loginBo.execute(loginInfo);	
						
		//ログイン成功時の処理
		if (user != null) {
			// ユーザー情報が格納されたクラスuserをセッションスコープに保存
			//（セッションスコープを作成し、"loginUser"と名付け、クラス「user」を格納）
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			
			// スケジュールリスト取得を担当するGetScheduleLogicクラスを生成
			GetScheduleLogic scheduleBo = new GetScheduleLogic();

			// 引数として現在の年月を渡して、データベースから
			// 引数の期間のスケジュールリストを取得するexecuteメソッドを呼び出す
			YearMonth currentYearMonth = YearMonth.now();
			int userId = user.getId();
			List<Schedule> scheduleList = scheduleBo.execute(currentYearMonth, userId);

			// 取得したユーザーIDをセッションスコープに保存
			session.setAttribute("userId", userId);

			// 取得したリストをセッションスコープに保存
			session.setAttribute("scheduleList", scheduleList);			
			
			// メイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
		
		} else {
			// ログイン失敗時
			// doGetに飛んでリダイレクトする
			response.sendRedirect("Login");
		}
		
	}
}
