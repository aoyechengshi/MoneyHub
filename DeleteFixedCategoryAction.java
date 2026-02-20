package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.DeleteFixedCategoryLogic;
import Logic.IncomeCategoryLogic;
import Logic.ShowIncomeLogic;
import Logic.findExpenseLogic;
import entity.Category;
import entity.Expenses;
import entity.Income;

public class DeleteFixedCategoryAction implements ActionIF {

	@Override
	public String execute(HttpServletRequest request) {
		String page = "home.jsp";
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				return "Logins.jsp";
			}

			int userId = (Integer) session.getAttribute("userId");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));

			DeleteFixedCategoryLogic logic = new DeleteFixedCategoryLogic();
			logic.deleteFixedCategory(userId, categoryId);

			CategoryDisplayiLogic displayLogic = new CategoryDisplayiLogic();
			ArrayList<Category> fixedCategories = displayLogic.CategoryDisplayKoteihi(userId);
			ArrayList<Category> variableCategories = displayLogic.CategoryDisplayHendouhi(userId);
			IncomeCategoryLogic incomeCategoryLogic = new IncomeCategoryLogic();
			ArrayList<Income> incomeCategoryList = incomeCategoryLogic.IncomeCategory(userId);

			request.setAttribute("fixedCategories", fixedCategories);
			request.setAttribute("variableCategories", variableCategories);
			request.setAttribute("incomeCategoryList", incomeCategoryList);
			
			// e002ロジック呼び出し
	        findExpenseLogic findExpenseLogic = new findExpenseLogic();
	        ArrayList<Expenses>expenseList =findExpenseLogic.findExpense(userId);
	        CategoryDisplayiLogic categoryDisplaylogic = new CategoryDisplayiLogic();
	        ArrayList<Category> variableCategories2 = categoryDisplaylogic.CategoryDisplayHendouhi(userId);
	        ArrayList<Category> fixedCategories2 = categoryDisplaylogic.CategoryDisplayKoteihi(userId);
	        ShowIncomeLogic incomeLogic = new ShowIncomeLogic();
			ArrayList<Income> incomeList = incomeLogic.showIncome(userId);
			IncomeCategoryLogic incomeCategoryLogic2 = new IncomeCategoryLogic();
			ArrayList<Income> incomeCategoryList2 =incomeCategoryLogic2.IncomeCategory(userId);

			//e002情報の格納
	        request.setAttribute("variableCategories", variableCategories2);
	        request.setAttribute("fixedCategories", fixedCategories2);
	        request.setAttribute("expenseList",expenseList);
	        request.setAttribute("incomeList", incomeList);
	        request.setAttribute("incomeCategoryList", incomeCategoryList2);

		} catch (UserBusinessException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (UserSystemException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		return page;
	}
}
