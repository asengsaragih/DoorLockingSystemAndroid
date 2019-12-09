package com.android.doorlockingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ImageButton mLockButton;
    private ImageButton mUnlockButton;
    private ConstraintLayout mLockView;
    private ConstraintLayout mUnlockView;
    private DatabaseReference mDatabaseRef;
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLockButton = findViewById(R.id.imageButton_lock);
        mUnlockButton = findViewById(R.id.imageButton_unlock);
        mLockView = findViewById(R.id.constraint_lock);
        mUnlockView = findViewById(R.id.constraint_unlock);
        mLoadingProgress = findViewById(R.id.progress_loading);

        mLoadingProgress.setVisibility(View.GONE);

        getWindow().setStatusBarColor(getResources().getColor(R.color.lock));

        mButtonClicked();
    }

    private void mButtonClicked() {
        mLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOpenDoor();
            }
        });

        mUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCloseDoor();
            }
        });
    }

    private void mOpenDoor() {
        mLoadingProgress.setVisibility(View.VISIBLE);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("STATUS");
        mDatabaseRef.setValue(new Unlock(1))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mLoadingProgress.setVisibility(View.GONE);
                        mUnlockView.setVisibility(View.VISIBLE);
                        mLockView.setVisibility(View.GONE);
                        getWindow().setStatusBarColor(getResources().getColor(R.color.unlock));
                        mToastMessage("Berhasil Membuka Pintu");
                        mVibrateShow(500);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mLoadingProgress.setVisibility(View.GONE);
                        mToastMessage("Gagal Membuka Pintu : " + e.getMessage());
                    }
                });
    }

    private void mCloseDoor() {
        mLoadingProgress.setVisibility(View.VISIBLE);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("STATUS");
        mDatabaseRef.setValue(new Unlock(0))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mLoadingProgress.setVisibility(View.GONE);
                        mUnlockView.setVisibility(View.GONE);
                        mLockView.setVisibility(View.VISIBLE);
                        getWindow().setStatusBarColor(getResources().getColor(R.color.lock));
                        mToastMessage("Berhasil Menutup Pintu");
                        mVibrateShow(500);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mLoadingProgress.setVisibility(View.GONE);
                        mToastMessage("Gagal Menutup Pintu : " + e.getMessage());
                    }
                });
    }

    private void mVibrateShow(int length) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(length, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(length);
        }
    }

    private void mToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
