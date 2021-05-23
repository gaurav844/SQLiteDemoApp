package com.taskom.sqlitedemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edt_name,edt_age;
    Button btn_add,btn_update,btn_delete,btn_viewAll;

    DBHelper dbHelper;
    ListView list_customer;
    CustomerModel selectedCustomerModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_name = findViewById(R.id.edt_name);
        edt_age = findViewById(R.id.edt_age);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_viewAll = findViewById(R.id.btn_view_all);
        list_customer = findViewById(R.id.list_customer);

        dbHelper = new DBHelper(this);


        setupList();


//        CustomerModel model = new CustomerModel();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname = edt_name.getText().toString();
                String cage = edt_age.getText().toString();

                try {
                    CustomerModel model = new CustomerModel(-1,Integer.parseInt(cage),cname);

                    boolean inserted = dbHelper.addCustomer(model);

                    if (inserted == true){
                        Toast.makeText(MainActivity.this, "customer added!", Toast.LENGTH_SHORT).show();
                        clearPrevData();
                        setupList();
                    }else{
                        Toast.makeText(MainActivity.this, "customer not added!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "cannot add customer!", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupList();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedCustomerModel == null){
                    Toast.makeText(MainActivity.this, "please select a customer first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = dbHelper.deleteCustomer(selectedCustomerModel);

                if (success == true){
                    Toast.makeText(MainActivity.this, "deleted successfully", Toast.LENGTH_SHORT).show();
                    clearPrevData();
                    setupList();
                }else{
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedCustomerModel == null){
                    Toast.makeText(MainActivity.this, "please select a customer first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String cname = edt_name.getText().toString();
                    String cage = edt_age.getText().toString();

                    selectedCustomerModel.setCustomer_name(cname);
                    selectedCustomerModel.setCustomer_age(Integer.valueOf(cage));


                    boolean success = dbHelper.updateCustomer(selectedCustomerModel);

                    if (success == true){
                        Toast.makeText(MainActivity.this, "updated successfully", Toast.LENGTH_SHORT).show();
                        clearPrevData();
                        setupList();
                    }else{
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "cannot update", Toast.LENGTH_SHORT).show();
                }

            }
        });


        list_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCustomerModel = (CustomerModel) parent.getItemAtPosition(position);
                edt_name.setText(""+selectedCustomerModel.getCustomer_name());
                edt_age.setText(""+selectedCustomerModel.getCustomer_age());
            }
        });







    }

    private void clearPrevData() {
        selectedCustomerModel = null;
        edt_name.setText("");
        edt_age.setText("");
    }

    private void setupList() {
        List<CustomerModel> models = dbHelper.getAllCustomers();
        ArrayAdapter<CustomerModel> adapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,models);
        list_customer.setAdapter(adapter);
    }
}