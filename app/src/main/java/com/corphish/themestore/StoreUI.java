package com.corphish.themestore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

/**
 * Created by Avinaba on 10/12/2016.
 */
public class StoreUI {

    // Context - on which context you would display themes
    Context context;

    // Layout - on which master layout you will add your child views
    LinearLayout layout = null;

    // Linearlayout Param - We define this so that we dont have to define it further.
    LinearLayout.LayoutParams params;

    // Themes ArrayList - The list of themes will be fetched from server as json and parsed as Themes'Arraylist, which will be processed here.
    ArrayList<Theme> themeArrayList = null;

    // Constructor - Initialize storeUI with this constructor, passing context as arg
    public StoreUI(Context context) {
        this.context = context;
    }

    // After initialization, set the layout using this function
    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

    public void setThemeArrayList(ArrayList<Theme> themeArrayList) {
        this.themeArrayList = themeArrayList;
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

        imageView.setAdjustViewBounds(true);

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

    public TextView createTextView(String string) {
        TextView textView = new TextView(context);
        textView.setTypeface(null, Typeface.BOLD);

        textView.setPadding(Constants.TEXTVIEW_PADDING,Constants.TEXTVIEW_PADDING,Constants.TEXTVIEW_PADDING,Constants.TEXTVIEW_PADDING);

        textView.setText(string);

        return textView;
    }

    public void createThemeCard(Theme theme) {
        CardView cardView = createCardView();

        LinearLayout horizontalLayout = createLayout(LinearLayout.HORIZONTAL);

        ImageView imageView = createImage();
        syncImage(imageView,theme.getThumbnailUrl());

        TextView textView = createTextView(theme.getName());

        horizontalLayout.addView(imageView);
        horizontalLayout.addView(textView);

        cardView.addView(horizontalLayout);
        createCardViewListener(cardView,theme);

        addToParent(cardView);
    }

    public void createCardViewListener(CardView cardView, final Theme theme) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ThemeViewerActivity.class);
                intent.putExtra("Theme",theme);
                context.startActivity(intent);
            }
        });
    }

    public void createPage(ArrayList<Theme> themes) {
        for(int i = 0; i < themes.size(); i++) createThemeCard(themes.get(i));
    }

    public int init() {

        int warn_count = 0;

        Log.i(Constants.LOG_TAG,"Initializing Store UI...");

        // Init layout params
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Check whether parent layout is defined or not
        if(layout == null) {
            Log.e(Constants.LOG_TAG,"Parent layout not defined. It must be defined using setLayout(LinearLayout).");
            return Constants.STATUS_FATAL;
        }

        // Check whether ThemeArray is init or not
        if(themeArrayList == null) {
            Log.e(Constants.LOG_TAG,"ThemeLists not defined. It must be defined using setThemeArrayList(ArrayList<Theme>)");
            return Constants.STATUS_FATAL;
        }

        // Check whether themeArrayList is empty or not
        if(themeArrayList.isEmpty()) {
            Log.w(Constants.LOG_TAG,"ThemeList defined but empty.");
            warn_count++;
        }

        Log.i(Constants.LOG_TAG,"StoreUI initialized" + (warn_count == 0 ? "." : " with warnings."));

        // Now that we are good to go, show the themes to user
        Log.i(Constants.LOG_TAG,"Showing themes...");
        createPage(themeArrayList);

        return Constants.STATUS_OK;
    }
}
