package DTO;

public class PostDto implements Dto{
    public final int postid;
    public final int userid;
    public final String data;

    public PostDto(int postId, int userId, String data){
        this.postid = postId;
        this.userid = userId;
        this.data = data;
    }
}
