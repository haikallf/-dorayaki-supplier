package main.services;

import javax.jws.WebService;
import java.sql.Timestamp;

@WebService(endpointInterface = "main.services.ListDorayaki")
public class ListDorayakiImpl implements ListDorayaki {

    @Override
    public String ListDorayakiPabrik(String ip) {
        System.out.println(ip);
        RateLimiter ratelimiter = new RateLimiter();
        ratelimiter.RateLimiter(ip,"list", new Timestamp(System.currentTimeMillis()));
        return "belom list";
    }
}