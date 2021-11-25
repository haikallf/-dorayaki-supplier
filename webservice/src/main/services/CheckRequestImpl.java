package main.services;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import javax.jws.WebService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

@WebService(endpointInterface = "main.services.CheckRequest")
public class CheckRequestImpl implements CheckRequest {

    @Override
    public String CheckRequest(String username) {

        try {
            int count = -1;
            DBHandler handler = new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement1 = conn.createStatement();
            Statement statement2 = conn.createStatement();
            String query = String.format("select idItem, sum(quantity) from request where username = '%s' and status = 1 and added = 0 group by idItem", username);
            ResultSet result = statement1.executeQuery(query);

            String query2 = String.format("update request set added = 1 where username = '%s' and status = 1 and added = 0", username);
            int result2 = statement2.executeUpdate(query2);

            JSONArray ja = new JSONArray();
            while (result.next()) {
                String id = result.getString("IdItem");
                String qty = result.getString("sum(quantity)");

                if (id == null || qty == null ) {return "{}";}

                JSONObject jo = new JSONObject();
                jo.put(id, qty);
                ja.add(jo);
            }
            String strja = ja.toString();
            return strja;


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
            return "{Error}";
        }

    }
}