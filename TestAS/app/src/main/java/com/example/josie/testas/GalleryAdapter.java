package com.example.josie.testas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Josie on 2015/12/14.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<TimeModel> listdata;
    private ViewHolder viewHolder;
    private OnItemClickLitener onItemClickLitener;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public GalleryAdapter(Context context, List<TimeModel> list){
        inflater = LayoutInflater.from(context);
        listdata = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listview_item, parent, false);
        viewHolder = new ViewHolder(view);

        viewHolder.iconImageview = (ImageView)view.findViewById(R.id.item_iconigview);
//        viewHolder.meImageview = (ImageView)view.findViewById(R.id.item_igview);
        viewHolder.nameTextview = (TextView)view.findViewById(R.id.item_name_tv);
        viewHolder.contentTextview = (TextView)view.findViewById(R.id.item_content_tv);
        viewHolder.timeTextview = (TextView)view.findViewById(R.id.item_time_tv);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder.iconImageview.setImageResource(R.drawable.dongtai_yuandian);
//        viewHolder.meImageview.setImageResource(listdata.get(position).getMeimageView());
        Log.e("position", Integer.toString(position));
        Log.e("listitem", listdata.get(position).getContenttextview().toString());
        viewHolder.nameTextview.setText(listdata.get(position).getNametextview());
        viewHolder.contentTextview.setText(listdata.get(position).getContenttextview());
        viewHolder.timeTextview.setText(listdata.get(position).getTimetextview());

        if (onItemClickLitener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(viewHolder.itemView, position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public ImageView iconImageview;
//        public ImageView meImageview;
        public TextView nameTextview;
        public TextView contentTextview;
        public TextView timeTextview;
    }
}
