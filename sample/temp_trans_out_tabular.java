package sample;

/**
 * Created by Kaponda on 26 Mar 2021.
 */
public class temp_trans_out_tabular {
    int card_no;
    String first_name, last_name, transfer_to, date_transfer_out, date_transfer_in;


    //Constructor
    public temp_trans_out_tabular (int card_no, String first_name, String last_name, String transfer_to, String date_transfer_out, String date_transfer_in) {
        this.card_no = card_no;
        this.first_name = first_name;
        this.last_name = last_name;
        this.transfer_to = transfer_to;
        this.date_transfer_out = date_transfer_out;
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

    public String getTransfer_to() {
        return transfer_to;
    }

    public void setTransfer_to(String transfer_to) {
        this.transfer_to = transfer_to;
    }

    public String getDate_transfer_out() {
        return date_transfer_out;
    }

    public void getDate_transfer_out(String date_transfer_out) {
        this.date_transfer_out = date_transfer_out;
    }

    public String getDate_transfer_in() {
        return date_transfer_in;
    }

    public void setDate_transfer_in(String date_transfer_in) {
        this.date_transfer_in = date_transfer_in;
    }
}
