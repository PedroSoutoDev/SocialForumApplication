package DAO;

import DTO.UserDto;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserDao {

    private static UserDao userDaoObject;

    UserDto[] users;

    public static UserDao getInstance() {
        if (userDaoObject == null) {
            userDaoObject = new UserDao();
        }
        return userDaoObject;
    }

    private UserDao() {
        //Populate the user list only once
        createUserList();
    }

    public ArrayList<UserDto> getAllUsers() {
        ArrayList<UserDto> userList = new ArrayList<UserDto>();
        for (int i =0; i < users.length; i++) {
            userList.add(users[i]);
        }
        return userList;
    }

    public UserDto getUserById(int id) {
        for (int i =0; i < users.length; i++) {
            if (users[i].userid== id) {
                return users[i];
            }
        }
        return null;
    }

    public UserDto getUserByUsername(String username) {
        for (int i =0; i < users.length; i++) {
            if (users[i].username.toLowerCase().equals(username.toLowerCase())) {
                return users[i];
            }
        }
        return null;
    }

    private void createUserList()
    {
        OkHttpClient client = new OkHttpClient();
        String url = "http://brianparra.com/sfsu/userData.json";
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(response.body().string()).getAsJsonObject();
            users = gson.fromJson(obj.get("users"), UserDto[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
