package sample;

/**
 * Created by Kaponda on 17 Apr 2021.
 */
public class bapt_child_tabular {
    int cert_no;
    String name, gender, dob, parent, house_no, zone, baptised_on, baptised_by, witness;

    //Constructor
    public bapt_child_tabular (int cert_no, String name, String gender, String dob, String parent, String house_no, String zone, String baptised_on, String baptised_by, String witness) {
        this.cert_no = cert_no;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.parent = parent;
        this.house_no = house_no;
        this.zone = zone;
        this.baptised_on = baptised_on;
        this.baptised_by = baptised_by;
        this.witness = witness;
    }

    //Getters and setters
    public int getCert_no() {
        return cert_no;
    }

    public void setCert_no(int cert_no) {
        this.cert_no = cert_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getBaptised_on() {
        return baptised_on;
    }

    public void setBaptised_on(String baptised_on) {
        this.baptised_on = baptised_on;
    }

    public String getBaptised_by() {
        return baptised_by;
    }

    public void setBaptised_by(String baptised_by) {
        this.baptised_by = baptised_by;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }
}
