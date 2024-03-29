package com.example.pruebaapphuelladigital;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Model.Cliente;

public class Index extends AppCompatActivity {
    private ListView listclients;
    SwipeRefreshLayout pullToRefresh;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = findViewById(R.id.toolbar);
        listclients=findViewById(R.id.ListViewClients);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listclients.setAdapter(adapter);
        setSupportActionBar(toolbar);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        listarclientes();
        refresh();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToRegisterNewClient = new Intent(getApplicationContext(),RegisterNewClient.class);
                startActivity(ToRegisterNewClient);
            }
        });
        listclients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                selecciondedatos(posicion);
                Intent TodatosClient =new Intent(getApplicationContext(),RegisterNewClient.class);
                TodatosClient.putExtra("DatosCliente", listclients.getItemIdAtPosition(posicion) );
                startActivity(TodatosClient);
            }
        });

    }
    public void listarclientes(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRootChild = mDatabase.child("Cliente");

        mRootChild.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s){
                String string = dataSnapshot.getValue().toString();
                arrayList.add(string);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){
                String string = dataSnapshot.getValue().toString();
                arrayList.remove(string);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }
    public void refresh(){
        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                listarclientes();
                adapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });
    }
    public void selecciondedatos(int position){
        listclients.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listclients.setItemChecked(position, true);
    }


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

}
