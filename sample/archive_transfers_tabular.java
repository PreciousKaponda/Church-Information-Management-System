package sample;

/**
 * Created by Kaponda on 17-May-2021.
 */

public class archive_transfers_tabular {
    int card_no;
    String first_name, last_name, outgoing_date, outgoing_location, incoming_date, incoming_location;


    //Constructor
    public archive_transfers_tabular (int card_no, String first_name, String last_name, String outgoing_date, String trans_outgoing_location, String trans_incoming_date, String trans_incoming_location) {
        this.card_no = card_no;
        this.first_name = first_name;
        this.last_name = last_name;
        this.outgoing_date = outgoing_date;
        this.outgoing_location = trans_outgoing_location;
        this.incoming_date = trans_incoming_date;
        this.incoming_location = trans_incoming_location;
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

    public String getOutgoing_date() {
        return outgoing_date;
    }

    public void setOutgoing_date(String outgoing_date) {
        this.outgoing_date = outgoing_date;
    }

    public String getOutgoing_location() {
        return outgoing_location;
    }

    public void setOutgoing_location(String outgoing_location) {
        this.outgoing_location = outgoing_location;
    }

    public String getIncoming_date() {
        return incoming_date;
    }

    public void setIncoming_date(String incoming_date) {
        this.incoming_date = incoming_date;
    }

    public String getIncoming_location() {
        return incoming_location;
    }

    public void setIncoming_location(String incoming_location) {
        this.incoming_location = incoming_location;
    }
}
