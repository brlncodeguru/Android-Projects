package com.example.lakshminarayanabr.inclass06;

/**
 * Created by lakshminarayanabr on 9/27/16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rigot on 9/26/2016.
 */

public class NewsItem implements Parcelable {

    String title, description, thumbnailImage, largeImage,date, link;

    public static Comparator<NewsItem> dateOrder =
            new Comparator<NewsItem>() {
                @Override
                public int compare(NewsItem n1, NewsItem n2) {

                    SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                    fmt.setTimeZone(TimeZone.getTimeZone("GMT"));

                    Date date1=null,date2=null;

                   try {
                        if(n1.getDate()!=null&& n2.getDate()!=null)
                        {
                            date1=fmt.parse(n1.getDate());
                            date2=fmt.parse(n2.getDate());

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (date1 != null) {
                        return date1.compareTo(date2);
                    }
                    return 0;
                }
            };

    public NewsItem() {
    }

    protected NewsItem(Parcel in) {
        title = in.readString();
        description = in.readString();
        thumbnailImage = in.readString();
        largeImage = in.readString();
        date = in.readString();
        link = in.readString();
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(thumbnailImage);
        parcel.writeString(largeImage);
        parcel.writeString(date);
        parcel.writeString(link);
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumbnailImage='" + thumbnailImage + '\'' +
                ", largeImage='" + largeImage + '\'' +
                ", date='" + date + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
