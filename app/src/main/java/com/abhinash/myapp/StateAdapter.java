package com.abhinash.myapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.bumptech.glide.request.RequestOptions;
import com.abhinash.myapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

    List<State> stateList;
    Context context;


    public StateAdapter(List<State> stateList, Context context) {
        this.stateList = stateList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        State state = stateList.get(position);
        final int stateId = state.getId();
        //holder.id.setText(state.getId());
        holder.name.setText(state.getName());
        Glide.with(context)
                .load(state.getImage())
                .apply(new RequestOptions()
                        .fitCenter())
                //.centerCrop()
                .into(holder.imageView);
        holder.description.setText(state.getDescription());
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTheState(stateId);
                stateList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, stateList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView id;
        public TextView name;
        public ImageView imageView;
        public TextView description;
        public ImageButton btnDel;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
           // id = view.findViewById(R.id.id);
            imageView = view.findViewById(R.id.imageView);
            btnDel = view.findViewById(R.id.btnDel);
        }
    }

    public void deleteTheState(int id){
        RetrofitInterface apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<State> call = apiInterface.deleteState(id);
        call.enqueue(new Callback<State>() {
            @Override
            public void onResponse(Call<State> call, Response<State> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(???, "State deleted successfully!", Toast.LENGTH_SHORT).show();
                }
                //notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<State> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


}
