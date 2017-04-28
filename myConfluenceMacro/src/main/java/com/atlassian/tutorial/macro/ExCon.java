package com.atlassian.tutorial.macro;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


public class ExCon implements Macro {

    public String execute(Map<String, String> map, String s, ConversionContext conversionContext) throws MacroExecutionException {

<<<<<<< HEAD
        String username =map.get("Username");
        String password =map.get("Password");
        String trying="before";
=======
        String username = map.get("Username");
        String password = map.get("Password");

>>>>>>> 6cc19092c6fd17c339e1446e9bb15d97285425ad
        // Specifies Exchange version, (any newer works as well)
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        // Log in with the respective Exchange account
        ExchangeCredentials credentials = new WebCredentials(username, password);
        // Verifies the credentials
        service.setCredentials(credentials);

        // Finds the URI for the E-mail
        try {
            service.autodiscoverUrl(username, new RedirectionUrlCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sets the Date Format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Sets start Date
        Date startDate = null;
        try {
            startDate = formatter.parse("2017-04-10 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Sets end Date
        Date endDate = null;
        try {
            endDate = formatter.parse("2017-05-30 13:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Defines which Calendar Folder to use
        CalendarFolder cf = null;
        try {
            cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Makes an array of Calendar Results
        FindItemsResults<Appointment> findResults = null;
        try {
            findResults = cf.findAppointments(new CalendarView(startDate, endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        findResults.getItems();
<<<<<<< HEAD


        LinkedList<Event> eventsList = new LinkedList<Event>();
        for (Appointment appt : findResults.getItems()) {

            // Loads event
=======
        LinkedList<String> eventsList = new LinkedList<String>();

        for (Appointment appt : findResults.getItems()) {
            // Make a new Event object to hold data of one appointment
            Event event = new Event();

            // Loads appt
>>>>>>> 6cc19092c6fd17c339e1446e9bb15d97285425ad
            try {
                appt.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

<<<<<<< HEAD
            // Make a new Event object to hold data of one appointment
            Event event = new Event();
            try {

                trying=  appt.getSubject().toString() ;
                System.out.println(trying);
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }

         /*   if (appt != null) {
                // Add subject of the event
                try {
                    event.addSubject(appt.getSubject().toString());
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }

                // Add an "all day" event
                try {
                    event.addAllDayEvent(appt.getIsAllDayEvent());
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }
=======
            // Add subject of the event
            try {
                event.addSubject(appt.getSubject());
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }
>>>>>>> 6cc19092c6fd17c339e1446e9bb15d97285425ad

            // Add an "all day" event
            try {
                event.addAllDayEvent(appt.getIsAllDayEvent().toString());
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }

            // Add a "not all day" event
            try {
                if (!appt.getIsAllDayEvent()) {
                    event.addStart(appt.getStart().toString());
                    event.addEnd(appt.getEnd().toString());
                }
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }

            // Add location of the event (if it exists)
            try {
                if (appt.getLocation() != null) {
                    event.addLocation(appt.getLocation());
                }
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }

            // Add the body of the event
            try {
                String poopoo = appt.getBody().toString();
                event.addBody(poopoo);
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }

            // Load an event to the linked list eventsList
<<<<<<< HEAD
            eventsList.add(event);
/*/


        }   try {
        //1. Get connection to database
        Connection myConn = DriverManager.getConnection("jdbc:mysql://130.229.188.219:3306/confluence", "tcomkproj2017", "tcomkproj2017");
        //2. Create a statement
        Statement myStm = myConn.createStatement();
        //3. Execute sql query

        //this section is to FETCH data
           /* ResultSet myRs = myStm.executeQuery("SELECT * FROM confluence.ao_950dc3_tc_events;");
            while (myRs.next()) {
                System.out.println(myRs.getString("SUMMARY"));
                //see sql queries for more info
            }*/

        //This section is to PUSH Data
        // myStm.executeUpdate("INSERT INTO confluence.ao_950dc3_tc_events (ALL_DAY, CREATED, DESCRIPTION, END, ID, LAST_MODIFIED, LOCATION, ORGANISER, RECURRENCE_ID_TIMESTAMP, RECURRENCE_RULE, REMINDER_SETTING_ID, SEQUENCE, START, SUB_CALENDAR_ID, SUMMARY, URL, UTC_END, UTC_START, VEVENT_UID)\n" +
        //  "VALUES ('1', '1493235152154', '', '1493251200000', '80', '1493235152154', '', '4028b8815babae10015babb056780000', NULL, NULL, NULL, '0', '1493164800000', 'dfa1eb25-eef12-42c8-abcf-71dec96b58ac', 'appt.getSubject().toString()', NULL, '1493244000000', '1493157600000', '20170426T193232Z--2091550207@localhost')");


        myStm.executeUpdate("INSERT INTO confluence.ao_950dc3_tc_events (ALL_DAY, CREATED, DESCRIPTION, END, LAST_MODIFIED, LOCATION, ORGANISER, RECURRENCE_ID_TIMESTAMP, RECURRENCE_RULE, REMINDER_SETTING_ID, SEQUENCE, START, SUB_CALENDAR_ID, SUMMARY, URL, UTC_END, UTC_START, VEVENT_UID)\n" +
                "VALUES ('1', '1493298045425', '', '1478131200000', '1493235152154', '', '4028b8815babae10015babb056780000', NULL, NULL, NULL, '0', '1478044800000', 'dfa1eb25-eef12-42c8-abcf-71dec96b58ac', '"+trying+"' , NULL, '1493244000000', '1493157600000', '20170426T193232Z--2091550207@localhost')");

        myConn.close(); //closing connection

    } catch (Exception exc) {
        exc.printStackTrace();
    }

        return "done";
=======
            eventsList.add(event.stringer());

        }

        String result = "";

        for (String events : eventsList) {
            result += events;
        }

        return result;
>>>>>>> 6cc19092c6fd17c339e1446e9bb15d97285425ad
    }

    // Simple error checker for the URI
    static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }

    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}
