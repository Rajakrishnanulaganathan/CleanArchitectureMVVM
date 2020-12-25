package com.rk.cleanarchitecture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder> {

    private Context mContext;
    protected List<MovieEntity> mPopularMovieList;
    private ClickListener clickListner;

    public PopularMovieAdapter(Context context) {
        this.mContext = context;


    }

    public void setList( List<MovieEntity> articleArrayList){
        this.mPopularMovieList=articleArrayList;
    }


    @NonNull
    @Override
    public PopularMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularMovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMovieViewHolder holder, int position) {
       Picasso.get().load(Constants.IMAGE_ENDPOINT_PREFIX+mPopularMovieList.get(position).getPosterPath()).into(holder.mPopularMovieImage);
    }

    @Override
    public int getItemCount() {
        return mPopularMovieList.size();
    }

    public void setonclicklistner(ClickListener clickListner) {
        this.clickListner=clickListner;
    }

    class PopularMovieViewHolder extends RecyclerView.ViewHolder {
        TextView mPopularMovieTitle;
        ImageView mPopularMovieImage;
        PopularMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mPopularMovieImage=itemView.findViewById(R.id.popular_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickListner.Onclick(getAdapterPosition());

                }
            });
        }
    }
}
