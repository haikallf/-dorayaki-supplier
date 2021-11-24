package main.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class RateLimiter {

    public boolean RateLimiter(String ip, String end, Timestamp time){

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

                return true;
            }
            else {
                System.out.println("Lebih dari 10");
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
            return false;
        }
    }
}
