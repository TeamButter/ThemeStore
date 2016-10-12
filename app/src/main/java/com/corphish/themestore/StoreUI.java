package com.corphish.themestore;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Avinaba on 10/12/2016.
 */
public class StoreUI {

    // Context - on which context you would display themes
    Context context;

    // Layout - on which master layout you will add your child views
    LinearLayout layout;

    // Constructor - Initialize storeUI with this constructor, passing context as arg
    public StoreUI(Context context) {
        this.context = context;
    }

    // After initialization, set the layout using this function
    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }
}
