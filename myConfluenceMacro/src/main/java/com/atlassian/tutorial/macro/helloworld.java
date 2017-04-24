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
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class helloworld implements Macro {

    public String execute(Map<String, String> map, String s, ConversionContext conversionContext) throws MacroExecutionException {
        Scanner sE = new Scanner(System.in);
        try {
            FileWriter fw = new FileWriter("output.JSON");                                                                      // Creates a dumpfile
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Email address: ");
        String name =map.get("Username");
        // String name = sE.next();
        //System.out.println("Password: ");
        //String pass = sE.next();
         String pass =map.get("Password");
        StringBuilder output=null;
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);                                     // Specifies which Exchange version, though any newer works as well
        ExchangeCredentials credentials = new WebCredentials(name, pass);                                                    // Log in with the respective Exchange account
        service.setCredentials(credentials);                                                                                 // Verifies the credentials

        try {
            service.autodiscoverUrl(name, new RedirectionUrlCallback());                                                         // Finds the URI for the E-mail
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                                            // Sets the Date Format
        Date startDate = null;                                                             // Sets start Date
        try {
            startDate = formatter.parse("2017-04-10 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;                                                               // Sets end Date
        try {
            endDate = formatter.parse("2017-05-30 13:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CalendarFolder cf = null;                                      // Defines which Calendar Folder to use
        try {
            cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FindItemsResults<Appointment> findResults = null;               // Makes an array of Calendar Results
        try {
            findResults = cf.findAppointments(new CalendarView(startDate, endDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        findResults.getItems();
        LinkedList<Event> eventsList = new LinkedList<Event>();

        for (Appointment appt : findResults.getItems()) {
            //Loads event
            try {
                appt.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //make a new Event object to hold data of one appointment
            Event event = new Event();

            if (appt != null) {
                //add subject of the event
                try {
                    event.addSubject(appt.getSubject().toString());
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }
                //add is the event all day event
                try {
                    event.addAllDayEvent(appt.getIsAllDayEvent());
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }
                //if event is not all day event
                try {
                    if (!appt.getIsAllDayEvent()) {
                        event.addStart(appt.getStart().toString());
                        event.addEnd(appt.getEnd().toString());
                    }
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }
                //add location if it is not null
                try {
                    if (appt.getLocation() != null) {
                        event.addLocation(appt.getLocation().toString());
                    }
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }
                //add body
                try {
                    event.addBody(appt.getBody().toString());
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }

            }
            //load an event to the linked list eventsList
            eventsList.add(event);


        }


















        /*
        for (Appointment appt : findResults.getItems()) {

            try {
                appt.load();                                                                                                     // Loads event
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Event event = new Event();
              // s = appt.getSubject().toString();

                event.addSubject(appt.getSubject().toString());


            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }
                                                                                                            // Adds String to JSON Array
        }
        */
        //return s;
        return eventsList.toString();
    }
    static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {                                             // Simple error checker for the URI
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }
    public BodyType getBodyType() { return BodyType.NONE; }

    public OutputType getOutputType() { return OutputType.BLOCK; }
}