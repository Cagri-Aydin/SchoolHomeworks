public class MenuItem {
    private String name;
    private double price;
    private int sales;

    public MenuItem(String name, double price, int sales) {
        this.name = name;
        this.price = price;
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getSales() {
        return sales;
    }

    @Override
    public String toString() {
        return "Ürün: " + name + ", Fiyat: " + price + " TL, Satış: " + sales;
    }
}
