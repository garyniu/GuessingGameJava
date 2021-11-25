import java.util.Scanner;

public class NumberGuessingGame
{
    private static Scanner scan = new Scanner (System.in);
    public static void main (String[] args){
        
        String readyGame = "";
        float score;
        int difficulty;
        double guessNumber;
        
        System.out.print('\u000C');
        println("\nWelcome to YANGG (Yet Another Number Guessing Game)!");
        println("Ready? Y/N");
        
        while (true){
            readyGame = scan.nextLine().toUpperCase(); 
            if ("Y".equals(readyGame) || "N".equals(readyGame)){
                break;
            }
            println("\nPlease respond with a (Y)es or a (N)o.");
        }
        
        
        while (readyGame.equals("Y")){
            //add the harder difficutlies here and in numgen
            
            difficulty = difficultySelection();
            System.out.println(difficulty);
            
            guessNumber = numGen(0, difficulty);// runs numgen
            
            mainGame(guessNumber);           
            
            println("Do you want to play again? Y/N");
            readyGame = scan.nextLine().toUpperCase(); 
            
        }
        
        System.out.println("\nThanks for playing YANGG! Remember to rate 5 stars on DMOJ! \nAll hail Lord and Saviour Bruce!");
    }
    
    //guesing main game
    private static int mainGame (double x) {
        int timesGuessed = 0;
        double guess;
        
        println("Start guessing!");
        
        while (true) {
            guess = scan.nextDouble();
            
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
    
    private static int difficultySelection (){
        String difficult;
        int howDifficult;
        println("What difficulty would you like?\n(E)asy - (M)edium - (H)ard");
        
        while (true){
            difficult = scan.nextLine().toUpperCase();
            
            if (difficult.equals("E")){
                println("Easy selected.");
                return 1;
            } else if (difficult.equals("M")){
                println("Medium selected.");
                return 2;
            } else if (difficult.equals("H")){
                println("Hard selected.");
                return 3;
            } else {
                println("Please respond with (E)asy, (M)edium, or (H)ard.");
            }
        }
    }
    
    //random number generator with user input
    private static double numGen (double intRand, int difficulty) {
        double upperBound;
        int upperBoundAlt;
        
        println("\nHow high do you want the number to be?");
        upperBound = scan.nextInt();
        scan.nextLine();
        
        upperBoundAlt = (int)upperBound;
        
        if (difficulty == 1){
            intRand = (int)(Math.random() * upperBound + 1);
        } else if (difficulty == 2){
            intRand = (int)(Math.random() * upperBound + 1);
            intRand += Math.random();
            intRand = ((double)((int)(intRand * 100.0)))/100.0;
            System.out.println(intRand);
        } else {
            intRand = (int)(Math.random() * upperBound + 1);
            intRand += Math.random() / 2;
            intRand = ((double)((int)(intRand * 10000.0)))/10000.0;
            System.out.println(intRand);
        }
        
        
        println("\nOkay, The number is between 1 and " + upperBoundAlt + ".");
        return intRand;
        
    }
    
    //short form print line
    private static void println(String x){
        System.out.println(x);
    }
    
}
