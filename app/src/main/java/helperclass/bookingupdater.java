package helperclass;

public class bookingupdater {
    String userid,drid,date,time,uid;

    public bookingupdater() {
    }

    public bookingupdater(String userid, String drid, String date, String time, String uid) {
        this.userid = userid;
        this.drid = drid;
        this.date = date;
        this.time = time;
        this.uid = uid;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
