package com.example.lakshminarayanabr.midtermexamapp;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class AppObject implements  Comparable<AppObject> {
    private long _id;



    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppObject object = (AppObject) o;

       if(appName.equals(((AppObject) o).appName))
       {
           return  true;
       }
        else
       {
        return  false;
       }
    }

    @Override
    public int hashCode() {
        int result = appName.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + thumbnailImageURL.hashCode();
        result = 31 * result + largeImageUrl.hashCode();
        result = 31 * result + priceUNIT.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppObject{" +
                "_id=" + _id +
                ", appName='" + appName + '\'' +
                ", price='" + price + '\'' +
                ", thumbnailImageURL='" + thumbnailImageURL + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                ", priceUNIT='" + priceUNIT + '\'' +
                '}';
    }

    String appName;
    String price;
    String thumbnailImageURL;
    String largeImageUrl;

    public String getPriceUNIT() {
        return priceUNIT;
    }

    public void setPriceUNIT(String priceUNIT) {
        this.priceUNIT = priceUNIT;
    }

    String priceUNIT;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumbnailImageURL() {
        return thumbnailImageURL;
    }

    public void setThumbnailImageURL(String thumbnailImageURL) {
        this.thumbnailImageURL = thumbnailImageURL;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public AppObject(String appName, String price, String thumbnailImageURL, String largeImageUrl) {

        this.appName = appName;
        this.price = price;
        this.thumbnailImageURL = thumbnailImageURL;
        this.largeImageUrl = largeImageUrl;
    }

    public AppObject() {

    }



    @Override
    public int compareTo(AppObject another) {
        if (Double.parseDouble(this.price) > Double.parseDouble(another.price)) {
            return 1;
        }
        else if (Double.parseDouble(this.price) > Double.parseDouble(another.price)) {
            return -1;
        }
        else {
            return 1;
        }

    }
}
