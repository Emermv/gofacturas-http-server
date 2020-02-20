package gofacturas.service;

import gofacturas.core.Db;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Printer extends Db {
    public Printer() {
        super();
    }
    public ArrayList<gofacturas.entity.Printer> getAll(){
        ArrayList<gofacturas.entity.Printer> printers=new ArrayList<>();
        ResultSet rs=select("select name,state from printers");
   try{
       while (rs.next()){
       printers.add(new gofacturas.entity.Printer(rs.getString("name"),rs.getInt("state")));
       }
       return printers;
   }catch (SQLException e){
       e.printStackTrace();
   }
        return null;
    }
    public void save(JSONArray printers){
     delete("delete from printers",null);

        for(Object  p : printers){
            JSONObject arg=(JSONObject) p;
            ArrayList<String> params=new ArrayList<>();
            params.add(arg.getString("name"));
            params.add(String.valueOf(arg.getInt("state")));
            insert("insert into printers(name,state) values(?,?)",params);
        }
        close();

    }
}
