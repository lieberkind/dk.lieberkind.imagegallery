package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by tomaslieberkind on 05/05/15.
 */
public class DownloadImagesTask extends AsyncTask<Void, Void, ArrayList<Bitmap>> {

    private ImageCache imageCache;
    private ImageFetcher imageFetcher;

    /**
     * Create an instance of this task
     */
    public DownloadImagesTask() {
        imageCache = ImageCache.getInstance();
        imageFetcher = new PrimitiveImageFetcher();
    }

    /**
     * Fetch a list of images
     *
     * @param arguments The non-existing arguments
     * @return The fetched images
     */
    @Override
    protected ArrayList<Bitmap> doInBackground(Void... arguments) {

        // If our cache isn't empty, we simple fetch images from there
        if(!imageCache.empty()) {
            return imageCache.all();
        }

        // If our cache is empty, we fetch the images and make sure to
        // add them to the cache before returning them
        ArrayList<Bitmap> images = imageFetcher.fetch();

        for(int i = 0; i < images.size(); i++) {
            imageCache.addImage(images.get(i));
        }

        return images;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> images) {
        if(images != null) {
            System.out.println("Images fetched: " + images.size());
        }
    }
}
