package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.AssetsCategoryTotalAmountLogic;
import Logic.FindAssetsCategoryLogic;
import Logic.InsertAssetsLogic;
import entity.Assets;

public class InsertAssetsAction implements ActionIF {
	@Override
	public String execute(HttpServletRequest request) {
		String page = "home.jsp";

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				return "Logins.jsp";
			}

			int userId = (Integer) session.getAttribute("userId");
			String categoryName = request.getParameter("categoryName");
			if (categoryName == null || categoryName.trim().isEmpty()) {
				throw new UserBusinessException("項目名を入力してください。");
			}
			InsertAssetsLogic logic = new InsertAssetsLogic();
			logic.InsertAssets(userId, categoryName);
			//表示用
			AssetsCategoryTotalAmountLogic assetsCategoryTotalAmountLogic = new AssetsCategoryTotalAmountLogic();
			ArrayList<Assets> catgoryTotalAmount = new ArrayList<>();
			catgoryTotalAmount = assetsCategoryTotalAmountLogic.AssetsCategoryTotalAmount(userId);
			FindAssetsCategoryLogic findAssetsCategoryLogic = new FindAssetsCategoryLogic();
			ArrayList<Assets> catgoryName = new ArrayList<>();
			catgoryName = findAssetsCategoryLogic.FindAssetsCategory(userId);

			request.setAttribute("catgoryTotalAmount", catgoryTotalAmount);
			request.setAttribute("catgoryName", catgoryName);

		} catch (UserBusinessException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (UserSystemException e) {
			request.setAttribute("errorMessage", "システムエラーが発生しました");
		}

		return page;
	}
}
