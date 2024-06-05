package sample;

/**
 * Created by Kaponda on 27 Mar 2021.
 */
public class perm_trans_in_tabular {
    int card_no;
    String first_name, last_name, transfer_from, date_transfer_in;

    //Constructor
    public perm_trans_in_tabular(int card_no, String first_name, String last_name, String transfer_from, String date_transfer_in) {
        this.card_no = card_no;
        this.first_name = first_name;
        this.last_name = last_name;
        this.transfer_from = transfer_from;
        this.date_transfer_in = date_transfer_in;
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

    public String getTransfer_from() {
        return transfer_from;
    }
    public void setTransfer_from(String transfer_from) {
        this.transfer_from = transfer_from;
    }

    public String getDate_transfer_in() {
        return date_transfer_in;
    }
    public void setDate_transfer_in(String date_transfer_in) {
        this.date_transfer_in = date_transfer_in;
    }

}
