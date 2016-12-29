package com.example.lakshminarayanabr.homework7;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lakshminarayanabr on 11/21/16.
 */

public class UserMessage {

    String Id;
    String fromUserId;
    String toUserId;
    String Comment;
    String FileThumbnailId;
    String Type;
    String CreatedAt;
    String readStatus;


    @Override
    public String toString() {
        return "UserMessage{" +
                "Id='" + Id + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", Comment='" + Comment + '\'' +
                ", FileThumbnailId='" + FileThumbnailId + '\'' +
                ", Type='" + Type + '\'' +
                ", CreatedAt='" + CreatedAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMessage that = (UserMessage) o;

        if (!Id.equals(that.Id)) return false;
        if (!fromUserId.equals(that.fromUserId)) return false;
        if (!toUserId.equals(that.toUserId)) return false;
        if (Comment != null ? !Comment.equals(that.Comment) : that.Comment != null) return false;
        if (FileThumbnailId != null ? !FileThumbnailId.equals(that.FileThumbnailId) : that.FileThumbnailId != null)
            return false;
        if (!Type.equals(that.Type)) return false;
        return CreatedAt.equals(that.CreatedAt);

    }

    @Override
    public int hashCode() {
        int result = Id.hashCode();
        result = 31 * result + fromUserId.hashCode();
        result = 31 * result + toUserId.hashCode();
        result = 31 * result + (Comment != null ? Comment.hashCode() : 0);
        result = 31 * result + (FileThumbnailId != null ? FileThumbnailId.hashCode() : 0);
        result = 31 * result + Type.hashCode();
        result = 31 * result + CreatedAt.hashCode();
        return result;
    }


}
