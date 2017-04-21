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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * ews by microsoft
 */

public class testClass {

    public static void main(String[] args) throws Exception {

        Scanner sE = new Scanner(System.in);                                                                                // Reads input from standard input device
        FileWriter fw = new FileWriter("output.JSON");                                                                      // Creates a dumpfile
        JSONObject obj = new JSONObject();                                                                                  // Creates a JSON object
        JSONArray Body = new JSONArray();                                                                                   // Creates a JSON array for the event body

        System.out.println("Email address: ");
        String name = sE.next();
        System.out.println("Password: ");
        String pass = sE.next();

        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);                                     // Specifies which Exchange version, though any newer works as well
        ExchangeCredentials credentials = new WebCredentials(name, pass);                                                    // Log in with the respective Exchange account
        service.setCredentials(credentials);                                                                                 // Verifies the credentials

        service.autodiscoverUrl(name, new RedirectionUrlCallback());                                                         // Finds the URI for the E-mail


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                                            // Sets the Date Format
        Date startDate = formatter.parse("2016-05-01 12:00:00");                                                             // Sets start Date
        Date endDate = formatter.parse("2017-05-30 13:00:00");                                                               // Sets end Date
        CalendarFolder cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);                                      // Defines which Calendar Folder to use
        FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));               // Makes an array of Calendar Results
        findResults.getItems();                                                                                              // Gets items
        for (Appointment appt : findResults.getItems()) {
            appt.load();                                                                                                     // Loads event
            String s = appt.getBody().toString();                                                                            // Changes the Calendar Event body to a simple String
            Body.add(s);                                                                                                     // Adds String to JSON Array
        }
        obj.put("Body List", Body);                                                                                          // Puts Body into JSON Object
        fw.write(obj.toJSONString());                                                                                        // FileWriter writes the String to the Object
        fw.close();
        System.out.print("done!");

    }


    static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {                                             // Simple error checker for the URI
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }

}
