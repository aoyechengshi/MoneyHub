package entity;

import java.sql.Date;

public class Expenses {

	private int expenseId; //出費ID
	private int categoryId; // カテゴリID
	private int userId; // ユーザーID
	private String type; // "変動費" or "固定費"
	private String name; // カテゴリ名（例: 昼食費）
	private int amount; // 金額
	private String memo; // 内訳
	private Date date; // 日付
	private int budget; // 予算

	// コンストラクタ（空）
	public Expenses() {
	}

	// コンストラクタ（全項目）
	public Expenses(int expenseId, int categoryId, int userId, String type, String name, int amount, String memo,
			Date date) {
		this.categoryId = categoryId;
		this.userId = userId;
		this.type = type;
		this.name = name;
		this.amount = amount;
		this.memo = memo;
		this.date = date;
		this.expenseId = expenseId;
	}

	public Expenses(int categoryId, int userId, int amount, String memo, Date date) {
		this.categoryId = categoryId;
		this.userId = userId;
		this.amount = amount;
		this.memo = memo;
		this.date = date;
	}

	public Expenses(int categoryId, String name, int amount, String memo) {
		this.categoryId = categoryId;
		this.name = name;
		this.amount = amount;
		this.memo = memo;
	}

	public Expenses(int expenseId, int categoryId, String type, String name, int amount, String memo, Date date) {
		this.expenseId = expenseId;
		this.categoryId = categoryId;
		this.type = type;
		this.name = name;
		this.amount = amount;
		this.memo = memo;
		this.date = date;
	}

	public Expenses(int categoryId, String name, int budget, int amount, Date date) {
		this.categoryId = categoryId;
		this.name = name;
		this.budget = budget;
		this.amount = amount;
		this.date = date;
	}

	// getter / setter
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}
	
}
