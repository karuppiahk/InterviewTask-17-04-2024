package kk.projects.interviewtask.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kk.projects.interviewtask.R;
import kk.projects.interviewtask.model.task_model;


public class task_adapter extends RecyclerView.Adapter<task_adapter.ViewHolder> {

    private Context mContext;


    private List<task_model.ResponseItem> listdataList;


    private List<task_model.ResponseItem> listdataFiltered;


    // RecyclerView recyclerView;
    public task_adapter(Context mContext, List<task_model.ResponseItem> listdata) {
        this.listdataList = listdata;
        this.listdataFiltered = listdata;
        notifyItemChanged(0, listdataFiltered.size());
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.task_visit, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;


    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.pdtname.setText(listdataFiltered.get(position).getTitle());
        holder.id.setText(listdataFiltered.get(position).getId());


        holder.pdtCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.gallery_full_img);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageView close_img = (ImageView) dialog.findViewById(R.id.close_img);
                TextView id = (TextView) dialog.findViewById(R.id.id);
                TextView uid = (TextView) dialog.findViewById(R.id.uid);
                TextView title = (TextView) dialog.findViewById(R.id.title);
                TextView disck = (TextView) dialog.findViewById(R.id.disck);

                id.setText(listdataFiltered.get(position).getId());
                uid.setText(listdataFiltered.get(position).getUserId());
                title.setText(listdataFiltered.get(position).getTitle());
                disck.setText(listdataFiltered.get(position).getBody());


                close_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });


    }


    @Override
    public int getItemCount() {
        return listdataFiltered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView pdtname, id;
        public CardView pdtCard;

        public ViewHolder(View itemView) {
            super(itemView);

            this.id = itemView.findViewById(R.id.id);
            this.pdtname = itemView.findViewById(R.id.pdtname);
            this.pdtCard = itemView.findViewById(R.id.pdtCard);


        }


    }

    //..also create a method which will hide the dialog when some work is done

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listdataFiltered = listdataList;
                } else {
                    List<task_model.ResponseItem> filteredList = new ArrayList<>();
                    for (task_model.ResponseItem movie : listdataList) {
                        if (movie.getId().toLowerCase().contains(charString.toLowerCase()) || movie.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    listdataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listdataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listdataFiltered = (ArrayList<task_model.ResponseItem>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }


}