package technicise.com.demoslidingdrawerapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by amiyo on 16/9/15.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener{


    public ListView listViewObjForBottom;
    public String[] firstName, providerNpiID, lat, longg, address;
    // Google Map
    public GoogleMap googleMap;

    Double providerLatitude = 0.0, providerLongitude = 0.0;
    String jsonResponse, url1;
    TextView TextViewObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



		/* Creating view corresponding to the fragment */
        View v = inflater.inflate(R.layout.fragment_layout2, container, false);

        FrameLayout frameLayoutObj=(FrameLayout)v.findViewById(R.id.background_view);
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
        frameLayoutObjBottom.addView(listViewObjForBottom);

        FrameLayout FrameHeaderLayout=(FrameLayout)v.findViewById(R.id.drawer_bar);
        TextViewObj = new TextView(getActivity());
        FrameHeaderLayout.addView(TextViewObj);

        return v;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    /* ****** Custom BaseAdapter ****** */
    public class SearchResultAdapter extends BaseAdapter
    {
        Context context;

        public SearchResultAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public int getCount()
        {
            return firstName.length;
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public int getViewTypeCount()
        {
            return getCount();
        }

        @Override
        public int getItemViewType(int position)
        {
            return position;
        }
        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(context);

            convertView = inflater.inflate(R.layout.community_common_tab_layout, null);

            TextView FirstName = (TextView) convertView.findViewById(R.id.from);
            TextView BusinessAddress = (TextView) convertView.findViewById(R.id.subject);

            FirstName.setText(firstName[position]+"");
            BusinessAddress.setText(address[position]+"");
            return convertView;
        }
    }


    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {

            // Get a handle to the Map Fragment
            googleMap = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            url1 ="http://webservice.mycuratio.com/webservice/code/index.php?/ProviderNew/getProviderByPartialNameZipDistance/smith/60601/5"; //400
            new ProviderSearchPareJSONdataAsyntask().execute();


            if(googleMap != null)
            {
                googleMap.setOnMarkerClickListener(this);
                googleMap.setOnInfoWindowClickListener(this);
            }
        }
    }


    public boolean onMarkerClick(Marker marker)
    {
        try
        {
            marker.showInfoWindow();
            String m = marker.getId();
            m = m.replace("m","");
            final int alfaValue =  Integer.valueOf(m);


            //auto-Scroll the ListView position
            listViewObjForBottom.smoothScrollToPositionFromTop(alfaValue, alfaValue);
            //programatically click on ListView row Item for corresponding marker Click
            listViewObjForBottom.performItemClick(listViewObjForBottom.getAdapter().getView(alfaValue, null, null), alfaValue, listViewObjForBottom.getItemIdAtPosition(alfaValue));

        }
        catch (Exception error)
        {

            error.printStackTrace();
        }
        return false;
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
                TextViewObj.setText("  " + jsonArray.length() + " Data found");

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

                    /*
                    *  Marker  Add
                    * */

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


            listViewObjForBottom.setAdapter(new SearchResultAdapter(getActivity()));
        }

    }
        @Override
        public void onResume ()
        {
            super.onResume();
             initilizeMap();
        }

 }

