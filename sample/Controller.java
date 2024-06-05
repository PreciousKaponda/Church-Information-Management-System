package sample;

public class Controller {
    /**
     * Christians details can be accessed/manipulated from these classes
     */
    //MEMBERS
    public void view_christians(){
        //Open interface that'll allow user to look at all the christians in the database
        christians callChristians = new christians();
        callChristians.viewChristians();
    }
    public void add_christians(){
        //Open interface that'll allow clerk to add new members
        add_christian adding = new add_christian();
        adding.add_new_member();
    }
    public void modify_christians(){
        //Open interface that'll allow user to change member's data
        modify modifying = new modify();
        modifying.modifications();
    }
    public void transfer_christians(){
        //Open NEW INTERFACE that'll allow user to transfer people and change the DB to reflect those transfers as necessary
        transfers callTransfers = new transfers();
        callTransfers.transfer();
    }

    //BAPTISMS
    public void view_baptisms() {
        //Open interface that'll allow user to look at all the baptised christians in the database
        baptism_view baptismsTable = new baptism_view();
        baptismsTable.baptised_christians();
    }
    public void add_baptisms() {
        //Open interface that'll allow clerk to add new baptised members
        baptism_add addBaptised = new baptism_add();
        addBaptised.add_baptised_member();
    }
    public void modify_baptisms() {
        //Open interface that'll allow user to change baptised member's data
        baptism_modify modBaptisms = new baptism_modify();
        modBaptisms.baptism_modifications();
    }


    /**
     * Child baptism details can be accessed/manipulated from these classes
     */
    public void view_child_bapt () {
        //Open interface that'll allow user to see table of baptised children
        bapt_child_view bapt_child_table = new bapt_child_view();
        bapt_child_table.baptised_children();
    }
    public void add_child_bapt () {
        //Open interface that'll allow user to add baptised children to the database
        add_bapt_child adding_baptised_child = new add_bapt_child();
        adding_baptised_child.add_baptised_child();
    }
    public void modify_child_bapt () {
        //Open interface that'll allow user to change details of individual's baptisms
        bapt_child_modify child_bapt_mods = new bapt_child_modify();
        child_bapt_mods.child_baptism_modification();
    }


    /**
     * Archive details can be accessed/manipulated from these classes
     */
    public void christian_archive(){
        //Open interface that'll allow user to access archive data from several years back
        archives archiving = new archives();
        archiving.depository();
    }
    public void archive_transfers(){
        //Open interface that'll allow user to access archive transfers
        archive_transfers call_archive_transfers = new archive_transfers();
        call_archive_transfers.view_archive_transfers();
    }
}
