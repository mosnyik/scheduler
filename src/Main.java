import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SendEmail sendEmail = new SendEmail();
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");
        System.out.println("What is your name?");
        String userName = scanner.next();
        System.out.println("enter your email");
        String email = scanner.next();

        if(userName.isEmpty()  && email.isEmpty())
        {
            System.out.println("You must provide your userName and email");
        }else
        {
//            sendEmail.sendRegMail(userName, email);
            sendEmail.sendLoginMail(userName, email);
            //      sendEmail.sendBookingMail();
            System.out.println("Mail sent");
        }
    }
}