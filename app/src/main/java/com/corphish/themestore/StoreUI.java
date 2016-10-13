package com.corphish.themestore;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.CardView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by Avinaba on 10/12/2016.
 */
public class StoreUI {

    // Context - on which context you would display themes
    Context context;

    // Layout - on which master layout you will add your child views
    LinearLayout layout;

    // Linearlayout Param - We define this so that we dont have to define it further.
    LinearLayout.LayoutParams params;

    // Constructor - Initialize storeUI with this constructor, passing context as arg
    public StoreUI(Context context) {
        this.context = context;
    }

    // After initialization, set the layout using this function
    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

    public void addToParent(View v) {
        layout.addView(v);
    }

    public CardView createCardView() {
        CardView cardView = new CardView(context);

        params.setMargins(Constants.CARDVIEW_MARGIN,Constants.CARDVIEW_MARGIN,Constants.CARDVIEW_MARGIN,Constants.CARDVIEW_MARGIN);
        cardView.setLayoutParams(params);

        cardView.setCardElevation(Constants.CARDVIEW_ELEVATION);

        return cardView;
    }

    public ImageView createImage() {
        ImageView imageView = new ImageView(context);

        imageView.setMaxWidth(Constants.IMAGEVIEW_DIMENSION);
        imageView.setMaxHeight(Constants.IMAGEVIEW_DIMENSION);

        return imageView;
    }

    public void syncImage(ImageView imageView, String url) {
        UrlImageViewHelper.setUrlDrawable(imageView, url);
    }

    public LinearLayout createLayout(int orientation) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(orientation);

        return linearLayout;
    }

    public void init() {
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
