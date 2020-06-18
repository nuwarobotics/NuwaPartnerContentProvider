# NuwaPartnerContentProvider
This is a example for globol scope local command.
It allow 3rd customize specific local commend of each Content.

It do not denpend on NuwaSDK version, but software version.
Please make sure Robot on 100JP MR2 later software.

# Compatible robot version
coming soon

# `Nuwa Website`
* NuwaRobotics Website (https://www.nuwarobotics.com/)
* NuwaRobotics Developer Website (https://dss.nuwarobotics.com/)

NOTICE : Please get NuwaSDK from Nuwarobotics Developer Website

# `SOP : How to use example`
 * 安裝支援全域LocalCommand的Patch(APK)
    + 原Nuwa SDK的LocalCommand僅作用在APP開啟並call API時，此功能用於提供第三方有一個媒介可以把必要資訊讓NUWA System可以讀到並設定。
    + 請先安裝附件SkillService APP (SkillService_2020-06-12_v0.0.1.0511fbe_debug.apk
    
 * Step 1 : 把範例中幾隻Code Copy到自己的APP中  (注意:有幾隻Code建議copy後不要動)
    + PartnerContentProvider.java (Copy, but DO NOT MODIFY)
    + PartnerContentProviderHelper.java (Copy, but DO NOT MODIFY)
    + DataColumns.java (Copy, but DO NOT MODIFY)
    + PartnerProviderAPI.java (Copy, 3rd need mofiy some config)
    + DebugReceiver.java (Optional, debug用，不一定要copy，裡面只有測試code)
    
 * Step 2 : 修改資料庫名稱資訊，請打開PartnerProviderAPI.java (並修改這三個變數)
 ```
   //Modify 3rd AUTHORITY (MUST MODIFY，請注意，這禮也要連同修改在AndroidManifest.xml)
   public static final String AUTHORITY = "com.3rd.provider.content.cmd";

   //Modify 3rd local command DB name
   public static final String DATABASE_NAME = "PartnerContentDB";
   
   //Modify 3rd local command table name (請注意，這禮也要連同修改在AndroidManifest.xml)
   public static final String TABLE_NAME = "nuwa_example_cmd_table";
 ```
 * Step 3 : Androidmanifest.xml宣告provider 
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
 * Step 4 : 3rd實現使用者可定義local command的UI
   + 這裡分兩部份，第一是讓使用者可輸入設定，第二是將local_command與執行指定簡報的Intent存至資料庫。
   + 第一部份，3rd實作設定UI與呼叫方式
        - 請3rd設計一下UI，原則上是一個無標點符號的純中文(或純英文字串)。
        - 請實作一個Intent可以把指定簡報呼叫起來的方式
   + 第二部份，寫入資料至Provider(範例如下)

 ```
   //Insert 1st local_command data
   ContentValues mTestData1 = new ContentValues();
   //Optional : String, 這是從UI上給使用者設定下來得字串  (另外，再資料庫中不可重複出現同樣的字串)
       mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_LOCAL_COMMAND, "我要設定聲音");
   //Mandatory : String, 關鍵字(進階用途，用來組合metadata的com.nuwarobotics.api.meta.array.PARTNER_CONTENT_VOICE_FORMAT字串)
       mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_ENTITY, "設定聲音");
   //Mandatory : String, 讓NUWA系統知道，要用何種方式啟動Intent.  (目前支援這三種 ACTIVITY, SERVICE, BROADCAST)
       mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_START_TYPE, DataColumns.START_TYPE_ACTIVITY);

   //Example of convert Intent to Uri, 這邊要組合出可以把3rd叫起來並且執行指定內容的Intent，把他轉為uri字串存起來
       Intent testIntent = new Intent("com.nuwarobotics.settings.SHOW");
       testIntent.putExtra("page","VolumeCategory");
   //Mandatory : String, Full Intent uri to trigger 3rd function (將上述Intent轉為uri存起來)
       mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_URI, testIntent.toUri(Intent.URI_INTENT_SCHEME));
   //Optional : String, Intent target package(if target not your app)
       mTestData1.put(DataColumns.PARTNER_CONTENT_CMD_COLUMN.COLUMN_INTENT_PACKAGE, "com.nuwarobotics.settings");
       
   //Insert Data to DB
   Uri myuri = PartnerProviderAPI.insert(context,mTestData1);  //這個小API是個範例，3rd可以再自己補上需要的db操作
   ```
# `Debug`
 * query local_command list on database by adb
   ```
   adb shell content query --uri content://com.3rd.provider.content.cmd/nuwa_example_cmd_table --projection local_command:entity:start_type:intent_uri:intent_uri --where "start_type=\'ACTIVITY\'"
   ```

