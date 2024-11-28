package com.example.financemanager.Activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financemanager.Adapter.CategoryAdapterGrid;
import com.example.financemanager.DAO.CategoryDAO;
import com.example.financemanager.DAO.TransactionDAO;
import com.example.financemanager.Dialogs.Calendar_Dialog;
import com.example.financemanager.Model.Category;
import com.example.financemanager.Model.Transaction;
import com.example.financemanager.R;
import com.example.financemanager.Utils.FormatDate;

import java.util.Date;
import java.util.List;

public class InputFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView btnIncome, btnExpense, tvDate;
    EditText etDescription, etAmount;
    ImageView btnAddCagetory, btnPreviousDay, btnNextDay, btnCalendar;
    GridView gridView;
    Button btnSave;

    private CategoryAdapterGrid adapter;
    private List<Category> list;
    private CategoryDAO categoryDAO;
    private Category selectedCategory;
    private  int user_Id;
    private String type;

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
        btnExpense = view.findViewById(R.id.btnExpense);
        tvDate = view.findViewById(R.id.tvDate);
        btnPreviousDay = view.findViewById(R.id.btnPreviousDay);
        btnNextDay = view.findViewById(R.id.btnNextDay);
        etDescription = view.findViewById(R.id.etDescription);
        etAmount = view.findViewById(R.id.etAmount);
        btnAddCagetory = view.findViewById(R.id.btnAddCagetory);
        btnCalendar = view.findViewById(R.id.btnCalendar);
        btnSave = view.findViewById(R.id.btnSaveTransaction);
        tvDate.setText(FormatDate.DateToString(new Date()));
        type = "Chi tiêu";

        gridView = view.findViewById(R.id.gridView);
        categoryDAO = new CategoryDAO(view.getContext());
        list = categoryDAO.getAllCategoriesByType(user_Id, type);
        adapter = new CategoryAdapterGrid(view.getContext(), R.layout.item_category_grid, list);
        gridView.setAdapter(adapter);

        selectedCategory = null;

    }

    private void addEvents(){
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Thu nhập")) return;
                btnIncome.setAlpha(1f);
                btnExpense.setAlpha(0.3f);
                type = "Thu nhập";
                list = categoryDAO.getAllCategoriesByType(user_Id, type);
                adapter = new CategoryAdapterGrid(getContext(), R.layout.item_category_grid, list);
                gridView.setAdapter(adapter);
            }
        });

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Chi tiêu")) return;
                btnIncome.setAlpha(0.3f);
                btnExpense.setAlpha(1f);
                type = "Chi tiêu";
                list = categoryDAO.getAllCategoriesByType(user_Id, type);
                adapter = new CategoryAdapterGrid(getContext(), R.layout.item_category_grid, list);
                gridView.setAdapter(adapter);
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

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedCategory = list.get(position);
            view.setSelected(true);
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = tvDate.getText().toString();
                String description = etDescription.getText().toString();
                String amount = etAmount.getText().toString();
                if (amount.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền (>0)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedCategory == null){
                    Toast.makeText(getContext(), "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
                    return;
                }

                TransactionDAO transactionDAO = new TransactionDAO(getContext());
                Transaction transaction = new Transaction(0, Double.parseDouble(amount), date, description, selectedCategory.getCategoryId(), user_Id);
                if (transactionDAO.insertTransaction(transaction)){
                    Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        list = categoryDAO.getAllCategoriesByType(user_Id, type);
        adapter = new CategoryAdapterGrid(getContext(), R.layout.item_category_grid, list);
        gridView.setAdapter(adapter);

    }
}