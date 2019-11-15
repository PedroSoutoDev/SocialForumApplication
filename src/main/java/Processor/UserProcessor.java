package Processor;

import DAO.UserDao;
import DTO.ResponseBuilder;
import DTO.ResponseDto;
import DTO.UserDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserProcessor implements Processor {
    @Override
    public ResponseDto process(HashMap<String, String> args) {
        Date date = new Date();

        // If args is null, it can only be /users. In which case, we return all users
        if (args.isEmpty()) {
            ResponseBuilder rb =  new ResponseBuilder();
            rb.setDate(date);
            rb.setParams(null);
            rb.setResponseCode("OK");

            ArrayList<UserDto> userArrayList = UserDao.getInstance().getAllUsers();
            rb.setResponse(userArrayList.toArray(new UserDto[userArrayList.size()]));

            return rb.build();
        }

        //If there is a  parameter, it can only be /users?userid=<num>. In which case, we return a single user
        if (args.get("userid") != null && isNumeric(args.get("userid")) && args.size() == 1) {
            ResponseBuilder rb =  new ResponseBuilder();
            rb.setDate(date);
            rb.setParams(args);
            rb.setResponseCode("OK");

            int userIdToGet = Integer.parseInt(args.get("userid"));
            UserDto user = UserDao.getInstance().getUserById(userIdToGet);

            if (user != null) {
                UserDto[] userDtoArray = new UserDto[1];
                userDtoArray[0] = user;
                rb.setResponse(userDtoArray);
            } else {
                rb.setResponse(new UserDto[0]);
            }

            return rb.build();
        }

        //If there are args, but they dont match the above, it is an error
        else {
            ResponseBuilder rb =  new ResponseBuilder();
            rb.setDate(date);
            rb.setParams(args);
            rb.setResponseCode("ERROR");
            rb.setResponse(null);

            return rb.build();
        }
    }

    private boolean isNumeric(String strNum) {
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}

