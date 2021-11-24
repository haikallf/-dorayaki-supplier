package main.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.*;

@WebService(endpointInterface = "main.services.DorayakiService")
public class DorayakiServiceImpl implements DorayakiService {

    @Override
    public String RateLimiter() {
//        INI CONTOH CARA NGAKSES DATABASE DOANG
//        try {
//            DBHandler handler = new DBHandler();
//            Connection conn = handler.getConnection();
//            Statement statement = conn.createStatement();
//            String query = "BLABLA";
//            ResultSet result = statement.executeQuery(query);
//
//            while(result.next()) {
//                System.out.println(result.getString("nama_kolom"));
//            }
//            return "Listnya";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "gagal";
//        }
        return "tes";
    }
}
