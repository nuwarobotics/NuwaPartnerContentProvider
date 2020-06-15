package com.nuwarobotics.provider.cmdcontent;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


/**
 * Developer could send broadcast to trigger some debug function.
 * Use this command to query database
 * adb shell content query --uri content://com.3rd.provider.content.cmd/nuwa_example_cmd_table --projection local_command:entity:start_type:intent_uri:intent_uri --where "start_type=\'ACTIVITY\'"
 */

public class DebugReceiver extends BroadcastReceiver {
    private static final String TAG = "DebugReceiver";
    private static final String INSERT_INTENT = "com.nuwarobotics.api.action.insert_test_data";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"DebugReceiver onReceive "+intent.getAction());
        if (intent.getAction().equals(INSERT_INTENT)) {
            //Use this command to trigger insert test data
            //adb shell am broadcast -n "com.nuwarobotics.provider.cmdcontent/com.nuwarobotics.provider.cmdcontent.DebugReceiver" -a com.nuwarobotics.api.action.insert_test_data
            insertTestDate(context);

        }
    }

    /**
     * THIS IS INSERT FORMAT EXAMPLE
     */
    private void insertTestDate(Context context){
        Log.e(TAG,"insertTestDate");

        //Insert 1st local_command data
        ContentValues mTestData1 = new ContentValues();
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_LOCAL_COMMAND, "我要設定聲音");
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_ENTITY, "設定聲音");
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_START_TYPE, DataColumns.START_TYPE_ACTIVITY);

        //Example of convert Intent to Uri
        Intent testIntent = new Intent("com.nuwarobotics.settings.SHOW");
        testIntent.putExtra("page","VolumeCategory");
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_URI, testIntent.toUri(Intent.URI_INTENT_SCHEME));
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_PACKAGE, "com.nuwarobotics.settings");
        Uri myuri = PartnerProviderAPI.insert(context,mTestData1);
        Log.e(TAG,"insertTestDate mTestData1 result="+myuri);


        //Insert 3rd local_command data
        ContentValues mTestData3 = new ContentValues();
        mTestData3.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_LOCAL_COMMAND, "小紅帽別耍我");
        mTestData3.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_ENTITY, "小紅帽");
        mTestData3.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_START_TYPE, DataColumns.START_TYPE_ACTIVITY);
        //Example of direct write intent uri to db
        mTestData3.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_URI, "intent:#Intent;launchFlags=0x30008000;component=com.nuwarobotics.app.robottheater/.TheaterActivity;S.STORY_CONTENT=%E5%B0%8F%E7%B4%85%E5%B8%BD;end");
        mTestData3.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_PACKAGE, "com.nuwarobotics.app.robottheater");
        myuri = PartnerProviderAPI.insert(context,mTestData3);
        Log.e(TAG,"insertTestDate mTestData3 result="+myuri);


    }
}
