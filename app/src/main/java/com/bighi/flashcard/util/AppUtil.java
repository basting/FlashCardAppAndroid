package com.bighi.flashcard.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Bastin Gomez
 * @since 11-Oct-15
 */
public class AppUtil {
    public static boolean getShuffleIndicator(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        boolean shuffleInd = SP.getBoolean("opt_shuffle_cards",false);
        return shuffleInd;
    }
}