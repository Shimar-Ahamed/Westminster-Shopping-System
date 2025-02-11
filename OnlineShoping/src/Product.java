abstract class Product {
    private String productID;
    private String productName;
    private int availableItems;
    private double price;

    public Product(String productID, String productName, int availableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    public abstract String getProductType();

    public String getProductID(){
        return productID;
    }
    public void setProductID(String productID){
        this.productID=productID;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName=productName;
    }

    public int getAvailableItems(){
        return availableItems;
    }

    public void setAvailableItems(int availableItems){
        this.availableItems=availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }






    public String toString(){
        return "Product ID: "+ productID +
                "\nProduct Name: "+ productName +
                "\nAvailable Items: "+ availableItems +
                "\nPrice: "+ price;
    }
}
