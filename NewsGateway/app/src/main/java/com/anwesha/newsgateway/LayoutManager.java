package com.anwesha.newsgateway;


import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



    public class LayoutManager implements Serializable {

        private int source;
        private int article;
        private ArrayList<String> categories = new ArrayList<>();
        private ArrayList<Source> sources = new ArrayList<>();
        private ArrayList<Article> articles = new ArrayList<>();

        public void setSource(int source) {
            this.source = source;
        }

        public void setArticle(int article) {
            this.article = article;
        }

        public ArrayList<String> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<String> categories) {
            this.categories = categories;
        }

        public ArrayList<Source> getSources() {
            return sources;
        }

        public void setSources(ArrayList<Source> sources) {
            this.sources = sources;
        }

        public ArrayList<Article> getArticles() {
            return articles;
        }

        public void setArticles(ArrayList<Article> articles) {
            this.articles = articles;
        }
    }