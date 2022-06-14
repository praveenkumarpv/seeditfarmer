package helperclass;

import com.google.firebase.Timestamp;

public class messageupdater {
    String message,farmerid,userid,product,sender;
    Timestamp time;

    public messageupdater() {
    }

    public messageupdater(String message, String farmerid, String userid, String product, String sender, Timestamp time) {
        this.message = message;
        this.farmerid = farmerid;
        this.userid = userid;
        this.product = product;
        this.sender = sender;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFarmerid() {
        return farmerid;
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
