package helperclass;

public class doctordata {
    String emailid,phonenumber,name,uid,selected;

    public doctordata() {
    }

    public doctordata(String emailid, String phonenumber, String name, String uid, String selected) {
        this.emailid = emailid;
        this.phonenumber = phonenumber;
        this.name = name;
        this.uid = uid;
        this.selected = selected;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
