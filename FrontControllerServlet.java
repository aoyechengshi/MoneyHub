package Servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Action.ActionIF;
import Action.AddFixedCategoryAction;
import Action.AddIncomeCategoryAction;
import Action.AddVariableCategoryAction;
import Action.AnalysisExpenseAction;
import Action.AnalysisIncomeExpenseAction;
import Action.AssetsAction;
import Action.CategoryDisplayAction;
import Action.DeleteExpenseAction;
import Action.DeleteFixedCategoryAction;
import Action.DeleteIncomeAction;
import Action.DeleteIncomeCategoryAction;
import Action.InsertAssetsAction;
import Action.LoginAction;
import Action.RecordAssetsAction;
import Action.RecordExpenseFixedAction;
import Action.RecordExpenseVariableAction;
import Action.RecordIncomeAction;
import Action.ReturnHomeJspAction;
import Action.SetAction;
import Action.UpdateBudgetAction;
import Action.UpdateIncomeAction;
import Action.UpdeteExpenseAction;

@WebServlet(urlPatterns = { "/UserFC" })
public class FrontControllerServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "/WEB-INF/jsp/";
		String page = null;

		ActionIF action = null;
		//パラメーターの取得
		request.setCharacterEncoding("UTF-8");
		String buttonId = request.getParameter("buttonId");

		if (buttonId == null || buttonId.equals("")) {
			//ログイン画面のbuttonIdをデフォルトとして設定する
			buttonId = "e000";
		}
		switch (buttonId) {
		case "e000":
			//ログイン画面へボタン
			page = "Logins.jsp";
			break;
		case "e001":
			action = new LoginAction();
			page = action.execute(request);
			break;
		case "e002": // 
			action = new CategoryDisplayAction();
			page = action.execute(request);
			break;
		case "e003": // 
			action = new RecordExpenseVariableAction();
			page = action.execute(request);
			break;
		case "e004": // 
			action = new RecordExpenseFixedAction();
			page = action.execute(request);
			break;
		case "e005": // 
			action = new RecordIncomeAction();
			page = action.execute(request);
			break;
		case "e006": // 
			action = new ReturnHomeJspAction();
			page = action.execute(request);
			break;
		case "e007": // 
			action = new UpdeteExpenseAction();
			page = action.execute(request);
			break;
		case "e008": // 
			action = new DeleteExpenseAction();
			page = action.execute(request);
			break;
		case "e009": // 
			action = new UpdateIncomeAction();
			page = action.execute(request);
			break;
		case "e010": // 
			action = new DeleteIncomeAction();
			page = action.execute(request);
			break;
		case "e011": // 
			action = new SetAction();
			page = action.execute(request);
			break;
		case "e012": // 
			action = new AddFixedCategoryAction();
			page = action.execute(request);
			break;
		case "e013": // 
			action = new DeleteFixedCategoryAction();
			page = action.execute(request);
			break;
		case "e014": // 
			action = new AddVariableCategoryAction();
			page = action.execute(request);
			break;
		case "e015": // 
			action = new AddIncomeCategoryAction();
			page = action.execute(request);
			break;
		case "e016": // 
			action = new DeleteIncomeCategoryAction();
			page = action.execute(request);
			break;
		case "e017": // 
			page = "Assets.jsp";
			break;
		case "e018": // 
			action = new AnalysisExpenseAction();
			page = action.execute(request);
			break;
		case "e019": // 
			action = new UpdateBudgetAction();
			page = action.execute(request);
			break;
		case "e020": // 
			action = new AnalysisIncomeExpenseAction();
			page = action.execute(request);
			break;
		case "e021": // 
			action = new AssetsAction();
			page = action.execute(request);
			break;
		case "e022": // 
			action = new RecordAssetsAction();
			page = action.execute(request);
			break;
		case "e023": // 
			action = new InsertAssetsAction();
			page = action.execute(request);
			break;
		default:
			//buttonIdが存在しない場合
			page = "Logins.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(path + page);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
