package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class PesquisaActivity extends AppCompatActivity {
    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;

    PesquisaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        find();

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        searchButton.setOnClickListener(view -> {
            String searchTerm = searchInput.getText().toString();
            if(searchTerm.isEmpty() || searchTerm.length()<3){
                Toast.makeText(getApplicationContext(),"Invalidoo", Toast.LENGTH_SHORT).show();
                return;
            }
            setupSearchRecyclerView(searchTerm);
        });
    }

    void setupSearchRecyclerView(String searchTerm){
        Query query  = Utility.getCollectionReference().whereGreaterThanOrEqualTo("titulo", searchTerm);
        FirestoreRecyclerOptions<Conteudo> options = new FirestoreRecyclerOptions.Builder<Conteudo>()
                .setQuery(query,Conteudo.class).build();
        adapter = new PesquisaAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    public void find(){
        searchInput = findViewById(R.id.seach_input);
        searchButton = findViewById(R.id.search_btn);
        backButton = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.search_recycler_view);

        searchInput.requestFocus();
    }
}