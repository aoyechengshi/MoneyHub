package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.IncomeCategoryLogic;
import Logic.RecordExpenseLogic;
import Logic.ShowIncomeLogic;
import Logic.findExpenseLogic;
import entity.Category;
import entity.Expenses;
import entity.Income;

public class RecordExpenseVariableAction implements ActionIF{

	 @Override
	    public String execute(HttpServletRequest request) {
	        String page = "home.jsp";
	        try {
	            HttpSession session = request.getSession(false);
	            if (session == null || session.getAttribute("userId") == null) {
	                return "Logins.jsp";
	            }

	          //パラメーターの取得
	            int userId = (Integer) session.getAttribute("userId");
	            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
	            int amount = Integer.parseInt(request.getParameter("amount"));
				String memo = request.getParameter("memo");
				String dateStr = request.getParameter("date");
				java.sql.Date date = java.sql.Date.valueOf(dateStr);

	            // ロジック呼び出し
	            RecordExpenseLogic recordlogic = new RecordExpenseLogic();
	            recordlogic.RecordExpense(categoryId, userId, amount, memo, date);
	            findExpenseLogic findExpenseLogic = new findExpenseLogic();
	            ArrayList<Expenses>expenseList =findExpenseLogic.findExpense(userId);
	            CategoryDisplayiLogic logic = new CategoryDisplayiLogic();
	            ArrayList<Category> variableCategories = logic.CategoryDisplayHendouhi(userId);
	            ArrayList<Category> fixedCategories = logic.CategoryDisplayKoteihi(userId);
	            ShowIncomeLogic incomeLogic = new ShowIncomeLogic();
				ArrayList<Income> incomeList = incomeLogic.showIncome(userId);
				IncomeCategoryLogic incomeCategoryLogic = new IncomeCategoryLogic();
				ArrayList<Income> incomeCategoryList =incomeCategoryLogic.IncomeCategory(userId);

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

