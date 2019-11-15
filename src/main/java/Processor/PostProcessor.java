package Processor;

import DAO.PostDao;
import DTO.PostDto;
import DTO.ResponseBuilder;
import DTO.ResponseDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PostProcessor implements Processor {
    @Override
    public ResponseDto process(HashMap<String, String> args) {
        Date date = new Date();

        // If args is null, it can only be /posts. In which case, we return all posts
        if (args.isEmpty()) {
            ResponseBuilder rb =  new ResponseBuilder();
            rb.setDate(date);
            rb.setParams(null);
            rb.setResponseCode("OK");

            ArrayList<PostDto> userArrayList = PostDao.getInstance().getAllPosts();
            rb.setResponse(userArrayList.toArray(new PostDto[userArrayList.size()]));

            return rb.build();
        }

        if (args.get("postid") != null && isNumeric(args.get("postid")) && args.size() == 1) {
            ResponseBuilder rb =  new ResponseBuilder();
            rb.setDate(date);
            rb.setParams(args);
            rb.setResponseCode("OK");

            int postIdToGet = Integer.parseInt(args.get("postid"));
            PostDto post = PostDao.getInstance().getPostById(postIdToGet);

            if (post != null) {
                PostDto[] userDtoArray = new PostDto[1];
                userDtoArray[0] = post;
                rb.setResponse(userDtoArray);
            } else {
                rb.setResponse(new PostDto[0]);
            }

            return rb.build();
        }

        if (args.get("userid") != null && isNumeric(args.get("userid")) && args.size() == 1) {
            ResponseBuilder rb =  new ResponseBuilder();
            rb.setDate(date);
            rb.setParams(args);
            rb.setResponseCode("OK");

            int postIdToGet = Integer.parseInt(args.get("userid"));
            ArrayList<PostDto> posts = PostDao.getInstance().getPostsByUserId(postIdToGet);

            if (!(posts.isEmpty())) {
                rb.setResponse(posts.toArray(new PostDto[posts.size()]));
            } else {
                rb.setResponse(new PostDto[0]);
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
