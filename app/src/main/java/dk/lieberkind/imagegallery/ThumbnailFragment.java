package dk.lieberkind.imagegallery;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThumbnailFragment extends Fragment {

    /**
     * The ImageCache from where already fetched mImages can be retrieved
     */
    private ImageCache mImageCache = ImageCache.getInstance();

    /**
     * The list of mImages to display as thumbnails
     */
    private ArrayList<Image> mImages = new ArrayList<>();

    /**
     * The gridView containing the thumbnails
     */
    private GridView gridView;

    /**
     * The listener that responds to thumbnail clicks
     */
    private OnThumbnailClickedListener mListener;

    /**
     * Required empty public constructor
     */
    public ThumbnailFragment() {
    }

    /**
     * The onAttach lifecycle method
     *
     * Here we make sure that the activity containing the fragment implements the
     * OnThumbnailClickedListener interface, or else we bail
     *
     * @param activity The activity containing the fragment
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnThumbnailClickedListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException("Containing activities must be instances OnThumbnailClickedListener");
        }
    }

    /**
     * The onCreate lifecycle method
     *
     * Only used to restore state on configuration changes
     *
     * @param savedInstanceState Bundle containing the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Only check the image cache if it is not the first time this fragment is created
        if(savedInstanceState != null && !mImageCache.empty()) {
            mImages = mImageCache.all();
        }
    }

    /**
     * The onCreateView lifecycle method
     *
     * Here we initialize everything that has to do with the fragments view. This includes setting
     * the images that should be displayed as thumbnails, and what should happen when a thumbnail
     * is clicked
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
        View view = inflater.inflate(R.layout.fragment_thumbnail, container, false);

        gridView = (GridView) view.findViewById(R.id.thumbnails);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onThumbnailClicked(position);
            }
        });

        setImages(mImages);

        return view;
    }

    /**
     * Set the images to display as thumbnails
     *
     * @param images List of images
     */
    public void setImages(ArrayList<Image> images) {
        mImages = images;

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        for(Image image : images) {
            bitmaps.add(image.getBitmap());
        }

        gridView.setAdapter(new ImageAdapter(getActivity(), bitmaps));
    }

    /**
     * Interface which containing activities of this fragment must implement
     */
    public interface OnThumbnailClickedListener {

        /**
         * Handle the thumbnail clicked event
         *
         * @param position The position of the thumbnail clicked in the list of thumbnails
         */
        public void onThumbnailClicked(int position);
    }

    /**
     * Adapter that decides how single thumbnails should be presented
     */
    private class ImageAdapter extends BaseAdapter {

        /**
         * List of images to show as thumbnails
         */
        private ArrayList<Bitmap> mImages;

        /**
         * The context
         */
        private Context mContext;

        /**
         * Create a new ImageAdapter instance
         *
         * @param context The context
         * @param images List of images
         */
        public ImageAdapter(Context context, ArrayList<Bitmap> images) {
            mContext = context;
            mImages = images;
        }

        /**
         * Get the amount of images
         *
         * @return The amount of images
         */
        @Override
        public int getCount() {
            return mImages.size();
        }

        /**
         * Get the item at the given position
         *
         * @param position The position
         *
         * @return The item at the given position
         */
        @Override
        public Object getItem(int position) {
            return mImages.get(position);
        }

        /**
         * Get the id of the item at given position
         *
         * @param position The position
         *
         * @return The id
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Get the view for the item at given position
         *
         * @param position The position
         * @param convertView The old view to re-use, if possible
         * @param parent The parent that the view will be attached to
         *
         * @return View for the item at given position
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if(convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView =  (ImageView) convertView;
            }

            imageView.setImageBitmap(mImages.get(position));
            return imageView;
        }
    }
}
