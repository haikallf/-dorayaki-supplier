package main.services;

import javax.jws.WebService;
import java.sql.Timestamp;

@WebService(endpointInterface = "main.services.ListDorayaki")
public class ListDorayakiImpl implements ListDorayaki {

    @Override
    public String ListDorayakiPabrik(String ip) {

        RateLimiter ratelimiter = new RateLimiter();
        int status = ratelimiter.RateLimiter(ip,"list", new Timestamp(System.currentTimeMillis()));
        if (status == 1) {
            return "Array List Dorayaki";

        }
        else if (status == 0) {
            return "Anda melakukan terlalu banyak request, harap tunggu...";
        }
        else {
            return "Terjadi kesalahan, silahkan coba lain kali.";
        }

    }
}