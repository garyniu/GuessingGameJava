import java.util.Scanner; //scanner import to allow for user inputs

/**
 * Yet Another Number Guessing Game (YANGG) By Gary Niu
 * 
 * Features: 
 * - 2 seperate point systems, with 4 point equations in total
 * - Dynamic Hint selection that affects the final score
 * - Crash-Proof! (Through Try... Catch loops)
 * - 3 Diffirent Stories, with each having their own scoring equations
 * - Selectable Difficulties, Easy being whole numbers, Medium adding 2 decimals, Hard adding 4 decimals
 * 
 * Instruction / Notes:
 * - Story 1 is the funniest (subjectively)
 * - Medium and Hard is pretty frusturating and boring, just stick to Easy
 * - If you really want to skip everything and test the equations / other things, 
 *   just have Difficulty = 1 and enter the highest number as 1
 * - If you want to see development history and some other random notes for this project, 
 *   here is the GitHub I set up for it: https://github.com/garyniu/GuessingGameJava
 * 
 * Credit for borrowed code / assets:
 * - readyGame loop and input (modified) from Mr.Cohen
 * - Generate 2 / 4 decimal number (Accepted answer) from Stefan Haustein 
 *   https://stackoverflow.com/questions/39470238/fastest-way-to-generate-a-random-double-number-in-java
 */

public class NGyetAnotherGuessingGame
{
    private static Scanner scan = new Scanner (System.in); //creating new scanner
    public static void main (String[] args){
        
        String readyGame = "";
        float score;
        int difficulty, storyChoice;
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
        
        //borrowed from Mr.Cohen
        while (readyGame.equals("Y")){
            
            difficulty = difficultySelection();//difficuly selection
            
            guessNumber = numGen(0, difficulty);// runs random number generation with difficulty
            
            storyChoice = story(); //runs story selection, then returns a 1/2/3 number for further use
            
            mainGame(guessNumber, difficulty, storyChoice); //main game, where the number guessing is, takes in the generated number, difficulty, and story choice
            
            //to play again
            println("Do you want to play again? Y/N");
            readyGame = scan.nextLine().toUpperCase(); 
            
        }
        
        //final exit text
        System.out.println("\nThanks for playing YANGG! Remember to rate 5 stars on DMOJ! \nAll hail Lord and Saviour Bruce!");
    }
    
