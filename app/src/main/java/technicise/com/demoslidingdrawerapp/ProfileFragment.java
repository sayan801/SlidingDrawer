package technicise.com.demoslidingdrawerapp;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by amiyo on 16/9/15.
 */
public class ProfileFragment extends Fragment {

    ArrayList<DataModel> DataArray;
    ListAdapter ProfileListAdapter ;
    public ListView listViewObjForBottom;
    public String[] firstName, providerNpiID, lat, longg, address;

    // Google Map
    public GoogleMap googleMap;
    Double providerLatitude = 0.0, providerLongitude = 0.0;
    String jsonResponse, url1;

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

            url1 =
                    "http://webservice.mycuratio.com/webservice/code/index.php?/ProviderNew/getProviderByPartialNameZipDistance/smith/60601/5"; //400
            new ProviderSearchPareJSONdataAsyntask().execute();
/*
             googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(22.756919, 88.507255))
                    .title("Bamangachi")
                    .snippet("Population: 35k"));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(22.756919-0.03,  88.507255+0.001), 12));*/

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /** * Private Class for Parsing the JSON over the Internet.  */
    private class ProviderSearchPareJSONdataAsyntask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ServiceHandler serviceHandler = new ServiceHandler();

            // Making a request to url and getting the response
            jsonResponse = serviceHandler.makeServiceCall(url1, ServiceHandler.GET);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("npidata");

                //set Array Size;
                firstName = new String[jsonArray.length()];
                providerNpiID = new String[jsonArray.length()];
                lat = new String[jsonArray.length()];
                longg = new String[jsonArray.length()];
                address = new String[jsonArray.length()];

                //set counted search result
               // middle.setText("  " + jsonArray.length() + " Data found");

                JSONObject jsonObject1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        jsonObject1 = jsonArray.getJSONObject(i);

                        // getting  provider Name
                        if (jsonObject1.has("fullname")) {
                            firstName[i] = jsonObject1.getString("fullname");
                        } else {
                            firstName[i] = "Unknown Name";
                        }
                        if (jsonObject1.has("fulladdress")) {
                            address[i] = jsonObject1.getString("fulladdress");
                        } else {
                            address[i] = "Unknown Address";
                        }

                        // for Lat Common code for all like NAME & ALSO HOSPITAL
                        if (jsonObject1.has("latitude")) {
                            if (String.valueOf(jsonObject1.getDouble("latitude")).equals("Unknown") ||
                                    String.valueOf(jsonObject1.getDouble("latitude")).equals("OVER_QUERY_LIMIT")) {
                                lat[i] = String.valueOf(0.0);
                            } else {
                                lat[i] = String.valueOf(jsonObject1.getDouble("latitude"));
                            }
                        } else {
                            lat[i] = String.valueOf(0.0);
                        }
                        // for Long
                        if (jsonObject1.has("longitude")) {
                            if (String.valueOf(jsonObject1.getDouble("longitude")).equals("Unknown") ||
                                    String.valueOf(jsonObject1.getDouble("longitude")).equals("OVER_QUERY_LIMIT")) {
                                longg[i] = String.valueOf(0.0);
                            } else {
                                longg[i] = String.valueOf(jsonObject1.getDouble("longitude"));
                            }
                        } else {
                            longg[i] = String.valueOf(0.0);
                        }
                    } catch (Exception e) {
                        Log.d("CRASH 141 ", e.toString());
                    }

                    providerLatitude = Double.parseDouble(lat[i]);
                    providerLongitude = Double.parseDouble(longg[i]);
                    if (googleMap != null) {
                        googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(providerLatitude, providerLongitude))
                                        .title(firstName[i])
                                        .snippet(address[i])
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerblack)));
                        
                    }


                }

                if (googleMap != null) {
                    //1st marker as Center position
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(Double.parseDouble(lat[0]), Double.parseDouble(longg[0])), 9));
                }

            } catch (Exception e) {
                Log.d("CRASH 161 ", e.toString());
            }
        }

    }
           @Override
            public void onResume () {
                super.onResume();
                initilizeMap();
            }

        }

