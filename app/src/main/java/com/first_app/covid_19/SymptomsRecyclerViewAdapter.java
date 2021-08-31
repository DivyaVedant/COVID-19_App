package com.first_app.covid_19;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SymptomsRecyclerViewAdapter extends RecyclerView.Adapter<SymptomsRecyclerViewAdapter.ViewHolder> {

    //For Log.d use
    private static final String TAG = "SymptomsRecyclerViewAdapter";

    private ArrayList<String> mText = new ArrayList<>();
    private ArrayList<String> mImageUrl = new ArrayList<>();
    private Context context;

    public SymptomsRecyclerViewAdapter(Context context, ArrayList<String> mText, ArrayList<String> mImageUrl) {
        this.context = context;
        this.mText = mText;
        this.mImageUrl = mImageUrl;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView Symptoms;
        ImageView mImageViewSymptoms;
        TextView mTxtSymptoms;

        public ViewHolder(View itemView) {
            super(itemView);
            Symptoms = itemView.findViewById(R.id.Symptoms);
            mImageViewSymptoms = (ImageView) itemView.findViewById(R.id.imageViewSymptoms);
            mTxtSymptoms = (TextView) itemView.findViewById(R.id.txtSymptoms);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_symptoms, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(context)
                .asBitmap()
                .load(mImageUrl.get(position))
                .into(holder.mImageViewSymptoms);

        holder.mTxtSymptoms.setText(mText.get(position));

        final String text = (String) holder.mTxtSymptoms.getText();

        holder.Symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrl.size();
    }
}
