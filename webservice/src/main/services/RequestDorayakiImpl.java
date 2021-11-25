package main.services;

import javax.jws.WebService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

@WebService(endpointInterface = "main.services.RequestDorayaki")
public class RequestDorayakiImpl implements RequestDorayaki {

    @Override
    public String RequestDorayakiPabrik(String ip, String username, int idItem, int quantity) {
        // parameter : idRequest, username, idItem, quantity, timestamp, status
        // status otomatis pending (1 : accept, 0 : pending, -1 : decline)

        RateLimiter ratelimiter = new RateLimiter();
        Timestamp time = new Timestamp(System.currentTimeMillis());

        int status = ratelimiter.RateLimiter(ip,"req", time);
        if (status == 1) {
            // bikin parameter jadi string json
            String input = String.format("{\"\"username\":%s,\"idItem\":%d,\"quantity\":%d,\"timestamp\":%s}", username, idItem, quantity, time);
            sendReq(input);
            return "Request berhasil dikirim.";

        }
        else if (status == 0) {
            return "Anda melakukan terlalu banyak request, harap tunggu...";
        }
        else {
            return "Terjadi kesalahan, silahkan coba lain kali.";
        }

    }

    public void sendReq(String input) {

        try {

            URL url = new URL("http://localhost:3001/addRequest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


}
