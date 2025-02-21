package com.example.financemanager.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financemanager.Adapter.TransactionAdapter;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class StatementFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView tvName, tvBalance, tvExpense, tvYear;
    Button btnYear, btnMonthly;

    BarChart barChart;

    User user;
    private String type;
    TransactionDAO transactionDAO;
    List<Transaction> list;
    TransactionAdapter adapter;

    int balance, income, expense, selectedYear;

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
        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("year")) return;
                type = "year";
                btnYear.setAlpha(1f);
                btnMonthly.setAlpha(0.6f);
                tvYear.setVisibility(View.INVISIBLE);

                List<Transaction> incomeList = transactionDAO.getTransactionsByType(user.getId(), "Thu nhập");
                List<Transaction> expenseList = transactionDAO.getTransactionsByType(user.getId(), "Chi tiêu");

                Map<String, Double> incomeData = groupTransactionsByYear(incomeList);
                Map<String, Double> expenseData = groupTransactionsByYear(expenseList);
                loadBarChart(incomeData, expenseData);

            }
        });

        btnMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("monthly")) return;
                type = "monthly";
                //set trạng thái
                btnYear.setAlpha(0.6f);
                btnMonthly.setAlpha(1f);
                tvYear.setVisibility(View.VISIBLE);

                List<Transaction> incomeList = transactionDAO.getTransactionsByTypeAndYear(user.getId(), "Thu nhập", selectedYear+"");
                List<Transaction> expenseList = transactionDAO.getTransactionsByTypeAndYear(user.getId(), "Chi tiêu", selectedYear+"");

                Map<String, Double> incomeData = groupTransactionsByMonth(incomeList);
                Map<String, Double> expenseData = groupTransactionsByMonth(expenseList);
                loadBarChart(incomeData, expenseData);
            }
        });

        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYearPickerDialog(view);
            }
        });

    }

    private void addControls(View view) {
        NavigationViewActivity activity = (NavigationViewActivity) getActivity();
        if (activity != null) {
            UserDAO userDAO = new UserDAO(activity);
            user = userDAO.getUserByEmail(activity.getEmail());
        }
        type = "monthly";

        transactionDAO = new TransactionDAO(getContext());

        tvName = view.findViewById(R.id.tvName);
        tvName.setText(user.getUsername());
        tvBalance = view.findViewById(R.id.tvBalance);
        tvExpense = view.findViewById(R.id.tvExpense);
        tvYear = view.findViewById(R.id.tvYear);

        income = transactionDAO.getTotalByType(user.getId(), "Thu nhập");
        expense = transactionDAO.getTotalByType(user.getId(), "Chi tiêu");

        selectedYear = Calendar.getInstance().get(Calendar.YEAR);
        tvYear.setText(String.valueOf(selectedYear));

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvBalance.setText(decimalFormat.format(income - expense));
        tvExpense.setText(decimalFormat.format(expense));

        btnYear = view.findViewById(R.id.year);
        btnMonthly = view.findViewById(R.id.month);

        barChart = view.findViewById(R.id.barChart);

        List<Transaction> incomeList = transactionDAO.getTransactionsByTypeAndYear(user.getId(), "Thu nhập", selectedYear+"");
        List<Transaction> expenseList = transactionDAO.getTransactionsByTypeAndYear(user.getId(), "Chi tiêu", selectedYear+"");

        Map<String, Double> incomeData = groupTransactionsByMonth(incomeList);
        Map<String, Double> expenseData = groupTransactionsByMonth(expenseList);
        loadBarChart(incomeData, expenseData);

    }

    public Map<String, Double> groupTransactionsByMonth(List<Transaction> transactions) {
        if (transactions==null){
            return null;
        }
        Map<String, Double> valuesByType = new LinkedHashMap<>(); // Dùng LinkedHashMap để giữ thứ tự

        for (Transaction transaction : transactions) {
            String date = transaction.getDate();
            double amount = transaction.getAmount() / 1000000.0;
            amount = Math.round(amount * 10.0) / 10.0;

            // Trích xuất MM/yyyy từ chuỗi date
            String monthYear = "T" + date.substring(3,5);

            // Cộng dồn số tiền vào tháng tương ứng
            valuesByType.put(monthYear, valuesByType.getOrDefault(monthYear, 0.0) + amount);
        }

        return valuesByType;
    }

    public Map<String, Double> groupTransactionsByYear(List<Transaction> transactions) {
        if (transactions==null){
            return null;
        }
        Map<String, Double> valuesByType = new LinkedHashMap<>(); // Dùng LinkedHashMap để giữ thứ tự

        for (Transaction transaction : transactions) {
            String date = transaction.getDate();
            double amount = transaction.getAmount() / 1000000.0;
            amount = Math.round(amount * 10.0) / 10.0;

            // Trích xuất MM/yyyy từ chuỗi date
            String year = date.substring(6);

            // Cộng dồn số tiền vào tháng tương ứng
            valuesByType.put(year, valuesByType.getOrDefault(year, 0.0) + amount);
        }

        return valuesByType;
    }

    private void loadBarChart(Map<String, Double> incomeData, Map<String, Double> expenseData) {
        // Nhóm dữ liệu thu nhập và chi tiêu


        // Hợp nhất danh sách tháng
        Set<String> allMonths = new TreeSet<>(incomeData.keySet());
        allMonths.addAll(expenseData.keySet());

        // Chuẩn bị dữ liệu cho biểu đồ
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        ArrayList<BarEntry> expenseEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int index = 0;

        for (String month : allMonths) {
            float income = incomeData.getOrDefault(month, 0.0).floatValue(); // Nếu không có dữ liệu, đặt 0
            float expense = expenseData.getOrDefault(month, 0.0).floatValue();

            incomeEntries.add(new BarEntry(index, income));
            expenseEntries.add(new BarEntry(index, expense));
            labels.add(month);
            index++;
        }

        // Tạo dataset
        BarDataSet incomeSet = new BarDataSet(incomeEntries, "Thu nhập");
        incomeSet.setColor(Color.parseColor("#00D09E"));
        incomeSet.setValueTextSize(12f);

        BarDataSet expenseSet = new BarDataSet(expenseEntries, "Chi tiêu");
        expenseSet.setColor(Color.parseColor("#0068FF"));
        expenseSet.setValueTextSize(12f);

        // Tạo dữ liệu biểu đồ
        BarData data = new BarData(incomeSet, expenseSet);

        // Cấu hình khoảng cách và độ rộng
        int numberOfGroups = labels.size();
        float groupSpace = 0.1f;
        float barSpace = 0.02f;
        float barWidth = (1f - groupSpace) / 2f - barSpace -0.01f;

        data.setBarWidth(barWidth);

        // Đặt dữ liệu vào biểu đồ
        barChart.setData(data);

        // Tùy chỉnh trục X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // Thiết lập nhãn
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);                // Đặt nhãn bên dưới
        xAxis.setGranularity(1f);                                     // Khoảng cách tối thiểu giữa các nhãn
        xAxis.setLabelCount(labels.size()*2, true);        // Số lượng nhãn tối đa
        xAxis.setCenterAxisLabels(true);                              // Căn nhãn giữa các nhóm
        xAxis.setAxisMinimum(0);                                      // Bắt đầu từ vị trí 0
        xAxis.setAxisMaximum(numberOfGroups);                         // Kết thúc tại số lượng nhãn

        // Nhóm các cột
        barChart.groupBars(0, groupSpace, barSpace);

        // Tùy chỉnh trục Y
        barChart.getAxisLeft().setAxisMinimum(0); // Giá trị tối thiểu là 0
        barChart.getAxisRight().setEnabled(false);

        // Các tùy chỉnh khác
        barChart.getDescription().setEnabled(false);
        barChart.animateY(700); // Hiệu ứng cột
        barChart.invalidate();   // Làm mới biểu đồ
    }

    private void showYearPickerDialog(View view) {

        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_year_picker);

        dialog.show();
        dialog.setCancelable(false);

        // Kết nối các View trong layout
        NumberPicker yearPicker = dialog.findViewById(R.id.yearPicker);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);

        // Thiết lập giá trị cho NumberPicker
        yearPicker.setMinValue(2000); // Năm nhỏ nhất
        yearPicker.setMaxValue(2100); // Năm lớn nhất
        yearPicker.setValue(selectedYear); // Năm mặc định

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedYear != yearPicker.getValue()){

                    selectedYear = yearPicker.getValue();

                    List<Transaction> incomeList = transactionDAO.getTransactionsByTypeAndYear(user.getId(), "Thu nhập", selectedYear+"");
                    List<Transaction> expenseList = transactionDAO.getTransactionsByTypeAndYear(user.getId(), "Chi tiêu", selectedYear+"");

                    Map<String, Double> incomeData = groupTransactionsByMonth(incomeList);
                    Map<String, Double> expenseData = groupTransactionsByMonth(expenseList);
                    loadBarChart(incomeData, expenseData);

                }
                tvYear.setText(String.valueOf(selectedYear));
                dialog.dismiss(); // Đóng dialog
            }
        });

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