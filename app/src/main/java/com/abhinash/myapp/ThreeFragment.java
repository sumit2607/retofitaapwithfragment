package com.abhinash.myapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.abhinash.myapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreeFragment extends Fragment {

    TextView txtStatename;
    TextView txtImgurl;
    TextView txtStateDesc;
    TextView txtStateId;
    TextView edtStateId;
    EditText edtStatename;
    EditText edtImgurl;
    EditText edtStateDesc;
    Button btnSave;

    private OnFragmentInteractionListener listener;

    public static ThreeFragment newInstance() {
        return new ThreeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        txtStateId = v.findViewById(R.id.txtStateId);
        edtStateId = v.findViewById(R.id.edtStateId);
        txtStatename = v.findViewById(R.id.txtStatename);
        edtStatename = v.findViewById(R.id.edtStatename);
        txtImgurl = v.findViewById(R.id.txtImgurl);
        edtImgurl = v.findViewById(R.id.edtImgurl);
        txtStateDesc = v.findViewById(R.id.txtStateDesc);
        edtStateDesc = v.findViewById(R.id.edtStateDesc);
        btnSave = v.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State s = new State();
                int i = Integer.parseInt(edtStateId.getText().toString());
                s.setId(i);
                s.setName(edtStatename.getText().toString());
                s.setImage(edtImgurl.getText().toString());
                s.setDescription(edtStateDesc.getText().toString());
                addState(s);
            }
        });

        return v;
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

    public void addState(State s) {
        RetrofitInterface apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<State> call = apiInterface.addState(s);
        call.enqueue(new Callback<State>() {
            @Override
            public void onResponse(Call<State> call, Response<State> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "State added successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<State> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public interface OnFragmentInteractionListener {
    }
}