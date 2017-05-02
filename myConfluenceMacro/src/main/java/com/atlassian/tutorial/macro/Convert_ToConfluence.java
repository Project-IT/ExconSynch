package com.atlassian.tutorial.macro;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by KataMar on 02/05/17.
 */
public class Convert_ToConfluence {

        protected static String subject_to_conf(Outlook_Event outlookEvent){
            return outlookEvent.getSubject();

        }
        protected static String location_to_conf(Outlook_Event outlookEvent){
            return outlookEvent.getLocation();

        }
        protected static String all_day_to_conf(Outlook_Event outlookEvent){
            return outlookEvent.getAllDay();
        }
        protected static String body_to_conf(Outlook_Event outlookEvent){
            Document doc = Jsoup.parse(outlookEvent.getBody());
            String body = doc.body().text();
            return  body;
        }
        protected static String sequence_to_conf(Outlook_Event outlookEvent){
            return outlookEvent.getAppointmentSequenceNumber();
        }


}





    /* protected String insertQuery="INSERT INTO " + TABLENAME +
            " (ALL_DAY, CREATED, DESCRIPTION, END, LAST_MODIFIED, LOCATION, ORGANISER, RECURRENCE_ID_TIMESTAMP, RECURRENCE_RULE, REMINDER_SETTING_ID, SEQUENCE, START, SUB_CALENDAR_ID, SUMMARY, URL, UTC_END, UTC_START, VEVENT_UID)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
   * protected String all_day=                null;
   - protected String created=                null;
   * protected String description=            null;
   - protected String end=                    null;
   - protected String last_modified=          null;
   * protected String location=               null;
   ... protected String organiser=              null;
   ... protected int    recurrence_id_timestamp=0;
   ... protected String recurrence_rule=        null;
   ...  protected String reminder_setting_id=    null;
   * protected String sequence=               null;
   - protected String start=                  null;
    protected String sub_calendar_id=        null;
   * protected String summary=                null;
   ... protected String url=                    null;
   - protected String utc_end=                null;
   - protected String utc_start=              null;
   ... protected String vevent_uid=             null;


//comment * cecilia&kata    - shayan & aristoteles        ... do it later
    */



