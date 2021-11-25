package main.services;

import javax.jws.WebService;
import java.sql.Timestamp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@WebService(endpointInterface = "main.services.ListDorayaki")
public class ListDorayakiImpl implements ListDorayaki {

    @Override
    public String ListDorayakiPabrik(String ip) {

        RateLimiter ratelimiter = new RateLimiter();
        int status = ratelimiter.RateLimiter(ip,"list", new Timestamp(System.currentTimeMillis()));
        if (status == 1) {
            return getList();

        }
        else if (status == 0) {
            return "Anda melakukan terlalu banyak request, harap tunggu...";
        }
        else {
            return "Terjadi kesalahan, silahkan coba lain kali.";
        }

    }

    public String getList() {

        try {

            URL url = new URL("http://localhost:3001/dorayaki");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String output2 = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                output2 += output;
            }

            conn.disconnect();

            return output2;

        } catch (MalformedURLException e) {

            e.printStackTrace();
            return "Error, terjadi kesalahan, silahkan coba lagi";

        } catch (IOException e) {

            e.printStackTrace();
            return "Error, terjadi kesalahan, silahkan coba lagi";
        }
    }
}