package com.example.foodcentersystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {
    private static final Scanner input = new Scanner(System.in);

    // ArrayList to hold Queue objects
    static ArrayList<FoodQueue> foodQueues = new ArrayList<>(); // [FoodQueue_1, FoodQueue_2, FoodQueue_3]
    static FoodQueue waitingQueue = new FoodQueue(4, 50, 650); // waiting queue
    static int burgerStock = 50;  // Standard burgers stock && maintaining to display remaining burger stock
//    static int burgersRequired; // Getting customer's requested burger quantity who removed as a served customer
    static Date sessionStartTime; // Additional data to save in text file as session start time
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    // Formatting the date using SimpleDateFormat method


    public static void main(String[] args) {
        // Creating objects from Queue class
        // These every Queue object contains a unique ArrayList to store the Customer objects belongs to the queueNumber

        // Adding Queue objects to a new ArrayList called queues
        foodQueues.add(new FoodQueue(1, 2, 650)); // 650 in LKR
        foodQueues.add(new FoodQueue(2, 3, 650)); // 650 in LKR
        foodQueues.add(new FoodQueue(3, 5, 650)); // 650 in LKR

        // Main loop starts
        while (true) {
            fillByWaitingList();
            sessionStartTime = new Date();
            System.out.println("\n====== Foodies Fave Food Center ======");
            System.out.println();
            System.out.println("       100 or VFQ: View all Queues");
            System.out.println("       101 or VEQ: View all Empty Queues");
            System.out.println("       102 or ACQ: Add customer to a Queue");
            System.out.println("       103 or RCQ: Remove a customer from a Queue");
            System.out.println("       104 or PCQ: Remove a served customer");
            System.out.println("       105 or VCS: View Customers Sorted in alphabetical order");
            System.out.println("       106 or SPD: Store Program Data into file");
            System.out.println("       107 or LPD: Load Program Data from file");
            System.out.println("       108 or STK: View Remaining burgers Stock");
            System.out.println("       109 or AFS: Add burgers to Stock");
            System.out.println("       110 or IFQ: View income for each queue");
            System.out.println("       111 or CWQ: Clear waiting queue");
            System.out.println("       112 or GUI: Launch the GUI");
            System.out.println("       999 or EXT: Exit the Program");

            System.out.print("       Enter the option ➡️ ");
            String option = input.next().toUpperCase(); // Taking user input as String value & converts to UpperCase
            System.out.println();

            switch (option) {
                case "100", "VFQ" -> displayQueues();
                case "101", "VEQ" -> viewEmptyQueues();
                case "102", "ACQ" -> addCustomer();
                case "103", "RCQ" -> {
                    String output = removeCustomer();
                    System.out.println(output);
                }
                case "104", "PCQ" -> removeServedCustomer();
                case "105", "VCS" -> viewCustomersSorted();
                case "106", "SPD" -> storeProgramData();
                case "107", "LPD" -> loadProgramData();
                case "108", "STK" -> viewStock();
                case "109", "AFS" -> addBurgersToStock();
                case "110", "IFQ" -> viewIncome();
                case "111", "CWQ" -> clearWaitingQueue();
                case "112", "GUI" -> {
                        System.out.println("Launching Application");
                        myLaunch(Main.class);
                }
                case "999", "EXT" -> {
                    System.out.println("Terminating the program...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void displayQueues() {
        System.out.println("******************");
        System.out.println("*    CASHIERS    *");
        System.out.println("******************");

        FoodQueue foodQueue1 = foodQueues.get(0);
        FoodQueue foodQueue2 = foodQueues.get(1);
        FoodQueue foodQueue3 = foodQueues.get(2);

        ArrayList<Customer> queue1Customers = foodQueue1.getQueueWithCustomers();
        ArrayList<Customer> queue2Customers = foodQueue2.getQueueWithCustomers();
        ArrayList<Customer> queue3Customers = foodQueue3.getQueueWithCustomers();

        String[] firstQueueCustomerNames = new String[5];
        String[] secondQueueCustomerNames = new String[5];
        String[] thirdQueueCustomersNames = new String[5];


        for (int i = 0; i < queue1Customers.size(); i++) {
            firstQueueCustomerNames[i] = queue1Customers.get(i).getCustomerFullName();
        }
        for (int i = 0; i < queue2Customers.size(); i++) {
            secondQueueCustomerNames[i] = queue2Customers.get(i).getCustomerFullName();
        }
        for (int i = 0; i < queue3Customers.size(); i++) {
            thirdQueueCustomersNames[i] = queue3Customers.get(i).getCustomerFullName();
        }


        for (int i = 0; i < 5; i++) {
            int maxQueue = 2;
            if (i < maxQueue) {
                if (firstQueueCustomerNames[i] != null) {
                    firstQueueCustomerNames[i] = "O";
                } else {
                    firstQueueCustomerNames[i] = "X";
                }
            } else {
                firstQueueCustomerNames[i] = " ";
            }
        }
        for (int j = 0; j < 5; j++) {
            int maxQueue = 3;
            if (j < maxQueue) {
                if (secondQueueCustomerNames[j] != null) {
                    secondQueueCustomerNames[j] = "O";
                } else {
                    secondQueueCustomerNames[j] = "X";
                }
            } else {
                secondQueueCustomerNames[j] = " ";
            }
        }
        for (int k = 0; k < thirdQueueCustomersNames.length; k++) {
            if (thirdQueueCustomersNames[k] != null) {
                thirdQueueCustomersNames[k] = "O";
            } else {
                thirdQueueCustomersNames[k] = "X";
            }
        }

        for (int l = 0; l < 5; l++) {
            System.out.println("    " + firstQueueCustomerNames[l] + "   " + secondQueueCustomerNames[l] + "   " + thirdQueueCustomersNames[l]);
        }

        System.out.println("\n'O' - Occupied  |  'X' - Unoccupied");
    }

    private static void viewEmptyQueues() {
        FoodQueue foodQueue1 = foodQueues.get(0);
        FoodQueue foodQueue2 = foodQueues.get(1);
        FoodQueue foodQueue3 = foodQueues.get(2);

        boolean firstQueueNotFull = !isQueueFull(foodQueue1);
        boolean secondQueueNotFull = !isQueueFull(foodQueue2);
        boolean thirdQueueNotFull = !isQueueFull(foodQueue3);

        System.out.println("*** Cashier queues with empty slots ***");

        if (firstQueueNotFull) {
            System.out.println("Cashier Queue 1: " + foodQueue1.toString(foodQueue1.getQueueWithCustomers()));
        } else {
            System.out.println("Cashier Queue 1: [ No empty slots available ]");
        }
        if (secondQueueNotFull) {
            System.out.println("Cashier Queue 2: " + foodQueue2.toString(foodQueue2.getQueueWithCustomers()));
        } else {
            System.out.println("Cashier Queue 2: [ No empty slots available ]");
        }
        if (thirdQueueNotFull) {
            System.out.println("Cashier Queue 3: " + foodQueue3.toString(foodQueue3.getQueueWithCustomers()));
        } else {
            System.out.println("Cashier Queue 3: [ No empty slots available ]");
        }
    }

    private static void addCustomer() {
        String returnBackQueue;
        String customerFirstName;
        String customerLastName;
        int burgerCountRequired;

        System.out.println("Enter the first name of the customer: ");
        customerFirstName = input.next();
        System.out.println("Enter the last name of the customer: ");
        customerLastName = input.next();
        try {
            System.out.println("Number of burgers required: ");
            String numberOfBurgers = input.next();
            burgerCountRequired = Integer.parseInt(numberOfBurgers);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Customer addition failed.");
            System.out.println("Invalid input!, check & enter the quantity again.");
            return;
        }

        // Creates a customer object using given inputs and store them inside Queue object within the ArrayList
        Customer customer = new Customer(customerFirstName, customerLastName, burgerCountRequired);

        returnBackQueue = selectQueueToAdd();
        if (!returnBackQueue.equals("1") && !returnBackQueue.equals("2") &&
                !returnBackQueue.equals("3") && !returnBackQueue.equals("4")) {
            return;
        }

        switch (returnBackQueue) {
            case "1" -> {
                // Appending customer to the foodQueue 1
                FoodQueue firstFoodQueue = foodQueues.get(0);
                firstFoodQueue.addCustomerToQueue(customer);
                System.out.println("Customer " + customer.getCustomerFullName() +
                        " added to the cashier queue " + returnBackQueue + " successfully.");
            }
            case "2" -> {
                // Appending customer to the foodQueue 2
                FoodQueue secondFoodQueue = foodQueues.get(1);
                secondFoodQueue.addCustomerToQueue(customer);
                System.out.println("Customer " + customer.getCustomerFullName() +
                        " added to the cashier queue " + returnBackQueue + " successfully.");
            }
            case "3" -> {
                // Appending customer to the foodQueue 3
                FoodQueue thirdFoodQueue = foodQueues.get(2);
                thirdFoodQueue.addCustomerToQueue(customer);
                System.out.println("Customer " + customer.getCustomerFullName() +
                        " added to the cashier queue " + returnBackQueue + " successfully.");
            }
            default -> {
                // Adding customers to the waiting queue
                waitingQueue.addCustomerToQueue(customer);
                System.out.println("Customer " + customer.getCustomerFullName() +
                        " added to the waiting list.");
            }
        }
    }

    private static String removeCustomer() {
        int position;
        int indexOfCustomer;
        int queueNumber;
        boolean removeCustomerStatus;

        try {
            System.out.println("Enter the queue number of the customer to be removed(1, 2, 3): ");
            String queue = input.next();
            queueNumber = Integer.parseInt(queue);
        } catch (Exception e) {
            return "Enter the inputs as mentioned! (Integers!)";
        }

        // checks whether the selected queue is empty if not only function continues
        removeCustomerStatus = isQueueEmpty(queueNumber);
        if (removeCustomerStatus) {
            return "There are no customers in queue " + queueNumber;
        }

        switch (queueNumber) {
            case 1 -> {
                try {
                    System.out.println("Enter the position of the customer(1, 2): ");
                    position = input.nextInt();
                    if (position == 1 || position == 2) { // 1 <= position <= 2
                        indexOfCustomer = position - 1;
                    } else {
                        return "Invalid position selected!";
                    }
                } catch (Exception e) {
                    return "Enter the inputs as mentioned.(Integers!)";
                }

                FoodQueue foodQueue1 = foodQueues.get(0);
                if (indexOfCustomer < foodQueue1.getQueueWithCustomers().size()) {
                    // removing the customers name at given index (indexOfCustomer)
                    String removedCustomerName = foodQueue1.removeCustomerFromQueue(indexOfCustomer);

                    return "Customer " + removedCustomerName + " removed from queue " + queueNumber + " successfully.";
                }
            }
            case 2 -> {
                try {
                    System.out.println("Enter the position of the customer(1, 2, 3): ");
                    position = input.nextInt();
                    if (position == 1 || position == 2 || position == 3) { // 1 <= position <= 3
                        indexOfCustomer = position - 1;
                    } else {
                        return "Invalid position selected!";
                    }
                } catch (Exception e) {
                    return "Enter the inputs as mentioned.(Integers!)";
                }

                FoodQueue foodQueue2 = foodQueues.get(1);
                if (indexOfCustomer < foodQueue2.getQueueWithCustomers().size()) {
                    // removing the customers name at given index (indexOfCustomer)
                    String removedCustomerName = foodQueue2.removeCustomerFromQueue(indexOfCustomer);

                    return "Customer " + removedCustomerName + " removed from queue " + queueNumber + " successfully.";
                }
            }
            case 3 -> {
                try {
                    System.out.println("Enter the position of the customer(1, 2, 3, 4, 5): ");
                    position = input.nextInt();
                    if (position == 1 || position == 2 || position == 3 || position == 4 || position == 5) { // 1 <= position <= 5
                        indexOfCustomer = position - 1;
                    } else {
                        return "Invalid position selected!";
                    }
                } catch (Exception e) {
                    return "Enter the inputs as mentioned.(Integers!)";
                }

                FoodQueue foodQueue3 = foodQueues.get(2);
                if (indexOfCustomer < foodQueue3.getQueueWithCustomers().size()) {
                    // removing the customers name at given index (indexOfCustomer)
                    String removedCustomerName = foodQueue3.removeCustomerFromQueue(indexOfCustomer);

                    return "Customer " + removedCustomerName + " removed from queue " + queueNumber + " successfully.";
                }
            }
            case 4 -> {
                try {
                    System.out.println("Waiting Queue: " + waitingQueue.toString(waitingQueue.getQueueWithCustomers()));
                    System.out.println("Enter the position of the customer in waiting queue: ");
                    position = input.nextInt();

                    if (0 < position && position <= waitingQueue.getMaxQueueLength()) {
                        indexOfCustomer = position - 1 ;
                    } else {
                        return "Invalid position selected!";
                    }
                } catch (Exception e) {
                    return "Enter the inputs as mentioned.(Integers!)";
                }

                if (indexOfCustomer < waitingQueue.getQueueWithCustomers().size()) {
                    // removing the customers name at given index (indexOfCustomer) from waiting queue
                    String removedCustomerName = waitingQueue.removeCustomerFromQueue(indexOfCustomer);

                    return "Customer " + removedCustomerName + " removed from waiting queue successfully.";
                }
            }
            default -> {
                return "Invalid queue number!\nFailed attempt on customer removal.";
            }
        }
        return "There's no customer at the selected position!\nFailed attempt on customer removal.";
    }

    private static void removeServedCustomer() {
        int indexOfCustomer = 0; // Current serving customer's position is always 1 so the index is always 0
        String queue; // Getting user input as a string and converts it into Integer and stored in queueNumber
        int queueNumber;
        boolean isQueueEmpty;
        String removedCustomerName;

        System.out.println("Enter the queue number(1, 2, 3): ");
        queue = input.next();

        if (!queue.equals("1") && !queue.equals("2") && !queue.equals("3")) {
            System.out.println("Invalid queue number!" +
                    "\nFailed attempt on customer removal.");
            return;
        }

        queueNumber = Integer.parseInt(queue);

        // Passing the queueNumber to isQueueEmpty() function to do the validation part whether selected queue is empty or not.
        isQueueEmpty = isQueueEmpty(queueNumber); // This will return boolean and used it to change the queue position status
        // and remove the customer object from the queue
        if (isQueueEmpty) {
            System.out.println("There are no customers in queue " + queueNumber);
            return;
        }

        if (queueNumber == 1) {
            if (burgerStock > 0) {
                Customer customerToBeRemoved = foodQueues.get(0).getQueueWithCustomers().get(indexOfCustomer);
                foodQueues.get(0).addRemovedCustomers(customerToBeRemoved);
                removedCustomerName = foodQueues.get(0).removeCustomerFromQueue(indexOfCustomer);

                int burgersRequired = customerToBeRemoved.getBurgerCountRequired(); // customer's required burger count
                burgerStock -= burgersRequired; // Burger stock decrementing by requested burger count of a customer

            } else {
                System.out.println("Oops!!! Burgers now Out-Of-Stock" +
                        "\nFailed attempt on customer removal.");
                return;
            }
        } else if (queueNumber == 2) {
            if (burgerStock > 0) {
                Customer customerToBeRemoved = foodQueues.get(1).getQueueWithCustomers().get(indexOfCustomer);
                foodQueues.get(1).addRemovedCustomers(customerToBeRemoved);
                removedCustomerName = foodQueues.get(1).removeCustomerFromQueue(indexOfCustomer);

                int burgersRequired = customerToBeRemoved.getBurgerCountRequired(); // customer's required burger count
                burgerStock -= burgersRequired; // Burger stock decrementing by requested burger count of a customer

            } else {
                System.out.println("Oops!!! Burgers now Out-Of-Stock" +
                        "\nFailed attempt on customer removal.");
                return;
            }
        } else {
            if (burgerStock > 0) {
                Customer customerToBeRemoved = foodQueues.get(2).getQueueWithCustomers().get(indexOfCustomer);
                foodQueues.get(2).addRemovedCustomers(customerToBeRemoved);
                removedCustomerName = foodQueues.get(2).removeCustomerFromQueue(indexOfCustomer);

                int burgersRequired = customerToBeRemoved.getBurgerCountRequired(); // customer's required burger count
                burgerStock -= burgersRequired; // Burger stock decrementing by requested burger count of a customer

            } else {
                System.out.println("Oops!!! Burgers now Out-Of-Stock" +
                        "\nFailed attempt on customer removal.");
                return;
            }
        }
        System.out.println("Customer " + removedCustomerName +
                " removed from the queue " + queueNumber + " after billing.");
    }

    private static void viewCustomersSorted() {
        System.out.println("===== All customers in each queue(Sorted by Ascending order) =====");
        FoodQueue foodQueue1 = foodQueues.get(0);
        FoodQueue foodQueue2 = foodQueues.get(1);
        FoodQueue foodQueue3 = foodQueues.get(2);

        foodQueue1.sortCustomers();
        foodQueue2.sortCustomers();
        foodQueue3.sortCustomers();
    }

    private static void storeProgramData() {
        Date sessionEndTime = new Date();
        FoodQueue queue1 = foodQueues.get(0);
        FoodQueue queue2 = foodQueues.get(1);
        FoodQueue queue3 = foodQueues.get(2);

        try {
            FileWriter fileWriter = new FileWriter("sessionData.txt");


            // Store last view of queues
            fileWriter.write("====== Foodies Fave Food Center ======");
            fileWriter.write("\n \n"); // Consuming as a linebreak in the text file


            // Store session start time and end time
            fileWriter.write("\nSession   ---->   Start: " + formatter.format(sessionStartTime) + "\n");
            fileWriter.write("                    End: " + formatter.format(sessionEndTime) + "\n");


            // Store queue status with customer details
            fileWriter.write("\n====== Cashier Queue status with customer details (With actual customer position) ======");
            fileWriter.write("\n");
            fileWriter.write("\nCashier 1:\n");
            if (!isQueueEmpty(1)) {
                for (Customer customer : queue1.getQueueWithCustomers()) {
                    int customerIndex = queue1.getQueueWithCustomers().indexOf(customer);

                    fileWriter.write("\n         Customer " + (customerIndex + 1) + ": " + customer);
                }
            } else {
                fileWriter.write("Queue is empty at this stage");
                fileWriter.write("\n");
            }

            fileWriter.write("\nCashier 2:\n");
            if (!isQueueEmpty(2)) {
                for (Customer customer : queue2.getQueueWithCustomers()) {
                    int customerIndex = queue2.getQueueWithCustomers().indexOf(customer);

                    fileWriter.write("\n         Customer " + (customerIndex + 1) + ": " + customer);
                }
            } else {
                fileWriter.write("Queue is empty at this stage");
                fileWriter.write("\n");
            }

            fileWriter.write("\nCashier 3:\n");
            if (!isQueueEmpty(3)) {
                for (Customer customer : queue3.getQueueWithCustomers()) {
                    int customerIndex = queue3.getQueueWithCustomers().indexOf(customer);

                    fileWriter.write("\n         Customer " + (customerIndex + 1) + ": " + customer);
                }
            } else {
                fileWriter.write("Queue is empty at this stage");
            }
            fileWriter.write("\n");


            // Store number of served customers during the session
            int totalCustomersServed = queue1.getRemovedCustomers().size() +
                    queue2.getRemovedCustomers().size() +
                    queue3.getRemovedCustomers().size();

            fileWriter.write("\nTotal number of served customers: " + totalCustomersServed);

            // Store number of served burgers during the session
            int totalBurgersServed = queue1.getTotalBurgersServed() +
                    queue2.getTotalBurgersServed() +
                    queue3.getTotalBurgersServed();

            fileWriter.write("\nNumber of burgers served: " + totalBurgersServed);
            fileWriter.write("\nPrice of a burger: " + queue1.getBurgerPrice() + " LKR");
            fileWriter.write("\n");


            // Store incomes for each queue
            queue1.setQueueIncome();
            queue2.setQueueIncome();
            queue3.setQueueIncome();

            fileWriter.write("\n=== Expected Queue Income ===");
            fileWriter.write("\n");
            fileWriter.write("\nQueue 1: " + queue1.getQueueIncome() + " LKR");
            fileWriter.write("\nQueue 2: " + queue2.getQueueIncome() + " LKR");
            fileWriter.write("\nQueue 3: " + queue3.getQueueIncome() + " LKR");
            fileWriter.write("\n");


            // Store burger stock
            fileWriter.write("\nBurger Stock: " + burgerStock);


            fileWriter.close();
            System.out.println("\nProgram data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }

    private static void loadProgramData() {
        try {
            File file = new File("sessionData.txt");
            Scanner input = new Scanner(file);

            // Read and print the contents of the file
            while (input.hasNextLine()) {
                String data = input.nextLine();
                System.out.println(data);
            }

            input.close();
            System.out.println("Program data loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error occurred while loading session data: " + e.getMessage());
        }
    }

    private static void viewStock() {
        System.out.println("Stock remaining: " + burgerStock);
        String addBurgers;
        if (burgerStock <= 10) {
            System.out.println("Limited Stock Available - Act Now!!!");

            System.out.print("Would you extend the stock now?(yes/no) ");
            addBurgers = input.next().toLowerCase();
            if (addBurgers.equals("yes") || addBurgers.equals("y")) {
                addBurgersToStock();
            }
        }
    }

    private static void addBurgersToStock() {
        System.out.print("Enter the count of burgers: ");
        int burgersCount = input.nextInt();
        burgerStock += burgersCount;
        System.out.println(burgersCount + " burgers added to stock.");
        viewStock();
    }

    private static void viewIncome() {
        FoodQueue foodQueue1 = foodQueues.get(0);
        FoodQueue foodQueue2 = foodQueues.get(1);
        FoodQueue foodQueue3 = foodQueues.get(2);

        System.out.println("===== Here are the incomes of each queue so far =====");
        System.out.println();

        if (!foodQueue1.getRemovedCustomers().isEmpty()) {
            foodQueue1.setQueueIncome();
            System.out.println("Queue 1 : " +
                    foodQueue1.getQueueIncome() +
                    " LKR");
        } else {
            System.out.println("Queue 1 : No income yet");
        }
        if (!foodQueue2.getRemovedCustomers().isEmpty()) {
            foodQueue2.setQueueIncome();
            System.out.println("Queue 2 : " +
                    foodQueue2.getQueueIncome() +
                    " LKR");
        } else {
            System.out.println("Queue 2 : No income yet");
        }
        if (!foodQueue3.getRemovedCustomers().isEmpty()) {
            foodQueue3.setQueueIncome();
            System.out.println("Queue 3 : " +
                    foodQueue3.getQueueIncome() +
                    " LKR");
        } else {
            System.out.println("Queue 3 : No income yet");
        }
    }

    public static void clearWaitingQueue() {
        waitingQueue.getQueueWithCustomers().clear();
        System.out.println("Waiting queue is cleared.");
    }

    private static String selectQueueToAdd() {
        FoodQueue firstQueue = foodQueues.get(0);
        FoodQueue secondQueue = foodQueues.get(1);
        FoodQueue thirdQueue = foodQueues.get(2);

        int firstQueueSize = firstQueue.getQueueWithCustomers().size();
        int secondQueueSize = secondQueue.getQueueWithCustomers().size();
        int thirdQueueSize = thirdQueue.getQueueWithCustomers().size();

        boolean logic1 = firstQueueSize <= secondQueueSize;
        boolean logic2 = secondQueueSize <= thirdQueueSize;
        boolean logic3 = logic1 && logic2;
        // logic 3 means firstQueue has the least number of customer count, then the highest priority sets to firstQueue

        if (areQueuesFull()) {
            System.out.println("Attention!!! All queues are full.");
            return "4";
        } else {
            if (isQueueFull(firstQueue)) {
                System.out.println("Cashier queue 1 is full.");
                if (isQueueFull(secondQueue)) {
                    System.out.println("Cashier queue 2 also full.");
                    // Customer adding to the queue 3.
                    return "3";
                }
            }
            if (isQueueFull(secondQueue)) {
                System.out.println("Cashier queue 2 is full.");
            }
            if (logic3) {
                if (firstQueueSize < firstQueue.getMaxQueueLength()) {
                    // Customer adding to the queue 1.
                    return "1";
                }
            }
            if (logic2) {
                if (secondQueueSize < secondQueue.getMaxQueueLength()) {
                    // Customer adding to the queue 2.
                    return "2";
                } else {
                    System.out.println("Cashier queue 2 is full.");
                    // Customer adding to the queue 3.
                    return "3";
                }
            } else {
                // Customer adding to the queue 3.
                return "3";
            }
        }
    }

    private static String selectQueue() {
        FoodQueue firstQueue = foodQueues.get(0);
        FoodQueue secondQueue = foodQueues.get(1);
        FoodQueue thirdQueue = foodQueues.get(2);

        int firstQueueSize = firstQueue.getQueueWithCustomers().size();
        int secondQueueSize = secondQueue.getQueueWithCustomers().size();
        int thirdQueueSize = thirdQueue.getQueueWithCustomers().size();

        boolean logic1 = firstQueueSize <= secondQueueSize;
        boolean logic2 = secondQueueSize <= thirdQueueSize;
        boolean logic3 = logic1 && logic2;
        // logic 3 means firstQueue has the least number of customer count, then the highest priority sets to firstQueue

        if (areQueuesFull()) {
            return "4";
        } else {
            if (isQueueFull(firstQueue)) {
                if (isQueueFull(secondQueue)) {
                    // Customer adding to the queue 3.
                    return "3";
                }
            }
            if (logic3) {
                if (firstQueueSize < firstQueue.getMaxQueueLength()) {
                    // Customer adding to the queue 1.
                    return "1";
                }
            }
            if (logic2) {
                if (secondQueueSize < secondQueue.getMaxQueueLength()) {
                    // Customer adding to the queue 2.
                    return "2";
                } else {
                    // Customer adding to the queue 3.
                    return "3";
                }
            } else {
                // Customer adding to the queue 3.
                return "3";
            }
        }
    }

    private static boolean isQueueEmpty(int queueNumber) {
        FoodQueue foodQueue1 = foodQueues.get(0);
        FoodQueue foodQueue2 = foodQueues.get(1);
        FoodQueue foodQueue3 = foodQueues.get(2);

        if (queueNumber == 1) {
            return foodQueue1.getQueueWithCustomers().isEmpty();
        } else if (queueNumber == 2) {
            return foodQueue2.getQueueWithCustomers().isEmpty();
        } else {
            return foodQueue3.getQueueWithCustomers().isEmpty();
        }
    }

    public static boolean isQueueFull(FoodQueue foodQueue) {
        int foodQueueSize = foodQueue.getQueueWithCustomers().size();
        int maxFoodQueueSize = foodQueue.getMaxQueueLength();

        return foodQueueSize == maxFoodQueueSize;
    }

    private static boolean areQueuesFull() {
        FoodQueue foodQueue1 = foodQueues.get(0);
        FoodQueue foodQueue2 = foodQueues.get(1);
        FoodQueue foodQueue3 = foodQueues.get(2);

        return isQueueFull(foodQueue1) && isQueueFull(foodQueue2) && isQueueFull(foodQueue3);
    }

    private static void fillByWaitingList() {
        FoodQueue foodQueue1 = foodQueues.get(0);
        FoodQueue foodQueue2 = foodQueues.get(1);
        FoodQueue foodQueue3 = foodQueues.get(2);

        String selectedQueue = selectQueue();

        if (!(waitingQueue.getQueueWithCustomers().isEmpty()) && !areQueuesFull()) {
            Customer customer = waitingQueue.getQueueWithCustomers().remove(0);
            switch (selectedQueue) {
                case "1" -> {
                    foodQueue1.addCustomerToQueue(customer);
                    System.out.println("Customer " + customer.getCustomerFullName() +
                            " added to the cashier queue " + selectedQueue + " successfully.");
                }
                case "2" -> {
                    foodQueue2.addCustomerToQueue(customer);
                    System.out.println("Customer " + customer.getCustomerFullName() +
                            " added to the cashier queue " + selectedQueue + " successfully.");
                }
                case "3" -> {
                    foodQueue3.addCustomerToQueue(customer);
                    System.out.println("Customer " + customer.getCustomerFullName() +
                            " added to the cashier queue " + selectedQueue + " successfully.");
                }
            }
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("FinalLogo.png")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            stage.setTitle("Foodies Fave Food Center");
            stage.getIcons().add(logo);
            stage.setScene(scene);
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static volatile boolean javaFxLaunched = false;

    public static void myLaunch(Class<? extends Application> applicationClass) {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(()->Application.launch(applicationClass)).start();
            javaFxLaunched = true;
        } else { // Next times
            Platform.runLater(()->{
                try {
                    Application application = applicationClass.newInstance();
                    Stage primaryStage = new Stage();
                    application.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
