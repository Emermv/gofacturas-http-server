package gofacturas.core;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Db {
    Connection link = null;

    public Db() {
        try {
            // create a connection to the database
            link = DriverManager.getConnection(Constants.DB_NAME);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void close(){
        try {
            if (link != null) {
                link.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ResultSet select(String sql) {
        try {
            Statement stmt = link.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insert(String sql,ArrayList<String> args) {

        try (
             PreparedStatement pstmt = link.prepareStatement(sql)) {
               int i=1;
                 for(String arg : args){
                     pstmt.setString(i++, arg);
                 }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void delete(String sql,ArrayList<String> args) {
        try (
             PreparedStatement pstmt = link.prepareStatement(sql)) {

           if(args!=null){
               for(String arg : args){
                   pstmt.setString(1, arg);
               }
           }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
