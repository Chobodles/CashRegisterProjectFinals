import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

class CashRegister {
    static Scanner sc  = new Scanner(System.in);
    static LocalDateTime dateTime = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy - HH:mm:ss");
    static String formattedDateTime = dateTime.format(formatter);
    static String currentUser;
    static String isDiscounted;
    static double totalAmount;
    static double finalPaymentAmount;
    static double finalChange;

    static ArrayList <String> order = new ArrayList<>();
    static ArrayList <Double> price = new ArrayList<>();
    static ArrayList <Integer> quantity = new ArrayList<>();

    public static boolean regexValidationUsername (String username){
        Pattern regexPattern1 = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
        Matcher matcher1 = regexPattern1.matcher(username);
        return matcher1.find();
    }

    public static boolean regexValidationPassword(String password){
        Pattern regexPattern2 = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[a-zA-Z0-9]{8,20}$");
        Matcher matcher2 = regexPattern2.matcher(password);
        return matcher2.find();
    }

    public static void login(){
        Scanner sc = new Scanner(System.in);
        ArrayList <String> createdUsernames = new ArrayList<>();
        ArrayList <String> createdPasswords = new ArrayList<>();

        boolean isSigningIn = true;
        while (isSigningIn){
            System.out.println();
            System.out.print("Hello Create an account or sign in?(1/2): ");
            String userInputAccount = sc.nextLine();
            if (userInputAccount.equals("1")){
                while(true){
                    String inputUsername;
                    String inputPassword;

                    System.out.print("Enter a Username: ");
                    inputUsername = sc.nextLine();
                    System.out.print("Enter a Password: ");
                    inputPassword = sc.nextLine();

                    boolean usernameAccepted = regexValidationUsername(inputUsername);
                    boolean passwordAccepted = regexValidationPassword(inputPassword);

                    if(usernameAccepted&&passwordAccepted) {
                        System.out.println("Account Created. Please sign in to proceed.");
                        createdUsernames.add(inputUsername);
                        createdPasswords.add(inputPassword);
                        break;
                    }
                    else if (usernameAccepted)
                        System.out.println("Password must contain at least one uppercase letter, one number, and be 8-20 characters long.");
                    else if (passwordAccepted)
                        System.out.println("Username must be alphanumeric and 5–15 characters long.");
                    else
                        System.out.println("Username must be alphanumeric and 5–15 characters long.\nPassword must contain at least one uppercase letter, one number, and be 8-20 characters long.");
                }
            }

            else if (userInputAccount.equals("2")) {
                if (createdUsernames.isEmpty())
                    System.out.println("No accounts found. Please Create an account to continue.");

                else {
                    while (true) {
                        String signInUsername;
                        String signInPassword;
                        boolean usernameValid = false;
                        boolean passwordValid = false;

                        System.out.print("Enter Username: ");
                        signInUsername = sc.nextLine();
                        System.out.print("Enter Password: ");
                        signInPassword = sc.nextLine();

                        for (int i = 0; i < createdUsernames.size(); i++) {
                            if (createdUsernames.get(i).equals(signInUsername) && createdPasswords.get(i).equals(signInPassword)) {
                                usernameValid = true;
                                passwordValid = true;
                                break;
                            } else if (createdUsernames.get(i).equals(signInUsername))
                                usernameValid = true;
                            else if (createdPasswords.get(i).equals(signInPassword))
                                passwordValid = true;
                        }

                        if (usernameValid && passwordValid) {
                            System.out.println("proceeding to main menu");
                            isSigningIn = false;
                            currentUser = signInUsername;
                            mainMenu();
                            break;
                        } else if (usernameValid)
                            System.out.println("Account Username or Password is incorrect");
                        else if (passwordValid)
                            System.out.println("Account Username or Password is incorrect");
                        else
                            System.out.println("Account with username and password cannot be found!");
                    }
                }


            } else if (userInputAccount.equals("3")) {                 // remember to remove this
                mainMenu();
            }

            else
                System.out.println("invalid Input please try again");
        }
    }


    public static void main(String[] args) {
        System.out.println("Welcome to Mang Inasal");
        login();
    }
    public static String[] getMenuItems(){
        return new String[]{
                "Paa Large (PM1) - Solo",  "Paa Large (PM1) - Unlimited Rice",  "Pecho Large (PM2) - Solo",
                "Pecho Large (PM2) - Unlimited Rice",  "1 Stick Pork BBQ - Solo",  "1 Stick Pork BBQ - With Drink",
                "2 Sticks Pork BBQ - Solo",  "2 Sticks Pork BBQ - With Drink",  "Pork Sisig - Solo",
                "Bangus Sisig - Solo",  "Grilled Liempo - Solo",  "Palabok - Solo",
                "Palabok - With Drink",  "Empanada - Solo",  "Empanada - With Drink",
                "Extra Creamy Halo-Halo",  "Crema de Leche Halo-Halo",  "Iced Red Gulaman",
                "Iced Tea",  "Coke",  "Sprite",
                "Plain Rice",  "Java Rice",  "Soup",
                "Chicken Oil",  "Toyomansi",  "Peanut Sauce",
                "Spiced Vinegar",  "Mushroom Gravy"
        };

    }
    public static double[] getMenuPrices(){
        return new double[]{
                129, 164, 142,
                170, 53,  71,
                89, 104, 111,
                134, 174, 84,
                106, 62,  84,
                77, 77,  44,
                44, 44, 44,
                28, 39, 11,
                7,  7,  8,
                8,  11
        };

    }

