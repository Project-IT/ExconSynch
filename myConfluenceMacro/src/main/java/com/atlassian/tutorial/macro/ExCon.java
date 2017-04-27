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


public class ExCon implements Macro {

    public String execute(Map<String, String> map, String s, ConversionContext conversionContext) throws MacroExecutionException {

        String username = map.get("Username");
        String password = map.get("Password");

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
        LinkedList<String> eventsList = new LinkedList<String>();

        for (Appointment appt : findResults.getItems()) {
            // Make a new Event object to hold data of one appointment
            Event event = new Event();

            // Loads appt
            try {
                appt.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add subject of the event
            try {
                event.addSubject(appt.getSubject());
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }

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
            eventsList.add(event.stringer());

        }

        String result = "";

        for (String events : eventsList) {
            result += events;
        }

        return result;
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
