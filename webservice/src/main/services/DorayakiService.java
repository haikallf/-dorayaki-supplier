package main.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.sql.Timestamp;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface DorayakiService {
    @WebMethod
    public String RateLimiter(String ip, String end, Timestamp time);
}
