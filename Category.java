package entity;

public class Category {
    private int categoryId;
    private String type; // 変動費 or 固定費
    private String name;
    private int budget;  // 予算

    public Category() {}

    public Category(int categoryId, String type, String name, int budget) {
        this.categoryId = categoryId;
        this.type = type;
        this.name = name;
        this.budget = budget;
    }
    
    public Category(int categoryId, int budget) {
        this.categoryId = categoryId;
        this.budget = budget;
    }

    // JavaBeans 規約に沿った getter/setter
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getBudget() { return budget; }
    public void setBudget(int budget) { this.budget = budget; }
}
