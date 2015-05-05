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

    public DownloadImagesTask() {
        imageCache = ImageCache.getInstance();
        imageFetcher = new PrimitiveImageFetcher();
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(Void... arguments) {

        if(!imageCache.empty()) {
            return imageCache.all();
        }

        ArrayList<Bitmap> images = imageFetcher.fetch();

        for(int i = 0; i < images.size(); i++) {
            imageCache.addImage(images.get(i));
        }

        return imageCache.all();
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> images) {
        if(images != null) {
            System.out.println("Images fetched: " + images.size());
        }
    }
}
