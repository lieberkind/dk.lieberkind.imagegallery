package dk.lieberkind.imagegallery;

import java.util.ArrayList;

public interface ImageFetcher {

    /**
     * Fetch a list of images
     *
     * @return The fetched images
     */
    public ArrayList<Image> fetch();
}
