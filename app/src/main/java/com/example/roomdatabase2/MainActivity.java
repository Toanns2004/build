package com.example.roomdatabase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.roomdatabase2.database.UserDatabase;
import com.example.roomdatabase2.databinding.ActivityMainBinding;
import com.example.roomdatabase2.interf.IUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private UserAdapter adapter;
    private List<User> userList;

    private IUser iUser = new IUser() {
        @Override
        public void getUser(User user) {
            sendUserDetail(user);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter = new UserAdapter();
        userList = new ArrayList<>();
        adapter.setData(userList,iUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycelview.setLayoutManager(linearLayoutManager);
        binding.recycelview.setAdapter(adapter);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        loadData();
    }

    private void addUser() {
        String name = binding.name.getText().toString().trim();
        String phone = binding.phone.getText().toString().trim();

        User user = new User(name,phone);
        if (checkPhone(user)){
            return;
        }
        UserDatabase.getInstance(this).userDAO().insertUser(user);
        Toast.makeText(this, "add"+user, Toast.LENGTH_SHORT).show();
        binding.name.setText("");
        binding.phone.setText("");
        hideSoftKeyboard();

       loadData();
    }

    private void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void loadData() {
        userList = UserDatabase.getInstance(this).userDAO().getListUser();
        adapter.setData(userList,iUser);
    }

    private boolean checkPhone(User user){
        List<User> users = UserDatabase.getInstance(this).userDAO().checkData(user.getPhone());
        return users!= null && !users.isEmpty();
    }
    private void sendUserDetail(User user){
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtra("intentUser",bundle);
        startActivity(intent);
    }
}