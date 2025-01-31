public class Calendar {
    public static void main(String[] args) {
        
        //See Learn the Part for the complete instructions (link in resources folder of Udemy video).  

        String day = "Friday"; //Can be any day.
        System.out.println("Hey, are you free on " + day + "? \n");
        System.out.println("Hmm, let me check my calendar.");
        if(day.equals("Monday")){
            System.out.println("Sorry, I have to stay at work late.");
        }
        else
            if(day.equals("Tuesday")){
                System.out.println("It looks like I have meetings all day.");
            }
            else
                if(day.equals("Wednesday")){
                    System.out.println("I have a dentist appointment. Some other time!");
                }
                else
                    if(day.equals("Thursday")){
                        System.out.println("Sorry, thursday is date night!");
                    }
                    else
                        if(day.equals("Friday")||day.equals("Saturday")||day.equals("Sunday")){
                            System.out.println("I'm free!!");
                        }
                        else
                            System.out.println("That's not a day.");


       //Check 'calendar' here:
        

    }
}
