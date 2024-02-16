package mosnyik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class RegisterUser {

    mosnyik.SendEmail sendMail = new mosnyik.SendEmail();

    final Scanner scanner = new Scanner(System.in);
    ArrayList<String> userName = new ArrayList<String>();
   public ArrayList<String> email = new ArrayList<String>();
   public ArrayList<String> password = new ArrayList<String>();
    ArrayList<String> confirmPassword = new ArrayList<String>();
    ArrayList<String> appointmentTitle = new ArrayList<String>();
    ArrayList<String> appointmentContent = new ArrayList<String>();
    ArrayList<String> appointmentDate = new ArrayList<String>();
    ArrayList<String> appointmentTime = new ArrayList<String>();



//     if (!userName.matches("[a-zA-Z]+")) {
//            System.out.println("Invalid userName format. Please enter a single name without spaces.");
//            continue;
//        }

    public void addUser() throws Exception {
        System.out.println("Please enter your userName");
        String userName = scanner.next();
        System.out.println("Please enter your email");
        String email= scanner.next();

        while( true ){
            System.out.println("Please enter your password");
            String password =scanner.next();
            System.out.println("Please confirm your password");
            String confirmPassword =scanner.next();

            if(password.equals(confirmPassword)) {
                this.userName.add(String.valueOf(userName));
                this.email.add(email);
                this.password.add(password);
                System.out.println("You have registered as " + userName);
                System.out.println("When you want to login, use " + email + " " + "and your password");
                sendMail.sendRegMail(userName, email);
                this.login();
                break;
            }
            System.out.println("Your password does not match!!");

        }
    }


    public void login() throws Exception{
        // clear the screen before staring this code
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            // Clear console screen on Unix-like systems (e.g., macOS, Linux)
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        System.out.println("\t\t                        ");
        System.out.println("\t\t ______________________");
        System.out.println("\t\t|                      |");
        System.out.println("\t\t|   Welcome to Login   |");
        System.out.println("\t\t|______________________|");
        System.out.println(" ");

        System.out.println("Enter your email: ");
        String email = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();

        boolean flag = false;

        while(true){
        for (int i = 0; i < this.email.size() ; i++) {

            if(this.email.get(i).equals(email) && this.password.get(i).equals(password) ){
                flag = true;
                System.out.println(this.userName.get(i));
                sendMail.sendLoginMail(this.userName.get(i), email);
                break;
            }
        }
        if(flag){
            // clear the screen before staring this code
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Clear console screen on Unix-like systems (e.g., macOS, Linux)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            System.out.println("\t\t                                 ");
            System.out.println("\t\t _______________________________");
            System.out.println("\t\t|                               |");
            System.out.println("\t\t|    SCHEDULE AN APPOINTMENT    |");
            System.out.println("\t\t|_______________________________|");
            System.out.println(" ");
            System.out.println("1. Schedule New Appointment  \t 2. See Appointment List \t 3. Exit \n ");
            byte selection = scanner.nextByte();
            switch(selection){
                case 1->{
                    System.out.println("You can schedule Appointment now");
                    System.out.print("Enter your appointment title : ");
                    String title = scanner.next();
                    System.out.print("Enter your appointment description: ");
                    String content = scanner.next();
                    System.out.print("Enter appointment date (dd-MM-yyyy): ");
                    String dateInput = scanner.next();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    try {
                        // Parse the user input into a LocalDate object
                        LocalDate date = LocalDate.parse(dateInput, formatter);
                        // Get the current date
                        LocalDate currentDate = LocalDate.now();

                        // Compare the input date with the current date
                        if ( currentDate.isEqual(date)) {
                            System.out.println("The appointment date is today.");
                            System.out.println("Today's date is: " + currentDate);
                            System.out.println("Your appointment date is: " + date);

                        } else if (currentDate.isBefore(date)) {

                            System.out.println("The day is has not reached.");
                            System.out.println("Today's date is: " + currentDate);
                            System.out.println("Your appointment date is: " + date);
                        } else if(currentDate.isAfter(date)){
                            System.out.println("You have missed your appointment.");
                            System.out.println("Today's date is: " + currentDate);
                            System.out.println("Your appointment date is: " + date);
                        } else{
                            System.out.println("I don't know what is wrong with your appointment");
                            System.out.println("Today's date is: " + currentDate);
                            System.out.println("Your appointment date is: " + date);
                        }

                        // Print the parsed date
                        System.out.println("Input date: " + date);

                    } catch (Exception e) {
                        // Handle parsing errors
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                    }
                    System.out.print("Enter a time (eg 1:30 pm): ");
                    String timeInput = scanner.next();

                    // Define a time formatter to parse the user input
//                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

                    try {
                        // Parse the user input into a LocalTime object
                        LocalTime time = LocalTime.parse(timeInput, formatTime);
                        // Get the current time
                        LocalTime currentTime = LocalTime.now();

                        // Compare the input time with the current time
                        if (time.equals(currentTime)) {
                            System.out.println("It is your appointment time.");
                        } else if (time.isBefore(currentTime)) {
                            System.out.println("You have some time before your appointment.");
                        } else {
                            System.out.println("You have missed your appointment.");
                        }
                        // Print the parsed time
                        System.out.println("Parsed time: " + time);
                    } catch (Exception e) {
                        // Handle parsing errors
                        System.out.println("Invalid time format. Please enter the time in hh:mm:ss format.");
                    }
                    return;
                }
                case 2 ->{
                    System.out.println("List of appointments");
                    return;
                }
                default -> System.exit(0);
            }
        } else {

            throw new RuntimeException("Invalid email or password. Please try again.");
        }

        }


    }

}
