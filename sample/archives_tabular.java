package sample;

/**
 * Created by Kaponda on 11-Aug-21
 */

public class archives_tabular {
    int card_no;
    String first_name, last_name, house_no, gender, zone, baptised_on, baptised_by, baptised_at;


    //Constructor
    public archives_tabular (int card_no, String first_name, String last_name, String house_no, String gender, String zone, String baptised_on, String baptised_by, String baptised_at) {
        this.card_no = card_no;
        this.first_name = first_name;
        this.last_name = last_name;
        this.house_no = house_no;
        this.gender = gender;
        this.zone = zone;
        this.baptised_on = baptised_on;
        this.baptised_by = baptised_by;
        this.baptised_at = baptised_at;
    }


    //Getters and setters
    public int getCard_no() {
        return card_no;
    }

    public void setCard_no(int card_no) {
        this.card_no = card_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getBaptised_at() {
        return baptised_at;
    }

    public void setBaptised_at(String baptised_at) {
        this.baptised_at = baptised_at;
    }
}
