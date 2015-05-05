package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by tomaslieberkind on 05/05/15.
 */
public interface ImageFetcher {

    /**
     * Fetch a list of images
     *
     * @return The fetched images
     */
    public ArrayList<Bitmap> fetch();
}
