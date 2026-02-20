package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.IncomeCategoryLogic;
import Logic.UpdateBudgetLogic;
import entity.Category;
import entity.Income;

public class UpdateBudgetAction implements ActionIF{

	@Override
    public String execute(HttpServletRequest request) {
        String page = "Set.jsp";
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                return "Logins.jsp";
            }

          //パラメーターの取得
            int userId = (Integer) session.getAttribute("userId");	
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int budget = Integer.parseInt(request.getParameter("budget"));
            
            //更新用
            UpdateBudgetLogic updateBudgetLogic = new UpdateBudgetLogic();
            updateBudgetLogic.updateCategoriesBudget(userId, categoryId, budget);
            updateBudgetLogic.updateMonthlyBudget(userId, categoryId, budget);
            
            //表示用
            CategoryDisplayiLogic logic = new CategoryDisplayiLogic();
            ArrayList<Category> fixedCategories = logic.CategoryDisplayKoteihi(userId);
            ArrayList<Category> variableCategories = logic.CategoryDisplayHendouhi(userId);
            IncomeCategoryLogic incomeCategoryLogic = new IncomeCategoryLogic();
			ArrayList<Income> incomeCategoryList =incomeCategoryLogic.IncomeCategory(userId);
 
            request.setAttribute("fixedCategories", fixedCategories);
            request.setAttribute("variableCategories", variableCategories);
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
