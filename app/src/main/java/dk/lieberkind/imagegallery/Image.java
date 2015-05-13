package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;

public class Image {

    private Bitmap image;
    private String title;

    public Image(Bitmap image, String title) {
        this.image = image;
        this.title = title;
    }

    public Bitmap getBitmap() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
