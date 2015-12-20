package com.cleannation.app.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleannation.app.MeetupData;
import com.cleannation.app.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.util.ArrayList;

/**
 * Created by varun on 17/12/15.
 */
public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyRecyclerViewAdapter2.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter2";
    private ArrayList<MeetupData> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;
        TextView location;


        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.meetName);
            dateTime = (TextView) itemView.findViewById(R.id.date);
            location = (TextView) itemView.findViewById(R.id.location);

            label.setText("");

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter2(ArrayList<MeetupData> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_meetup, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        //holder.label.setText(mDataset.get(position).getmText1());
        //holder.dateTime.setText(mDataset.get(position).getmText1());
        try {
            holder.label.setText(mDataset.get(position).getName());
        }catch (Exception e){
            //holder.label.setText("");
        }

        //Log.d("CleanNation",holder.label.getText().toString());
        try {
            holder.dateTime.setText(mDataset.get(position).getDate());
        }catch(Exception e){
            holder.dateTime.setText("");
        }

        try {
            holder.location.setText(mDataset.get(position).getLocation()+" - "+mDataset.get(position).getPin());
        }catch (Exception e){
            holder.location.setText("");
        }

        //holder.image=mDataset.get(position).getImage();
        //loadImage(holder.image,holder.img);



    }

    public void addItem(MeetupData dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    private void loadImage(ParseFile thumbnail, final ImageView img){
        Log.d("cleanNationThumb",thumbnail.toString());
        Log.d("cleanNationThumb",Boolean.toString(thumbnail.isDataAvailable()));
        //if(thumbnail.isDataAvailable()){
        try{
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp= BitmapFactory.decodeByteArray(data, 0, data.length);
                    img.setImageBitmap(bmp);
                }
            });
        }
        catch(Exception e){
            //else{
            img.setImageResource(R.drawable.wallpaper);
        }
    }

}