    //guesing main game
    private static int mainGame (double generatedNumber, int difficulty, int storyChoice) {
        int timesGuessed = 0, tempNumber, smallHint = 0, bigHint = 0, score = 0, arbitraryScore = 0;
        int[] tempHintAmount = new int[2]; //array to store the amount of hints used from hint method
        double guess;//double for the user guesses to allow for decimals
        String guessNumberTemp; //for usage in the anti-crashing code for entry
        String[] storyLines = new String[2];
        tempNumber = (int) generatedNumber; //for easy difficulty, removes the unneeded empty decimals 
        
        //short lines of text to display for user
        if (storyChoice == 1){
            storyLines[0] = "Good job! Taxes are staying steady!";
            storyLines[1] = "Oh no! Taxes have been raised by a substantial amount!";
        } else if (storyChoice == 2){
            storyLines[0] = "Good job! Your salary is consistent!";
            storyLines[1] = "Oh no! You lost a major chunk of your salary!";
        } else {
            storyLines[0] = "Hooray! Free money!";
            storyLines[1] = "Boo! That’s not even enough money for 8/1000 of a 3080 on Ebay!";
        }
        
        println("\nStart guessing!");
        //no
        
        while (true) {
            //prevents user from entering anything else besides number and crashing program
            while (true){
                guessNumberTemp = scan.nextLine();
                try {
                    guess = Double.parseDouble(guessNumberTemp);
                    break;
                } catch (NumberFormatException e) {
                    println("Please respond with either a whole number or a decimal.");
                    continue;
                }
            }
            
            if (guess == generatedNumber){ // if user gets guess correct
                if (timesGuessed <= 1){ //if user gets the guess in 1 try or less (not phyiscally possible, but just in case)
                    if (difficulty == 1){
                        // seperated the print message just for specific case where user might get it right in 1 try
                        println("Correct!\nThe number was "+ tempNumber + ".\nIt took you 1 try to guess the number.\n"); //easy difficulty does not have any decimals
                    } else {
                        //for cases where the difficulty was either medium or hard
                        println("Correct!\nThe number was "+ generatedNumber + ".\nIt took you 1 try to guess the number.\n");
                    }
                } else { // if user got it over 1 try
                    if (difficulty == 1){ // if user selected easy difficulty
                        println("Correct!\nThe number was "+ tempNumber + ".\nIt took you " + timesGuessed + " tries to guess the number.\n");
                    } else { //all other difficulties
                        println("Correct!\nThe number was "+ generatedNumber + ".\nIt took you " + timesGuessed + " tries to guess the number.\n");
                    }
                }
                
                //Seperated whole equation into seperate equations, as Java was having some problems with orders of operation
                arbitraryScore = 50 - timesGuessed;
                arbitraryScore = arbitraryScore * 2;
                arbitraryScore = arbitraryScore - (smallHint * 5); //punishemnt for usage of smallHints, caps at 4
                arbitraryScore = arbitraryScore - (bigHint * 10); //punishment for usage of bigHints, caps at 4
                
                //a universal score to allow comparision between all difficulty levels and all story choices
                println("Your calculated score was " + arbitraryScore + ".\n");
                
                //story endings, 2 for each story for success or failure
                if (storyChoice == 1 && score > 15) println("Unfortunately, your mind-control device was not effective enough at preventing\nDoug Ford from raising taxes. Seems like you will have to go back to working\nin the rat race, paying " + score + "% in taxes.");
                else if (storyChoice == 1 && score <= 15) println("Taxes have successfully been lowered to " + score + "%. \nSeems like you'll be able to retire after all! \nDoug Ford has been kicked out of office, \nafter being found to be the person that supplied Rob Ford with methamphetamine.");
                
                if (storyChoice == 2 && score < 70000) println("Unfortunately, your negotiation tactics were not that effictive at the number guessing game. \nYour salary had fallen to " + score + " dollars.");
                else if (storyChoice == 2 && score >= 70000) println("Success! Your \"tatics\" were efficitive, \nand your salary only dropped to " + score + " dollars.");
                
                if (storyChoice == 3 && score < 1300) println("The amount money was so small, that it would have cost more printing \nthe cheque than the money is worth itself. \nEach cheque is worth " + score + " dollars only.");
                else if (storyChoice == 3 && score >= 1300)println("Impressive! Your guessing was so good, \nthat you might be able to get a 2nd hand 1070 TI on Ebay with the cheque!\nEach cheque is worth " + score + " dollars.");
                
                //prints depending on the universal score
                if (arbitraryScore <= 50) println ("\nBetter luck next time!");
                else println ("\nGood job on guessing!");
                
                return 0;
    
            } else if (guess < generatedNumber){ //if guess is lower than the generated number
                println("Too low!");
            } else if (guess > generatedNumber){ //if guess is higher than the generated number
                println("Too high!");
            }
            
            //java cannot do rules of operation so i just slammed everything in here
            if (storyChoice == 1) {
                score = 2 * timesGuessed;
                score = score * score;
                score = score / (timesGuessed + 2);
            } else if (storyChoice == 2) {
                score = 15 * timesGuessed;
                score = score * score;
                score = score * 2;
                score = 80000 - score;
            } else if (storyChoice == 3) {
                score = timesGuessed * 5;
                score = score * score;
                score = 2000 - score;
            }
            
            //prevents score from being negative
            if (score < 0) score = 0;
            
            //prints the aformentioned mid-guessing story lines every 3 tries
            if (storyChoice == 1 && score <= 15 && (timesGuessed % 3) == 0 && timesGuessed != 0) println(storyLines[0]);
            else if (storyChoice == 1 && score > 15 && (timesGuessed % 3) == 0&& timesGuessed != 0) println(storyLines[1]);
            
            if (storyChoice == 2 && score >= 70000 && (timesGuessed % 3) == 0 && timesGuessed != 0) println(storyLines[0]);
            else if (storyChoice == 2 && score < 70000 && (timesGuessed % 3) == 0 && timesGuessed != 0) println(storyLines[1]);
            
            if (storyChoice == 3 && score >= 1300 && (timesGuessed % 3) == 0 && timesGuessed != 0) println(storyLines[0]);
            else if (storyChoice == 3 && score < 1300 && (timesGuessed % 3) == 0 && timesGuessed != 0) println(storyLines[1]);
            
            //access hint method, and stores the return value in a size 2 integer array as methods do not allow multiple returns
            tempHintAmount = hint(smallHint, bigHint, generatedNumber, guess, difficulty);
            //takes the numbers from the array returned by hint method and places in two integers for access later
            smallHint = tempHintAmount[0];
            bigHint = tempHintAmount[1];
            
            //add 1 for each time this is looped
            timesGuessed++;
        }
        
    }   
    
