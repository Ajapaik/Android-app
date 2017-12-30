package ee.ajapaik.android.fragment;

import android.content.Context;

import ee.ajapaik.android.data.Album;
import ee.ajapaik.android.util.WebAction;

public class FavoritesFragment extends AlbumFragment {

    @Override
    protected WebAction<Album> createAction(Context context) {
        return Album.createFavoritesAction(context);
    }
}
