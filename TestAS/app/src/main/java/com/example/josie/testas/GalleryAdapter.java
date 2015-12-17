package com.example.josie.testas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
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
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
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
        if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, null);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            itemViewHolder.iconImageview = (ImageView)view.findViewById(R.id.item_iconigview);
            itemViewHolder.nameTextview = (TextView)view.findViewById(R.id.item_name_tv);
            itemViewHolder.contentTextview = (TextView)view.findViewById(R.id.item_content_tv);
            itemViewHolder.timeTextview = (TextView)view.findViewById(R.id.item_time_tv);
            return itemViewHolder;
        }
      else if (viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, null);
            FooterViewHolder footerViewHolder = new FooterViewHolder(view);
//            footerViewHolder.footer_Textview = (TextView)view.findViewById(R.id.footer_tv);
            footerViewHolder.metaballView = (MetaballView)view.findViewById(R.id.load_metaball);
//            if (footerViewHolder.timeTextview == null){
//                Log.e("footer", String.valueOf(footerViewHolder.timeTextview));
//            }
            return footerViewHolder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            Log.e("error" , String.valueOf(((ItemViewHolder) holder).contentTextview));
            holder.iconImageview.setImageResource(R.drawable.dongtai_yuandian);
//        viewHolder.meImageview.setImageResource(listdata.get(position).getMeimageView());
            holder.nameTextview.setText(listdata.get(position).getNametextview());
            holder.contentTextview.setText(listdata.get(position).getContenttextview());
            holder.timeTextview.setText(listdata.get(position).getTimetextview());

//            if (onItemClickLitener != null) {
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onItemClickLitener.onItemClick(((ItemViewHolder) holder).itemView, position);
//                    }
//                });
//            }
        }
        else if (holder instanceof FooterViewHolder){
            holder.metaballView.setPaintMode(0);
        }

    }

//    private ViewHolder FooterViewHolder(View view) {
//        viewHolder = new ViewHolder(view);
//        viewHolder.footer_Textview = (TextView)view.findViewById(R.id.footer_tv);
//        return viewHolder;
//    }

//    private ViewHolder ItemViewHolder(View view) {
////        View view = inflater.inflate(R.layout.listview_item, parent, false);
//        viewHolder = new ViewHolder(view);
//
//        viewHolder.iconImageview = (ImageView)view.findViewById(R.id.item_iconigview);
////        viewHolder.meImageview = (ImageView)view.findViewById(R.id.item_igview);
//        viewHolder.nameTextview = (TextView)view.findViewById(R.id.item_name_tv);
//        viewHolder.contentTextview = (TextView)view.findViewById(R.id.item_content_tv);
//        viewHolder.timeTextview = (TextView)view.findViewById(R.id.item_time_tv);
//
//        return viewHolder;
//    }

//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        if (holder instanceof ItemViewHolder) {
//            ((ItemViewHolder) holder).iconImageview.setImageResource(R.drawable.dongtai_yuandian);
////        viewHolder.meImageview.setImageResource(listdata.get(position).getMeimageView());
//            Log.e("position", Integer.toString(position));
//            Log.e("listitem", listdata.get(position).getContenttextview().toString());
//            ((ItemViewHolder) holder).nameTextview.setText(listdata.get(position).getNametextview());
//            ((ItemViewHolder) holder).contentTextview.setText(listdata.get(position).getContenttextview());
//            ((ItemViewHolder) holder).timeTextview.setText(listdata.get(position).getTimetextview());
//
//            if (onItemClickLitener != null) {
//                ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onItemClickLitener.onItemClick(holder.itemView, position);
//                    }
//                });
//            }
//        }
//        else if (holder instanceof FooterViewHolder){
//            ((FooterViewHolder) holder).footer_Textview.setText("加载中....");
//        }
//    }


    //这里设置为+1主要是为了footer
    @Override
    public int getItemCount() {
        return listdata.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public int i = 0;
        public ImageView iconImageview;
        public TextView nameTextview;
        public TextView contentTextview;
        public TextView timeTextview;
//        public TextView footer_Textview;
        public MetaballView metaballView;
    }

    private static class ItemViewHolder extends ViewHolder {
        public ItemViewHolder(View view) {
            super(view);
        }
    }

    private static class FooterViewHolder extends ViewHolder {
//        public TextView footer_Textview;
        public FooterViewHolder(View view) {
            super(view);
        }
    }
}
