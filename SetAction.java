package Action;


import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.IncomeCategoryLogic;
import entity.Category;
import entity.Income;

public class SetAction implements ActionIF{

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
            
            //
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

