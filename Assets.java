package entity;

import java.sql.Date;

public class Assets {

	private int sumIncomeAmount;
	private int fixedExpense;
	private int variableExpense;
	private int userId;
	private Date date;
	private String categoryName;
	private int categoryVariableAmount;
	private int assetsCategoryId;
	private int amount;

	public Assets(int sumIncomeAmount, int fixedExpense, int variableExpense, int userId, Date date) {
		this.sumIncomeAmount = sumIncomeAmount;
		this.fixedExpense = fixedExpense;
		this.variableExpense = variableExpense;
		this.userId = userId;
		this.date = date;
	}

	public Assets(int amount,  int userId, Date date,String categoryName, int assetsCategoryId) {
		this.userId = userId;
		this.date = date;
		this.categoryName = categoryName;
		this.amount = amount;
		this.assetsCategoryId = assetsCategoryId;
	}

	public Assets(int sumIncomeAmount, int fixedExpense, int variableExpense, int userId, Date date,
			String categoryName, int categoryVariableAmount) {
		this.sumIncomeAmount = sumIncomeAmount;
		this.fixedExpense = fixedExpense;
		this.variableExpense = variableExpense;
		this.userId = userId;
		this.date = date;
		this.categoryName = categoryName;
		this.categoryVariableAmount = categoryVariableAmount;
	}
	
	public Assets(
			String categoryName, int categoryVariableAmount) {
		this.categoryName = categoryName;
		this.categoryVariableAmount = categoryVariableAmount;
	}

	public Assets() {
	}

	public int getSumIncomeAmount() {
		return sumIncomeAmount;
	}

	public void setSumIncomeAmount(int sumIncomeAmount) {
		this.sumIncomeAmount = sumIncomeAmount;
	}

	public int getFixedExpense() {
		return fixedExpense;
	}

	public void setFixedExpense(int fixedExpense) {
		this.fixedExpense = fixedExpense;
	}

	public int getVariableExpense() {
		return variableExpense;
	}

	public void setVariableExpense(int variableExpense) {
		this.variableExpense = variableExpense;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;

	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryVariableAmount() {
		return categoryVariableAmount;
	}

	public void setCategoryVariableAmount(int categoryVariableAmount) {
		this.categoryVariableAmount = categoryVariableAmount;
	}

	public int getAssetsCategoryId() {
		return assetsCategoryId;
	}

	public void setAssetsCategoryId(int assetsCategoryId) {
		this.assetsCategoryId = assetsCategoryId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	

}
