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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

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
        setSupportActionBar(toolbar);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        listarclientes();
        refresh();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToRegisterNewClient = new Intent(getApplicationContext(),RegisterNewClient.class);
            }
        });
        listclients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                Intent TodatosClient =new Intent(getApplicationContext(),RegisterNewClient.class);
                TodatosClient.putExtra("DatosCliente", listclients.getItemIdAtPosition(posicion) );
                startActivity(TodatosClient);
            }
        });

    }
    public void listarclientes(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRootChild = mDatabase.child("Cliente");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listclients = (ListView) findViewById(R.id.ListViewClients);
        listclients.setAdapter(adapter);

        mRootChild.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s){
                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){
                String string = dataSnapshot.getValue(String.class);
                arrayList.remove(string);
                adapter.notifyDataSetChanged();
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
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mRootChild = mDatabase.child("Nombre de la base de datos");
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                mRootChild.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String string = dataSnapshot.getValue(String.class);
                        arrayList.add(string);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        String string = dataSnapshot.getValue(String.class);
                        arrayList.remove(string);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Refreshcounter = Refreshcounter + 1;
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
