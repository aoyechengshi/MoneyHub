package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.CategoryDisplayiLogic;
import Logic.IncomeCategoryLogic;
import Logic.LoginLogic;
import Logic.SetBudgetLogic;
import Logic.ShowIncomeLogic;
import Logic.findExpenseLogic;
import entity.Category;
import entity.Expenses;
import entity.Income;
import entity.User;

public class LoginAction implements ActionIF {

	public String execute(HttpServletRequest request) {
		String page ="home.jsp";
		try {
			//パラメーターの取得
			String userId2 = request.getParameter("userId");
			String password = request.getParameter("password");
			
			ArrayList<String>errorMessageList = new ArrayList<>();
			if(userId2==null || userId2.equals("")) {
				errorMessageList.add("UserIDが未入力です。");
			}
			if(password==null || password.equals("")) {
				errorMessageList.add("passwordが未入力です。");
			}
			if(!errorMessageList.isEmpty()) {
				throw new UserBusinessException(errorMessageList);
			}
			int userId = Integer.parseInt(userId2);

			
			//業務ロジック呼び出し
			LoginLogic logic = new LoginLogic();
			User user = logic.login(userId,password);
					
			SetBudgetLogic setBudgetLogic = new SetBudgetLogic();
			setBudgetLogic.setBudget(user.getUserId());
			//セッションの生成
			HttpSession session = request.getSession(true);
			//ログイン情報の格納
			session.setAttribute("loginUser", user);
			session.setAttribute("userId", user.getUserId());
			
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
			
		}catch(UserBusinessException e) {
			request.setAttribute("errorMessageList", e.getMessageList());
			request.setAttribute("errorMessage", e.getMessage());
			//遷移先のページ名の設定
			page="Logins.jsp";
		}catch(UserSystemException e) {
			request.setAttribute("errorMessaage", e.getMessage());
			//遷移先のページ名の設定
			
		}
		return page;
	}
	
}
