package entity;

import java.sql.Date;

public class Income {

	private int incomeId;

	// カテゴリID（incomeCategoryId に統一）
	private int incomeCategoryId;

	// ユーザーID
	private int userId;

	// カテゴリ名
	private String category;

	// 金額
	private int amount;

	// 日付
	private Date date;

	// メモ
	private String note;

	// ----- コンストラクタ -----

	// 全項目
	public Income(int incomeCategoryId, int userId, int amount, String category, Date date, String note) {
		this.incomeCategoryId = incomeCategoryId;
		this.userId = userId;
		this.amount = amount;
		this.category = category;
		this.date = date;
		this.note = note;
	}

	public Income(int incomeId, int incomeCategoryId, int userId, int amount, String category, Date date, String note) {
		this.incomeCategoryId = incomeCategoryId;
		this.userId = userId;
		this.amount = amount;
		this.category = category;
		this.date = date;
		this.note = note;
		this.incomeId = incomeId;
	}

	
	
	// IDなし（新規登録用）
	public Income(int userId, int amount, int incomeCategoryId, Date date, String note) {
		this.userId = userId;
		this.amount = amount;
		this.incomeCategoryId = incomeCategoryId;
		this.date = date;
		this.note = note;
	}

	// カテゴリID + カテゴリ名 + 日付 + メモ
	public Income(int incomeCategoryId, String category, Date date, String note) {
		this.incomeCategoryId = incomeCategoryId;
		this.category = category;
		this.date = date;
		this.note = note;
	}

	// デフォルトコンストラクタ
	public Income() {
	}

	// ----- getter / setter -----

	public int getIncomeCategoryId() {
		return incomeCategoryId;
	}

	public void setIncomeCategoryId(int incomeCategoryId) {
		this.incomeCategoryId = incomeCategoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(int incomeId) {
		this.incomeId = incomeId;
	}

}
