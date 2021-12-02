import java.util.Scanner; //scanner

public class NumberGuessingGame
{
    private static Scanner scan = new Scanner (System.in); //creating new scanner
    public static void main (String[] args){
        
        String readyGame = "";
        float score;
        int difficulty;
        double guessNumber;
        
        System.out.print('\u000C');// clears screen
        //general welcome text
        println("\nWelcome to YANGG (Yet Another Number Guessing Game)!");
        println("Ready? Y/N");
        
        //forces user to respond with either a y or a n
        while (true){
            readyGame = scan.nextLine().toUpperCase(); 
            if ("Y".equals(readyGame) || "N".equals(readyGame)){
                break;
            }
            println("\nPlease respond with a (Y)es or a (N)o.");
        }
        
        
        while (readyGame.equals("Y")){
            
            difficulty = difficultySelection();//difficuly selection
            
            guessNumber = numGen(0, difficulty);// runs numgen
            
            mainGame(guessNumber, difficulty);           
            
            //to play again
            println("Do you want to play again? Y/N");
            readyGame = scan.nextLine().toUpperCase(); 
            
        }
        
        //final text
        System.out.println("\nThanks for playing YANGG! Remember to rate 5 stars on DMOJ! \nAll hail Lord and Saviour Bruce!");
    }
    
    //guesing main game
    private static int mainGame (double x, int difficulty) {
        int timesGuessed = 0, tempNumber, smallHint = 0, bigHint = 0;
        int randHint = (int)(Math.random() * 2 + 1);//test int, if still here in final hand in then i am idiot and forgor to delete
        int[] tempHintAmount = new int[2]; //array to store the amount of hints used from hint method
        //double for the user guesses to allow for decimals
        double guess;
        
        //RENAME X (DO IT)(NOW)
        tempNumber = (int) x; //for easy difficulty, removes the unneeded empty decimals 
        
        println("Start guessing!");
        //no
        
        while (true) {
            //double to include decimals
            guess = scan.nextDouble();
            
            if (guess == x){ // if user gets guess correct
                if (timesGuessed <= 1){ //if user gets the guess in 1 try or less (not phyiscally possible, but just in case)
                    if (difficulty == 1){
                        // seperated the print message just for specific case where user might get it right in 1 try
                        println("Correct!\nThe number was "+ tempNumber + ".\nIt took you 1 try to guess the number.\n"); //easy difficulty does not have any decimals
                    } else {
                        //for cases where the difficulty was either medium or hard
                        println("Correct!\nThe number was "+ x + ".\nIt took you 1 try to guess the number.\n");
                    }
                } else { // if user got it over 1 try
                    if (difficulty == 1){ // if user selected easy difficulty
                        println("Correct!\nThe number was "+ tempNumber + ".\nIt took you " + timesGuessed + " tries to guess the number.\n");
                    } else { //all other difficulties
                        println("Correct!\nThe number was "+ x + ".\nIt took you " + timesGuessed + " tries to guess the number.\n");
                    }
                }
                //java more like bruh
                scan.nextLine();//oracle foundation pls fix
                return 0;
                
            } else if (guess < x){ //if guess is lower than the generated number
                println("Too low!");
            } else if (guess > x){ //if guess is higher than the generated number
                println("Too high!");
            }
            
            scan.nextLine();//oracle foundation pls fix
            
            //tempHintAmount still in 
            tempHintAmount = hint(smallHint, bigHint, randHint, x, guess, difficulty);
            //takes the numbers from the array returned by hint method and places in two integers for access later
            smallHint = tempHintAmount[0];
            bigHint = tempHintAmount[1];
            System.out.println(smallHint + " | " + bigHint);//test code, remove later
            
            //add 1 for each time this is looped
            timesGuessed++;
        }
        
    }   
    
