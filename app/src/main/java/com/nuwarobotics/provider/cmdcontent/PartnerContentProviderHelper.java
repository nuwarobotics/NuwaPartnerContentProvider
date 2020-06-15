package com.nuwarobotics.provider.cmdcontent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Defines a helper class that opens the SQLite database for this provider when a request is
 * received. If the database doesn't yet exist, the helper creates it.
 * PLEASE DO NOT MODIFY THIS FILE
 */
public class PartnerContentProviderHelper extends SQLiteOpenHelper {

    /**
     * Instantiates a new SQLite database using the supplied database name and version
     *
     * @param context The current context
     */
    PartnerContentProviderHelper(Context context) {
        super(context,
                PartnerProviderAPI.DATABASE_NAME,
                null,
                DataColumns.DATABASE_VERSION);
    }
    /**
     * Executes the queries to drop all of the tables from the database.
     *
     * @param db A handle to the provider's backing database.
     */
    private void dropTables(SQLiteDatabase db) {
        // If the table doesn't exist, don't throw an error
        db.execSQL("DROP TABLE IF EXISTS " + PartnerProviderAPI.TABLE_NAME);
    }
    /**
     * Does setup of the database. The system automatically invokes this method when
     * SQLiteDatabase.getWriteableDatabase() or SQLiteDatabase.getReadableDatabase() are
     * invoked and no db instance is available.
     *
     * @param db the database instance in which to create the tables.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the tables in the backing database for this provider
        db.execSQL(DataColumns.CREATE_PARTNER_CONTENT_CMD_TABLE_SQL);

    }
    /**
     * Handles upgrading the database from a previous version. Drops the old tables and creates
     * new ones.
     *
     * @param db The database to upgrade
     * @param version1 The old database version
     * @param version2 The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
        Log.w(PartnerContentProviderHelper.class.getName(),
                "Upgrading database from version " + version1 + " to "
                        + version2 + ", which will destroy all the existing data");
        // Drops all the existing tables in the database
        dropTables(db);
        // Invokes the onCreate callback to build new tables
        onCreate(db);
    }
    /**
     * Handles downgrading the database from a new to a previous version. Drops the old tables
     * and creates new ones.
     * @param db The database object to downgrade
     * @param version1 The old database version
     * @param version2 The new database version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int version1, int version2) {
        Log.w(PartnerContentProviderHelper.class.getName(),
                "Downgrading database from version " + version1 + " to "
                        + version2 + ", which will destroy all the existing data");

        // Drops all the existing tables in the database
        dropTables(db);

        // Invokes the onCreate callback to build new tables
        onCreate(db);

    }
}
