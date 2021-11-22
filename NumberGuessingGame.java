import java.util.Scanner;

public class NumberGuessingGame
{
    private static Scanner scan = new Scanner (System.in);
    public static void main (String[] args){
        
        String readyGame;
        float score;
        int guessNumber;
        
        System.out.println("\n\nWelcome to YANGG (Yet Another Number Guessing Game)!");
        System.out.println("Ready? Y/N");
        readyGame = scan.nextLine().toUpperCase(); 
        
        while (readyGame.equals("Y")){
            guessNumber = numGen(0);// runs numgen
            
            System.out.println("Do you want to play again? Y/N");
            readyGame = scan.nextLine().toUpperCase(); 
            
        }
        System.out.println("\nThanks for playing YANGG! Remember to rate 5 stars on DMOJ! \nAll hail Lord and Saviour Bruce!");
    }
    private static int numGen (int intRand) {
        long upperBound;
        
        System.out.println("\nHow high do you want the number to be?");
        upperBound = scan.nextInt();
        scan.nextLine();
        intRand = (int)(Math.random() * upperBound + 1);
        
        System.out.println("\nOkay, The number is between 1 and " + upperBound + ".");
        return intRand;
        
    }
    
}
