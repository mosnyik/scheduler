package mosnyik;

import java.util.ArrayList;
import java.util.Scanner;

public class RegisterUser {

    mosnyik.SendEmail sendMail = new mosnyik.SendEmail();
    ArrayList<String> userName = new ArrayList<String>();
   public ArrayList<String> email = new ArrayList<String>();
   public ArrayList<String> password = new ArrayList<String>();
    ArrayList<String> confirmPassword = new ArrayList<String>();

    final Scanner scanner = new Scanner(System.in);

    public void addUser() throws Exception {

        while( true ){
            System.out.println("Please enter your userName");
            String userName = scanner.next();
            if (!userName.matches("[a-zA-Z]+")) {
                System.out.println("Invalid userName format. Please enter a single name without spaces.");
                continue;
            }
            System.out.println("Please enter your email");
            String email= scanner.next();

            System.out.println("Please enter your password");
            String password =scanner.next();
            System.out.println("Please confirm your password");
            String confirmPassword =scanner.next();

            if(password.equals(confirmPassword)) {
                this.userName.add(String.valueOf(this.userName));
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
                sendMail.sendLoginMail(userName.get(i), email);
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
//                    System.out.print("Enter your email: ");
//                    String email = scanner.next();
//                    System.out.print("Enter your password: ");
//                    String password = scanner.next();
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
