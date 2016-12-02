package com.saikorobot.fridge;

import com.saikorobot.fridge.sample.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int nonStateCount;

    @State
    private int stateCount;

    TextView textNonStateCount;

    TextView textStateCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fridge.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_main);
        textNonStateCount = (TextView) findViewById(R.id.text_non_state_count);
        textStateCount = (TextView) findViewById(R.id.text_state_count);
        findViewById(R.id.button_non_state_count_up).setOnClickListener(this);
        findViewById(R.id.button_state_count_up).setOnClickListener(this);
        updateText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fridge.saveInstanceState(this, outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_non_state_count_up:
                nonStateCount += 1;
                break;
            case R.id.button_state_count_up:
                stateCount += 1;
                break;
        }
        updateText();
    }

    private void updateText() {
        textNonStateCount.setText(String.valueOf(nonStateCount));
        textStateCount.setText(String.valueOf(stateCount));
    }
}
