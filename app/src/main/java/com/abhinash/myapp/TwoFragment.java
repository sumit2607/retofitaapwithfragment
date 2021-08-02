package com.abhinash.myapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwoFragment extends Fragment {

    private CoordinatorLayout coordinatorLayout;
    StateAdapter stateAdapter;
    RecyclerView recyclerView;
    List<State> stateList = new ArrayList<>();
    private OnFragmentInteractionListener listener;
    public static TwoFragment newInstance() {
        return new TwoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        stateAdapter = new StateAdapter(stateList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(stateAdapter);
        getStateList();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void getStateList() {
        RetrofitInterface apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<List<State>> call = apiInterface.getStates();
        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {

                if (response==null){
                    Toast.makeText(getActivity(), "Somthing Went Wrong...!!", Toast.LENGTH_SHORT).show();
                }else{
                    for (State state:response.body()){
                        stateList.add(state);
                    }
                    //Log.i("RESPONSE: ", ""+response.toString());
                }
                stateAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }



    public interface OnFragmentInteractionListener {
    }
}