    private static int story () {
        String userInputStory, contStory;
        int storyChoice, userTypeLength;
        
        //stories stored in an array, for looping through
        String[] story1 = {"\nThis year, Doug Ford has just been re-elected to be the Mayor of Ontario!", 
                           "Now, in a recent news article from the CBC, it says that he is planning to \nraise taxes for the middle class yet again, and lower taxes for the high class!", 
                           "Fortunately, you have a mind-control device at your hands, and you can use it to change his mind.", 
                           "Unfortunately, Ford’s mind is so hardwired by the bribes from the upper class, \nthat he is unable to lower taxes, only to raise them.", 
                           "Even more, unfortunately, his mind is encoded by a 64 bit randomly generated number game, \nfrom a Grade 11 Computer science project.", 
                           "Guess the number right, and Doug Ford’s tax-raising spree will slow down. \nGet too many wrong, and prepare to say bye-bye to your retirement fund!"}, 
                 story2 = {"\nThe new superintendent of the YRDSB, Louise Sirisko, \nhas made some sweeping changes to the education system.", 
                           "Unfortunately, for you the player, this meant massive budget cuts, \nso the superintendent can keep over her tax fraud quota.", 
                           "One aspect that majorly affected you was the salary cuts, and this year, \nshe is yet again proposing more cuts!", 
                           "You could have made more money in 5 years working as a \nplumber compared to 10 years as a teacher at this point!", 
                           "Luckily, you have your good ole’ negotiation tactics, which obviously do not include break and enter, \nand holding someone hostage at 2 AM in the morning.", 
                           "For some odd reason, the superintendent now intends to \nbase your salary on a 64-bit number guessing game, \nwhere every guess wrong is an amount off of your salary.", 
                           "You agree to it, desperate to have it quickly over before the police arrive."}, 
                 story3 = {"\nNobody likes worldwide pandemics.", 
                           "The only good part of it is the “free” money handouts.", 
                           "Because of the new COVID variant originating from South Africa, \nthe Canadian government is now considering doing another handout.", 
                           "For some strange reason, they want to use a Grade 11 Computer Science \nproject to calculate how much money to give out.", 
                           "Fine by you, as you like free money."};
                           
        println("\nWould you like Story 1, (Doug Ford and Taxes), Story 2, (Superintendent of YRDSB and budget cuts),\nStory 3, (Pandemic Money handouts), or (R)andom?");
        
        //story selection
        while (true){
            userInputStory = scan.nextLine();
            if (userInputStory.equals("1")){
                storyChoice = 1;
                break;
            } else if (userInputStory.equals("2")) {
                storyChoice = 2;
                break;
            } else if (userInputStory.equals("3")) {
                storyChoice = 3;
                break;
            } else if (userInputStory.equals("r") || userInputStory.equals("R")) {
                //generates a random number between 1 and 3
                storyChoice = 3 + (int)(Math.random() * ((3 - 1) + 1));
                break;
            } else {
                println("Please respond with a 1, 2, 3, or (R)andom.");
            }
        }
        
        //prints out the stories to console with a 3 second delay between each line, try - catch there cause idk really, it said something about an exception
        if (storyChoice == 1){
            for (int i = 0; i < story1.length; i++){
                System.out.println(story1[i]);
                try {
                    Thread.sleep(3000);
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        } else if (storyChoice == 2){
            for (int i = 0; i < story2.length; i++){
                System.out.println(story2[i]);
                try {
                    Thread.sleep(3000);
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        } else {
            for (int i = 0; i < story3.length; i++){
                System.out.println(story3[i]);
                try {
                    Thread.sleep(3000);
                } catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        //returns either 1, 2, or 3 for story selection
        return storyChoice;
    }
    
    //god save me
    private static int[] hint (int smallHint, int bigHint, double guessingNumber, double guess, int difficulty){
        String hintSelection, guessingNumberWholeString = String.valueOf(guessingNumber), guessingNumberDecimalString = "0", overHintLimitBig, overHintLimitSmall, guessNumberWhole = String.valueOf(guess), rightPositionChars = "", guessingNumberTempAlt = "", guessNumberTempAlt = "";
        int numLength = 0, loopNum = 0, loopNumGuess = 0, zerosMissing, rightPosCount = 0, forLoopRepeatTimes;
        int[] hintAmount = new int[2];
        hintAmount[0] = smallHint; 
        hintAmount[1] = bigHint;
        
        //compare the guess and the hidden number to see which ones are in the right place and how many are in the right place
        //two while loops for counting amount of numbers before decimal, to align the two numbers
        while (true){
            if (guessingNumberWholeString.charAt(loopNum) == '.'){
                break;
            }
            loopNum++;
        }
        
        while (true){
            if (guessNumberWhole.charAt(loopNumGuess) == '.'){
                break;
            }
            loopNumGuess++;
        }
        
        zerosMissing = loopNum - loopNumGuess;
        
        //adds zeros to the guess if it is smaller than the generated number
        for (int i = 0; i < zerosMissing; i++){
            guessNumberWhole = "0" + guessNumberWhole;
        }
        
        //puts the forlooprepeattimes to equal the number with the larger length
        if (guessingNumberWholeString.length() < guessNumberWhole.length()){
            forLoopRepeatTimes = guessingNumberWholeString.length(); 
        } else {
            forLoopRepeatTimes = guessNumberWhole.length(); 
        }
        
        //method just for easy difficulty, removes the last 0 that comes with the double, so it does not show up as a correct digit in hint
        if (difficulty == 1){
            guessingNumberTempAlt = guessingNumberWholeString.substring(0, guessingNumberWholeString.length() - 1);
            guessNumberTempAlt = guessNumberWhole.substring(0, guessNumberWhole.length() - 1);
            
            if (guessingNumberTempAlt.length() < guessNumberTempAlt.length()){
                forLoopRepeatTimes = guessingNumberTempAlt.length(); 
            } else {
                forLoopRepeatTimes = guessNumberTempAlt.length(); 
            }
        }
        
        //counts how many numbers were in the right position, and what numbers they were into a string and an integer
        for (int i = 0; i < forLoopRepeatTimes; i++){
            if (difficulty == 1){
                if (guessingNumberTempAlt.charAt(i) == guessNumberTempAlt.charAt(i) && (guessingNumberTempAlt.charAt(i) != '.' || guessNumberTempAlt.charAt(i) != '.')){
                    rightPositionChars = rightPositionChars + guessNumberTempAlt.charAt(i);
                    rightPosCount++;
                }
            } else {
                if (guessingNumberWholeString.charAt(i) == guessNumberWhole.charAt(i) && (guessingNumberWholeString.charAt(i) != '.' || guessNumberWhole.charAt(i) != '.')){
                    rightPositionChars = rightPositionChars + guessNumberWhole.charAt(i);
                    rightPosCount++;
                }
            }
        }
        
        //seperate the guessing number into whole and decimal
        if (difficulty == 1){
            guessingNumberWholeString = guessingNumberWholeString.substring(0, guessingNumberWholeString.length() - 2);
            numLength = guessingNumberWholeString.length();
        } else if (difficulty == 2){
            guessingNumberDecimalString = guessingNumberWholeString.substring(Math.max(guessingNumberWholeString.length() - 2, 0));
            guessingNumberWholeString = guessingNumberWholeString.substring(0, guessingNumberWholeString.length() - 3);
            numLength = guessingNumberWholeString.length() + 2;
        } else if (difficulty == 3){
            guessingNumberDecimalString = guessingNumberWholeString.substring(Math.max(guessingNumberWholeString.length() - 4, 0));
            guessingNumberWholeString = guessingNumberWholeString.substring(0, guessingNumberWholeString.length() - 5);
            numLength = guessingNumberWholeString.length() + 4;
        }
        
        //text for selecting hints
        println("\nWould you like a (S)mall hint or a (B)ig hint? \nType anything else to continue.");
        hintSelection = scan.nextLine().toUpperCase();
        
        
        if (hintSelection.equals("S")){
            //warns user of repeating hints
            if (smallHint > 3){ 
                println("Maximum amounts of small hints given.\nAre you sure you want another small hint? Y/N \n(Asking for any more small hints will no longer affect your score.)");
                while (true){
                    overHintLimitSmall = scan.nextLine().toUpperCase();
                    if (overHintLimitSmall.equals("N")){
                        println("Guess!\n");
                        return hintAmount;
                    } else if (overHintLimitSmall.equals("Y")){
                        break;
                    } else {
                        println("Please respond with a (Y)es or a (N)o.");
                    }
                }
            }
            
            //gives the hints
            if (smallHint == 0 || smallHint % 4 == 0){
                if (Integer.parseInt(guessingNumberWholeString) % 2 == 0){
                    println("The number can be evenly divided by 2.");
                } else {
                    println("The number cannot be evenly divided by 2.");
                }
                
            } else if (smallHint % 4 == 1){

                if (Integer.parseInt(guessingNumberDecimalString) < 50){
                    println("The decimal section of the number is less than 1/2.");
                } else {
                    println("The decimal section of the number is larger than 1/2.");
                }
                
            } else if (smallHint % 4 == 2){
                System.out.println("Your guess had " + rightPosCount + " position/s right.");
                
            } else if (smallHint % 4 == 3){
                 for (int i = 2; i < 10; i++){
                     if (Integer.parseInt(guessingNumberWholeString) % i == 0){
                         System.out.println("The number is divisible by " + i + ".");
                         break;
                     }
                 }
            }
            
            smallHint++;
            
        } else if (hintSelection.equals("B")){
            //warns user of repeating big hints
            if (bigHint > 4){
                println("Maximum amounts of big hints given.\nAre you sure you want another big hint? Y/N \n(Asking for any more big hints will no longer affect your score.)");
                while (true){
                    overHintLimitBig = scan.nextLine().toUpperCase();
                    if (overHintLimitBig.equals("N")){
                        println("Guess!\n");
                        return hintAmount;
                    } else if (overHintLimitBig.equals("Y")){
                        break;
                    } else {
                        println("Please respond with a (Y)es or a (N)o.\n");
                    }
                }
            }
            
            //prints out big hints
            if (bigHint == 0 || bigHint % 5 == 0){
                System.out.println("The first digit of the number is " + guessingNumberWholeString.charAt(0) + ".");
            } else if (bigHint % 5 == 1){
                System.out.println("The last digit of the number is " + guessingNumberWholeString.charAt(guessingNumberWholeString.length() - 1) + ".");
            } else if (bigHint % 5 == 2){
                System.out.println("There are " + numLength + " digit/s in the number.");
            } else if (bigHint % 5 == 3){
                if (difficulty != 1){
                    System.out.println("The last decimal of the number is " + guessingNumberDecimalString.charAt(guessingNumberDecimalString.length() - 1) + ".");
                } else {
                    System.out.println("The center digit of the number is " + guessingNumberWholeString.charAt((guessingNumberWholeString.length() / 2)) + ".");
                }
            } else if (bigHint % 5 == 4){
                
                //execption for no guesses right
                if (rightPositionChars == "" || rightPositionChars == "0"){
                    println("None of the digits in your guess was right.");
                } else {
                    if (rightPositionChars.length() == 1){
                        System.out.print("The digit you had right was" + rightPositionChars + ".");
                    } else {
                        //to print out all the correct number, and have the last number printed out as ", and x."
                        System.out.print("The digits you had right were");
                        for (int i = 0; i < rightPositionChars.length() - 1; i++){
                            System.out.print(" ");
                            System.out.print(rightPositionChars.charAt(i));
                            System.out.print(",");
                        }
                        System.out.print(" and " + rightPositionChars.charAt(rightPositionChars.length() - 1) + ".\n");
                    }
                }
            }
            
            bigHint++;
        }
        
        //needed prompt, as it would kick you out from method with no siginal
        println("Guess!\n");
        
        //puts the 2 numbers in array to return
        hintAmount[0] = smallHint;
        hintAmount[1] = bigHint;
        return hintAmount;
    }
    
    //difficulty selection
    private static int difficultySelection (){
        String difficult;
        int howDifficult;
        
        println("\nWhat difficulty would you like?\n(E)asy - (M)edium - (H)ard");
        
        //forces user to select difficulty
        while (true){
            difficult = scan.nextLine().toUpperCase();

            //method returns difficulty as an integer for later usage and refrence
            if (difficult.equals("E")){
                println("\nEasy selected.\nThe number is going to be a whole number.");
                return 1;
            } else if (difficult.equals("M")){
                println("\nMedium selected.\nThe number is going to have 2 decimal places.");
                return 2;
            } else if (difficult.equals("H")){
                println("\nHard selected.\nThe number is going to have 4 decimal places.");
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
        String upperBountTemp;
        //base line is 0, and user inputed number is the maximum of what the number can be
        //no negative numbers, maybe later (probably not cause im lazy)
        
        //string supremacy (Although c++ got the rights to have lowercase s in their string)
        println("\nHow high do you want the number to be?");
        while (true){
            upperBountTemp = scan.nextLine();
            try {
                upperBound = Double.parseDouble(upperBountTemp);
                break;
            } catch (NumberFormatException e) {
                println("Please respond with either a whole number or a decimal.");
                continue;
            }
        }
        
        //storage for the final statement, unaltered number
        upperBoundAlt = (int)upperBound;
        
        if (difficulty == 1){ //generates whole number
            intRand = (int)(Math.random() * upperBound + 1);//shamelessly stolen from stack overflow 
            
            System.out.println(intRand);//REMOVE THIS IN FINAL
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
