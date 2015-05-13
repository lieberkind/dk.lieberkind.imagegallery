package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;

/**
 * Simple container class to represent an image with a title
 */
public class Image {

    /**
     * The actual image
     */
    private Bitmap image;

    /**
     * The image title
     */
    private String title;

    /**
     * Create an instance of Image
     *
     * @param image The Bitmap of the image
     * @param title The image title
     */
    public Image(Bitmap image, String title) {
        this.image = image;
        this.title = title;
    }

    /**
     * Get the image Bitmap
     *
     * @return The actual image
     */
    public Bitmap getBitmap() {
        return image;
    }

    /**
     * Get the image title
     *
     * @return The image title
     */
    public String getTitle() {
        return title;
    }
}
