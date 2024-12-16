package com.anwesha.newsgateway;

import android.nfc.Tag;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anwesha.newsgateway.databinding.ViewpagerBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
        TextView articleHeadline;
        TextView articleDate;
        TextView articleAuthors;
        TextView articleText;
        TextView pageNumber;
        ImageView articleImage;

        public ViewHolder(@NonNull View itemView) {
                super(itemView);
                articleHeadline = itemView.findViewById(R.id.articleHeadline);
                articleDate = itemView.findViewById(R.id.articleDate);
                articleAuthors = itemView.findViewById(R.id.articleAuthors);
                articleText = itemView.findViewById(R.id.articleText);
                articleImage = itemView.findViewById(R.id.articleImage);
                pageNumber = itemView.findViewById(R.id.pageNumber);
        }
}