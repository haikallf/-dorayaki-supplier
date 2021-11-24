package main.services;

import javax.jws.WebService;
import java.sql.*;

@WebService(endpointInterface = "main.services.DorayakiService")
public class DorayakiServiceImpl implements DorayakiService {

    @Override
    public String RateLimiter(String ip, String end, Timestamp time){

        try {
            int count = -1;

            DBHandler handler = new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String query = String.format("select count(*) from log_request where ip = '%s' and endpoint = '%s' and timestamp > ('%s' - interval 1 minute)",ip,end,time);
            ResultSet result = statement.executeQuery(query);

            while(result.next()) {
                String s = result.getString("count(*)");
                count = Integer.parseInt(s);
            }

            if (count <= 10) {
                System.out.println("count "+count);
                // tulis ke log_request
                query = String.format("insert into log_request(ip,endpoint,timestamp) values ('%s','%s','%s')",ip,end,time);
                int update = statement.executeUpdate(query);
                System.out.println("Berhasil masuk log, terusin ke backend");
                conn.close();
            }
            else {
                System.out.println("Lebih dari 10");
            }

            return "Operasi Berhasil";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

//    public static void main(String[] args) {
//        RequestLimiter("1111","john",new Timestamp(System.currentTimeMillis()));
//       }
}
