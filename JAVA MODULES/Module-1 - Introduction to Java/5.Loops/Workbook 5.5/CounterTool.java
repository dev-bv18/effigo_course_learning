import java.util.Scanner;

public class CounterTool {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("I hear you like to count by threes\n");
        System.out.println("Jimmy: It depends.");
        System.out.println("Oh, ok...");

       // See detailed instructions on Learn the Part.
       System.out.print("Pick a number to count by: ");
        int i=sc.nextInt();
        System.out.print("Pick a number to start counting from: ");
        int j=sc.nextInt();
        System.out.print("Pick a number to count to: ");
        int k=sc.nextInt();

        for(int l=j;l<=k;l+=i)
            System.out.print(l+" ");
        sc.close();
    }
}
