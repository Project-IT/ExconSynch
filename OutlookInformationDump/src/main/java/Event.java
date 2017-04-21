/**
 * Created by KataMar on 21/04/17.
 */

//This is an object to hold information about a single event.
// This object will be a node for the EventsList ArrayList collection which will contain all the events in a
//chronological order
public class Event {
    String start;  //Start time of the event
    String end;    //End time of the event
    String location; //Location of the event
    String body;  //Body aka the message in the event
    Boolean allDay; //boolean is the event an all day event?
    String subject; //event subject

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

    public String toString(){
        return "| Subject = "+ this.subject +"| Start =" + this.start +
                "|  End = "+ this.end + "|  Location = "+ this.location+
                "| isAllDay = "+ this.allDay.toString() + "| Body = "+ body +"  \n";
    }


}
