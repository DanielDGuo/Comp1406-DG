import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bank {
    private String name;
    private BankAccount[] accounts;
    private int size = 10;

    public Bank(String n) {
        name = n;
        accounts = new BankAccount[size];
    }

    public boolean addAccount(BankAccount account) {
        int currentIndex = 0;
        for (BankAccount b : accounts) {
            //if it's empty, set it to the account
            if (b == null) {
                accounts[currentIndex] = account;
                account.setInstitution(this);
                return true;
            }
            //checks for dupe
            if (b.getAccountNumber() == account.getAccountNumber()) {
                return false;
            }
            currentIndex++;
        }
        //if there's no empty spot, return false
        return false;
    }


    public double getLargestBalance() {
        double largestBal = -1;
        //for each bank account that isn't null. if the largestBal is bigger than the current, set it as the new largest
        for (BankAccount b : accounts) {
            if (b != null && b.getBalance() > largestBal) {
                largestBal = b.getBalance();
            }
        }
        return largestBal;
    }

    public void performMonthlyUpkeep() {
        //for every bank account that isn't null, perform the upkeep
        for (BankAccount b : accounts) {
            if (b != null) {
                b.monthlyUpkeep();
            }
        }
    }

    public boolean transfer(int source, int destination, double amount) {
        //if the transfer shouldn't happen, return false
        if (source >= size || destination >= size || source == destination || accounts[source] == null
                || accounts[destination] == null || accounts[source].getBalance() < amount || amount < 0) {
            return false;
        }
        //otherwise, perform the transfer and return true
        accounts[source].setBalance(accounts[source].getBalance() - amount);
        accounts[destination].setBalance(accounts[destination].getBalance() + amount);
        return true;
    }

    @Override
    public String toString() {
        //return the name of the bank
        return name;
    }


    public static void main(String[] args) {
        Bank gullAndBull = new Bank("Gull And Bull");
        System.out.println(gullAndBull);

        //following should be true
        ChequingAccount ch = new ChequingAccount("Jayden", null, 1);
        ch.setBalance(100);
        System.out.println(gullAndBull.addAccount(ch));

        ch = new ChequingAccount("Jaden", null, 2);
        ch.setBalance(100);
        System.out.println(gullAndBull.addAccount(ch));

        ch = new ChequingAccount("Jadan", null, 3);
        ch.setBalance(100);
        System.out.println(gullAndBull.addAccount(ch));

        ch = new ChequingAccount("Jaydan", null, 4);
        ch.setBalance(100);
        System.out.println(gullAndBull.addAccount(ch));

        ch = new ChequingAccount("Bob", null, 5);
        ch.setBalance(100);
        System.out.println(gullAndBull.addAccount(ch));

        //should be false
        ch = new ChequingAccount("Bob the Second", null, 5);
        ch.setBalance(100);
        System.out.println(gullAndBull.addAccount(ch));


        //following should be true
        SavingsAccount sv = new SavingsAccount("Caden", null, 6);
        sv.setBalance(100);
        System.out.println(gullAndBull.addAccount(sv));

        sv = new SavingsAccount("Cadan", null, 7);
        sv.setBalance(100);
        System.out.println(gullAndBull.addAccount(sv));

        sv = new SavingsAccount("Cayden", null, 8);
        sv.setBalance(150);
        System.out.println(gullAndBull.addAccount(sv));

        sv = new SavingsAccount("Caydan", null, 9);
        sv.setBalance(200);
        System.out.println(gullAndBull.addAccount(sv));

        //should be 200
        System.out.println(gullAndBull.getLargestBalance());
        //should be true
        System.out.println(gullAndBull.transfer(8, 7, 150));
        //should be 300
        System.out.println(gullAndBull.getLargestBalance());
        //should be false
        System.out.println(gullAndBull.transfer(8, 7, 150));
        //should be false
        System.out.println(gullAndBull.transfer(9, 7, 50));
        //should be false
        System.out.println(gullAndBull.transfer(1, 7, -1));
        //should be false
        System.out.println(gullAndBull.transfer(1, 1, 50));

        //should be true
        sv = new SavingsAccount("Tom", null, 10);
        sv.setBalance(200);
        System.out.println(gullAndBull.addAccount(sv));
        System.out.println(sv.getInstitution());

        //should be false
        sv = new SavingsAccount("Tom the Second", null, 11);
        sv.setBalance(100);
        System.out.println(gullAndBull.addAccount(sv));

        gullAndBull.performMonthlyUpkeep();
        //should be 303(300 in a savings account + 300 * 0.01)
        System.out.println(gullAndBull.getLargestBalance());
    }
}
