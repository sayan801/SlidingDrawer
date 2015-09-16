package technicise.com.demoslidingdrawerapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by amiyo on 16/9/15.
 */
public class ProfileFragment extends Fragment {

    ArrayList<DataModel> DataArray;
    ListAdapter ProfileListAdapter ;
    public ListView listViewObjForBottom;

    // Google Map
    public GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



		/* Creating view corresponding to the fragment */
        View v = inflater.inflate(R.layout.fragment_layout2, container, false);

        FrameLayout frameLayoutObj=(FrameLayout)v.findViewById(R.id.background_view);

       // final ListView listViewObj = new ListView(getActivity());

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
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
                Toast.makeText(getActivity(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });*/

        LayoutInflater layoutInflater= (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayoutObj.addView(layoutInflater.inflate(R.layout.map_view, null));

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        // Showing status
        if(status!= ConnectionResult.SUCCESS)

        { // Google Play Services are not available
        // Showing Toast

        }
        else {
            try {
                // Loading map
                initilizeMap();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        listViewObjForBottom = new ListView(getActivity());
        FrameLayout frameLayoutObjBottom=(FrameLayout)v.findViewById(R.id.drawer_content);
        DataArray = new ArrayList<DataModel>();
        ProfileListAdapter = new ListAdapter(getActivity(), DataArray);
        listViewObjForBottom.setAdapter(ProfileListAdapter);
        String Load_List_View_API ="http://webservice.mycuratio.com/webservice/code/index.php?/communities/getCommunityDetailsByType/community-member";


        TabHandler Json_Fetch = new TabHandler(getActivity(), Load_List_View_API, DataArray) {


            @Override
            public void onReadyDataList() {
                ProfileListAdapter.notifyData();
            }

        };

        Json_Fetch.fetchJSON(null);

        frameLayoutObjBottom.addView(listViewObjForBottom);

        FrameLayout FrameHeaderLayout=(FrameLayout)v.findViewById(R.id.drawer_bar);
        final TextView TextViewObj = new TextView(getActivity());

        String  Count= String.valueOf(listViewObjForBottom.getAdapter().getCount());
        TextViewObj.setText("ListView Total Item is "+Count);
        TextViewObj.setGravity(View.TEXT_ALIGNMENT_CENTER);
        FrameHeaderLayout.addView(TextViewObj);

        return v;
    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {

            // Get a handle to the Map Fragment
            googleMap = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();


             googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(22.756919, 88.507255))
                    .title("Bamangachi")
                    .snippet("Population: 35k"));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(22.756919-0.03,  88.507255+0.001), 12));

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        initilizeMap();
    }

  }
