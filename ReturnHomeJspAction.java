package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.IncomeCategoryLogic;
import Logic.ShowIncomeLogic;
import Logic.findExpenseLogic;
import entity.Category;
import entity.Expenses;
import entity.Income;

public class ReturnHomeJspAction implements ActionIF {

	@Override
	public String execute(HttpServletRequest request) {
		String page = "home.jsp";
        try {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			page = "Logins.jsp";
		}
		int userId = (Integer) session.getAttribute("userId");
		
		 // e002ロジック呼び出し
        findExpenseLogic findExpenseLogic = new findExpenseLogic();
        ArrayList<Expenses>expenseList =findExpenseLogic.findExpense(userId);
        CategoryDisplayiLogic categoryDisplaylogic = new CategoryDisplayiLogic();
        ArrayList<Category> variableCategories = categoryDisplaylogic.CategoryDisplayHendouhi(userId);
        ArrayList<Category> fixedCategories = categoryDisplaylogic.CategoryDisplayKoteihi(userId);
        ShowIncomeLogic incomeLogic = new ShowIncomeLogic();
		ArrayList<Income> incomeList = incomeLogic.showIncome(userId);
		IncomeCategoryLogic incomeCategoryLogic = new IncomeCategoryLogic();
		ArrayList<Income> incomeCategoryList =incomeCategoryLogic.IncomeCategory(userId);

		//e002情報の格納
        request.setAttribute("variableCategories", variableCategories);
        request.setAttribute("fixedCategories", fixedCategories);
        request.setAttribute("expenseList",expenseList);
        request.setAttribute("incomeList", incomeList);
        request.setAttribute("incomeCategoryList", incomeCategoryList);
        
	 } catch (UserBusinessException e) {
         request.setAttribute("errorMessageList", e.getMessageList());
         return "Logins.jsp";
     } catch (UserSystemException e) {
         request.setAttribute("errorMessage", e.getMessage());
     }
		return page;
	}
	
}
