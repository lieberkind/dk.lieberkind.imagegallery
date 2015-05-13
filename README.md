# ImageGallery
*MMAD Mandatory assignment #2 by Tomas Lieberkind*
 
This is an implementation of the basic and advanced part of the assignment. The repository (and a "compiled" version of this README file) can be found at https://github.com/lieberkind/dk.lieberkind.imagegallery

## Implementation details
The app consists of one host activity, `MainActivity`, and two fragments; `ThumbnailFragment` and `GalleryFragment`. Furthermore an `ImageFragment` is used to display a single item in the `GalleryFragment`.

An `ImageCache` singleton class takes care of storing the images locally once they have been fetched from the server, and the cache is used throughout the app as to circumvent the need of re-fetching the images.

For fetching the images, a `SequentialImageFetcher` class has been implemented. This class is an implementation of the `ImageFetcher` interface, which I initially created in hope that I would get the time to develop a more sophisticated fetcher, that could fetch multiple images simultaneously.

The host activity's main responsibilities are fetching images from the server and managing transitions and interaction between the `ThumbnailFragment` and `GalleryFragment`. When the activity is created for the first time, it starts an AsyncTask (`MainActivity.DownloadImagesTask`) that uses the `SequentialImageFetcher` to download all the images, store them in the cache and pass them to the `ThumbnailFragment`.

Not surprisingly, the `ThumbnailFragment` takes care of displaying the images in a thumbnail overview. This is the first thing the user sees. When the user clicks a thumbnail, the `ThumbnailFragment` is replaced by the `GalleryFragment` which opens a large version of the clicked image (and displays its filename). In the requirements it was stated that it should be possible for the user to click an image in the gallery to take them to a thumbnail overview. However, I found it more logical to let the back button take the user back to the thumbnail overview.

## Roadmap (lol)
It was my intention to implement functionality to upload images as well, and I actually implemented the server to handle this. This was implemented as a small PHP application built on the Lumen framework, and can be found at http://imageserver.lieberkind.io. It is possible to send a POST request with an image and a title to `POST /images` and it is possible to fetch all images at `GET /images`. The repository for this can be found at https://github.com/lieberkind/imageserver and the most interesting stuff happens in `app/Http/routes.php`.