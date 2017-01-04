package com.org.iii.blackmenu;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;

public class Main3Activity extends AppCompatActivity {
    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        bottomBar = BottomBar.attach(this, savedInstanceState);

//                new BottomBarFragment(SampleFragment.newInstance("Content for recents."), R.drawable.restaurantmenu, "Recents"),
//                new BottomBarFragment(SampleFragment.newInstance("Content for food."), R.drawable.shoppingcart, "Food"),
//                new BottomBarFragment(SampleFragment.newInstance("Content for favorites."), R.drawable.restaurantmenu, "Favorites"),
//                new BottomBarFragment(SampleFragment.newInstance("Content for locations."), R.drawable.shoppingcart, "Location")

        // Setting colors for different tabs when there's more than three of them.
        bottomBar.mapColorForTab(0, "#3B494C");
        bottomBar.mapColorForTab(1, "#00796B");
        bottomBar.mapColorForTab(2, "#7B1FA2");
        bottomBar.mapColorForTab(3, "#FF5252");

        bottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:
                        // Item 1 Selected
                }
            }
        });

        // Make a Badge for the first tab, with red background color and a value of "4".
        BottomBarBadge unreadMessages = bottomBar.makeBadgeForTabAt(1, "#E91E63", 5);

        // Control the badge's visibility
        unreadMessages.show();
        //unreadMessages.hide();

        // Change the displayed count for this badge.
        unreadMessages.setCount(2);

        // Change the show / hide animation duration.
//        unreadMessages.setAnimationDuration(9999999);

        // If you want the badge be shown always after unselecting the tab that contains it.
        unreadMessages.setAutoShowAfterUnSelection(true);
    }
}
