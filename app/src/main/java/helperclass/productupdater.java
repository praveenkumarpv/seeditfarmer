package helperclass;

public class productupdater {
   String farmeruid,productname,productprice,productdiscription,productimage;

    public productupdater() {
    }

    public productupdater(String farmeruid, String productname, String productprice, String productdiscription, String productimage) {
        this.farmeruid = farmeruid;
        this.productname = productname;
        this.productprice = productprice;
        this.productdiscription = productdiscription;
        this.productimage = productimage;
    }

    public String getFarmeruid() {
        return farmeruid;
    }

    public void setFarmeruid(String farmeruid) {
        this.farmeruid = farmeruid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductdiscription() {
        return productdiscription;
    }

    public void setProductdiscription(String productdiscription) {
        this.productdiscription = productdiscription;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }
}
