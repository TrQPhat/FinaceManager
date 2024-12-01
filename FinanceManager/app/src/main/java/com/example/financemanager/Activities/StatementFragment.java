package com.example.financemanager.Activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financemanager.Adapter.TransactionAdapter;
import com.example.financemanager.DAO.CategoryDAO;
import com.example.financemanager.DAO.TransactionDAO;
import com.example.financemanager.DAO.UserDAO;
import com.example.financemanager.Model.Transaction;
import com.example.financemanager.Model.User;
import com.example.financemanager.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StatementFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView tvName, tvBalance, tvExpense;
    Button btnIncome, btnExpense;

    BarChart barChart;

    User user;
    private String type;
    TransactionDAO transactionDAO;
    List<Transaction> list;
    TransactionAdapter adapter;

    int balance, income, expense;

    public StatementFragment() {
        // Required empty public constructor
    }

    public static StatementFragment newInstance(String param1, String param2) {
        StatementFragment fragment = new StatementFragment();
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
                if(type.equals("Thu nhập")) return;
                type = "Thu nhập";
                //set trạng thái của nút
                btnIncome.setAlpha(1f);
                btnExpense.setAlpha(0.6f);

                //hiển thị danh sách thu nhập

            }
        });

        // hiển thị chi tiêu
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Chi tiêu")) return;
                type = "Chi tiêu";
                //set trạng thái
                btnIncome.setAlpha(0.6f);
                btnExpense.setAlpha(1f);


            }
        });


    }

    private void addControls(View view) {
        NavigationViewActivity activity = (NavigationViewActivity) getActivity();
        if (activity != null) {
            UserDAO userDAO = new UserDAO(activity);
            user = userDAO.getUserByEmail(activity.getEmail());
        }
        type = "Chi tiêu";

        transactionDAO = new TransactionDAO(getContext());

        tvName = view.findViewById(R.id.tvName);
        tvName.setText(user.getUsername());
        tvBalance = view.findViewById(R.id.tvBalance);
        tvExpense = view.findViewById(R.id.tvExpense);

        income = transactionDAO.getTotalByType(user.getId(), "Thu nhập");
        expense = transactionDAO.getTotalByType(user.getId(), "Chi tiêu");

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvBalance.setText(decimalFormat.format(income - expense));
        tvExpense.setText(decimalFormat.format(expense));

        btnIncome = view.findViewById(R.id.btnIncome);
        btnExpense = view.findViewById(R.id.btnExpense);

        barChart = view.findViewById(R.id.barChart);

        loadBarChart(view);

    }

    private void loadBarChart(View view) {
        TransactionDAO transactionDAO = new TransactionDAO(view.getContext());
        List<Transaction> transactions = transactionDAO.getTransactionsByType(user.getId(), type);
        if (transactions.isEmpty()){
            return;
        }
        Map<String, Integer> valuesByType = new LinkedHashMap<>();
        for (Transaction transaction : transactions) {
            String date = transaction.getDate();
            int amount = transaction.getAmount();

            // Trích xuất MM/yyyy từ chuỗi date
            String monthYear = date.substring(3); // Lấy từ ký tự thứ 3 đến hết (MM/yyyy)

            // Cộng dồn số tiền vào tháng tương ứng
            valuesByType.put(monthYear, valuesByType.getOrDefault(monthYear, 0) + amount);
        }

        // Chuyển dữ liệu thành các Entry cho Bar Chart
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : valuesByType.entrySet()) {
            entries.add(new BarEntry(index, entry.getValue()));
            labels.add(entry.getKey());
            index++;
        }

        // Tạo dataset và thêm vào biểu đồ
        BarDataSet dataSet = new BarDataSet(entries, "Chi tiêu hàng tháng");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        barChart.setData(data);

        // Tùy chỉnh biểu đồ
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate(); // Refresh biểu đồ
    }

    public Map<String, Integer> groupTransactionsByMonth(List<Transaction> transactions) {
        if (transactions==null){
            return null;
        }
        Map<String, Integer> valuesByType = new LinkedHashMap<>(); // Dùng LinkedHashMap để giữ thứ tự

        for (Transaction transaction : transactions) {
            String date = transaction.getDate();
            int amount = transaction.getAmount();

            // Trích xuất MM/yyyy từ chuỗi date
            String monthYear = date.substring(3); // Lấy từ ký tự thứ 3 đến hết (MM/yyyy)

            // Cộng dồn số tiền vào tháng tương ứng
            valuesByType.put(monthYear, valuesByType.getOrDefault(monthYear, 0) + amount);
        }

        return valuesByType;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
    }

    public int getUserId(){
        return user.getId();
    }
}