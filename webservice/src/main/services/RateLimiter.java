package main.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;


public class RateLimiter {

    // return 1 : berhasil lolos, masuk ke database, 0 : Lebih dari treshold, -1 : Database error
    public int RateLimiter(String ip, String end, Timestamp time){

        try {
            int count = -1;
            DBHandler handler = new DBHandler("Rate Limiter ("+end+")");
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String query = String.format("select count(*) from log_request where ip = '%s' and endpoint = '%s' and timestamp > ('%s' - interval 1 minute)",ip,end,time);
            ResultSet result = statement.executeQuery(query);

            while(result.next()) {
                String s = result.getString("count(*)");
                count = Integer.parseInt(s);
            }

            if (count <= 10) {
                // tulis ke log_request
                query = String.format("insert into log_request(ip,endpoint,timestamp) values ('%s','%s','%s')",ip,end,time);
                int update = statement.executeUpdate(query);
                System.out.println("Endpoint "+end+" inserted to log_request.");
                statement.close();
                conn.close();
                result.close();
                return 1;
            }
            else {
                System.out.println("Terlalu banyak request.");
                conn.close();
                return 0;
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
            return -1;
        }
    }
}
