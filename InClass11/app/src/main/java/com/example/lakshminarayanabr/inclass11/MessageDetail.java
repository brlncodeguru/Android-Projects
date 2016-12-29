package com.example.lakshminarayanabr.inclass11;

import java.util.ArrayList;

/**
 * Created by lakshminarayanabr on 11/14/16.
 */

public class MessageDetail {
    String Id;
    String UserId;
    String Comment;
    String FileThumbnailId;
    String Type;
    String CreatedAt;
    String UpdatedAt;
    String post;
    String username;

    ArrayList<Comment> postcomments;

    @Override
    public String toString() {
        return "MessageDetail{" +
                "Id='" + Id + '\'' +
                ", UserId='" + UserId + '\'' +
                ", Comment='" + Comment + '\'' +
                ", FileThumbnailId='" + FileThumbnailId + '\'' +
                ", Type='" + Type + '\'' +
                ", CreatedAt='" + CreatedAt + '\'' +
                ", UpdatedAt='" + UpdatedAt + '\'' +
                ", post='" + post + '\'' +
                ", username='" + username + '\'' +
                ", postcomments=" + postcomments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDetail that = (MessageDetail) o;

        if (!Id.equals(that.Id)) return false;
        if (!UserId.equals(that.UserId)) return false;
        return CreatedAt.equals(that.CreatedAt);

    }

    public MessageDetail() {
        this.postcomments=new ArrayList<>();
    }

    public MessageDetail(ArrayList<com.example.lakshminarayanabr.inclass11.Comment> postcomments) {
        this.postcomments = postcomments;
    }

    @Override
    public int hashCode() {
        int result = Id.hashCode();
        result = 31 * result + UserId.hashCode();
        result = 31 * result + CreatedAt.hashCode();
        return result;
    }

    //    public String getCommentsforpost() {
//        return post;
//    }
//
//    public void setCommentsforpost(String commentsforpost) {
//        this.post = commentsforpost;
//    }
//
//    public String getId() {
//        return Id;
//    }
//
//    public void setId(String id) {
//        Id = id;
//    }
//
//    public String getUserId() {
//        return UserId;
//    }
//
//    public void setUserId(String userId) {
//        UserId = userId;
//    }
//

//    public String getComment() {
//        return Comment;
//    }
//
//    public void setComment(String comment) {
//        Comment = comment;
//    }

//    public String getFileThumbnailId() {
//        return FileThumbnailId;
//    }
//
//    public void setFileThumbnailId(String fileThumbnailId) {
//        FileThumbnailId = fileThumbnailId;
//    }
//
//    public String getType() {
//        return Type;
//    }
//
//    public void setType(String type) {
//        Type = type;
//    }
//
//    public String getCreatedAt() {
//        return CreatedAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        CreatedAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return UpdatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        UpdatedAt = updatedAt;
//    }
}
