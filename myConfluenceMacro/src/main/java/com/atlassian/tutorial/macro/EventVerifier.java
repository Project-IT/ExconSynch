package com.atlassian.tutorial.macro;
/**
 * Created by jeppe on 2017-05-01.
 */
public abstract class EventVerifier extends dbParameters{
    //defensive methods to check the input of the fields
    public boolean verifyAll_day(){
        return true;
    }
    public boolean verifyCreated(){
        return true;
    }
    public boolean verifyDescription(){
        return true;
    }
    public boolean verifyEnd(){
        return true;
    }
    public boolean verifyLast_modified(){
        return true;
    }
    public boolean verifyLocation(){
        return true;
    }
    public boolean verifyOrganiser(){
        return true;
    }
    public boolean verifyRecurrence_rule(){
        return true;
    }
    public boolean verifyReminding_setting_id(){
        return true;
    }
    public boolean verifySequence(){
        return true;
    }
    public boolean verifySub_calendar_id(){
        return true;
    }
    public boolean verifySummary(){
        return true;
    }
    public boolean verifyUrl(){
        return true;
    }
    public boolean verifyUtc_end(){
        return true;
    }
    public boolean verifyUtc_start(){
        return true;
    }
    public boolean verifyVevent_uid(){
        return true;
    }
}
