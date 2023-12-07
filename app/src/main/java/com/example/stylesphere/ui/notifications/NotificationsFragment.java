package com.example.stylesphere.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stylesphere.FAQ;
import com.example.stylesphere.R;
import com.example.stylesphere.SignInActivity;
import com.example.stylesphere.StyleTips;
import com.example.stylesphere.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private Button faq;
    private Button tips;
    private Button signOut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
        faq = root.findViewById(R.id.faq);
        tips = root.findViewById(R.id.stlyeTips);
        signOut = root.findViewById(R.id.signOut);

//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFaq();
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStyleTips();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutUser();
            }
        });

        return root;
    }

    public void openFaq() {
        Intent intent = new Intent(requireContext(), FAQ.class);
        startActivity(intent);
    }

    public void openStyleTips() {
        Intent intent = new Intent(requireContext(), StyleTips.class);
        startActivity(intent);
    }
    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(requireContext(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}