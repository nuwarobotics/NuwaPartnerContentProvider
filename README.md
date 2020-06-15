# NuwaPartnerContentProvider
This is a example for globol scope local command.
It allow 3rd customize specific local commend of each Content.

It do not denpend on NuwaSDK version, but software version.
Please make sure Robot on 100JP MR2 later software.

# `Nuwa Website`
* NuwaRobotics Website (https://www.nuwarobotics.com/)
* NuwaRobotics Developer Website (https://dss.nuwarobotics.com/)

NOTICE : Please get NuwaSDK from Nuwarobotics Developer Website

# `SOP : How to use example`
 * Integrate NuwaPartnerContentProvider source code to 3rd App
    + PartnerContentProvider.java (Copy, but DO NOT MODIFY)
    + PartnerContentProviderHelper.java (Copy, but DO NOT MODIFY)
    + DataColumns.java (Copy, but DO NOT MODIFY)
    + PartnerProviderAPI.java (Copy, 3rd need mofiy some config)
    + DebugReceiver.java (Optional)
 * Declare provider to 3rd Androidmanifest.xml 
 ```
    <provider
        android:name=".PartnerContentProvider"
        android:authorities="com.3rd.provider.content.cmd"
        android:exported="true">
        <meta-data
            android:name="com.nuwarobotics.api.meta.PARTNER_CONTENT_TABLE"
            android:value="nuwa_example_cmd_table" />
        <meta-data
            android:name="com.nuwarobotics.api.meta.array.PARTNER_CONTENT_VOICE_FORMAT"
            android:resource="@array/voice_cmd_format" />
    </provider>
 ```
 * Modify Config on PartnerProviderAPI.java
 ```
    //Modify 3rd AUTHORITY (MUST MODIFY)
    public static final String AUTHORITY = "com.3rd.provider.content.cmd";
 
    //Modify 3rd local command DB name
    public static final String DATABASE_NAME = "PartnerContentDB";
    
    //Modify 3rd local command table name
    public static final String TABLE_NAME = "nuwa_example_cmd_table";
 ```
 * Start Insert Local Command Data to DB
 ```
    //Insert 1st local_command data
    ContentValues mTestData1 = new ContentValues();
    //Optional : String, user customize locol_command
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_LOCAL_COMMAND, "我要設定聲音");
    //Mandatory : String, entity of this command
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_ENTITY, "設定聲音");
    //Mandatory : String, start type such as ACTIVITY, SERVICE, BROADCAST
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_START_TYPE, DataColumns.START_TYPE_ACTIVITY);

    //Example of convert Intent to Uri
        Intent testIntent = new Intent("com.nuwarobotics.settings.SHOW");
        testIntent.putExtra("page","VolumeCategory");
    //Mandatory : String, Full Intent uri to trigger 3rd function
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_URI, testIntent.toUri(Intent.URI_INTENT_SCHEME));
    //Optional : String, Intent target package(if target not your app)
        mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_PACKAGE, "com.nuwarobotics.settings");
        
    //Insert Data to DB
    Uri myuri = PartnerProviderAPI.insert(context,mTestData1);
 ```
