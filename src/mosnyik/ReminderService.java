package mosnyik;


import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ReminderService {
    SendEmail sendEmail = new SendEmail();



    private final ScheduledExecutorService scheduler;

    public ReminderService() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void startReminderService(RegisterUser reg) {
        scheduler.scheduleAtFixedRate(() -> checkForReminders(reg), 0, 1, TimeUnit.MINUTES);
    }

    private void checkForReminders(RegisterUser reg)  {


        if(!reg.appointmentDate.isEmpty()) {
            for (int i = 0; i < reg.appointmentDate.size(); i++) {

                // Define the target date and time string
                String appointmentDateTimeString = reg.appointmentDate.get(i) + " " + reg.appointmentTime.get(i);

                // Define the formatter for the target date and time string
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

                try {
                    // Parse the target date and time string to LocalDateTime
                    LocalDateTime date = LocalDateTime.parse(appointmentDateTimeString, formatter);

                    // Get the current date and time
                    LocalDateTime currentDate = LocalDateTime.now();

                    if ( date.isAfter(currentDate)) {
                    System.out.println("You should get ready for your upcoming " + " " + reg.appointmentTitle.get(i) + " appointment");
                } else if (date.isBefore(currentDate)) {
                    System.out.println("You have missed your" + " " + reg.appointmentTitle.get(i) + " appointment" + " on " + reg.appointmentDate.get(i) + " " + reg.appointmentTime.get(i));
                } else if (date.equals(currentDate)) {
                    System.out.println("You should be at your appointment already");
                    sendEmail.sendBookingReminderMail(reg.email.get(i), reg.appointmentDate.get(i), reg.appointmentTime.get(i), reg);

                }
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid date and time format: " + e.getMessage());
                }

            }
 }
    }


}

