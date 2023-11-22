package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class JogoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FilmeAdapter filterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        find();
        setupRecyclerView();


    }
    void setupRecyclerView(){
        String j = "Jogo";
        Query query  = Utility.getCollectionReference()
                .whereEqualTo("tipo", j);
        //.orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Conteudo> options = new FirestoreRecyclerOptions.Builder<Conteudo>()
                .setQuery(query,Conteudo.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterAdapter = new FilmeAdapter(options,this);
        recyclerView.setAdapter(filterAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        filterAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        filterAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        filterAdapter.notifyDataSetChanged();
    }

    public void find(){
        recyclerView = findViewById(R.id.recyler_view);

    }
}
