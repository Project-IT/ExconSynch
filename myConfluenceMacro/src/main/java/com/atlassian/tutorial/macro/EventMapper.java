package com.atlassian.tutorial.macro;

import java.sql.*;
import java.util.Random;

/**
 * Created by alexander on 2017-05-05.
 */
public class EventMapper {

    private final String tableName = "OutlookUIDtable";

    public void tableMaker(Connection myConn) throws SQLException {
        Statement stmt = myConn.createStatement();
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + tableName + "(ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, OutlookUID VARCHAR(255) , ConfluenceUID VARCHAR(2048))";
        stmt.execute(sqlCreate);
    }

    public boolean tableMap(String OutlookUID, Connection myConn, EventUpdater eu, eventParameters ep) throws SQLException {

        Random random = new Random();
        int value = random.nextInt(999999999)+1000000000;
        String NewVEVUID = (ep.created + String.valueOf(value) + "@130.229.172.50"); //VEVENT UID (after "@" put the IP of the host)

        eventInserter ei = new eventInserter();
        Statement stmt = myConn.createStatement();
        PreparedStatement preparedStatement;

        preparedStatement = myConn.prepareStatement("SELECT ConfluenceUID FROM confluence.outlookuidtable WHERE OutlookUID='" + OutlookUID + "'");
        ResultSet myRs = preparedStatement.executeQuery();

        if (!myRs.next()) {
            String sqlInsert = "INSERT INTO " + tableName + "(OutlookUID, ConfluenceUID)" + "VALUES ('" + OutlookUID + "', '" + NewVEVUID + "')";
            ep.setVevent_uid(NewVEVUID);
            stmt.executeUpdate(sqlInsert);
            ei.insert(ep, myConn);
            return false;
        } else {
            ep.setVevent_uid(myRs.getString("ConfluenceUID"));
            eu.update(ep, myConn);
        }
        return true;
    }
}
