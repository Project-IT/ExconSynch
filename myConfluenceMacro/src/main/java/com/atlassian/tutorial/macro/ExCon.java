package com.atlassian.tutorial.macro;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;

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

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


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

        LinkedList<Outlook_Event> eventsList = new LinkedList<Outlook_Event>();
        //Confluence_Event ep=new Confluence_Event();
        Connection myConn=null;
        Confluence_Event conf_event = new Confluence_Event();
        Outlook_Event outlookEvent = new Outlook_Event();

        eventInserter is = new eventInserter();
        conf_event.setUser("tcomkproj2017");
        conf_event.setPassword("tcomkproj2017");
        conf_event.setdbUrl("localhost:3306/confluence");
        try {
            myConn = DriverManager.getConnection(conf_event.getDbUrl(), conf_event.getUser(), conf_event.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gets items
        try {

            for (Appointment appt : findResults.getItems()) {
                //Loads outlookEvent
                appt.load();
                //make a new Outlook_Event object to hold data of one appointment



                if (appt != null) {
                    outlookEvent.addSubject(appt.getSubject().toString());
                    outlookEvent.addAllDayEvent(appt.getIsAllDayEvent());
                    if (!appt.getIsAllDayEvent()) {
                        outlookEvent.addStart(appt.getStart().toString());
                    }
                    if (appt.getLocation() != null) {
                        outlookEvent.addLocation(appt.getLocation().toString());
                    }
                    outlookEvent.addBody(appt.getBody().toString());
                    outlookEvent.addEndDate(appt.getEnd().toString());
                    outlookEvent.addAppointmentSequenceNumber(appt.getAppointmentSequenceNumber().toString());
                    outlookEvent.addNetShowUrl(appt.getNetShowUrl().toString());


                }

                conf_event.setSummary(Convert_ToConfluence.subject_to_conf(outlookEvent));
                conf_event.setAll_day(Convert_ToConfluence.all_day_to_conf(outlookEvent));
                conf_event.setLocation(Convert_ToConfluence.location_to_conf(outlookEvent));
                conf_event.setDescription(Convert_ToConfluence.body_to_conf(outlookEvent));
                conf_event.setSequence(Convert_ToConfluence.sequence_to_conf(outlookEvent));


                is.insert(conf_event, myConn);
                eventsList.add(outlookEvent);





                //load an outlookEvent to the linked list eventsList
                /*
                System.out.println("CONFLUENCE summary: "+ Convert_ToConfluence.subject_to_conf(outlookEvent));
                System.out.println("location: " + Convert_ToConfluence.location_to_conf(outlookEvent));
                System.out.println("all day: "+ Convert_ToConfluence.all_day_to_conf(outlookEvent));
                System.out.println("OUTLOOK: "+ outlookEvent.toString());
                System.out.println("just body ");
                System.out.println(Jsoup.parse(appt.getBody().toString()));
                Document doc = Jsoup.parse(appt.getBody().toString());
                String text = doc.body().text();
                System.out.println("text : "+ text);
                */

            }
        }   catch(Exception exc){
            exc.printStackTrace();
        }




       /* try {
            ep.setUser("tcomkproj2017");
            ep.setPassword("tcomkproj2017");
            ep.setdbUrl("localhost:3306/confluence");
            myConn = DriverManager.getConnection(ep.getDbUrl(), ep.getUser(), ep.getPassword());
            for (Appointment appt : findResults.getItems()) {
                // Make a new Outlook_Event object to hold data of one appointment
                // Loads appt
                try {
                    appt.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Make a new Outlook_Event object to hold data of one appointment
                Outlook_Event event = new Outlook_Event();
                try {
                    fromOutlook = appt.getSubject().toString();

                    System.out.println(fromOutlook);
                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }
                ep.setAll_day("1");                //all day 1
                ep.setCreated("1493235152154");   //created
                ep.setDescription("");                //description
                ep.setEnd("1493251200000");   //End
                ep.setLast_modified("1493251200000");   //Last_Modified
                ep.setLocation("");      //Location
                ep.setOrganiser("4028b8815babae10015babb056780000");//Organiser
                ep.setRecurrence_id_timestamp(0);            //rec. Id Timestamp
                ep.setRecurrence_rule("");            //Rec. Rule
                ep.setReminder_setting_id("");           //Reminder_SETTING_ID
                ep.setSequence("0");              //SEQUENCE
                ep.setStart("1493164800000");  //START
                ep.setSub_calendar_id("dfa1eb25-ef12-42c8-abcf-71dec96b58ac");//SUB_CALENDAR_ID
                ep.setSummary(fromOutlook);                //SUMMARY
                ep.setUrl("NULL");           //URL
                ep.setUtc_end("1493244000000");  //UTC_END
                ep.setUtc_start("1493157600000");  //UTC_START
                ep.setVevent_uid("20170426T193232Z--2091550207@localhost");//VEVENT UID
                ei.insert(ep, myConn);
            }
            myConn.close();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        */
        return "This is the end of execute";


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
