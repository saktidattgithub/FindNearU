package com.search.findnearu;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;


public class Setting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SeekBar mybar;
    Button save;int distance=0;
    private OnFragmentInteractionListener mListener;
    SharedPreferences location_preference;
    SharedPreferences.Editor editor_location_preference;
    int step = 1;
    int max = 20;
    int min = 2;
    TextView tv_setdistance;
    Activity activity;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Setting() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location_preference=getActivity().getSharedPreferences("location_pref",getActivity().MODE_WORLD_READABLE);
        editor_location_preference=location_preference.edit();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity=getActivity();
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        DiscreteSeekBar discreteSeekBar1 = (DiscreteSeekBar) view.findViewById(R.id.discrete1);
        mybar = (SeekBar) view.findViewById(R.id.seekBar1);
        tv_setdistance = (TextView) view.findViewById(R.id.id_text_setdistance);
if(location_preference!=null && location_preference.getString("distance_set","2")!=null)
{
    discreteSeekBar1.setProgress(Integer.valueOf(location_preference.getString("distance_set","2")));
    tv_setdistance.setText("Selected Distance :"+location_preference.getString("distance_set","2")+" km");
}else {
    discreteSeekBar1.setProgress(2);
    tv_setdistance.setText("Selected Distance :"+"2 km");

}
        save = (Button) view.findViewById(R.id.id_save);
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       /* editor_location_preference.putString("distance",String.valueOf(distance*1000));
                                        editor_location_preference.commit();*/
           Toast.makeText(getActivity(), "Distance saved for further use", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(getActivity(), MainActivity.class);
                                        startActivity(i);
                                    }
                                }
        );
        //--------------------

        mybar.setMax( (max - min) / step );




       /* mybar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getActivity(), "Selected distance "+String.valueOf(seekBar.getProgress()), Toast.LENGTH_SHORT).show();
                distance=seekBar.getProgress();
                editor_location_preference.putString("distance",String.valueOf(distance*1000));
                editor_location_preference.commit();
                //add here your implementation
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                //add here your implementation
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                double value = min + (progress * step);
                //add here your implementation
            }
        });
        */
        //--------------------

        discreteSeekBar1.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value ;
            }
        });

        discreteSeekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                Toast.makeText(getActivity(), "Selected distance "+String.valueOf(seekBar.getProgress()), Toast.LENGTH_SHORT).show();
                distance=seekBar.getProgress();
                editor_location_preference.putString("distance",String.valueOf(distance*1000));
                editor_location_preference.putString("distance_set",String.valueOf(distance));

                editor_location_preference.commit();
            }
        });

        //------------------------


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
      //  mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}
