package com.bighi.flashcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import com.bighi.flashcard.util.AppUtil;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private TextView flashCardText;
    private TextView slideNo;

    private float x1, x2;
    private static final int MIN_DISTANCE = 150;
    private final ArrayList<String> flashCardList = new ArrayList<>();
    private int currentPosition = 0;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        flashCardText = (TextView) findViewById(R.id.flashCardText);
        slideNo = (TextView) findViewById(R.id.slideNo);

        initFlashCards();
    }

    private void initFlashCards()
    {
        flashCardList.add("StringBuffer is thread-safe\r\n" +
                "StringBuilder is not thread-safe");

        flashCardList.add("HashTable is thread-safe\r\n" +
                "HashMap is not thread-safe");

        flashCardList.add("Two ways to create threads\r\n" +
                "* Implement Runnable interface\r\n" +
                "* Extend Thread class");

        flashCardList.add("Best data structure to implement Stack\r\n" +
                "** Linked List **");

        flashCardList.add("Examples of stable sorting\r\n" +
                "** Merge sort **");

        flashCardList.add("Examples of unstable sorting\r\n" +
                "** Quick sort **");

        flashCardText.setText(flashCardList.get(currentPosition));

        updateCount();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1) {
                        //Toast.makeText(this, "Left to Right swipe [Previous]", Toast.LENGTH_SHORT).show();
                        getPreviousCard();
                    }
                    // Right to left swipe action
                    else {
                        //Toast.makeText(this, "Right to Left swipe [Next]", Toast.LENGTH_SHORT).show ();
                        getNextCard();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void updateCount() {
        slideNo.setText(String.format("%d of %d", currentPosition + 1, flashCardList.size()));
    }

    private void getNextCard() {
        if(AppUtil.getShuffleIndicator(getBaseContext())) {
            currentPosition = getNextRandomIndex(currentPosition);
        }else{
            currentPosition = getNextIndexForRightKey(currentPosition);
        }
        flashCardText.setText(flashCardList.get(currentPosition));
        updateCount();
    }

    private void getPreviousCard() {
        if(AppUtil.getShuffleIndicator(getBaseContext())) {
            currentPosition = getNextRandomIndex(currentPosition);
        }else {
            currentPosition = getNextIndexForLeftKey(currentPosition);
        }

        flashCardText.setText(flashCardList.get(currentPosition));
        updateCount();
    }

    private int getNextIndexForLeftKey(int currPos) {
        if (currPos <= 0) {
            return flashCardList.size() - 1;
        }
        return currPos - 1;
    }

    private int getNextIndexForRightKey(int currPos) {
        if (currPos >= flashCardList.size() - 1) {
            return 0;
        }
        return currPos + 1;
    }

    private int getNextRandomIndex(int currPos) {
        int randInd;
        int min = 0;
        int max = flashCardList.size() - 1;
        do {
            randInd = random.nextInt(max - min + 1) + min;
        } while(randInd == currPos);
        return randInd;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SettingsActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
