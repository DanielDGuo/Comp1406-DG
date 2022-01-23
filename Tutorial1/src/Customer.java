public class Customer {
    String name;
    int age;
    float money;
    boolean admitted;

    public Customer(String initName) {
        name = initName;
        age = 0;
        money = 0.0f;
        admitted = false;
    }
    public Customer(String initName, int initAge) {
        name = initName;
        age = initAge;
        money = 0.0f;
        admitted = false;
    }
    public Customer(String initName, int initAge, float initMoney) {
        name = initName;
        age = initAge;
        money = initMoney;
        admitted = false;
    }
    public Customer() {
        name = null;
        age = 0;
        money = 0.0f;
        admitted = false;
    }

    public float computeFee(){
        if (age <= 3){
            return 0;
        } else if (age < 18){
            return 8.5f;
        } else if (age >= 65){
            return (float) (12.75 * .5);
        }
        return 12.75f;
    }

    public boolean spend(float amount){
        if(amount <= money){
            money = money - amount;
            return true;
        }
        return false;
    }

    public boolean hasMoreMoneyThan(Customer c){
        if(money > c.money){
            return true;
        }
        return false;
    }

    public void payAdmission(){
        if(spend(computeFee())){
            admitted = true;
        }
    }

    @Override
    public String toString() {
        if (admitted) {
            return "Customer " + name + ": a " + age + " year old with $" + money + " who has been admitted";
        } else {
            return "Customer " + name + ": a " + age + " year old with $" + money + " who has not been admitted";
        }
    }
}
