public class Doubles {
    public static void main(String[] args) {

   //      See Learn the Part for instructions.
        int dice1;
        int dice2;
        while(true){
           dice1=rollDice();
            dice2=rollDice();
            System.out.println("Dice 1: "+dice1);
            System.out.println("Dice 2: "+dice2+"\n");

            if(dice1==dice2)
                break;
        }



    }
public static int rollDice(){
        return (int)(Math.random()*6)+1;
}
}





