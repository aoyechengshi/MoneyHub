package Action;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.AssetsCategoryTotalAmountLogic;
import Logic.FindAssetsCategoryLogic;
import entity.Assets;

public class AssetsAction implements ActionIF {
	@Override
	public String execute(HttpServletRequest request) {
		String page = "home.jsp";

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				return "Logins.jsp";
			}

			int userId = (Integer) session.getAttribute("userId");

			AssetsCategoryTotalAmountLogic assetsCategoryTotalAmountLogic = new AssetsCategoryTotalAmountLogic();
			ArrayList<Assets> catgoryTotalAmount = new ArrayList<>();
			catgoryTotalAmount = assetsCategoryTotalAmountLogic.AssetsCategoryTotalAmount(userId);
			FindAssetsCategoryLogic findAssetsCategoryLogic = new FindAssetsCategoryLogic();
			ArrayList<Assets> catgoryName = new ArrayList<>();
			catgoryName =findAssetsCategoryLogic.FindAssetsCategory(userId);

			request.setAttribute("catgoryTotalAmount", catgoryTotalAmount);
			request.setAttribute("catgoryName", catgoryName);
			
			// ★ どの画面を表示するか指定
            request.setAttribute("content", "assets");

		} catch (UserBusinessException e) {
			request.setAttribute("errorMessage", e.getMessage());
		} catch (UserSystemException e) {
			request.setAttribute("errorMessage", "システムエラーが発生しました");
		}

		return page;
	}
}
