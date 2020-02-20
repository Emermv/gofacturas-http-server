package gofacturas.service;

import gofacturas.core.Db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Settings extends Db {
    public Settings() {
        super();
    }
    public HashMap<String,gofacturas.entity.Settings> getInCode(String [] code){
        String codes=String.join("','",code);

       ResultSet rs= select("select id,code,value,type,state from settings where code in('"+codes+"')");
       try{
           HashMap<String,gofacturas.entity.Settings> settings=new HashMap<>();
           while(rs.next()){
               gofacturas.entity.Settings s=new gofacturas.entity.Settings(
                       rs.getString("id"),
                       rs.getString("code"),
                       rs.getString("value"),
                       rs.getInt("state"),
                       rs.getString("type"));
               settings.put(s.getCode(),s);;

           }
           return settings;
       }catch (SQLException e){
           e.printStackTrace();
       }finally {
           close();
       }
       return null;
    }
}
