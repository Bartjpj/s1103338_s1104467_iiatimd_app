package com.example.s1103338_s1104467_iiatimd_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    private Context mContext;
    private ArrayList<RecipeItem> mRecipeList;

    private OnClickListener mOnClickListener;

    public void setOnRecipeClickListener(OnClickListener listener){
        mOnClickListener = listener;
    }

    public RecipeAdapter(Context context, ArrayList<RecipeItem> recipeList){
        mContext = context;
        mRecipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_card, parent,false);
        return new RecipeViewHolder(v, mOnClickListener);

}
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeItem currentItem = mRecipeList.get(position);
        String imageURL = currentItem.getmImageURL();
        String recipeTitel = currentItem.getmReceptTitel();

        holder.mTextViewRecipe.setText(recipeTitel);
        //picasso gebruiken om image op te halen
        Picasso.with(mContext).load(imageURL).fit().centerInside().into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewRecipe;

        OnClickListener onClickListener;


        public RecipeViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewRecipe = itemView.findViewById(R.id.textView_recipe);
            this.onClickListener = onClickListener;

//            itemView.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mOnClickListener.onRecipeClick(position);
                        }
                    }
                }
            });
        }

//        @Override
//        public void onClick(View v) {
//            onClickListener.onRecipeClick(getAdapterPosition());
//        }
    }

    public interface OnClickListener{
        void onRecipeClick(int position);
    }
}

