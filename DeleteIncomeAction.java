package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.DeleteIncomeLogic;
import Logic.IncomeCategoryLogic;
import Logic.ShowIncomeLogic;
import Logic.findExpenseLogic;
import entity.Category;
import entity.Expenses;
import entity.Income;

public class DeleteIncomeAction implements ActionIF{

	@Override
	public String execute(HttpServletRequest request) {
		String page = "home.jsp";

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				return "Logins.jsp";
			}

			int userId = (Integer) session.getAttribute("userId");

			int incomeId = Integer.parseInt(request.getParameter("incomeId"));

			DeleteIncomeLogic deleteIncomeLogic = new DeleteIncomeLogic();
			deleteIncomeLogic.deleteIncome(incomeId, userId);

			// ==========================
			// 表示用データ取得
			// ==========================
			findExpenseLogic findExpenseLogic = new findExpenseLogic();
			ArrayList<Expenses> expenseList = findExpenseLogic.findExpense(userId);

			CategoryDisplayiLogic categoryLogic = new CategoryDisplayiLogic();
			ArrayList<Category> variableCategories = categoryLogic.CategoryDisplayHendouhi(userId);
			ArrayList<Category> fixedCategories = categoryLogic.CategoryDisplayKoteihi(userId);

			ShowIncomeLogic showIncomeLogic = new ShowIncomeLogic();
			ArrayList<Income> incomeList = showIncomeLogic.showIncome(userId);

			IncomeCategoryLogic incomeCategoryLogic = new IncomeCategoryLogic();
			ArrayList<Income> incomeCategoryList = incomeCategoryLogic.IncomeCategory(userId);

			request.setAttribute("variableCategories", variableCategories);
			request.setAttribute("fixedCategories", fixedCategories);
			request.setAttribute("expenseList", expenseList);
			request.setAttribute("incomeList", incomeList);
			request.setAttribute("incomeCategoryList", incomeCategoryList);

		} catch (UserBusinessException e) {
			request.setAttribute("errorMessageList", e.getMessageList());
			return "Logins.jsp";
		} catch (UserSystemException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (IllegalArgumentException e) {
			request.setAttribute("errorMessage", "入力値が不正です: " + e.getMessage());
		}

		return page;
	}
}
