package com.nuwarobotics.provider.cmdcontent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

public class PartnerProviderAPI {

    /**
     * 3rd define it-own AUTHORITY string
     * NOTIEC : It must same with string define on AndroidManifest.xml
     *         <provider
     *             android:name=".PartnerContentProvider"
     *             android:authorities="com.3rd.provider.content.cmd"
     *             android:exported="true">
     */
    public static final String AUTHORITY = "com.3rd.provider.content.cmd";
    /**
     * 3rd define it-own table name
     * NOTICE : It must declare metaData on AndroidManifest.xml
     *             <meta-data
     *                 android:name="com.nuwarobotics.api.meta.PARTNER_CONTENT_TABLE"
     *                 android:value="nuwa_example_cmd_table" />
     */
    public static final String TABLE_NAME = "nuwa_example_cmd_table";

    /**
     * 3rd could define it's own table name  (Suggest not modify)
     */
    public static final String DATABASE_NAME = "PartnerContentDB";


    /**
     * Insert
     * @param context
     * @param values
     * @return
     */
    public static Uri insert(Context context, ContentValues values)  {
        Log.e("DebugReceiver","PartnerProviderAPI.insert");
        Uri result_uri = null;
        result_uri = context.getContentResolver().insert(DataColumns.TABLE_NAME_CMD_LIST_CONTENTURI, values);
        Log.e("DebugReceiver", "PartnerProviderAPI.insert success " + result_uri);

        return result_uri;
    }








}
