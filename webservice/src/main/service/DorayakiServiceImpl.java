package main.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.sql.*;

@WebService(endpointInterface = "main.services.DorayakiService")
public class DorayakiServiceImpl {

    public static String RequestLimiter(String ip, String end){

        try {
            DBHandler handler = new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String query = String.format("select count(*) from log_request where ip = %s and endpoint = %s and timestamp between now() - interval 1 minute and now()",ip,end);
//            String query = String.format("select * from log_request where ip = %s",ip);
            ResultSet result = statement.executeQuery(query);

            while(result.next()) {
                System.out.println(result.getString("count(*)"));
            }
            return "Berhasil";

        } catch (Exception e) {
            e.printStackTrace();
            return "gagal";
        }
    }

    public static void main(String[] args) {
        String a = RequestLimiter("121212","2");
        System.out.println(a);
       }
}
