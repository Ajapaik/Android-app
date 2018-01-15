package ee.ajapaik.android.data;

import android.content.Context;
import android.location.Location;
import android.net.Uri;

import com.google.gson.JsonObject;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import ee.ajapaik.android.data.util.Model;
import ee.ajapaik.android.util.Objects;
import ee.ajapaik.android.util.Size;
import ee.ajapaik.android.util.WebAction;

public class Photo extends Model {
    private static final String TAG = "Photo";

    private static final String API_STATE_PATH = "/photo/state/";
    private static final String API_FAVORITE_PATH = "/photo/favorite/set/";

    private static final String KEY_IDENTIFIER = "id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_WIDTH = "width";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_REPHOTOS = "rephotos";
    private static final String KEY_UPLOADS = "uploads";
    private static final String KEY_FAVORITED = "favorited";

    public static WebAction<Photo> createStateAction(Context context, Photo photo) {
        return createStateAction(context, photo.getIdentifier());
    }

    public static WebAction<Photo> createStateAction(Context context, String photoIdentifier) {
        Map<String, String> parameters = new Hashtable<String, String>();

        parameters.put("id", photoIdentifier);

        return new Action(context, API_STATE_PATH, parameters, photoIdentifier);
    }

    public static WebAction<Photo> createFavoritingAction(Context context, String photoIdentifier, boolean favorited) {
        Map<String, String> parameters = new Hashtable<String, String>();

        parameters.put("id", photoIdentifier);
        parameters.put("favorited", String.valueOf(favorited));

        return new Action(context, API_FAVORITE_PATH, parameters, photoIdentifier + "|favorite-" + favorited);
    }

    public static Photo parse(String str) {
        return CREATOR.parse(str);
    }

    public static Uri resolve(Uri uri) {
        return resolve(uri, 400);
    }

    public static Uri resolve(Uri uri, int preferredDimension) {
        if(uri != null) {
            String str = uri.toString();

            str = str.replace("[DIM]", Integer.toString(preferredDimension));

            return Uri.parse(str);
        }

        return uri;
    }

    public static Photo update(Photo photo, int rephotos, int uploads) {
        Photo copy = new Photo(photo.getAttributes());

        copy.m_rephotos = rephotos;
        copy.m_uploads = uploads;

        return copy;
    }

    private String m_identifier;
    private Uri m_image;
    private int m_width;
    private int m_height;
    private String m_title;
    private Date m_date;
    private String m_author;
    private Hyperlink m_source;
    private Location m_location;
    private int m_rephotos;
    private int m_uploads;
    private boolean m_favorited;

    public Photo(JsonObject attributes) {
        this(attributes, null);
    }

    public Photo(JsonObject attributes, Photo basePhoto) {
        m_identifier = readIdentifier(attributes, KEY_IDENTIFIER);
        m_image = readUri(attributes, KEY_IMAGE, (basePhoto != null) ? basePhoto.getImage() : null);
        m_width = readInteger(attributes, KEY_WIDTH);
        m_height = readInteger(attributes, KEY_HEIGHT);
        m_title = readString(attributes, KEY_TITLE, (basePhoto != null) ? basePhoto.getTitle() : null);
        m_author = readString(attributes, KEY_AUTHOR, (basePhoto != null) ? basePhoto.getAuthor() : null);
        m_date = readDate(attributes, KEY_DATE);
        m_source = readHyperlink(attributes, KEY_SOURCE, (basePhoto != null) ? basePhoto.getSource() : null);

        if(canRead(attributes, KEY_LATITUDE) && canRead(attributes, KEY_LONGITUDE)) {
            m_location = new Location(TAG);
            m_location.setLatitude(readNumber(attributes, KEY_LATITUDE));
            m_location.setLongitude(readNumber(attributes, KEY_LONGITUDE));
        }

        m_rephotos = readInteger(attributes, KEY_REPHOTOS);
        m_uploads = readInteger(attributes, KEY_UPLOADS);
        m_favorited = readBoolean(attributes, KEY_FAVORITED);

        if(m_identifier == null || m_image == null || m_width == 0 || m_height == 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public JsonObject getAttributes() {
        JsonObject attributes = new JsonObject();

        write(attributes, KEY_IDENTIFIER, m_identifier);
        write(attributes, KEY_IMAGE, m_image);
        write(attributes, KEY_WIDTH, m_width);
        write(attributes, KEY_HEIGHT, m_height);
        write(attributes, KEY_TITLE, m_title);
        write(attributes, KEY_AUTHOR, m_author);
        write(attributes, KEY_DATE, m_date);
        write(attributes, KEY_SOURCE, m_source);

        if(m_location != null) {
            write(attributes, KEY_LATITUDE, m_location.getLatitude());
            write(attributes, KEY_LONGITUDE, m_location.getLongitude());
        }

        write(attributes, KEY_REPHOTOS, m_rephotos);
        write(attributes, KEY_UPLOADS, m_uploads);
        write(attributes, KEY_FAVORITED, m_favorited);

        return attributes;
    }

    public String getIdentifier() {
        return m_identifier;
    }

    public Uri getImage() {
        return m_image;
    }

    public Uri getThumbnail(int preferredDimension) {
        return resolve(m_image, preferredDimension);
    }

    public int getWidth() {
        return m_width;
    }

    public int getHeight() {
        return m_height;
    }

    public Size getSize() {
        return new Size(m_width, m_height);
    }

    public String getTitle() {
        return m_title;
    }

    public String getAuthor() {
        return m_author;
    }

    public Date getDate() {
        return m_date;
    }

    public Hyperlink getSource() {
        return m_source;
    }

    public Location getLocation() {
        return m_location;
    }

    public int getRephotosCount() {
        return m_rephotos;
    }

    public int getUploadsCount() {
        return m_uploads;
    }

    public boolean isFavorited() {
        return m_favorited;
    }

    public boolean isLandscape() {
        return m_width > m_height;
    }

    @Override
    public boolean equals(Object obj) {
        Photo photo = (Photo)obj;

        if(photo == this) {
            return true;
        }

        if(photo == null ||
                !Objects.match(photo.getIdentifier(), m_identifier) ||
                !Objects.match(photo.getImage(), m_image) ||
                photo.getWidth() != m_width ||
                photo.getHeight() != m_height ||
                !Objects.match(photo.getTitle(), m_title) ||
                !Objects.match(photo.getAuthor(), m_author) ||
                !Objects.match(photo.getDate(), m_date) ||
                !Objects.match(photo.getSource(), m_source) ||
                !Objects.match(photo.getLocation(), m_location) ||
                photo.getRephotosCount() != m_rephotos ||
                photo.getUploadsCount() != m_uploads) {
            return false;
        }

        return true;
    }

    private static class Action extends WebAction<Photo> {
        private String m_baseIdentifier;

        public Action(Context context, String path, Map<String, String> parameters, String baseIdentifier) {
            super(context, path, parameters, CREATOR);
            m_baseIdentifier = baseIdentifier;
        }

        @Override
        public String getUniqueId() {
            return getUrl() + m_baseIdentifier;
        }

        @Override
        protected Photo parseObject(JsonObject attributes) {
            return new Photo(attributes);
        }
    }

    public static final Model.Creator<Photo> CREATOR = new Model.Creator<Photo>() {
        @Override
        public Photo newInstance(JsonObject attributes) {
            return new Photo(attributes);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}