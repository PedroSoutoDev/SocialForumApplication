package DTO;

public class UserDto implements Dto{
    public final String username;
    public final int userid;

    public UserDto(String username, int userId){
        this.username = username;
        this.userid = userId;
    }
}
