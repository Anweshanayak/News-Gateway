package com.anwesha.newsgateway;

import android.text.SpannableString;

import java.io.Serializable;

public class Source implements Serializable, Comparable<Source> {
    private String sourceid;
    private String sourcename;
    private String sourceurl;
    private String sourcecategory;
    private transient SpannableString coloredName;

    public SpannableString getColoredName() {
        return coloredName;
    }

    public void setColoredName(SpannableString coloredName) {
        this.coloredName = coloredName;
    }

    public Source() {
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getSourcecategory() {
        return sourcecategory;
    }

    public void setSourcecategory(String sourcecategory) {
        this.sourcecategory = sourcecategory;
    }

    public int compareTo(Source s) {
        return sourcename.compareTo(s.sourcename);
    }
}
