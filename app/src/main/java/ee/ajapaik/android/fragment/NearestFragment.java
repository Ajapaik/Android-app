package ee.ajapaik.android.fragment;

import android.content.Context;
import android.location.Location;

import ee.ajapaik.android.test.R;
import ee.ajapaik.android.data.Album;
import ee.ajapaik.android.util.WebAction;

public class NearestFragment extends AlbumFragment {
    private static final int DEFAULT_RANGE = 1000;

    @Override
    protected WebAction<Album> createAction(Context context) {
        Location location = getSettings().getLocation();

        return (location != null) ? Album.createNearestAction(context, location, null, DEFAULT_RANGE) : null;
    }

    @Override
    protected String getPlaceholderString() {
        return getString(R.string.nearest_label_no_data);
    }
}
