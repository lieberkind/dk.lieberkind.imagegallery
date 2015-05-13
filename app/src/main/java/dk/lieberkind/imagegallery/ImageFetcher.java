package dk.lieberkind.imagegallery;

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
    public ArrayList<Image> fetch();
}
