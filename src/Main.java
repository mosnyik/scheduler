//import java.util.Scanner;
//
//// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
//// then press Enter. You can now see whitespace characters in your code.
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        mosnyik.SendEmail sendEmail = new mosnyik.SendEmail();
//        // Press Opt+Enter with your caret at the highlighted text to see how
//        // IntelliJ IDEA suggests fixing it.
//        System.out.println("Hello and welcome!");
//        System.out.println("What is your name?");
//        String userName = scanner.next();
//        System.out.println("enter your email");
//        String email = scanner.next();
//
//        if(userName.isEmpty()  && email.isEmpty())
//        {
//            System.out.println("You must provide your userName and email");
//        }else
//        {
////            sendEmail.sendRegMail(userName, email);
//            sendEmail.sendLoginMail(userName, email);
//                  sendEmail.sendBookingMail();
//            System.out.println("Mail sent");
//        }
//    }
//}

import mosnyik.RegisterUser;
import mosnyik.SendEmail;

import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) throws Exception {

        RegisterUser reg = new RegisterUser();
//        SendEmail sendMail = new SendEmail();
        final Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            // clear the screen before staring this code
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Clear console screen on Unix-like systems (e.g., macOS, Linux)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            System.out.println("\t\t\t\t---------------------------------------");
            System.out.println("\t\t\t\t            | SCHEDULE APP  |          ");
            System.out.println("\t\t\t\t---------------------------------------");

            System.out.println("1. Register \t 2. Login \t 3. Exit \n ");
            byte selection = scanner.nextByte();

            switch (selection) {
                case 1 -> {
                    flag = false;
                    System.out.println("You want Register");
                    reg.addUser();
                }
                case 2 -> {
                    flag = false;
                    System.out.println("You want Login");
                    reg.login();
                }
                case 3 ->{
                    flag = false;
//                    return;
                }
                default -> System.exit(0);
            }
        }

    }

}