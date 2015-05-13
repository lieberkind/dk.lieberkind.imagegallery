package dk.lieberkind.imagegallery;

import java.util.ArrayList;

/**
 * Created by tomaslieberkind on 05/05/15.
 */
public class ImageCache {

    private ArrayList<Image> images;

    private static ImageCache instance;

    private ImageCache() {
        images = new ArrayList<>();
    }

    public static ImageCache getInstance() {
        if(instance != null) {
            return instance;
        } else {
            instance = new ImageCache();
            return instance;
        }
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public ArrayList<Image> all() {
        return images;
    }

    public boolean empty() {
        return images.isEmpty();
    }
}
