import java.util.Scanner;

public class Guess  {
    public static void main(String[] args) {

       System.out.print("I chose a number between 1 and 5. Try to guess it: ");
        
       Scanner scan = new Scanner(System.in);
       System.out.print("I chose a number between 1 and 5. Try to guess it:");
       int v=scan.nextInt();
       //See Learn the Part for detailed instructions.
        int num=(int)(Math.random() * 5) + 1;
        while(num!=v)
        {
            System.out.print("Guess again: ");
            v=scan.nextInt();
        }
        System.out.println("YOu got it!");
        scan.close();
    }

}
