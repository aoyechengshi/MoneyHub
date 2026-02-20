package Action;

import java.sql.Date;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import Exception.UserBusinessException;
import Exception.UserSystemException;
import Logic.AssetsCategoryTotalAmountLogic;
import Logic.FindAssetsCategoryLogic;
import Logic.RecordAssetsLogic;
import entity.Assets;

public class RecordAssetsAction implements ActionIF {
	@Override
	public String execute(HttpServletRequest request) {
		String page = "home.jsp";

		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userId") == null) {
				return "Logins.jsp";
			}

			int userId = (Integer) session.getAttribute("userId");
			/* ===== パラメータ取得 ===== */
			String categoryIdStr = request.getParameter("assetsCategoryId");
			String amountStr = request.getParameter("amount");

			if (categoryIdStr == null || amountStr == null) {
				throw new UserBusinessException("入力値が不正です。");
			}

			int assetsCategoryId = Integer.parseInt(categoryIdStr);
			int amount = Integer.parseInt(amountStr);

			if (amount <= 0) {
				throw new UserBusinessException("金額は1円以上で入力してください。");
			}
			String recordDateStr = request.getParameter("recordDate");

			if (recordDateStr == null || recordDateStr.isEmpty()) {
				throw new UserBusinessException("記録日を選択してください。");
			}

			Date recordDate = Date.valueOf(recordDateStr);

			RecordAssetsLogic recordAssetsLogic = new RecordAssetsLogic();
			recordAssetsLogic.RecordAssets(userId, assetsCategoryId, amount, recordDate);
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
