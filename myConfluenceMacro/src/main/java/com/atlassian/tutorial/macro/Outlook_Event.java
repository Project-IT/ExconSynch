package com.atlassian.tutorial.macro;

//this is our Outlook Outlook_Event Object

//This is an object to hold information about a single event.
// This object will be a node for the EventsList ArrayList collection which will contain all the events in a
//chronological order
public class Outlook_Event {
    String start;  //Start time of the event
    String end;    //End time of the event
    String location; //Location of the event
    String body;  //Body aka the message in the event
    Boolean allDay; //boolean is the event an all day event?
    String subject; //event subject
    String endDate;
    String appointmentSequenceNumber;
    String url;

    //What about event name, type etc?

    public void addStart(String start){
        this.start = start;
    }

    public void addEnd(String end){
        this.end = end;
    }
    public void addLocation(String location){
        this.location = location;
    }
    public void addBody(String body){
        this.body = body;
    }
    public void addAllDayEvent(Boolean allDay){
        this.allDay = allDay;
    }
    public void addSubject(String subject){
        this.subject = subject;
    }
    public void addEndDate(String endDate){this.endDate = endDate ;}
    public void addAppointmentSequenceNumber(String sequence){this.appointmentSequenceNumber = sequence;}
    public void addNetShowUrl(String url){this.url = url;}


    public String getSubject(){return this.subject;}
    public String getLocation(){return this.location;}
    public String getAllDay(){return this.allDay.toString();}
    public String getEndDate(){return this.endDate;}
    public String getBody(){return this.body;}
    public String getAppointmentSequenceNumber(){return this.appointmentSequenceNumber;}
    public String getNetShowUrl(){return this.url;}


    public String toString(){
        return "| Subject = "+ this.subject +"| Start =" + this.start +
                "|  End = "+ this.end + "|  Location = "+ this.location+
                "| isAllDay = "+ this.allDay.toString() + "| Body = "+ body +
                "|   endDate = "+ endDate +
                "  \n";
    }


}
