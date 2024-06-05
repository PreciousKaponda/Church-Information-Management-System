package sample;

/**
 * Created by Kaponda on 22 Mar 2021.
 */
 public class transfers_controller {
	clerk_home callClerk;

	public void view_temp_trans_in() {
        temp_trans_in_view callTemp_trans_in_view = new temp_trans_in_view();
        callTemp_trans_in_view.temp_trans_in_table();
    }
    public void add_temp_trans_in() {
        temp_trans_in_add callTemp_trans_in_add = new temp_trans_in_add();
        callTemp_trans_in_add.add_temp_trans_in();
    }
    public void view_perm_trans_in() {
        perm_trans_in_view callPerm_trans_in_view = new perm_trans_in_view();
        callPerm_trans_in_view.perm_trans_in_table();
    }
    public void add_perm_trans_in() {
        perm_trans_in_add callPerm_trans_in_add = new perm_trans_in_add();
        callPerm_trans_in_add.add_perm_trans_in();
    }
    public void view_temp_trans_out() {
        temp_trans_out_view callTemp_trans_out_view = new temp_trans_out_view();
        callTemp_trans_out_view.temp_trans_out_table();
    }
    public void add_temp_trans_out() {
        temp_trans_out_add callTemp_trans_out_add = new temp_trans_out_add();
        callTemp_trans_out_add.add_temp_trans_out();
    }
    public void view_perm_trans_out() {
        perm_trans_out_view callPerm_trans_out_view = new perm_trans_out_view();
        callPerm_trans_out_view.perm_trans_out_table();
    }
    public void add_perm_trans_out() {
        perm_trans_out_add callPerm_trans_out_add = new perm_trans_out_add();
        callPerm_trans_out_add.add_perm_trans_out();
    }
     
    public void back_function() {
        callClerk = new clerk_home();
        callClerk.clerk();
    }
 }