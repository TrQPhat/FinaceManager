package com.example.financemanager.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.financemanager.DAO.UserDAO;
import com.example.financemanager.Model.User;
import com.example.financemanager.R;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView tvName, tvBalance;
    Button btnIncome, btnOutcome;
    User user;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void addEvents() {
        //hiển thị thu nhập
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_IncomeFragment();

                //set trạng thái của nút
                btnIncome.setAlpha(1f);
                btnOutcome.setAlpha(0.6f);
            }
        });

        // hiển thị chi tiêu
        btnOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_OutcomeFragment();

                //set trạng thái
                btnIncome.setAlpha(0.6f);
                btnOutcome.setAlpha(1f);
            }
        });
    }

    private void addControls(View view) {
        NavigationViewActivity activity = (NavigationViewActivity) getActivity();
        if (activity != null) {
            UserDAO userDAO = new UserDAO(activity);
            user = userDAO.getUserByEmail(activity.getEmail());
        }

        tvName = view.findViewById(R.id.tvName);
        tvName.setText(user.getUsername());
        tvBalance = view.findViewById(R.id.tvBalance);
        //set số dư
        btnIncome = view.findViewById(R.id.btnIncome);
        btnOutcome = view.findViewById(R.id.btnOutcome);
        load_IncomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
    }

    private void load_IncomeFragment(){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, new IncomeFragment());
        transaction.commit();
    }

    private void load_OutcomeFragment(){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, new OutcomeFragment());
        transaction.commit();
    }
}