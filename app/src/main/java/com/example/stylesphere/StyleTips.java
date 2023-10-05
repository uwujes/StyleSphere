package com.example.stylesphere;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.stylesphere.databinding.ActivityStyleTipsBinding;

public class StyleTips extends AppCompatActivity {

    private ActivityStyleTipsBinding binding;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStyleTipsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        textView = root.findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());


//        FloatingActionButton fab = binding.fab;
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}