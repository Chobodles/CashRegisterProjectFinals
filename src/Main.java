import java.util.*;
import java.util.regex.*;

class CashRegister {

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
        ArrayList <String> createdUsernames = new ArrayList<String>();
        ArrayList <String> createdPasswords = new ArrayList<String>();

        boolean isSigningIn = true;
        while (isSigningIn){
            System.out.println();
            System.out.print("Hello Create an account or sign in?(1/2): ");
            String userInputAccount = sc.nextLine();
            if (userInputAccount.equals("1")){
                while(true){
                    String inputUsername = null;
                    String inputPassword = null;

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

            else if (userInputAccount.equals("2")){
                if (createdUsernames.isEmpty())
                    System.out.println("No accounts found. Please Create an account to continue.");

                else {
                    while(true){
                        String signInUsername = null;
                        String signInPassword = null;
                        boolean usernameValid = false;
                        boolean passwordValid = false;


                        System.out.print("Enter Username: ");
                        signInUsername = sc.nextLine();
                        System.out.print("Enter Password: ");
                        signInPassword = sc.nextLine();

                        for (int i = 0; i<createdUsernames.size();i++){
                            if (createdUsernames.get(i).equals(signInUsername) && createdPasswords.get(i).equals(signInPassword)) {
                                usernameValid = true;
                                passwordValid = true;
                                break;
                            }
                            else if (createdUsernames.get(i).equals(signInUsername))
                                usernameValid = true;
                            else if (createdPasswords.get(i).equals(signInPassword))
                                passwordValid = true;
                        }

                        if(usernameValid&&passwordValid) {
                            System.out.println("proceeding to main menu");
                            isSigningIn = false;
                            mainMenu();
                            break;
                        }
                        else if (usernameValid)
                            System.out.println("Account Username or Password is incorrect");
                        else if (passwordValid)
                            System.out.println("Account Username or Password is incorrect");
                        else
                            System.out.println("Account with username and password cannot be found!");
                    }
                }
            }
            else
                System.out.println("invalid Input please try again");
        }

    }

    public static void main(String[] args) {
        System.out.println("Welcome to Mang Inasal");
        login();
    }

    public static void mainMenu(){
        Scanner sc  = new Scanner(System.in);

        String[] menuItems = {
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

        double[] menuPrices = {
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

        System.out.println("Mang Inasal Menu:");
        for (int i = 0; i <= menuItems.length-1; i++) {
            System.out.printf("%-38s %7.2f%n", menuItems[i], menuPrices[i]); //Display menu items
        }
        ArrayList <String> order = new ArrayList<String>();
        ArrayList <Double> price = new ArrayList<Double>();
        ArrayList <Integer> quantity = new ArrayList<Integer>();

        String choice = null;
        boolean properOrder = true;
        int input2 = 0;
        do {                                                        //receive orders part and prints out the current orders
            System.out.print("Enter Your Order: ");
            String input = sc.nextLine();
            for (int i = 0; i < menuItems.length; i++) {
                if (menuItems[i].equals(input)) {
                    System.out.printf("%-38s %7.2f\n", menuItems[i], menuPrices[i]); //Display ordered item with price
                    System.out.println();
                    order.add(menuItems[i]);
                    price.add(menuPrices[i]);

                    System.out.print("Enter Quantity: ");
                    input2 = sc.nextInt();
                    quantity.add(input2);
                    sc.nextLine();
                    properOrder = true;
                    break;
                } else if (i == menuItems.length-1 && !properOrder){

                    System.out.println("Please provide proper input");
                    System.out.println();
                } else{
                    properOrder = false;
                }
            }

            System.out.println();
            System.out.println("**************************Current Orders**************************");
            System.out.println("Order:                                   Amount:        Quantity:");

            for (int i = 0; i < price.size();i++){
                System.out.printf("%-40s %-14.0f %-14d\n",order.get(i) , price.get(i), quantity.get(i));
            }

            do{
                System.out.println();
                System.out.print("Do you have any additional orders?(Y/N): ");
                choice = sc.nextLine().toUpperCase();
                System.out.println();

                if (choice.equals("Y")||choice.equals("N")) {
                    break;

                } else{
                    System.out.println("Please provide proper input");
                }

            } while(true);

        } while(choice.equals("Y"));

        String choiceChange = null;
        String inputChange = null;
        String orderChange = null;

        do {                                           //Edit order replace or remove
            System.out.println("*********************************Current Orders********************************* ");
            System.out.println("Order:                                   Amount:        Quantity:      Total:");

            for (int i = 0; i < price.size();i++){
                System.out.printf("%-40s %-14.0f %-14d %-14.2f\n",order.get(i) , price.get(i), quantity.get(i),(price.get(i)*quantity.get(i)));
            }
            System.out.println();
            System.out.print("Do you have any changes?(Y/N): ");

            choiceChange = sc.nextLine().toUpperCase();
            if (choiceChange.equals("Y")){
                System.out.print("Do you want to remove or replace?: ");
                inputChange = sc.nextLine().toUpperCase();
                if (inputChange.equals("REMOVE")){
                    System.out.print("Enter order to remove: ");
                    orderChange = sc.nextLine();
                    int removeOrder = order.indexOf(orderChange);
                    order.remove(removeOrder);
                    price.remove(removeOrder);
                    quantity.remove(removeOrder);

                } else if (inputChange.equals("REPLACE")){
                    System.out.print("Enter order to change: ");
                    orderChange = sc.nextLine();
                    int replaceOrder = order.indexOf(orderChange);
                    System.out.print("Enter new order: ");
                    String input = sc.nextLine();
                    for (int i = 0; i < menuItems.length; i++) {
                        if (menuItems[i].equals(input)) {
                            System.out.printf("%-39s %10.2f\n", menuItems[i], menuPrices[i]); //Display ordered item with price
                            order.set(replaceOrder, menuItems[i]);
                            price.set(replaceOrder, menuPrices[i]);
                            System.out.print("Enter Quantity: ");
                            input2 = sc.nextInt();
                            quantity.set(replaceOrder,input2);
                            sc.nextLine();
                            properOrder = true;
                            break;
                        } else if (i == menuItems.length-1 && !properOrder){

                            System.out.println("Please provide proper input");
                            System.out.println();
                        } else{
                            properOrder = false;
                        }
                    }

                } else{
                    System.out.println("Please provide proper input");
                    System.out.println();
                }

            } else if (choiceChange.equals("N")){
                break;

            } else{
                System.out.println("Please provide proper input");
                System.out.println();
            }

        } while(true);

        System.out.println();                                               //print all orders and total
        System.out.println("Proceeding to payment...\n");
        System.out.println("*********************************Current Orders********************************* ");
        System.out.println("Order:                                   Amount:        Quantity:      Total:");

        for (int i = 0; i < price.size();i++){
            System.out.printf("%-40s %-14.0f %-14d %-14.2f\n",order.get(i) , price.get(i), quantity.get(i),(price.get(i)*quantity.get(i)));
        }

        double total = 0;
        for (int i = 0; i < price.size();i++){
            total+=(price.get(i)*quantity.get(i));
        }

        System.out.printf("Total: %.2f\n", total);
        double discountedTotal = total;
        String discountChoice = "";

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
        int customerPayment = 0;
        double change = 0;
        while(true){                                      //accept payment and display change
            System.out.printf("Total Amount: %.2f\n", discountedTotal);
            System.out.print("Enter Payment Amount: ");
            customerPayment = sc.nextInt();

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

        }
        sc.nextLine();
        System.out.println("Thank you for ordering!");
        System.out.println();
        String orderRepeat = "Y";

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
}