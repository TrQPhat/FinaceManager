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
<<<<<<< HEAD
                btnExpense.setAlpha(1f);

                //hiện thị danh sách
                list = transactionDAO.getTransactionsByType(user.getId() ,type);
                adapter = new TransactionAdapter(getContext(), R.layout.transaction_item, list);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                isLongClick = true;

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setCancelable(false);
                alertDialog
                        .setTitle("Xóa giao dịch")
                        .setMessage("Bạn có chắc chắn muốn xóa giao dịch này?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            int transaction_id = list.get(position).getId();
                            if (transactionDAO.deleteTransaction(transaction_id)){
                                list.remove(list.get(position));
                                adapter.notifyDataSetChanged();
                            }
                            isLongClick = false;
                        })
                        .setNegativeButton("Không", (dialog, which) -> {isLongClick = false;})
                        .show();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isLongClick) return;

                Bundle bundle = new Bundle();
                bundle.putSerializable("transaction", list.get(position));

                InputFragment inputFragment = new InputFragment();
                inputFragment.setArguments(bundle);

                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.input);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, inputFragment)
                        .addToBackStack(null)
                        .commit();
=======
                btnOutcome.setAlpha(1f);
>>>>>>> parent of 625ff51 (update Transaction)
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
        btnIncome = view.findViewById(R.id.btnExpense);
        btnOutcome = view.findViewById(R.id.btnIncome);
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