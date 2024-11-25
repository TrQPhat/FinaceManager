package com.example.financemanager.Activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.financemanager.Adapter.IconAdapter;
import com.example.financemanager.DAO.IconDAO;
import com.example.financemanager.Dialogs.Calendar_Dialog;
import com.example.financemanager.Model.Icon;
import com.example.financemanager.R;
import com.example.financemanager.Utils.FormatDate;

import java.util.Date;
import java.util.List;

public class InputFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView btnIncome, btnOutcome, tvDate;
    EditText etDescription, etAmount;
    ImageView btnAddCagetory, btnPreviousDay, btnNextDay, btnCalendar;
    List<Icon> list;
    private RecyclerView recyclerView;
    private IconAdapter iconAdapter;
    private IconDAO iconDAO;
    private  int user_Id;

    public InputFragment() {
        // Required empty public constructor
    }

    public static InputFragment newInstance(String param1, String param2) {
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void addControls(View view){

        NavigationViewActivity activity = (NavigationViewActivity) getActivity();
        user_Id = activity.getUser_Id();
        btnIncome = view.findViewById(R.id.btnIncome);
        btnOutcome = view.findViewById(R.id.btnOutcome);
        tvDate = view.findViewById(R.id.tvDate);
        btnPreviousDay = view.findViewById(R.id.btnPreviousDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);
        etDescription = view.findViewById(R.id.etDescription);
        etAmount = view.findViewById(R.id.etAmount);
        btnAddCagetory = view.findViewById(R.id.btnAddCagetory);
        btnCalendar = view.findViewById(R.id.btnCalendar);

    }

    private void addEvents(){
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIncome.setAlpha(1f);
                btnOutcome.setAlpha(0.3f);

            }
        });

        btnOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIncome.setAlpha(0.3f);
                btnOutcome.setAlpha(1f);
            }
        });
        btnAddCagetory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user_Id = ;
                Intent intent = new Intent(getContext(), CagetoryManagementActivity.class);
                intent.putExtra("user_id", user_Id);
                startActivityForResult(intent,123);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Calendar_Dialog(getContext()).showCalendarDialog(tvDate);
            }
        });

        btnPreviousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textDate = tvDate.getText().toString();
                tvDate.setText(FormatDate.subtractDays(textDate, 1));

            }
        });

        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textDate = tvDate.getText().toString();
                tvDate.setText(FormatDate.addDays(textDate, 1));
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
    }
}