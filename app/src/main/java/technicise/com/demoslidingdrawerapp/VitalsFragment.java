package technicise.com.demoslidingdrawerapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import technicise.com.slidingdrawer3statelib.SharedPreferenceClass;

/**
 * Created by amiyo on 18/9/15.
 */
public class VitalsFragment extends Fragment {

    SharedPreferenceClass sharedPrefClassObj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        /* Creating view corresponding to the fragment */
        View v = inflater.inflate(R.layout.fragment_layout_vitals, container, false);

        FrameLayout frameLayoutObj=(FrameLayout)v.findViewById(R.id.background_view);
        LayoutInflater layoutInflater= (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        frameLayoutObj.addView(layoutInflater.inflate(R.layout.chart_graph_common_xml, null));
        sharedPrefClassObj = new SharedPreferenceClass(getActivity());
        sharedPrefClassObj.setKeySetSlidingState("SliderStateTwo");
        return v;
    }

}