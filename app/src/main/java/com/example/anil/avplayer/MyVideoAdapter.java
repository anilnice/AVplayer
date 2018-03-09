package com.example.anil.avplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Anil on 2/27/2018.
 */

class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.Viewholders> {
    ArrayList<String> list,image;
    Context ct;
    public MyVideoAdapter(MainActivity mainActivity, ArrayList<String> list) {
        this.ct=mainActivity;
        this.list=list;
    }

    @Override
    public Viewholders onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Viewholders(LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false));
    }

    @Override
    public void onBindViewHolder(final Viewholders holder, final int position) {
        Bitmap bm = ThumbnailUtils.createVideoThumbnail(list.get(position), MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        String path = MediaStore.Images.Media.insertImage(ct.getContentResolver(), bm, "Title", null);
        Picasso.with(ct)
                .load(path)
                .into(holder.vv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholders extends RecyclerView.ViewHolder{
        ImageView vv;
        public Viewholders(View itemView) {
            super(itemView);
            vv=(ImageView) itemView.findViewById(R.id.video);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent i=new Intent(ct, VideoPlayer.class);
                    i.putExtra("position",list.get(position));
                    ct.startActivity(i);
                }
            });
        }

    }
}
