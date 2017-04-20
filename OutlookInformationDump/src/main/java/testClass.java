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

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * ews by microsoft
 */

public class testClass {

    public static void main(String[] args) throws Exception {

        Scanner sE = new Scanner(System.in);
        FileWriter fw = new FileWriter("output.json");

        System.out.println ("Email address: ");
        String name = sE.next();
        System.out.println("Password: ");
        String pass = sE.next();

        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(name, pass);
        service.setCredentials(credentials);

        service.autodiscoverUrl(name, new RedirectionUrlCallback());


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = formatter.parse("2016-05-01 12:00:00");
        Date endDate = formatter.parse("2017-05-30 13:00:00");
        CalendarFolder cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
        FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));
        findResults.getItems();
        for (Appointment appt : findResults.getItems()) {

            appt.load();

            String s = appt.getBody().toString();
            fw.write(s);
        }
        fw.close();
        System.out.print("done!");

    }


    static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }

}
