package com.wictorsundstrom.Inlamning2;

import java.util.ArrayList;
import java.util.Scanner;

public class Inlamning2 {

    private static Scanner scan = new Scanner(System.in);
    private static ArrayList<Integer> primeList = new ArrayList<>();
    private static boolean isPrime = true;
    private static boolean isAdded = true;

    // main
    // Startar menyn
    public static void main (String []args){
        menu();
    }

    // menu
    // Bryts enbart om användaren skriver 0, kontrollerar om input är 0-9 och sedan startar valda metoden.
    private static void menu(){
        int menuChoice;
        String inputCheck;
        boolean breakMenu = true;

        while(breakMenu) {
            System.out.println("What do you want to do?\n1) Add\n2) Sort\n3) Search\n4) Find largest prime added and add multiple primes\n0) Exit");
            System.out.print("Menu choice: ");

            while (!(inputCheck = scan.nextLine()).matches("[0-9]+")){
                System.out.print("Not an int (0-9), Try again: ");
            }

            menuChoice = Integer.parseInt(inputCheck);

            switch (menuChoice) {
                case 1:
                    add();
                    break;
                case 2:
                    sort();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    addMultiplePrimes();
                    break;
                case 0:
                    breakMenu = false;
                    break;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    // add
    // Kollar om input är 0-9, är den 0-9 startar den metoden checkIfPrime och checkIfNumberExists
    private static void add(){
        String choice;
        int inputToInt;

        System.out.print("Input to check: ");

        // a)
        while (!(choice = scan.nextLine()).matches("[0-9]+")){
            System.out.print("Not an int, Try again: ");
        }

        inputToInt = Integer.parseInt(choice);

        // b)
        checkIfPrime(inputToInt);

        // c)
        checkIfNumberExists(inputToInt);
    }

    // checkIfPrime
    // Kollar om input modulo i * i <= input. Ålltså om 2*2 är lägre än input,
    // stämmer inte det ökar den till 3*3 sen 5*5 osv osv osv tills det att i * i blir större än input och då kollar den om den är prim eller inte.
    private static void checkIfPrime(int numberToCheck) {
        isPrime = true;
        int i = 2;

        if(numberToCheck <= 0 || numberToCheck == 1){
            isPrime = false;
        } else {
            while (i*i <= numberToCheck) {
                if (numberToCheck % i == 0) {
                    isPrime = false;
                    break;
                }
                if(i == 2){
                    i++;
                }
                i = i + 2;
            }
        }
    }

    // checkIfNumberExists
    // Tar emot input och kollar om det redan finns i arraylisten (primeList)
    private static void checkIfNumberExists(int numberToCheck) {
        if (!primeList.contains(numberToCheck) && isPrime) {
            primeList.add(numberToCheck);
            System.out.println(numberToCheck + " has been added");
            sumList();
            isAdded = true;
        }else if (isPrime) {
            System.out.println("The given input already exists");
            isAdded = false;
        }else {
            System.out.println(numberToCheck + " is not a prime");
        }
    }

    // sumList
    // Summerar allt i primeList och kallar efter det på checkIfPrime
    // Om det är ett primtal kallar den på checkIfNumberExists
    private static void sumList(){
        // d)
        int sum = 0;
        if(primeList.size()>1) {
            for (int i : primeList) {
                sum += i;
            }
            checkIfPrime(sum);
            checkIfNumberExists(sum);
        }
    }

    // sort
    // Kontrollerar om Arraylistan är tom.
    // Om den har minst 1 element så ska den sortera (Och kallar då på shellSort)
    private static void sort(){
        // e)
        if(primeList.size() < 1){
            System.out.println("Arraylist is empty, add something first");
        } else  {
            System.out.print("Before: ");
            System.out.println(primeList);

            shellSort();

            System.out.print("After: ");
            System.out.println(primeList);
        }
    }

    // shellSort
    // Sorterar arraylisten med hjälp av en shellSort för enligt videos jag kollade så är den en av de snabbaste och jag kände att då vill jag gärna testa på den och lära mig.
    // Den kollar avståndet mellan talen och ökar det
    private static void shellSort() {
        int j;
        int temp;

        for (int gap = primeList.size()/2; gap > 0; gap /= 2) {
            for (int i = gap; i < primeList.size(); i++) {
                temp = primeList.get(i);

                for (j = i; j >= gap && primeList.get(j-gap) > temp; j -= gap) {
                    primeList.set(j, primeList.get(j - gap));
                }
                primeList.set(j, temp);
            }
        }
    }

    // search
    // Kontrollerar om Arraylistan är tom.
    // Om den har minst 1 element så ska den söka. (Och kallar då på searchForValue)
    private static void search(){
        if (primeList.size() < 1) {
            System.out.println("Arraylist is empty, add something first");

        } else {
            searchForValue();
        }
    }

    // searchForValue
    // Söker efter input, Hittar den inte så startar den metoderna (closestLowerIndex och closestHigherIndex)
    // och sedan efter det startar den checkClosestIndex
    private static void searchForValue(){
        // f)
        String inputCheck;
        int index;
        int number;

        System.out.println("Which int are you looking for?");

        while (!(inputCheck = scan.nextLine()).matches("[0-9]+")){
            System.out.print("Not an int, Try again: ");
        }

        int intToSearchFor = Integer.parseInt(inputCheck);

        if((index = primeList.indexOf(intToSearchFor)) >= 0){
            System.out.println(intToSearchFor + " was found at index " + index);
        } else {
            int lowerNumber = closestLowerIndex(intToSearchFor);
            int higherNumber = closestHigherIndex(intToSearchFor);
            number = checkClosestIndex(lowerNumber, higherNumber, intToSearchFor);

            if(number == 0){
                System.out.println(intToSearchFor + " was not found, " + lowerNumber + " at index " + primeList.indexOf(lowerNumber) + " and " + higherNumber + " at index " + primeList.indexOf(higherNumber) + " were equally close");
            }
            else {
                System.out.println(intToSearchFor + " was not found, " + number + " was the closest at index " + primeList.indexOf(number));
            }
        }
    }

    // closestLowerIndex
    // Söker efter värdet som är närmast input som är lägre än input tills det att den når -1
    // (alltså det finns något lägre än input i listen)
    private static int closestLowerIndex(int number){
        while (number != -1) {
            if(primeList.contains(number)) {
                return number;
            }
            number--;
        }
        return -1;
    }

    // closestHigherIndex
    // När den blir anropad får den en int som den ska hittat det närmaste värdet i listan.
    // Anropar findHighestValue och för att hitta det största värdet som ett stopp i loopen.
    // och sedan söker efter värdet i listan som är nämrast inputen som är större än inputen tills det att den når den int den fick av findHighestValue.
    // (alltså om det inte finns något större än input)
    private static int closestHigherIndex(int number){
        int max = findHighestValue();

        while (number <= max) {
            if(primeList.contains(number)) {
                return number;
            }
            number++;
        }
        return -1;
    }

    // checkClosestIndex
    // Jämför det lägre och det högre talen och skickar tillbaka index på det talet som är närmast input
    // Math.abs används för att få negativa tal
    // Om tal är 0 eller mindre så får det värdet av den andra +1 (alltså den är längre ifrån än den andra)
    // Tal kan inte vara 0 eller lägre för då hade sökningen hittat den alt så finns det inte en högre/lägre
    private static int checkClosestIndex(int low, int high, int intSearchedFor){
        int lower = intSearchedFor-Math.abs(low);
        int higher = high-Math.abs(intSearchedFor);
        if(lower <= 0) {
            lower = higher+1;
        }
        if(higher <= 0){
            higher = lower+1;
        }
        if (lower < higher) {
            return low;
        }
        else if (lower == higher) {
            return 0;
        }
        else {
            return high;
        }
    }

    // findHighestValue
    // Kollar efter det största värdet i listan
    private static int findHighestValue(){
        int max = 0;

        for (int i : primeList) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    // addMultiplePrimes
    // Om Listan är tom ska den börja leta efter primes från talet 0.
    // Först så anropar metoden findHighestValue och och sedan kollar nästa prime efter den.
    // Efter den hittat det så anropar den på checkIfPrime tills den har lika många primes som input ville.
    private static void addMultiplePrimes(){
        // g)
        String input;
        int amount;
        int highest;

        System.out.print("How many primes do you want to add? ");
        while (!(input = scan.nextLine()).matches("[0-9]+")) {
            System.out.print("Not an int, Try again: ");
        }
        amount = Integer.parseInt(input);

        for (int i = 0; i < amount;) {
            isPrime = false;

            if(primeList.size() == 0){
                highest = 0;
            } else {
                highest = findHighestValue();
            }

            while (!isPrime) {
                highest = highest + 1;
                checkIfPrime(highest);
            }

            checkIfNumberExists(highest);
            if(isAdded){
             i++;
            }
        }
        System.out.println(primeList);
    }
}