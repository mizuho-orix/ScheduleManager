package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.GetScheduleLogic;
import model.Schedule;

/**
 * Servlet implementation class GetScheduleServlet
 */
@WebServlet("/GetScheduleServlet")
public class GetScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// main.jspのカレンダーの年月のドロップダウンメニューを
	// 変更した時にfetchから呼び出される
	// ドロップダウンメニューの年月を受け取ってJSONファイルを返す
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ajaxのリクエストから送られてきたrequestの引数から
		// yearとmonthを取り出して、int型→YearMonth型に変換して格納
		try {
			String yearStr = request.getParameter("year");
			String monthStr = request.getParameter("month");
			
			if (yearStr == null || monthStr == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "yearまたはmonthが指定されていません");
				return;
			}
			
			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));		
			YearMonth yearMonth = YearMonth.of(year, month);
			
			Object userObj = request.getSession().getAttribute("userId");

			if (userObj == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ユーザーIDが取得できませんでした");
				return;
			}

			int userId = (int) userObj;
			System.out.println(userId);
			System.out.println(yearMonth);
		
			GetScheduleLogic getScheduleLogic = new GetScheduleLogic();
			List<Schedule> scheduleList = getScheduleLogic.execute(yearMonth, userId);

			if (scheduleList == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "スケジュールの取得に失敗しました");
				return;
			}

			// JSONに変換
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
							(src, typeOfSrc, context) -> new com.google.gson.JsonPrimitive(src.toString()))
					.create();

			String json = gson.toJson(scheduleList);
			System.out.println(json);
        
			// JSON形式のファイルをresponseにセット
			response.setContentType("application/json; charset=UTF-8");
        
			// JavaScriptのfetchにJSONデータを送信
			response.getWriter().write(json);
		
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "サーバーエラー: " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
