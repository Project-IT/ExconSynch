package com.atlassian.tutorial.macro;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by alexander on 2017-05-08.
 */
public class EventDeleter {

    ArrayList<String> outlookIDs;

    public void delete(Connection myConn) throws SQLException {

        PreparedStatement ps = myConn.prepareStatement("SELECT * OutlookUID FROM confluence.outlookuidtable");
        ResultSet rs = ps.executeQuery();
        int j = 0;

        ArrayList<String> tableIDs = null;

        while (rs.next()) {
            tableIDs.add(rs.getString(j));
            j++;
        }

        tableIDs.removeIf(s -> s.equals(Outloo));

        Statement stmt = myConn.createStatement();

        //loop through remaining IDs - delete correct events
        if (tableIDs != null) {
            for (String ID : tableIDs) {

                String sqlDel = "DELETE FROM confluence.outlookuidtable WHERE OutlookUID='" + ID + "'";
                stmt.executeUpdate(sqlDel);

                PreparedStatement ps2 = myConn.prepareStatement("SELECT * OutlookUID FROM confluence.outlookuidtable WHERE OutlookUID='" + ID + "'");
                ResultSet rs2 = ps2.executeQuery();

                String calendarDel = "DELETE FROM confluence.ao_950dc3_tc_events WHERE ConfluenceUID=" + rs2.getString("ConfluenceUID");
                stmt.executeUpdate(calendarDel);

            }
        }
    }

}
