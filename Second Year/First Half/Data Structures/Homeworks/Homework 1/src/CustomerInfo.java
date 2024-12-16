import java.util.SortedSet;

class Date{
    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return day + "-" + month + "-" + year;
    }
}
class Transaction{
    private final Date shopping_date;
    private final String product;
    private int transaction_count;

    public Transaction(Date shopping_date, String product) {
        this.shopping_date = shopping_date;
        this.product = product;
    }

    public void setTransaction_count(int transaction_count) {
        this.transaction_count = transaction_count;
    }

    public int getTransaction_count() {
        return transaction_count;
    }

    public Date getShopping_date() {
        return shopping_date;
    }

    public String getProduct() {
        return product;
    }
    public Transaction compareFirst(Transaction A, Transaction B){
        Transaction res = new Transaction(new Date(0,0,0),"");
        if(A.getShopping_date().getYear() > B.getShopping_date().getYear())
            res = A;
        else if (A.getShopping_date().getYear() < B.getShopping_date().getYear())
            res = B;
        else{
            if(A.getShopping_date().getMonth() > B.getShopping_date().getMonth())
                res = A;
            else if(B.getShopping_date().getMonth() > A.getShopping_date().getMonth())
                res = B;
            else{
                if(A.getShopping_date().getDay() > B.getShopping_date().getDay())
                    res = A;
                else if(B.getShopping_date().getDay() > A.getShopping_date().getDay())
                    res = B;
                else{
                    int i = 0;
                    if(A.product.length() >= B.product.length()){
                        while(i != B.product.length()) {
                            if (A.product.charAt(i) > B.product.charAt(i))
                                res = A;
                            else if (B.product.charAt(i) > A.product.charAt(i))
                                res = B;
                            i++;
                        }
                        res = A;
                    }
                    else if(B.product.length() > A.product.length()){
                        while(i != A.product.length()) {
                            if (A.product.charAt(i) > B.product.charAt(i))
                                res = A;
                            else if (B.product.charAt(i) > A.product.charAt(i))
                                res = B;
                            i++;
                        }
                        res = B;
                    }
                }
            }
        }
        return res;
    }
}
public class CustomerInfo{
    private final String customer_ID;
    private final String customer_name;
    private ArrayList<Transaction> transactions;

    public CustomerInfo(String customer_ID, String customer_name){
        this.customer_ID = customer_ID;
        this.customer_name = customer_name;
        transactions = new ArrayList<>();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transaction.setTransaction_count(transaction.getTransaction_count() + 1);
    }


    @Override
    public String toString() {
        String result = transactions.getLength() + " transactions found for " + customer_name + "\n\n";
        for (int i = 1; i < transactions.getLength(); i++) {
            result += transactions.getEntry(i).getShopping_date() + ", " + transactions.getEntry(i).getProduct() + "\n";
        }
        return  result;
    }
}
