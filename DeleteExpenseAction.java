package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.DeleteExpenseLogic;
import Logic.IncomeCategoryLogic;
import Logic.ShowIncomeLogic;
import entity.Category;
import entity.Expenses;
import entity.Income;

public class DeleteExpenseAction implements ActionIF {
	@Override
    public String execute(HttpServletRequest request) {
        String page = "home.jsp";
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                return "Logins.jsp";
            }

            int expenseId = Integer.parseInt(request.getParameter("expenseId"));
            int userId = (Integer) session.getAttribute("userId");


            // ロジック呼び出し
            CategoryDisplayiLogic logic = new CategoryDisplayiLogic();
            ArrayList<Category> variableCategories = logic.CategoryDisplayHendouhi(userId);
            ArrayList<Category> fixedCategories = logic.CategoryDisplayKoteihi(userId);
            ShowIncomeLogic incomeLogic = new ShowIncomeLogic();
			ArrayList<Income> incomeList = incomeLogic.showIncome(userId);
			IncomeCategoryLogic incomeCategoryLogic = new IncomeCategoryLogic();
			ArrayList<Income> incomeCategoryList =incomeCategoryLogic.IncomeCategory(userId);
			
			DeleteExpenseLogic deleteExpenseLogic = new DeleteExpenseLogic();
			ArrayList<Expenses>  expenseList = deleteExpenseLogic.deleteExpense(userId, expenseId);


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
