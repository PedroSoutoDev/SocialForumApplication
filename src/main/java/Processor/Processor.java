package Processor;

import DTO.ResponseDto;

import java.util.HashMap;

public interface Processor {
    ResponseDto process(HashMap<String,String> args);
}
