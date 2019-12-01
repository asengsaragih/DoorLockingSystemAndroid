package com.android.doorlockingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton mLockButton;
    private ImageButton mUnlockButton;
    private ConstraintLayout mLockView;
    private ConstraintLayout mUnlockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLockButton = findViewById(R.id.imageButton_lock);
        mUnlockButton = findViewById(R.id.imageButton_unlock);
        mLockView = findViewById(R.id.constraint_lock);
        mUnlockView = findViewById(R.id.constraint_unlock);

        getWindow().setStatusBarColor(getResources().getColor(R.color.lock));

        mButtonClicked();
    }

    private void mButtonClicked() {
        mLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUnlockView.setVisibility(View.VISIBLE);
                mLockView.setVisibility(View.GONE);

                getWindow().setStatusBarColor(getResources().getColor(R.color.unlock));
            }
        });

        mUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUnlockView.setVisibility(View.GONE);
                mLockView.setVisibility(View.VISIBLE);

                getWindow().setStatusBarColor(getResources().getColor(R.color.lock));
            }
        });
    }
}
