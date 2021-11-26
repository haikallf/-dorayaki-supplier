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
            DBHandler handler = new DBHandler("Check Request");
            Connection conn = handler.getConnection();
            Statement statement1 = conn.createStatement();
            Statement statement2 = conn.createStatement();
            String query = String.format("select r.idItem, i.nama, sum(r.quantity) from request r natural join item i where username = '%s' and status = 1 and added = 0 group by idItem", username);
            ResultSet result = statement1.executeQuery(query);

            String query2 = String.format("update request set added = 1 where username = '%s' and status = 1 and added = 0", username); // JANLUP GANTI 1
            int result2 = statement2.executeUpdate(query2);

            JSONArray ja = new JSONArray();
            while (result.next()) {
                String id = result.getString("IdItem");
                String nm = result.getString("nama");
                String qty = result.getString("sum(r.quantity)");

                if (id == null || qty == null ) {return null;}
                JSONObject jo = new JSONObject();
                jo.put("idItem", id);
                jo.put("nama", nm);
                jo.put("quantity", qty);
                ja.add(jo);
            }
            String strja = ja.toString();
            statement1.close();
            statement2.close();
            conn.close();
            result.close();
            return strja;


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
            return null;
        }

    }
}