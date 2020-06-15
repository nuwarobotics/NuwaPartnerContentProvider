package com.nuwarobotics.provider.cmdcontent;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;



/**
 *
* Defines a ContentProvider that stores URLs of Picasa featured pictures
 * The provider also has a table that tracks the last time a picture URL was updated.
 */
 public class PartnerContentProvider extends ContentProvider {
    public static final String TAG = "UserDataProvider";

    // Indicates that the incoming query is for a picture URL
    public static final int PARTNER_CONTENT_CMD_URL_QUERY = 1;
    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;
    // Constants for building SQLite tables during initialization

    // Identifies log statements issued by this component
    // Defines an helper object for the backing database
    private SQLiteOpenHelper mDBHelper;
    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher;
    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {

        // Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);
        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code
        sUriMatcher.addURI(
                PartnerProviderAPI.AUTHORITY,
                PartnerProviderAPI.TABLE_NAME,
                PARTNER_CONTENT_CMD_URL_QUERY);

    }

    // Closes the SQLite database helper class, to avoid memory leaks
    public void close() {
        mDBHelper.close();
    }



    /**
     * Initializes the content provider. Notice that this method simply creates a
     * the SQLiteOpenHelper instance and returns. You should do most of the initialization of a
     * content provider in its static initialization block or in SQLiteDatabase.onCreate().
     */
    @Override
    public boolean onCreate() {
        Log.d(TAG,"UserDataProvider Create");
        // Creates a new database helper object
        mDBHelper = new PartnerContentProviderHelper(getContext());
        return true;
    }
    /**
     * Returns the result of querying the chosen table.
     * @see ContentProvider#query(Uri, String[], String, String[], String)
     * @param uri The content URI of the table
     * @param projection The names of the columns to return in the cursor
     * @param selection The selection clause for the query
     * @param selectionArgs An array of Strings containing search criteria
     * @param sortOrder A clause defining the order in which the retrieved rows should be sorted
     * @return The query results, as a {@link Cursor} of rows and columns
     */
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        Log.e(TAG,"query result ");
        SQLiteDatabase dbUserData = mDBHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            // If the query is for a picture URL
            case PARTNER_CONTENT_CMD_URL_QUERY:
                // Does the query against a read-only version of the database
                Log.e(TAG,"query result START");
                Cursor returnCursor = dbUserData.query(
                        PartnerProviderAPI.TABLE_NAME,
                        projection,
                        selection, selectionArgs, null, null, sortOrder);
                // Sets the ContentResolver to watch this content URI for data changes
                returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
                Log.e(TAG,"query result "+uri+ " size:"+returnCursor.getCount());
                return returnCursor;
            case INVALID_URI:
                Log.e(TAG,"query result INVALID_URI");
                throw new IllegalArgumentException("Query -- Invalid URI:" + uri);
        }
        return null;
    }
    /**
     * Returns the mimeType associated with the Uri (query).
     * @see ContentProvider#getType(Uri)
     * @param uri the content URI to be checked
     * @return the corresponding MIMEtype
     */
    @Override
    public String getType(Uri uri) {
        String mType = null;
        switch (sUriMatcher.match(uri)) {
            case PARTNER_CONTENT_CMD_URL_QUERY:
                mType = "vnd.android.cursor.dir/single";//return single data
                break;

            default:
                break;
        }
        return null;
    }
    /**
     *
     * Insert a single row into a table
     * @see ContentProvider#insert(Uri, ContentValues)
     * @param uri the content URI of the table
     * @param values a {@link ContentValues} object containing the row to insert
     * @return the content URI of the new row
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Decode the URI to choose which action to take
        Log.e(TAG,"insert");
        long id ;
        SQLiteDatabase localSQLiteDatabase = mDBHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            // For the modification date table
            case PARTNER_CONTENT_CMD_URL_QUERY:
                // Creates a writeable database or gets one from cache
                // Inserts the row into the table and returns the new row's _id value
                Log.e(TAG,"insert start");
                id = localSQLiteDatabase.insert(
                        PartnerProviderAPI.TABLE_NAME,
                        null,
                        values
                );
                Log.e(TAG,"insert end id="+id);
                // If the insert succeeded, notify a change and return the new row's content URI.
                if (-1 != id) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return Uri.withAppendedPath(uri, Long.toString(id));
                } else {
                    throw new SQLiteException("Insert error:" + uri);
                }

            default:
                throw new IllegalArgumentException("Insert: Invalid URI" + uri);
        }
        //return null;
    }
    /**
     * Returns an UnsupportedOperationException if delete is called
     * @see ContentProvider#delete(Uri, String, String[])
     * @param uri The content URI
     * @param selection The SQL WHERE string. Use "?" to mark places that should be substituted by
     * values in selectionArgs.
     * @param selectionArgs An array of values that are mapped to each "?" in selection. If no "?"
     * are used, set this to NULL.
     *
     * @return the number of rows deleted
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //throw new UnsupportedOperationException("Delete -- unsupported operation " + uri);
        final int match = sUriMatcher.match(uri);
        int numRows = 0;
        switch (match) {
            case PARTNER_CONTENT_CMD_URL_QUERY:
                if (!TextUtils.isEmpty(selection)) {
                    numRows = mDBHelper.getWritableDatabase().delete(PartnerProviderAPI.TABLE_NAME, selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numRows;
    }
    /**
     * Updates one or more rows in a table.
     * @see ContentProvider#update(Uri, ContentValues, String, String[])
     * @param uri The content URI for the table
     * @param values The values to use to update the row or rows. You only need to specify column
     * names for the columns you want to change. To clear the contents of a column, specify the
     * column name and NULL for its value.
     * @param selection An SQL WHERE clause (without the WHERE keyword) specifying the rows to
     * update. Use "?" to mark places that should be substituted by values in selectionArgs.
     * @param selectionArgs An array of values that are mapped in order to each "?" in selection.
     * If no "?" are used, set this to NULL.
     *
     * @return int The number of rows updated.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //throw new UnsupportedOperationException("Update is not supported.  Use delete + insert instead");
        final int match = sUriMatcher.match(uri);
        //final String update_id = uri.getLastPathSegment();
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int numRows = 0;
        switch (match) {
            case PARTNER_CONTENT_CMD_URL_QUERY:
                if (!TextUtils.isEmpty(selection)) {
                    numRows = db.update(PartnerProviderAPI.TABLE_NAME, values, selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return numRows;

    }
 }

