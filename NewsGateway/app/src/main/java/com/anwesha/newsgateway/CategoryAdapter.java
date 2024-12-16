package com.anwesha.newsgateway;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import com.anwesha.newsgateway.databinding.ViewpagerBinding;
import com.squareup.picasso.Picasso;

    public class CategoryAdapter extends
            RecyclerView.Adapter<ViewHolder> {

        private final MainActivity mainActivity;
        private final ArrayList<Article> countryList;

        public CategoryAdapter(MainActivity mainActivity, ArrayList<Article> countryList) {
            this.mainActivity = mainActivity;
            this.countryList = countryList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext()).
                            inflate(R.layout.viewpager, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


                Article c = countryList.get(position);
                holder.articleText.setText(c.getDescription());
                holder.articleAuthors.setText(c.getAuthor());
                holder.articleDate.setText(c.getPublishedat());
                holder.articleHeadline.setText(c.getTitle());
//        holder.articleImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                if (c.getUrltoimage() != null)
                    Picasso.get().load(c.getUrltoimage()).error(R.drawable.brokenimage)
                            .placeholder(R.drawable.loading).into(holder.articleImage);
                else
                    Picasso.get().load(c.getUrltoimage()).error(R.drawable.noimage)
                            .placeholder(R.drawable.loading).into(holder.articleImage);
//        holder.articleImage.setImageResource(c.getUrltoimage());
                holder.articleImage.setOnClickListener(v -> clickFlag(c.getUrl()));
                holder.articleText.setOnClickListener(v -> clickFlag(c.getUrl()));
                holder.articleHeadline.setOnClickListener(v -> clickFlag(c.getUrl()));
                if (countryList.size() >= 10) {
                    if(position<10)
                    holder.pageNumber.setText(String.format(
                            Locale.getDefault(), "%d of %d", (position + 1), 10));
                    else{

                    }
                }
                else
                    holder.pageNumber.setText(String.format(
                            Locale.getDefault(), "%d of %d", (position + 1), countryList.size()));
            }


        @Override
        public int getItemCount() {
            return countryList.size();
        }

        private void clickFlag(String name) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
            mainActivity.startActivity(intent);
        }
    }
