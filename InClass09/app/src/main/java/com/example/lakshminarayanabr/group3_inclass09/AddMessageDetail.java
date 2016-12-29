package com.example.lakshminarayanabr.group3_inclass09;

/**
 * Created by lakshminarayanabr on 11/1/16.
 */

public class AddMessageDetail {
    int Id;
    String Comment;
    int UserId;
    String FileThumbnailId;
    String Type;
    String CreatedAt;
    String UpdatedAt;

    @Override
    public String toString() {
        return "AddMessageDetail{" +
                "Id=" + Id +
                ", Comment='" + Comment + '\'' +
                ", UserId=" + UserId +
                ", FileThumbnailId='" + FileThumbnailId + '\'' +
                ", Type='" + Type + '\'' +
                ", CreatedAt='" + CreatedAt + '\'' +
                ", UpdatedAt='" + UpdatedAt + '\'' +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getFileThumbnailId() {
        return FileThumbnailId;
    }

    public void setFileThumbnailId(String fileThumbnailId) {
        FileThumbnailId = fileThumbnailId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }




}
