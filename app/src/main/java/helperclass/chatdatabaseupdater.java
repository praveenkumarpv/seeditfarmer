package helperclass;

public class chatdatabaseupdater {
    String productname,farmerid,userid,databaseid;

    public chatdatabaseupdater() {
    }

    public chatdatabaseupdater(String productname, String farmerid, String userid, String databaseid) {
        this.productname = productname;
        this.farmerid = farmerid;
        this.userid = userid;
        this.databaseid = databaseid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
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

    public String getDatabaseid() {
        return databaseid;
    }

    public void setDatabaseid(String databaseid) {
        this.databaseid = databaseid;
    }
}
