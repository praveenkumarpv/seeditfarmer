package helperclass;

public class prescription {
    String prescription,doctorid,date;

    public prescription() {
    }

    public prescription(String prescription, String doctorid, String date) {
        this.prescription = prescription;
        this.doctorid = doctorid;
        this.date = date;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
