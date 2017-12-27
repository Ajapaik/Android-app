package ee.ajapaik.android.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ee.ajapaik.android.PhotoActivity;
import ee.ajapaik.android.R;
import ee.ajapaik.android.adapter.PhotoAdapter;
import ee.ajapaik.android.data.Album;
import ee.ajapaik.android.data.Photo;
import ee.ajapaik.android.fragment.util.WebFragment;
import ee.ajapaik.android.widget.StaggeredGridView;

public abstract class PhotosFragment extends WebFragment {

    protected abstract void refresh();
    protected abstract String getPlaceholderString();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getSwipeRefreshLayout().setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh();
                    }
                }
        );

        getSwipeRefreshLayout().setRefreshing(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            getSwipeRefreshLayout().setRefreshing(true);
            refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setPhotoAdapter(StaggeredGridView gridView, final Album album) {
        gridView.setAdapter(new PhotoAdapter(gridView.getContext(), album.getPhotos(), getSettings().getLocation(), new PhotoAdapter.OnPhotoSelectionListener() {
            @Override
            public void onSelect(Photo photo) {
                PhotoActivity.start(getActivity(), photo, album);
            }
        }));
    }

    protected void initializeEmptyGridView(StaggeredGridView gridView) {
        getEmptyView().setText(getPlaceholderString());
        gridView.setAdapter(null);
    }

    protected TextView getEmptyView() {
        return (TextView)getView().findViewById(R.id.empty);
    }

    protected StaggeredGridView getGridView() {
        return getGridView(getView());
    }

    protected StaggeredGridView getGridView(View view) {
        return (StaggeredGridView)view.findViewById(R.id.grid);
    }

    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return (SwipeRefreshLayout)getView().findViewById(R.id.swiperefresh);
    }
}
