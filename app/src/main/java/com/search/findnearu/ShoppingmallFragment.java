package com.search.findnearu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.search.findnearu.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ShoppingmallFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyProgressDialog progress_dialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<DataModelClass> list_data;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences location_preference;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static TravelFragment newInstance(String param1, String param2) {
        TravelFragment fragment = new TravelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingmallFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
       /* mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        location_preference= getActivity().getSharedPreferences("location_pref",getActivity().MODE_WORLD_READABLE);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        if( new Utility(getActivity()).isNetworkAvailable()) {
            progress_dialog=new MyProgressDialog(getActivity());
            progress_dialog.start_dialog();
            Thread background = new Thread() {
                public void run() {
                    list_data = getFinalData(getDatafromServer());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list_data != null && list_data.size() > 0) {
                                progress_dialog.stop_dialog();
                                mAdapter = new ListAdapter(getActivity(), list_data);
                                mListView.setAdapter(mAdapter);
                            } else {
                                progress_dialog.stop_dialog();

                                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            };
            background.start();
        }else{
            new Utility(getActivity()).DataConnectionAlert();
        }
        //((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getActivity().startActivity(new Intent(getActivity(),MapActivity.class).putExtra("title",list_data.get(position).getName()).putExtra("lat",Double.valueOf(list_data.get(position).getLatitute())).putExtra("lang",Double.valueOf(list_data.get(position).getLongitute())));

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);
    }

    public ArrayList<DataModelClass> getFinalData (String response)
    {
        ArrayList<DataModelClass> list_data=new ArrayList<DataModelClass>();

        try
        {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            if(jsonArray!=null && jsonArray.length()>0)
            {
                for (int i=0;i<jsonArray.length();i++)
                {
                    DataModelClass data=new DataModelClass();
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    JSONObject jsonObject2=jsonObject1.getJSONObject("geometry");
                    JSONObject jsonObject3=jsonObject2.getJSONObject("location");
                    String latitude=jsonObject3.getString("lat");
                    String longitude=jsonObject3.getString("lng");
                    data.setLatitute(latitude);
                    data.setLongitute(longitude);
                    data.setName(jsonObject1.getString("name"));
                    data.setAddress(jsonObject1.getString("vicinity"));
                    // data.setRating(jsonObject1.getString("rating"));
                    list_data.add(data);
                }

            }
        }catch (Exception e)
        {

        }


        return list_data;
    }


    String getDatafromServer()
    {
        String response = null;
        try {
            String Location_URL_Medical="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+location_preference.getString("lat","")+","+location_preference.getString("lang","")+"&radius="+location_preference.getString("distance","1000")+"&types=shopping_mall|grocery_or_supermarket|home_goods_store&key="+Utility.SERVER_KEY;


            URL url = new URL(Location_URL_Medical);
            URLConnection connection=url.openConnection();
            HttpURLConnection httpURLConnection=(HttpURLConnection)connection;
            InputStreamReader inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String temp;
            String tempResponse="";
            while ((temp=bufferedReader.readLine())!=null)
            {
                tempResponse=tempResponse+temp;
            }
            response=tempResponse;

        }catch (Exception e)
        {

        }
        return  response;
    }

    public class ListAdapter extends BaseAdapter
    {
        Context _context;
        ArrayList<DataModelClass> _list_data_model;
        public ListAdapter(Context context,ArrayList<DataModelClass> list_data_model)
        {
            _list_data_model=list_data_model;
            _context=context;
        }

        @Override
        public int getCount() {
            return _list_data_model.size();
        }

        @Override
        public Object getItem(int position) {
            return _list_data_model.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.food_item_list, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.name);
            textView.setText(_list_data_model.get(position).getName());
            TextView textView_address = (TextView) rowView.findViewById(R.id.address);
            textView_address.setText(_list_data_model.get(position).getAddress());
            ImageView image=(ImageView)rowView.findViewById(R.id.image);
            image.setImageResource(R.drawable.shopping);
            return rowView;
        }
    }

}
