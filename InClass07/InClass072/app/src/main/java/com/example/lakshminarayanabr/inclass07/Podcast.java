package com.example.lakshminarayanabr.inclass07;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by lakshminarayanabr on 10/3/16.
 */
public class Podcast implements Serializable {
    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getLargeImageURL() {
        return LargeImageURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    String title,summary;

    String thumbnailURL,LargeImageURL;

    String releaseDate;

    public static Comparator<Podcast> dateOrder =
            new Comparator<Podcast>() {
                @Override
                public int compare(Podcast n1, Podcast n2) {

                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssz");




                    Date date1=null,date2=null;

                    try {
                        if(n1.getReleaseDate()!=null&& n2.getReleaseDate()!=null)
                        {
                            date1=fmt.parse(n1.getReleaseDate());
                            date2=fmt.parse(n2.getReleaseDate());

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


    public Podcast() {
    }

    public Podcast(String title, String summary, String thumbnailURL, String largeImageURL, String releaseDate) {

        this.title = title;
        this.summary = summary;
        this.thumbnailURL = thumbnailURL;
        LargeImageURL = largeImageURL;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                ", LargeImageURL='" + LargeImageURL + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
