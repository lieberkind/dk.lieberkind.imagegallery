package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by tomaslieberkind on 05/05/15.
 */
public class ImageCache {

    private ArrayList<Bitmap> images;

    private static ImageCache instance;

    private ImageCache() {
        images = new ArrayList<>();
    }

    public static ImageCache getInstance() {
        if(instance != null) {
            return instance;
        } else {
            return new ImageCache();
        }
    }

    public void addImage(Bitmap image) {
        images.add(image);
    }

    public ArrayList<Bitmap> all() {
        return images;
    }

    public boolean empty() {
        return images.isEmpty();
    }

    public int count() {
        return images.size();
    }
}
