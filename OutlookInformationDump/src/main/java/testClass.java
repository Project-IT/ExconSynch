import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.apache.commons.lang3.time.StopWatch;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * ews by microsoft
 */

public class testClass {

    public static void main(String[] args) throws Exception {

        Scanner sE = new Scanner(System.in);                                                                                // Reads input from standard input device
        FileWriter fw = new FileWriter("output.txt");                                                                      // Creates a dumpfile

        System.out.println("Email address: ");
        String name = sE.next();
        System.out.println("Password: ");
        String pass = sE.next();

        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);                                     // Specifies which Exchange version, though any newer works as well
        ExchangeCredentials credentials = new WebCredentials(name, pass);                                                    // Log in with the respective Exchange account
        StopWatch timer = new StopWatch();

        timer.start();
        service.setCredentials(credentials);                                                                                 // Verifies the credentials
        service.autodiscoverUrl(name, new RedirectionUrlCallback());                                                         // Finds the URI for the E-mail
        timer.stop();

        double s = timer.getTime();
        System.out.println("Time to login: " + s);
        timer.reset();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                                            // Sets the Date Format
        Date startDate = formatter.parse("2016-05-01 12:00:00");                                                             // Sets start Date
        Date endDate = formatter.parse("2017-05-30 13:00:00");                                                               // Sets end Date
        CalendarFolder cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);                                      // Defines which Calendar Folder to use
        FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));               // Makes an array of Calendar Results
        findResults.getItems();

        LinkedList<Event> eventsList = new LinkedList<Event>();
        // Gets items
        timer.start();
        for (Appointment appt : findResults.getItems()) {
            //Loads event
            appt.load();
            //make a new Event object to hold data of one appointment
            Event event = new Event();

            if (appt != null) {
                //add subject of the event
                event.addSubject(appt.getSubject().toString());
                //add is the event all day event
                event.addAllDayEvent(appt.getIsAllDayEvent());
                //if event is not all day event
                if (!appt.getIsAllDayEvent()) {
                    event.addStart(appt.getStart().toString());
                    event.addEnd(appt.getEnd().toString());
                }
                //add location if it is not null
                if (appt.getLocation() != null) {
                    event.addLocation(appt.getLocation().toString());
                }
                //add body
                event.addBody(appt.getBody().toString());

            }
            //load an event to the linked list eventsList
            eventsList.add(event);


        }
        s = timer.getTime();
        timer.stop();
        System.out.println("Time to get events: " + s);
        fw.write(eventsList.toString());
        fw.close();
        System.out.println("We are done!!! :)");

    }


    static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {                                             // Simple error checker for the URI
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }

}


///// tcomkproj2017@outlook.com
///// ITproject