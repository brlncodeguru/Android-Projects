package com.example.lakshminarayanabr.group3_inclass09;

/**
 * Created by lakshminarayanabr on 10/31/16.
 */

public class ChatMessageDetail {
    int Id;
    int UserId;
    String UserFname;
    String UserLname;
    String Comment;
    String FileThumbnailId;
    String Type;
    String CreatedAt;
    String UpdatedAt;

    public String getUserFname() {
        return UserFname;
    }

    public void setUserFname(String userFname) {
        UserFname = userFname;
    }

    public String getUserLname() {
        return UserLname;
    }

    public void setUserLname(String userLname) {
        UserLname = userLname;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getFileThumbnailId() {
        return FileThumbnailId;
    }

    public void setFileThumbnailId(String fileThumbnailId) {
        FileThumbnailId = fileThumbnailId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    @Override
    public String toString() {
        return " userId :" + UserId + " updatedAt :" + UpdatedAt + " id :" + Id + " comment :" + Comment + " fileThumbNail :" + FileThumbnailId
                + " type :" + Type + " createdAt :" + CreatedAt;
    }
}
