package main.services;

import javax.jws.WebService;

@WebService(endpointInterface = "main.services.RequestDorayaki")
public class RequestDorayakiImpl implements RequestDorayaki {

    @Override
    public String RequestDorayakiPabrik(String ip, int id, int jumlah) {
        return "belom req";
    }
}
