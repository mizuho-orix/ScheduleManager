package servlet;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Schedule;
import model.UpdateScheduleLogic;

@WebServlet("/UpdateScheduleServlet")
public class UpdateScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// セッションスコープからユーザーIDを取得
		int userId = (int)request.getSession().getAttribute("userId");

		// main.jspのモーダルの予定編集フォームの情報を取得
		int taskId = Integer.parseInt(request.getParameter("edit-TaskId"));	// スケジュールID
		LocalDate date = LocalDate.parse(request.getParameter("edit-Date"));	// 予定日
		String scheduleName = request.getParameter("edit-ScheduleName");		// 予定名
		String comment = request.getParameter("edit-Comment");					// 備考
		
		// 修正する予定内容を格納するScheduleクラスを生成
		Schedule updateSchedule = new Schedule(userId, taskId, date, scheduleName, comment);

		// 予定を修正する処理を担当するUpdateScheduleLogicクラスを生成
		UpdateScheduleLogic bo = new UpdateScheduleLogic();

		// 引数として予定内容を渡し、
		// データベース接続→修正するexecuteメソッドを呼び出す
		bo.excecute(updateSchedule);

		//メインメニューにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
