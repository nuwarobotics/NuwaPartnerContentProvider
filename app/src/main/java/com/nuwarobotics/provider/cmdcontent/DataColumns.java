package com.nuwarobotics.provider.cmdcontent;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * PLEASE DO NOT MODIFY THIS FILE
 */
public class DataColumns implements BaseColumns {

    private DataColumns() { }
    // The starting version of the database
    public static final int DATABASE_VERSION = 1;

    // The URI scheme used for content URIs
    public static final String SCHEME = "content";
    /**
     * The DataProvider content URI
     *  content://com.nuwarobotics.nuwadataprovider.database
     */
    public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + PartnerProviderAPI.AUTHORITY);

    /**
     *  table primary key column name
     */
    public static final String ROW_ID = BaseColumns._ID;

    /**
     * Data type
     */
    public static final String TEXT_TYPE = "TEXT";
    public static final String TEXT_UNIQUE_TYPE = "TEXT UNIQUE";
    public static final String TEXT_NOTNULL_TYPE = "TEXT NOT NULL";

    public static final String PRIMARY_KEY_TYPE = "INTEGER PRIMARY KEY";
    /**
     * Picture table content URI
     */
    public static final Uri TABLE_NAME_CMD_LIST_CONTENTURI =
            Uri.withAppendedPath(CONTENT_URI, PartnerProviderAPI.TABLE_NAME);

    /**
     * TABLE_NAME data column name
     */
    public class PARTNER_CONTENT_CMD_COLUMN {
        public static final String COLUMN_IDX = BaseColumns._ID; /*data index*/
        public static final String COLUMN_LOCAL_COMMAND = "local_command";/*String, user customize locol_command (Allow Empty)*/
        public static final String COLUMN_ENTITY = "entity";/*String, entity of this command (MUST)*/
        public static final String COLUMN_START_TYPE = "start_type";/*String, start type such as ACTIVITY, SERVICE, BROADCAST (MUST) */
        public static final String COLUMN_INTENT_PACKAGE = "intent_package";/*String, Intent target package (Allow Empty)*/
        public static final String COLUMN_INTENT_URI = "intent_uri";/*String, Full Intent uri to trigger 3rd function (MUST)*/
    }

    public static final String START_TYPE_ACTIVITY = "ACTIVITY";
    public static final String START_TYPE_SERVICE = "SERVICE";
    public static final String START_TYPE_BROADCAST = "BROADCAST";

    // Defines an SQLite statement that builds the Picasa picture URL table
    public static final String CREATE_PARTNER_CONTENT_CMD_TABLE_SQL = "CREATE TABLE" + " " +
            PartnerProviderAPI.TABLE_NAME + " " +
            "(" + " " +
            DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_IDX + " " + DataColumns.PRIMARY_KEY_TYPE + " ," +
            DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_LOCAL_COMMAND + " " + DataColumns.TEXT_UNIQUE_TYPE + " ," +
            DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_ENTITY + " " + DataColumns.TEXT_NOTNULL_TYPE + " ," +
            DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_START_TYPE + " " + DataColumns.TEXT_TYPE + " ," +
            DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_PACKAGE + " " + DataColumns.TEXT_TYPE + " ," +
            DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_URI + " " + DataColumns.TEXT_TYPE +
            ")";

    public static String[] Partner_Projection =
            {
                    PARTNER_CONTENT_CMD_COLUMN.COLUMN_IDX,
                    PARTNER_CONTENT_CMD_COLUMN.COLUMN_LOCAL_COMMAND,
                    PARTNER_CONTENT_CMD_COLUMN.COLUMN_ENTITY,
                    PARTNER_CONTENT_CMD_COLUMN.COLUMN_START_TYPE,
                    PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_PACKAGE,
                    PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_URI
            };
}
