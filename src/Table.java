public class Table {
    private int tableNumber;
    private Customer customer;

    public Table(int tableNumber){
        this.tableNumber = tableNumber;
        this.customer = null;
    }

    public boolean isFree(){
        if (customer == null){
            return true;
        }
        else{
            return false;
        }
    }

    public int getTableNumber(){
        return tableNumber;
    }
    
    public Customer getCustomer(){
        return customer;
    }

    public void seat(Customer c){
        this.customer = c;
    }

    public void release(){
        this.customer = null;
    }
}
