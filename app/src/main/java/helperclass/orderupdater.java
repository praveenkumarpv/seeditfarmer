package helperclass;

public class orderupdater {
    String productname,productpric,userid;

    public orderupdater() {
    }

    public orderupdater(String productname, String productpric, String userid) {
        this.productname = productname;
        this.productpric = productpric;
        this.userid = userid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductpric() {
        return productpric;
    }

    public void setProductpric(String productpric) {
        this.productpric = productpric;
    }
}
