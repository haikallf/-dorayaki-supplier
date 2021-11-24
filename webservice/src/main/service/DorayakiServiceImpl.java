package main.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.*;

@WebService(endpointInterface = "main.services.DorayakiService")
public class DorayakiServiceImpl {

    public static void main(String[] args) {

        try {
            DBHandler handler = new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String query = "select * from bahan_baku";
            ResultSet result = statement.executeQuery(query);

            while(result.next()) {
                System.out.println(result.getString("namaBahan"));
            }
//            return "Berhasil";

        } catch (Exception e) {
            e.printStackTrace();
//            return "gagal";
        }
    }
}
