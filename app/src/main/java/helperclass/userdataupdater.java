package helperclass;

import java.util.List;

public class userdataupdater {
    String uid;
    String name;
    String emailid;
    String phonenumber;
    String address;
    String selector;
    String age,bp,bg;
    List<String>bplist;
    List<String>bglist;
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

    public userdataupdater(String uid, String name, String emailid, String phonenumber, String address, String selector, String age, String bp, String bg, List<String> bplist, List<String> bglist) {
        this.uid = uid;
        this.name = name;
        this.emailid = emailid;
        this.phonenumber = phonenumber;
        this.address = address;
        this.selector = selector;
        this.age = age;
        this.bp = bp;
        this.bg = bg;
        this.bplist = bplist;
        this.bglist = bglist;
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

    public List<String> getBplist() {
        return bplist;
    }

    public void setBplist(List<String> bplist) {
        this.bplist = bplist;
    }

    public List<String> getBglist() {
        return bglist;
    }

    public void setBglist(List<String> bglist) {
        this.bglist = bglist;
    }
}
