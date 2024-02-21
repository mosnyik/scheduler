package mosnyik;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SendEmail {
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String apiKey = dotenv.get("API_KEY");

    // Get the current date and time
    private final LocalDateTime currentDateTime = LocalDateTime.now();

    // Format the date and time using a specific pattern
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss");

    private final String dateAndTime = currentDateTime.format(formatter);

    private final String osName = System.getProperty("os.name");
    private final String osVersion = System.getProperty("os.version");

    private  String ip = "";
    private  String city = "";
    private  String country = "";
    private final String masterEmail = "mosnyik@gmail.com";

    public void getUserLocation(){
    try {
        // Make a request to an IP geolocation service
        URL url = new URL("https://ipapi.co/json/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse the JSON response using Gson
        Gson gson = new Gson();
        JsonObject locationData = gson.fromJson(response.toString(), JsonObject.class);

        // Extract the desired fields
         ip = locationData.get("ip").getAsString();
        city = locationData.get("city").getAsString();
        country = locationData.get("country_name").getAsString();

        // Close the connection
        connection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();

    }

}
    public void printCurrentDate(){
    System.out.println("Current Date and Time: " + dateAndTime );
        System.out.println("Current Login os name: " + osName);
        System.out.println("Current Login os version: " + osVersion);

}


    public void sendRegMail(String userName, String email) {
        Email from = new Email(masterEmail);
        String subject = "Welcome to Scheduler!";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Dear " + userName + ","+" \n" +
                "\n" +
                "Welcome to Scheduler! We are thrilled to have you join our community. Thank you for choosing our platform for scheduling your appointments conveniently.\n"
                 +
                "At Scheduler, we strive to provide you with a seamless and hassle-free experience for managing your appointments. Whether you're booking appointments for medical consultations, salon services, or any other services, we've got you covered.\n" +
                "\n"+
                "  Here's a quick overview of what you can do with our app:\n" +
                "1. Schedule Appointments: Easily book appointments with your preferred service providers at your convenience.\n" +

                "2. Manage Your Calendar: Keep track of all your upcoming appointments and receive reminders so you never miss an appointment.\n" +

                "3. View Service Providers: Explore a variety of service providers available on our platform and choose the one that best fits your needs. \n" +

                "4. Rate and Review: Share your feedback by rating and reviewing service providers to help other users make informed decisions.\n" +
                "\n"+
                "To get started, simply log in to your account using the email and password you provided during registration. If you have any questions or need assistance, our support team is here to help. Feel free to reach reply this mail. \n" +
                "Once again, welcome aboard! We're excited to help you manage your appointments effortlessly.\n" +
                "\n"+
                "Best regards,\n" +
                "Nyikwagh Moses\n" +
                "Scheduler");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendLoginMail(String userName, String email) {
        getUserLocation();

        Email from = new Email(masterEmail);
        String subject = "Schedule - Login Notification";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Dear " + userName + ","+" \n" +
                "\n" +
                "We hope this email finds you well. We wanted to inform you that your Scheduler account was recently accessed.\n"
                +
                "\n" +
                "Below are the details of the login:\n" +
                "Date and Time: " + dateAndTime + "\n" +
                "Device: " + osName + " " + osVersion + "\n" +
                "Location: "+ city + " " + country +
                "\n" +
                "IP Address: "+ ip +
                "\n" +
                "\n" +
                "If this login was made by you, you can disregard this email. \n" +
                "However, if you did not authorize this login or suspect any unauthorized activity on your account, please take immediate action by changing your password and reviewing your account settings.\n"+
                "Thank you for choosing Scheduler for managing your appointments. We appreciate your trust in us.\n" +
                "\n" +
                "Best regards,\n" +
                "Nyikwagh Moses\n" +
                "Scheduler");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void sendBookingMail() {
        Email from = new Email(masterEmail);
        String subject = "You have booked an appointment";
        Email to = new Email("backenddude@gmail.com");
        Content content = new Content("text/plain", "Dear [Client's Name],\n" +
                "\n" +
                "Thank you for booking an appointment with us. We are excited to assist you with your [type of appointment] on [date] at [time]. Below are the details of your appointment:\n" +
                "\n" +
                "Appointment Details:\n" +
                "Date: [Date of the appointment]\n" +
                "Time: [Time of the appointment]\n" +
                "Location: [Location/address, if applicable]\n" +
                "\n" +
                "If you need to make any changes to your appointment, please let us know as soon as possible. You can reply to this email.\n" +
                "\n" +
                "We look forward to meeting with you and providing our services. If you have any questions or concerns, feel free to reach out to us.\n" +
                "\n" +
                "Best regards,\n" +
                "Scheduler");
        Mail mail = new Mail(from, subject, to, content);


        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
