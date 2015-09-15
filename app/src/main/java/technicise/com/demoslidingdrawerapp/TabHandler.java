package technicise.com.demoslidingdrawerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public abstract class TabHandler {

private ArrayList<DataModel> dataModelArrayList;
private String jsonURI = null;
private Context context;


        /**
         * Public constructor of Reminders_JSON_Handler.
         * @param url
         */
        public TabHandler(Context context, String url, ArrayList<DataModel> arrayList){
            this.jsonURI = url;
            this.context = context;
            this.dataModelArrayList = arrayList;

        }
  /*  public int Member_Tab_Icon[]=
            {
                    R.drawable.maleicon,
                    R.drawable.femaleicon

            };*/

        /**
         * Callback for data list reading.
         */
        public abstract void onReadyDataList();


        /**
         * Get the Reminders_Data_Model array list.
         * @return
         */
        public ArrayList<DataModel> getDataModelArrayList(){
            return  this.dataModelArrayList;
        }

        /**
         * Read and Parse the JSON Object.
         * @param jsonText
         */
        @SuppressLint("NewApi")
        public void readAndParseJSON(String jsonText) {
            try {
                JSONObject reader = new JSONObject(jsonText);
                JSONArray jsonArray = reader.getJSONArray("community_details");
                int loopImage=0;


                for (int index = 0; index < jsonArray.length(); index ++)
                {
                    JSONObject country = jsonArray.getJSONObject(index);

                    DataModel model = new DataModel();

                    model.Member_Date = country.getString("line1");
                    model.Member_Title = country.getString("line2");
                    model.Member_Details = country.getString("line3");
                   // model.Member_Icons = String.valueOf(Member_Tab_Icon[loopImage]);

                    loopImage++;
                    if(loopImage==2)
                    {

                        loopImage=0;
                    }

                    if( dataModelArrayList != null )
                        if ( model != null )
                            dataModelArrayList.add(model);
                }
            } catch (Exception error) {
                error.printStackTrace();
            }

        }
        /**
         * Fetch the JSON object data.. after finish the fetching read the data.
         */
        public void fetchJSON(final String loadingText){
            new AsyncTask<Void, Void, Void>(){

                @Override
                protected  void onPreExecute(){

                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        URL url = new URL(jsonURI);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setReadTimeout(10000 /* milliseconds */);
                        connection.setConnectTimeout(15000 /* milliseconds */);
                        connection.setRequestMethod("GET");
                        connection.setDoInput(true);
                        // Starts the query
                        connection.connect();
                        InputStream stream = connection.getInputStream();

                        String data = convertStreamToString(stream);

                        readAndParseJSON(data);
                        stream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void result){
                    super.onPostExecute(result);

                    if( dataModelArrayList != null )
                    {

                    }
                    onReadyDataList();
                }

            }.execute();

        }


        /**
         * Convert InputStream object into a String object.
         *
         * @param inputStream
         * @return
         */
        private static String convertStreamToString(InputStream inputStream) {
            java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

}

