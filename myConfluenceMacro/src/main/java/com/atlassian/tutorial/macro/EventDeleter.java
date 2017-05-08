package com.atlassian.tutorial.macro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alexander on 2017-05-08.
 */
public class EventDeleter {

    String[] outlookIDs = {};

    public void delete(Connection myConn) throws SQLException {


        int i = 0;


        for (String IDs : outlookIDs) {
            PreparedStatement ps = myConn.prepareStatement("SELECT OutlookUID FROM confluence.outlookuidtable WHERE OutlookUID='" + IDs + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

            }
        }
    }

}
