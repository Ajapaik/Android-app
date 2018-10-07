package ee.ajapaik.android;

import android.content.Context;
import android.content.Intent;

import ee.ajapaik.android.fragment.RecentRephotosFragment;

public class RecentRephotosActivity extends AlbumActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, RecentRephotosActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public RecentRephotosFragment createFragment() {
        return new RecentRephotosFragment();
    }
}
