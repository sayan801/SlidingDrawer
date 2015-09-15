package technicise.com.demoslidingdrawerapp;

import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class DemoActivity extends AppCompatActivity {

    ListAdapter MembersListAdapter;
    ArrayList<DataModel> DataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


        FrameLayout frameLayoutObj=(FrameLayout)findViewById(R.id.background_view);

        final ListView listViewObj = new ListView(this);

       /* // Defined Array values to show in ListView
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listViewObj.setAdapter(adapter);

        // ListView Item Click Listener
        listViewObj.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listViewObj.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });*/



        frameLayoutObj.addView(listViewObj);




        DataArray = new ArrayList<DataModel>();
        MembersListAdapter = new ListAdapter(getApplicationContext(), DataArray);
        listViewObj.setAdapter(MembersListAdapter);
        String Load_List_View_API ="http://webservice.mycuratio.com/webservice/code/index.php?/communities/getCommunityDetailsByType/community-member";


        TabHandler Json_Fetch = new TabHandler(getApplicationContext(), Load_List_View_API, DataArray) {


            @Override
            public void onReadyDataList() {
                MembersListAdapter.notifyData();
            }

        };

        Json_Fetch.fetchJSON(null);



        FrameLayout frameLayoutObjBottom=(FrameLayout)findViewById(R.id.drawer_content);
        final ListView listViewObjForBottom = new ListView(this);
        listViewObjForBottom.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        // Defined Array values to show in ListView
        String[] valuesForBottom = new String[] { "Amit Da",
                "Amiyo",
                "Amitabha Da",
                "Soumen Da",
                "Avik "

        };


        ArrayAdapter<String> adapterBottom = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, valuesForBottom);


        // Assign adapter to ListView
        listViewObjForBottom.setAdapter(adapterBottom);

        // ListView Item Click Listener
        listViewObjForBottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listViewObjForBottom.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });



        frameLayoutObjBottom.addView(listViewObjForBottom);

        /*
        *  Header Text
        * */

        FrameLayout FrameHeaderLayout=(FrameLayout)findViewById(R.id.drawer_bar);
        final TextView TextViewObj = new TextView(this);

        String  Count= String.valueOf(listViewObjForBottom.getAdapter().getCount());
        TextViewObj.setText("ListView Total Item is "+Count);
        TextViewObj.setGravity(View.TEXT_ALIGNMENT_CENTER);
        FrameHeaderLayout.addView(TextViewObj);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                String version = pInfo.versionName;
                int verCode = pInfo.versionCode;

                Toast.makeText(DemoActivity.this, "Version - " + version + ", Code - " + verCode, Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Toast.makeText(DemoActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
