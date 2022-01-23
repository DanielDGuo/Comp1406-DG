import java.util.Scanner;

public class TaxProgram {
    public static void main(String[] args){
        double income, fedTax, provTax;
        int dependents;

        Scanner input = new Scanner(System.in);
        System.out.print("Please enter your taxable income: ");
        income = input.nextDouble();
        System.out.print("Please enter your number of dependents: ");
        dependents = input.nextInt();
        System.out.println();

        fedTax = 0.0;
        provTax = 0.0;

        if(income <= 29590){
            fedTax = income * 0.17;
        } else if(29590 < income && income< 59180){
            fedTax = .17 * 29590 + .26 * (income - 29590);
        } else if(income >= 59180){
            fedTax = .17 * 29590 + .26 * 29590 + .29 * (income - 59180);
        }
        //fedTax finalized

        provTax = fedTax * .425 - 160.5 - (dependents * 328);
        if (provTax < 0){
            provTax = 0;
        }
        //provTax finalized


        System.out.println("Here is your tax breakdown:\n");
        System.out.println(String.format("%-14s", "Income") + String.format("%14s", "$" + String.format("%,.2f",income)));
        System.out.println(String.format("%-14s", "Dependents") + String.format("%14d", dependents));
        System.out.println("----------------------------" );
        System.out.println(String.format("%-14s", "Federal Tax") + String.format("%14s", "$" + String.format("%,.2f",fedTax)));
        System.out.println(String.format("%-14s", "Provincial Tax") + String.format("%14s", "$" + String.format("%,.2f",provTax)));
        System.out.println("============================" );
        System.out.println(String.format("%-14s", "Total Tax") + String.format("%14s", "$" + String.format("%,.2f",fedTax+provTax)));

    }
}
