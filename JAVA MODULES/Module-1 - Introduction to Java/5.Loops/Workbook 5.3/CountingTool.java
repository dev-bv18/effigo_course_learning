import java.util.Scanner;

public class CountingTool {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // See Learn the Part for the instructions.
        System.out.print("Hi Timmy! Choose a number to count to:");
        int num= scan.nextInt();
        for(int i=0;i<=num;i++)
        System.out.print(i+" ");
        scan.close();
    }
}
