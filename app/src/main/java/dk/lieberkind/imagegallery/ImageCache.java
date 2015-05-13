package dk.lieberkind.imagegallery;

import java.util.ArrayList;

/**
 * Simple singleton class to act as a cache for images, so they don't have to be re-fetched every
 * time the activity or fragments are destroyed
 */
public class ImageCache {

    /**
     * List of images
     */
    private ArrayList<Image> images;

    /**
     * The instance
     */
    private static ImageCache instance;

    /**
     * Private constructor
     */
    private ImageCache() {
        images = new ArrayList<>();
    }

    /**
     * Static method to return the instance, or create it if it hasn't already been done
     *
     * @return The ImageCache instance
     */
    public static ImageCache getInstance() {
        if(instance != null) {
            return instance;
        } else {
            instance = new ImageCache();
            return instance;
        }
    }

    /**
     * Add an image to the cache
     *
     * @param image The image to add
     */
    public void addImage(Image image) {
        images.add(image);
    }

    /**
     * Get all the images in the cache
     *
     * @return List of images
     */
    public ArrayList<Image> all() {
        return images;
    }

    /**
     * Determine whether or not the cache has images
     *
     * @return True or false
     */
    public boolean empty() {
        return images.isEmpty();
    }
}