    public static void displayMenuItems(){  //Display menu items
        String[] menuItems = getMenuItems();
        double[] menuPrices = getMenuPrices();

        System.out.println("Mang Inasal Menu:");
        for (int i = 0; i < menuItems.length; i++)
            System.out.printf("%-38s %7.2f%n", menuItems[i], menuPrices[i]);
    }

    public static void displayCurrentOrders(){
        System.out.println("*********************************Current Orders********************************* ");
        System.out.println("Order:                                   Amount:        Quantity:      Total:");

        for (int i = 0; i < price.size();i++){
            System.out.printf("%-40s %-14.0f %-14d %-14.2f\n",order.get(i) , price.get(i), quantity.get(i),(price.get(i)*quantity.get(i)));
        }
        System.out.println();
    }

    public static void addOrders(){
        sc.nextLine();
        String[] menuItems = getMenuItems();
        double[] menuPrices = getMenuPrices();
        String choice;

        do {
            System.out.print("Enter Your Order: ");
            String input = sc.nextLine();
            boolean itemFound = false;

            for (int i = 0; i < menuItems.length; i++) {
                if (menuItems[i].equalsIgnoreCase(input)) {
                    itemFound = true;
                    System.out.printf("%-38s %7.2f\n", menuItems[i], menuPrices[i]);
                    int inputQty = 0;
                    boolean validInput = false;

                    while (!validInput) {
                        System.out.print("Enter Quantity: ");
                        try {
                            inputQty = sc.nextInt();
                            sc.nextLine(); // consume newline
                            if (inputQty <= 0) {
                                System.out.println("Please enter a positive number.");
                            } else {
                                validInput = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            sc.nextLine(); // consume the invalid input
                        }
                    }

                    boolean alreadyOrdered = false;
                    for (int j = 0; j < order.size(); j++) {
                        if (order.get(j).equalsIgnoreCase(input)) {
                            quantity.set(j, quantity.get(j) + inputQty);
                            alreadyOrdered = true;
                            break;
                        }
                    }

                    if (!alreadyOrdered) {
                        order.add(menuItems[i]);
                        price.add(menuPrices[i]);
                        quantity.add(inputQty);
                    }

                    break; // item matched
                }
            }

            if (!itemFound) {
                System.out.println("Please provide proper input\n");
            }

            displayCurrentOrders();

            do {

                System.out.print("\nDo you have any additional orders? (Y/N): ");
                choice = sc.nextLine().toUpperCase();

                if (choice.equals("Y") || choice.equals("N")) {
                    break;
                } else {
                    System.out.println("Please provide proper input");
                }
            } while (true);

        } while (choice.equals("Y"));
    }


    public static void removeOrders() {
        if (order.isEmpty()){
            System.out.println("No orders to remove");
            mainMenu();
        }
        displayCurrentOrders();

        sc.nextLine();

        try {
            System.out.print("Enter order to remove: ");
            String orderChange = sc.nextLine();

            int removeOrder = order.indexOf(orderChange);

            order.remove(removeOrder);
            price.remove(removeOrder);
            quantity.remove(removeOrder);
            System.out.println("Order has been removed.");
            displayCurrentOrders();
        } catch (Exception e) {
            System.out.println("Order does not exist");
        }
    }


    public static void replaceOrders() {
        if (order.isEmpty()){
            System.out.println("No orders to replace");
            mainMenu();
        }
        displayCurrentOrders();

        String[] menuItems = getMenuItems();
        double[] menuPrices = getMenuPrices();
        sc.nextLine();
        boolean properOrder = false;

        System.out.print("Enter order to change: ");
        String replaceOrder = sc.nextLine();
        int replaceOrderIndex = order.indexOf(replaceOrder);

        if (replaceOrderIndex == -1) {
            System.out.println("That order does not exist.\n");
        } else {
            System.out.print("Enter new order: ");
            String input = sc.nextLine();

            for (int i = 0; i < menuItems.length; i++) {
                if (menuItems[i].equalsIgnoreCase(input)) {
                    System.out.printf("%-39s %10.2f\n", menuItems[i], menuPrices[i]);

                    order.set(replaceOrderIndex, menuItems[i]);
                    price.set(replaceOrderIndex, menuPrices[i]);

                    int quantityOfOrder = 0;
                    boolean validQty = false;

                    while (!validQty) {
                        System.out.print("Enter Quantity: ");
                        try {
                            quantityOfOrder = sc.nextInt();
                            sc.nextLine(); // consume newline

                            if (quantityOfOrder <= 0) {
                                System.out.println("Please enter a number greater than 0.");
                            } else {
                                validQty = true;
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            sc.nextLine(); // clear invalid input
                        }
                    }

                    quantity.set(replaceOrderIndex, quantityOfOrder);
                    System.out.println("Order has been replaced.");
                    displayCurrentOrders();
                    properOrder = true;
                    break;
                }
            }

            if (!properOrder) {
                System.out.println("Please provide a valid menu item.\n");
            }
        }
    }

    public static void payment(){
        if (order.isEmpty()){
            System.out.println("No orders to be paid");
            mainMenu();
        }
        sc.nextLine();
        System.out.println();                                               //print all orders and total
        System.out.println("Proceeding to payment...\n");
        displayCurrentOrders();

        double total = 0;
        for (int i = 0; i < price.size();i++){
            total+=(price.get(i)*quantity.get(i));
        }

        System.out.printf("Total: %.2f\n", total);
        double discountedTotal = total;
        String discountChoice;

        do{                                            //Discount
            System.out.println();
            System.out.println("Discount Applicable: ");
            System.out.println("Senior Citizen");
            System.out.println("PWD");
            System.out.print("Eligible for discount(Y/N): ");
            discountChoice = sc.nextLine().toUpperCase();
            if (discountChoice.equals("Y")) {
                discountedTotal = total- (total*.2);
                break;
            }
            else if(discountChoice.equals("N")){
                break;
            }
            else{
                System.out.println("Please provide proper input");
            }

        }while(true);

        System.out.println();
        int customerPayment;
        double change;
        while(true){                                      //accept payment and display change
            try {
                System.out.printf("Total Amount: %.2f\n", discountedTotal);
                System.out.print("Enter Payment Amount: ");
                customerPayment = sc.nextInt();
                sc.nextLine();

                if(customerPayment>=discountedTotal){
                    change = customerPayment - discountedTotal;
                    System.out.printf("Change: %.2f\n",change);
                    break;
                }
                else{
                    System.out.println();
                    System.out.println("Insufficient Amount!");
                    System.out.println("Please Provide Appropriate Payment Amount");
                    System.out.println();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            }

        }

        isDiscounted = discountChoice.equalsIgnoreCase("Y")? "Yes": "No";
        totalAmount = discountedTotal;
        finalPaymentAmount = customerPayment;
        finalChange = change;
        System.out.println("Thank you for ordering!");
        System.out.println();
        transactionLogger();

        String orderRepeat;

        do{                                         //repeat order
            System.out.print("Would you like to take another order?(Y/N): ");
            orderRepeat = sc.nextLine().toUpperCase();

            if (orderRepeat.equals("Y")){
                order.clear();
                price.clear();
                quantity.clear();
                mainMenu();
                break;

            } else if(orderRepeat.equals("N")){
                System.out.println("System Ended.");
                System.out.println("\n");
                System.exit(0);
                break;

            } else {
                System.out.println("Please provide proper input");
            }

        } while (true);
        sc.close();

    }
    public static void transactionLogger(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {

            writer.write("********************************************************************************");
            writer.newLine();
            writer.write("Date and Time: " + formattedDateTime);
            writer.newLine();
            writer.write("Username: " + currentUser);
            writer.newLine();
            writer.write("Order:                                   Amount:        Quantity:      Total:");
            writer.newLine();
            for (int i = 0; i < price.size();i++){
                String formattedLine = String.format("%-40s %-14.0f %-14d %-14.2f%n", order.get(i) , price.get(i), quantity.get(i),(price.get(i)*quantity.get(i)));
                writer.write(formattedLine);
            }
            writer.write("Discounted: " + isDiscounted);
            writer.newLine();
            writer.write("Total: " + String.format("%.2f", totalAmount));
            writer.newLine();
            writer.write("Payment Amount: " + String.format("%.2f", finalPaymentAmount));
            writer.newLine();
            writer.write("Change: " + String.format("%.2f", finalChange));
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Failed to update the file.");

        }
    }

    public static void mainMenu(){
        do {
            displayMenuItems();
            System.out.println();
            System.out.print("Type (\"1\" -> Add | \"2\" -> Replace | \"3\" -> Remove | \"4\" -> Payment | \"5\" -> Stop): ");
            String type = sc.next();

            switch (type){
                case "1" -> addOrders();
                case "2" -> replaceOrders();
                case "3" -> removeOrders();
                case "4" -> payment();
                case "5" -> System.exit(0);

                default -> System.out.println("Invalid Input\nPlease Try Again");
            }

        } while(true);

    }
}