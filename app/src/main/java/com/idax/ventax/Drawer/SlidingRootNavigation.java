package com.idax.ventax.Drawer;

import com.yarolegovich.slidingrootnav.SlidingRootNav;

public class SlidingRootNavigation {

    private static SlidingRootNav slidingRootNav;

    public static SlidingRootNav getSlidingRootNav(SlidingRootNav slidingRootNav){
        if (slidingRootNav != null){
            SlidingRootNavigation.slidingRootNav = slidingRootNav;
            return null;
        }
        return SlidingRootNavigation.slidingRootNav;
    }

}
