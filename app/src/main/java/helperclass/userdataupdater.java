package helperclass;

public class userdataupdater {
    String uid;
    String name;
    String emailid;
    String phonenumber;
    String address;
    String selector;
    String age,bp,bg;
    public userdataupdater() {
    }



    public userdataupdater(String uid, String name, String emailid, String phonenumber, String address, String selector) {
        this.uid = uid;
        this.name = name;
        this.emailid = emailid;
        this.phonenumber = phonenumber;
        this.address = address;
        this.selector = selector;
    }

    public userdataupdater(String uid, String name, String emailid, String phonenumber, String address, String selector, String age, String bp, String bg) {
        this.uid = uid;
        this.name = name;
        this.emailid = emailid;
        this.phonenumber = phonenumber;
        this.address = address;
        this.selector = selector;
        this.age = age;
        this.bp = bp;
        this.bg = bg;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
}
