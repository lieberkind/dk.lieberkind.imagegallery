package dk.lieberkind.imagegallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * This class decides how a single item in the gallery should look
 */
public class ImageFragment extends Fragment {

    /**
     * The actual image
     */
    private Bitmap image;

    /**
     * The image title
     */
    private String title;

    /**
     * Static method for creating an instance of this fragment populated with relevant data
     *
     * @param image Image bitmap
     * @param title Image title
     *
     * @return New instance of ImageFragment
     */
    public static ImageFragment create(Bitmap image, String title) {
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.image = image;
        imageFragment.title = title;
        return imageFragment;
    }

    /**
     * The onCreate lifecycle method
     *
     * This is only used to restore the instance state on configuration changes
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            image = savedInstanceState.getParcelable("image");
            title = savedInstanceState.getString("title");
        }
    }

    /**
     * The onCreateView lifecycle method
     *
     * Here we initialize everything that has to do with the fragment's view
     *
     * @param inflater Layout inflater
     * @param container Container for the fragment's view
     * @param savedInstanceState Bundle containing the saved instance state
     *
     * @return The fragment's view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView title = (TextView) view.findViewById(R.id.image_title);

        image.setImageBitmap(this.image);
        title.setText(this.title);

        return view;
    }

    /**
     * The onSaveInstanceState lifecycle method
     *
     * Here we save the image title and bitmap
     *
     * @param outState Bundle to contain state
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("image", image);
        outState.putString("title", title);
    }
}
