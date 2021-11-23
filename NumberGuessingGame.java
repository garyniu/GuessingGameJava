import java.util.Scanner;

public class NumberGuessingGame
{
    private static Scanner scan = new Scanner (System.in);
    public static void main (String[] args){
        
        String readyGame;
        float score;
        int guessNumber;
        
        System.out.print('\u000C');
        println("\nWelcome to YANGG (Yet Another Number Guessing Game)!");
        println("Ready? Y/N");
        readyGame = scan.nextLine().toUpperCase(); 
        
        while (readyGame.equals("Y")){
            guessNumber = numGen(0);// runs numgen
            
            mainGame(guessNumber);           
            
            println("Do you want to play again? Y/N");
            readyGame = scan.nextLine().toUpperCase(); 
            
        }
        System.out.println("\nThanks for playing YANGG! Remember to rate 5 stars on DMOJ! \nAll hail Lord and Saviour Bruce!");
    }
    
    //guesing main game
    private static int mainGame (int x) {
        int guess, timesGuessed = 0;
        
        println("Start guessing!");
        
        while (true) {
            guess = scan.nextInt();
            
            if (guess == x){
                if (timesGuessed <= 1){
                    println("Correct!\nThe number was "+ x + ".\nIt took you 1 try to guess the number.\n");
                } else {
                    println("Correct!\nThe number was "+ x + ".\nIt took you " + timesGuessed + " tries to guess the number.\n");
                }
                scan.nextLine();
                return 0;
            } else if (guess < x){
                println("Too low!");
            } else if (guess > x){
                println("Too high!");
            }
            timesGuessed++;
        }
        
    }   
    
    //random number generator with user input
    private static int numGen (int intRand) {
        long upperBound;
        
        println("\nHow high do you want the number to be?");
        upperBound = scan.nextInt();
        scan.nextLine();
        intRand = (int)(Math.random() * upperBound + 1);
        
        println("\nOkay, The number is between 1 and " + upperBound + ".");
        return intRand;
        
    }
    
    //short form print line
    private static void println(String x){
        System.out.println(x);
    }
    
}
