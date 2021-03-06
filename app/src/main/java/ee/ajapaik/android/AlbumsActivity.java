package ee.ajapaik.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ee.ajapaik.android.fragment.AlbumsFragment;

public class AlbumsActivity extends NavigationDrawerActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AlbumsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_albums);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new AlbumsFragment()).commit();
        }
    }
}
