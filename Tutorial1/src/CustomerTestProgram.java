public class CustomerTestProgram {
    public static void main(String args[]) {
        Customer c1, c2;
        c1 = new Customer("Bob");
        c1.age = 27;
        c1.money = 50;
        System.out.println(c1.name);
        System.out.println(c1.age);
        System.out.println(c1.money);
        c2 = new Customer("Bob2");
        c2.age = 272;
        c2.money = 502;
        System.out.println(c2.name);
        System.out.println(c2.age);
        System.out.println(c2.money);
    }
}