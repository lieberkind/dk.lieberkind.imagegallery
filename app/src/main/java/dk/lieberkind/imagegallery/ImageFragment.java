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
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {

    private Bitmap image;
    private String title;

    public static ImageFragment create(Bitmap image, String title) {
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.image = image;
        imageFragment.title = title;
        return imageFragment;
    }

    public ImageFragment() {
        setRetainInstance(true);
    }

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


}
