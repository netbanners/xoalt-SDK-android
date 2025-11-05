package com.connect.testbannerexample.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.connect.testbannerexample.R;
import com.connect.testbannerexample.RadialGradientDrw;
import com.connect.testbannerexample.TestActivity;

public class Title3 extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, null);
       // root.setBackground(RadialGradientDrw.getShader());
        TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setText("В книге о приключениях Тома Сойера писатель с большим мастерством нарисовал жизнь американского провинциального городка 40-х годов XIX века. Благодаря напряженному сюжету и блестящему юмору эта книга горячо любима читателями всего мира.");
        root.findViewById(R.id.ID).setOnClickListener(v->{
            ((TestActivity) getActivity()).nav("home");
        });
        return root;

    }
    }
