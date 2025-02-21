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
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private int selectedCategoryId;
    private  int user_Id;
    private String type;
    private String request;
    private Transaction transaction;

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
        btnIncome = view.findViewById(R.id.year);
        btnExpense = view.findViewById(R.id.month);
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
        selectedCategoryId = -1;
        request = "insert";
        transaction = null;

        gridView = view.findViewById(R.id.gridView);
        categoryDAO = new CategoryDAO(view.getContext());
        list = categoryDAO.getAllCategoriesByType(user_Id, type);
        adapter = new CategoryAdapterGrid(view.getContext(), R.layout.item_category_grid, list, selectedCategoryId);
        gridView.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle != null){
            transaction = (Transaction) bundle.getSerializable("transaction");
            if (transaction!=null) {
                tvDate.setText(transaction.getDate());
                etDescription.setText(transaction.getDescription());
                etAmount.setText(String.valueOf(transaction.getAmount()));
                selectedCategoryId = transaction.getCategoryId();
                request = "update";

                gridView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        for (int i=0; i<gridView.getChildCount(); i++){
                            if (adapter.getItem(i).getCategoryId()== selectedCategoryId) {
                                View view = gridView.getChildAt(i);
                                view.setSelected(true);
                            }
                        }
                    }
                });
            }
        }
    }

    private void addEvents(){
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Thu nhập")) return;
                btnIncome.setAlpha(1f);
                btnExpense.setAlpha(0.3f);
                btnSave.setText("Lưu thu nhập");
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
                btnSave.setText("Lưu chi tiêu");
                type = "Chi tiêu";
                list = categoryDAO.getAllCategoriesByType(user_Id, type);
                adapter = new CategoryAdapterGrid(getContext(), R.layout.item_category_grid, list);
                gridView.setAdapter(adapter);
                //adapter.notifyDataSetChanged(); //không hoạt động
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

        //Xử lý số tiền

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedCategoryId = list.get(position).getCategoryId();
            view.setSelected(true);
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = tvDate.getText().toString();
                String description = etDescription.getText().toString();
                String amount = etAmount.getText().toString();
                if (amount.isEmpty() || Integer.parseInt(amount) < 1000){
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền (số tiền >= 1000)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedCategoryId == -1){
                    Toast.makeText(getContext(), "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
                    return;
                }

                TransactionDAO transactionDAO = new TransactionDAO(getContext());

                if (request.equals("insert")) {
                    transaction = new Transaction(Integer.parseInt(amount), date, description, selectedCategoryId, user_Id);
                    if (transactionDAO.insertTransaction(transaction)){
                        Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    transaction.setAmount(Integer.parseInt(amount));
                    transaction.setDate(date);
                    transaction.setDescription(description);
                    transaction.setCategoryId(selectedCategoryId);
                    if (transactionDAO.updateTransaction(transaction)){
                        Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                        bottomNavigationView.setSelectedItemId(R.id.home);
                    }
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