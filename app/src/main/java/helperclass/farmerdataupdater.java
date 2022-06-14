package helperclass;

public class farmerdataupdater {
    String emailid,name,uid,selected;

    public farmerdataupdater() {
    }

    public farmerdataupdater(String emailid, String name, String uid, String selected) {
        this.emailid = emailid;
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
