package com.example.skyeatadmin.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.PendingPayment;
import com.example.skyeatadmin.Adapter.ManagerListAdapter;
import com.example.skyeatadmin.Model.Login;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerListFragment extends Fragment {

    RecyclerView lstManager;
    public String managerZone,managerId;
    Context context;
    ArrayList<Login.Data> managerList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manager_list, container, false);
        lstManager=view.findViewById(R.id.lstManager);

        getManager();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lstManager.setLayoutManager(layoutManager);
        return view;



    }
    @SuppressLint("ResourceAsColor")
    public void getManager()
    {

        RetrofitClient.getInstance().getMyApi().getLogin("get_new_loginflutter").enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                assert response.body() != null;
                managerList= (ArrayList<Login.Data>) response.body().getData();
                ManagerListAdapter managerListAdapter=new ManagerListAdapter(getContext(),managerList);
                lstManager.setAdapter(managerListAdapter);


                managerListAdapter.setOnClickListener(new ManagerListAdapter.OnClickListener() {
                    @Override
                    public void onClick(int position)
                    {
                        managerZone=managerList.get(position).getZone_name();
                        managerId=managerList.get(position).getId();
                        Intent intent=new Intent(getContext(),PendingPayment.class);
                        intent.putExtra("managerZone",managerZone);
                        intent.putExtra("managerId",managerId);
                        getContext().startActivity(intent);
//                        Fragment newFragment = new PendingPayment(); // Create an instance of the new fragment
//                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment_container, newFragment); // Replace the current fragment with the new fragment
//                        fragmentTransaction.addToBackStack("PendingPayment"); // Optional: Add the transaction to the back stack
//                        fragmentTransaction.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}