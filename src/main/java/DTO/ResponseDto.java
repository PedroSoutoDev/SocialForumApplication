package DTO;

import javax.xml.ws.Response;
import java.util.Date;
import java.util.HashMap;

public class ResponseDto {
    public final Date date;
    public final HashMap<String, String> params;
    public final String responseCode;
    public final Dto[] response;

    ResponseDto(Date date, HashMap<String, String> params, String responseCode, Dto[] response) {
        this.date = date;
        this.params = params;
        this.responseCode = responseCode;
        this.response = response;
    }
}
