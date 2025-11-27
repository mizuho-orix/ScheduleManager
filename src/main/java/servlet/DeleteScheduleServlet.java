package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.DeleteScheduleLogic;

/**
 * Servlet implementation class DeleteScheduleServlet
 */
@WebServlet("/DeleteScheduleServlet")
public class DeleteScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// JavaScriptから受け取ったtaskId(文字列)を数値に変換
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		
		// 予定を削除する処理を担当するDeleteScheduleServletクラスを生成
		DeleteScheduleLogic bo = new DeleteScheduleLogic();
		
		// 引数としてスケジュールIDを渡し、
		// データベース接続→削除するexecuteメソッドを呼び出す
		boolean result = bo.execute(taskId);
		
		// responseとして返す内容をtextとする
		response.setContentType("text/plain; charset=UTF-8");
		
		// 変数resultがtureなら"success"、falseなら"fail"を文字列として返す
		response.getWriter().write(result ? "success" : "fail");
	}
}
