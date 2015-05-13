package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * This class fetches images from the provided server. It fetches them sequentially, and could be
 * optimized by sending of requests for the images in parallel
 */
public class SequentialImageFetcher implements ImageFetcher {

    /**
     * The base URL of the server from which we fetch the images
     */
    private static final String BASE_URL = "http://www.itu.dk/people/jacok/MMAD/services/images/";

    /**
     * Fetch all remote images
     *
     * @return The fetched images
     */
    @Override
    public ArrayList<Image> fetch() {

        ArrayList<Image> images = new ArrayList<>();

        JSONArray paths = getImagePaths();

        if (paths == null) {
            return null;
        }

        for(int i = 0; i < paths.length(); i++) {
            try {
                String path = paths.getString(i);

                Image image = new Image(fetchImage(path), path.substring(4));

                images.add(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return images;
    }

    /**
     * Get a JSONArray containing the image paths to fetch
     *
     * @return The image paths
     */
    private JSONArray getImagePaths() {
        try {
            URL url = new URL(BASE_URL);
            URLConnection connection;
            connection = url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return new JSONArray(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Fetch a single image
     *
     * @param path The relative path from where to fetch the image
     * @return The fetched image
     */
    private Bitmap fetchImage(String path) {
        try {
            URL url = new URL(BASE_URL + path);
            URLConnection connection = url.openConnection();

            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
