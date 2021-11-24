package main.services;

import javax.jws.WebService;
import java.sql.Timestamp;

@WebService(endpointInterface = "main.services.ListDorayaki")
public class ListDorayakiImpl implements ListDorayaki {

    @Override
    public String ListDorayakiPabrik() {

        RateLimiter ratelimiter = new RateLimiter();
        ratelimiter.RateLimiter("ip","endpoint", new Timestamp(System.currentTimeMillis()));
        return "belom list";
    }
}