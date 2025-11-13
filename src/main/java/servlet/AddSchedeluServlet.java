package servlet;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddSchedeluServlet
 */
@WebServlet("/AddSchedeluServlet")
public class AddSchedeluServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSchedeluServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの取得
		// main.jspの新規予定作成フォームに入力された値を取得
		request.setCharacterEncoding("UTF-8");
		
		// フォームから送られた日付を取得（文字列）
		String dateStr = request.getParameter("add-Date");
		
		// 文字列を日付に変換
		LocalDate scheduleDate = LocalDate.parse(dateStr);
		
		// 予定内容を取得
		String schedule = request.getParameter("add-Schedule");

		// 備考を取得
		String comment = request.getParameter("add-Comment");
		
	
	}

}
