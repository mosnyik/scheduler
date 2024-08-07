package mosnyik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class RegisterUser {

    mosnyik.SendEmail sendMail = new mosnyik.SendEmail();

    final Scanner scanner = new Scanner(System.in);
    ArrayList<String> userName = new ArrayList<String>();
   public ArrayList<String> email = new ArrayList<String>();
   public ArrayList<String> password = new ArrayList<String>();
//    ArrayList<String> confirmPassword = new ArrayList<String>();
    ArrayList<String> appointmentTitle = new ArrayList<String>();
    ArrayList<String> appointmentContent = new ArrayList<String>();
    ArrayList<String> appointmentDate = new ArrayList<String>();
    ArrayList<String> appointmentTime = new ArrayList<String>();

    String formattedDate = "";
    String formattedTime = "";
    String userEmail = "";
    String userAppointment = "";

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
                userEmail = this.email.get(i);
                sendMail.sendLoginMail(this.userName.get(i), email);
                if(!this.appointmentDate.isEmpty()){
                    userAppointment = this.appointmentContent.get(i);
                    System.out.println("You can check for your appointments");


                }else{
                    System.out.println("No appointments yet");
                }
                break;
            }
        }
        if(flag){
            scheduleAppointment();
        } else {

            throw new RuntimeException("Invalid email or password. Please try again.");
        }

        }


    }

    public void scheduleAppointment() throws Exception {
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
                System.out.println("Enter your appointment title : ");
                scanner.nextLine();
                String title = scanner.nextLine();
                System.out.println("Enter your appointment description: ");
                String content = scanner.nextLine();

                while(true){
                    System.out.print("Enter appointment date (dd-MM-yyyy): ");
                    String dateInput = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    try {
                        // Parse the user input into a LocalDate object
                        LocalDate date = LocalDate.parse(dateInput, formatter);
                        formattedDate = date.format(formatter);
                        break;
                    } catch (Exception e) {
                        // Handle parsing errors
                        System.out.println("Invalid date format. Please enter the date in dd-MM-yyyy format.");
                    };

                };
                while(true){
                    System.out.print("Enter a time (eg 01:30 PM): ");

                    String timeInput = scanner.nextLine();

                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("hh:mm a");
                    try {
                        // Parse the user input into a LocalDate object
                        new SimpleDateFormat("hh.mm aa", Locale.US);
                        LocalTime time = LocalTime.parse(timeInput, formatTime);
                        formattedTime = time.format(formatTime);
                        break;
                    } catch (Exception e) {
                        System.out.println("The appointment time " + timeInput + "is wrong format" + e.getLocalizedMessage());
                        // Handle parsing errors
                        System.out.println("Invalid time format. Please enter the time in hh:mm:a format.");
                    };


                };


                this.appointmentTitle.add(title);
                this.appointmentContent.add(content);
                this.appointmentDate.add(this.formattedDate);
                this.appointmentTime.add(this.formattedTime);
                userAppointment = content;
                sendMail.sendBookingMail(this.userEmail,this.formattedDate,this.formattedTime, this);
                return;
            }
            case 2 ->{
                System.out.println("List of appointments");
                listAppointments();
                return;
            }
            default -> System.exit(0);
        }
    }

    public void listAppointments() throws Exception {
        System.out.println("List out the appointments");
        for (int i = 0; i < this.appointmentTitle.size(); i++) {
            System.out.println(i + 1 + " " + this.appointmentTitle.get(i) + "-" + this.appointmentDate.get(i) + " " + this.appointmentTime.get(i));
        }
    }

}
