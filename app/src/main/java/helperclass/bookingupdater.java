package helperclass;

public class bookingupdater {
    String userid,drid;

    public bookingupdater() {
    }

    public bookingupdater(String userid, String drid) {
        this.userid = userid;
        this.drid = drid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDrid() {
        return drid;
    }

    public void setDrid(String drid) {
        this.drid = drid;
    }
}
