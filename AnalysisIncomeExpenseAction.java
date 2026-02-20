package Action;

import java.sql.Date;
import java.time.YearMonth;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.AnalysisIncomeExpenseLogic;
import Logic.CategoryGetVariableExpenseLogic;
import Logic.sumIncomeAmountLogic;
import entity.Assets;

public class AnalysisIncomeExpenseAction implements ActionIF {
	@Override
	public String execute(HttpServletRequest request) {
		String page = "home.jsp";

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				return "Logins.jsp";
			}

			int userId = (Integer) session.getAttribute("userId");
			String targetMonth = request.getParameter("targetMonth");

			Date date;

			// ===== 未指定チェック =====
			if (targetMonth == null || targetMonth.isEmpty()) {
				throw new UserBusinessException("分析する月を選択してください");
			}

			// ===== 形式チェック（yyyy-MM）=====
			if (!targetMonth.matches("\\d{4}-(0[1-9]|1[0-2])")) {
				throw new UserBusinessException("月の指定形式が正しくありません（例：2025-10）");
			}

			// ===== 変換 =====
			YearMonth ym = YearMonth.parse(targetMonth);
			date = Date.valueOf(ym.atDay(1));

			AnalysisIncomeExpenseLogic analysisIncomeExpenselogic = new AnalysisIncomeExpenseLogic();
			Assets analysisIncomeExpense = analysisIncomeExpenselogic.AnalysisIncomeExpense(userId, date);
			sumIncomeAmountLogic sumIncomeAmountLogic = new sumIncomeAmountLogic();
			Assets sumIncomeAmount = sumIncomeAmountLogic.sumIncomeAmount(userId, date);
			CategoryGetVariableExpenseLogic categoryGetVariableExpenseLogic = new CategoryGetVariableExpenseLogic();
			ArrayList<Assets> categoryExpenseList = new ArrayList<>();
			categoryExpenseList = categoryGetVariableExpenseLogic.AnalysisExpense(userId, date);
	

			request.setAttribute("analysis", analysisIncomeExpense);
			request.setAttribute("income", sumIncomeAmount);
			request.setAttribute("categoryExpenseList", categoryExpenseList);
			
			// ★ どの画面を表示するか指定
            request.setAttribute("content", "assetsIncome");

		} catch (UserBusinessException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (UserSystemException e) {
			request.setAttribute("errorMessage", "システムエラーが発生しました");
		}

		return page;
	}
}
