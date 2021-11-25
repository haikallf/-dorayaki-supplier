package main.services;

import javax.jws.WebService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
            String input = String.format("{\"username\":\"%s\",\"idItem\":%d,\"quantity\":%d,\"timestamp\":\"%s\"}", username, idItem, quantity, time);
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

    public void sendReq(String data) {
        System.out.println(data);
        try {

            URL obj = new URL("http://localhost:3001/tambahrequest");
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("ufkyenpyzvbthpgf", "5btw1OE7XAz1zbSEcQKe");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setRequestProperty("Accept", "application/json");

            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();


            int responseCode = postConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();

                // print result
                System.out.println(response.toString());
            } 

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


}
