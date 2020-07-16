package pk.edu.pucit.mcproject;

import java.util.ArrayList;

public class PlaceItem {
    private String mPlaceName;
    private String mThumbnail;
    private ArrayList<String> mImageUrls;

    PlaceItem(String placeName, String thumbnail, ArrayList<String> ImageUrls){
        this.mPlaceName = placeName;
        this.mImageUrls = ImageUrls;
        this.mThumbnail = thumbnail;

    }
    PlaceItem(String placeName, String thumbnail){
        this.mPlaceName = placeName;
        this.mThumbnail = thumbnail;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public void setmImageUrls(ArrayList<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public ArrayList<String> getImageUrls() {
        return mImageUrls;
    }

}
