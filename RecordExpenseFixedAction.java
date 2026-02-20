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

public class RecordExpenseFixedAction implements ActionIF{

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
			String dateStr = request.getParameter("date");
			java.sql.Date date = java.sql.Date.valueOf(dateStr);
			 String [] dateList = request.getParameterValues("categories");
	            for(String category : dateList) {
	            	String[]arr = category.split(",");
	            	int categoryId = Integer.parseInt(arr[0]);
	            	String memo = arr[1];
	            	int amount = Integer.parseInt(arr[2]);
	            

            // ロジック呼び出し
	        RecordExpenseLogic recordlogic = new RecordExpenseLogic();
		    recordlogic.RecordExpense(categoryId, userId, amount, memo, date);
		    
            
	            }
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
