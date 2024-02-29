package mosnyik;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        LocalDate currentDate = LocalDate.now();
        if(!reg.appointmentDate.isEmpty()) {
            for (int i = 0; i < reg.appointmentDate.size(); i++) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(reg.appointmentDate.get(i), formatter);
                // Get the current date

                DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);

                // Parse the user input into a LocalTime object
                LocalTime appointmentTime = LocalTime.parse(reg.appointmentTime.get(i), formatTime);
                // Get the current time
                LocalTime currentTime = LocalTime.now();
//            String formattedAppointmentTime = appointmentTime.format(formatTime);

                if (date.isBefore(currentDate) && appointmentTime.isBefore(currentTime)) {
                    System.out.println("You should get ready for your upcoming " + reg.appointmentContent.get(i) + " appointment");
                } else if (date.isAfter(currentDate) && appointmentTime.isAfter(currentTime)) {
                    System.out.println("You have missed your" + reg.appointmentContent.get(i) + " appointment" + " on " + reg.appointmentDate.get(i));
                } else if (date.equals(currentDate) && appointmentTime.equals(currentTime)) {
                    System.out.println("You should be at your appointment already");
                    sendEmail.sendBookingReminderMail(reg.email.get(i), reg.appointmentDate.get(i), reg.appointmentTime.get(i), reg);

                }

            }
 }
        System.out.println("We would drop you a reminder when it is time for your appointment");
    }


}