    //god save me
    private static int[] hint (int smallHint, int bigHint, int randomHint, double guessingNumber, double guess, int difficulty){
        String hintSelection, guessingNumberWholeString = String.valueOf(guessingNumber), guessingNumberDecimalString = "";
        int integerGuessingNumber, integerGuess;
        double guessingNumberAlt = guessingNumber, guessAlt = guess;
        
        //seperate the guessing number into whole and decimal
        if (difficulty == 1){
            guessingNumberWholeString = guessingNumberWholeString.substring(0, guessingNumberWholeString.length() - 2);
        } else if (difficulty == 2){
            guessingNumberDecimalString = guessingNumberWholeString.substring(Math.max(guessingNumberWholeString.length() - 2, 0));
            guessingNumberWholeString = guessingNumberWholeString.substring(0, guessingNumberWholeString.length() - 3);
            
            //test code
            println(guessingNumberDecimalString);
            println(guessingNumberWholeString);
        } else if (difficulty == 3){
            guessingNumberDecimalString = guessingNumberWholeString.substring(Math.max(guessingNumberWholeString.length() - 4, 0));
            guessingNumberWholeString = guessingNumberWholeString.substring(0, guessingNumberWholeString.length() - 5);
            
            //test code
            println(guessingNumberDecimalString);
            println(guessingNumberWholeString);
        }
        
        //puts the guess and guess number into integers for comparision
        /*
        if (difficulty == 2){
            integerGuessingNumber = (int) guessingNumber * 100;
            integerGuess = (int) guess * 100;
        } else if (difficulty == 3){
            integerGuessingNumber = (int) guessingNumber * 10000;
            integerGuess = (int) guess * 10000;
        }
        */
        
        //text for selecting hints
        println("Would you like a (S)mall hint or a (B)ig hint? \nType anything else to continue.");
        hintSelection = scan.nextLine().toUpperCase();
        
        
        if (hintSelection.equals("S")){
            
            smallHint++;
        } else if (hintSelection.equals("B")){
            System.out.println("The first digit of the number is " + guessingNumberWholeString.charAt(0) + ".");
            System.out.println("The last digit of the number is " + guessingNumberWholeString.charAt(guessingNumberWholeString.length() - 1) + ".");
            System.out.println("The last decimal of the number is " + guessingNumberDecimalString.charAt(guessingNumberWholeString.length() - 1) + ".");
            
            
            //System.out.println("There are " + numLength + " digits in the number.");
            //System.out.println();
            
            System.out.println(randomHint);
            bigHint++;
        }
        
        //needed prompt, as it would kick you out from method with no siginal
        println("Guess!");
        
        //creates array and puts the 2 numbers in to return
        int[] hintAmount = new int[2];
        hintAmount[0] = smallHint;
        hintAmount[1] = bigHint;
        return hintAmount;
    }
    
    //difficulty selection
    private static int difficultySelection (){
        String difficult;
        int howDifficult;
        
        println("What difficulty would you like?\n(E)asy - (M)edium - (H)ard");
        
        //forces user to select difficulty
        while (true){
            difficult = scan.nextLine().toUpperCase();

            //method returns difficulty as an integer for later usage and refrence
            if (difficult.equals("E")){
                println("Easy selected.\nThe number is going to be a whole number.");
                return 1;
            } else if (difficult.equals("M")){
                println("Medium selected.\nThe number is going to have 2 decimal places.");
                return 2;
            } else if (difficult.equals("H")){
                println("Hard selected.\nThe number is going to have 4 decimal places.");
                return 3;
            } else {
                println("Please respond with (E)asy, (M)edium, or (H)ard.");
            }
        }
    }
    
    //random number generator with user input
    private static double numGen (double intRand, int difficulty) {
        double upperBound;//double cause code breaks for some reason oracle foundation pls fix
        int upperBoundAlt;
        
        //base line is 0, and user inputed number is the maximum of what the number can be
        //no negative numbers, maybe later (probably not cause im lazy)
        
        println("\nHow high do you want the number to be?");
        upperBound = scan.nextInt();
        scan.nextLine(); //oracle foundation pls fix
        
        //storage for the final statement, unaltered number
        upperBoundAlt = (int)upperBound;
        
        if (difficulty == 1){ //generates whole number
            intRand = (int)(Math.random() * upperBound + 1);//shamelessly stolen from stack overflow 
        } else if (difficulty == 2){ // generates number with 2 decimal places
            intRand = (int)(Math.random() * upperBound + 1);//shamelessly stolen from stack overflow 
            
            //to prevent the number from being over the user selected number
            if (intRand == upperBound){
                intRand--;
            }
            
            //generates decimal, adds to the number, then typeCasted into a maximum of 2 decimals
            intRand += Math.random();
            intRand = ((double)((int)(intRand * 100.0)))/100.0; //i made this, not stolen from stack overflow
            
            System.out.println(intRand);//REMOVE THIS IN FINAL
        } else { // generates number with 4 decimal places, optimally should be a else if statement but it doesn't really matter
            intRand = (int)(Math.random() * upperBound + 1);//shamelessly stolen from stack overflow 
            
            //to prevent the number from being over the user selected number
            if (intRand == upperBound){
                intRand--;
            }
            
            //generates decimal, adds to the number, then typeCasted into a maximum of 4 decimals
            intRand += Math.random() / 2;
            intRand = ((double)((int)(intRand * 10000.0)))/10000.0;//i forgot where i stole this from

            System.out.println(intRand);//REMOVE THIS IN FINAL
        }
        
        //prints and returns number
        println("\nOkay, The number is between 1 and " + upperBoundAlt + ".");
        return intRand;
        
    }
    
    //short form print line
    private static void println(String x){
        System.out.println(x);
    }
    
}
