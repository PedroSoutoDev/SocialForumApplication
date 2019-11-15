package DAO;

import DTO.PostDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class PostDao {

    private static PostDao postDaoObject;

    PostDto[] posts;

    public static PostDao getInstance() {
        if (postDaoObject == null) {
            postDaoObject = new PostDao();
        }
        return postDaoObject;
    }

    private PostDao() {
        //Populate the post list only once
        createPostList();
    }

    public ArrayList<PostDto> getAllPosts() {
        ArrayList<PostDto> postList = new ArrayList<PostDto>();
        for (int i =0; i < posts.length; i++) {
            postList.add(posts[i]);
        }
        return postList;
    }

    public PostDto getPostById(int id) {
        for (int i =0; i < posts.length; i++) {
            if (posts[i].postid == id) {
                return posts[i];
            }
        }
        return null;
    }

    public ArrayList<PostDto> getPostsByUserId(int userId) {
        ArrayList<PostDto> postList = new ArrayList<PostDto>();
        for (int i =0; i < posts.length; i++) {
            if (posts[i].userid == userId) {
                postList.add(posts[i]);
            }
        }
        return postList;
    }

    private void createPostList()
    {
        OkHttpClient client = new OkHttpClient();
        String url = "http://brianparra.com/sfsu/postData.json";
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonObject obj = jsonParser.parse(response.body().string()).getAsJsonObject();
            posts = gson.fromJson(obj.get("posts"), PostDto[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
