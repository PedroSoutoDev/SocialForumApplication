package DTO;

import java.util.Date;
import java.util.HashMap;

public class ResponseBuilder{
    private Date date;
    private HashMap<String, String> params;
    private String responseCode;
    private Dto[] response;

    public ResponseDto build(){
        ResponseDto responseToBuild = new ResponseDto(date, params, responseCode, response);
        return responseToBuild;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponse(Dto[] response) {
        this.response = response;
    }
}
