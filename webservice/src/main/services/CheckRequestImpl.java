package main.services;

import javax.jws.WebService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

@WebService(endpointInterface = "main.services.CheckRequest")
public class CheckRequestImpl implements CheckRequest {

    @Override
    public String CheckRequest(String ip) {

        try {
            int count = -1;
            DBHandler handler = new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String query = String.format("");
            ResultSet result = statement.executeQuery(query);

            while(result.next()) {
                String s = result.getString("count(*)");
                count = Integer.parseInt(s);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    return "belom";
//        RateLimiter ratelimiter = new RateLimiter();
//        int status = ratelimiter.RateLimiter(ip,"checkreq", new Timestamp(System.currentTimeMillis()));
//        if (status == 1) {
//            return "Array List Dorayaki";
//
//        }
//        else if (status == 0) {
//            return "Anda melakukan terlalu banyak request, harap tunggu...";
//        }
//        else {
//            return "Terjadi kesalahan, silahkan coba lain kali.";
//        }

    }
}