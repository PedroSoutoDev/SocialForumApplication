package Processor;

import DTO.ResponseBuilder;
import DTO.ResponseDto;

import java.util.Date;
import java.util.HashMap;

public class BadRequestProcessor implements Processor {
    @Override
    public ResponseDto process(HashMap<String, String> args) {
        Date date = new Date();

        ResponseBuilder rb =  new ResponseBuilder();
        rb.setDate(date);
        rb.setParams(args);
        rb.setResponseCode("ERROR");
        rb.setResponse(null);

        return rb.build();
    }
}
