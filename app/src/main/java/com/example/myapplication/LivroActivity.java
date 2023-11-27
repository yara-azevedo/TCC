package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.Query;

public class LivroActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FilmeAdapter filterAdapter;

    ImageButton backButton;
    TextView pageTitleTextView;

    TextView count_filme, count_serie, count_jogo, count_livro;

    LinearLayout linear_count_filme, linear_count_jogo, linear_count_livro, linear_count_serie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        find();
        pageTitleTextView.setText("Livros");
        setupRecyclerView();
        countF(); countJ(); countL(); countS();
        backButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });


    }
    void setupRecyclerView(){
        String l = "Livro";
        Query query  = Utility.getCollectionReference()
                .whereEqualTo("tipo", l);
        //.orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Conteudo> options = new FirestoreRecyclerOptions.Builder<Conteudo>()
                .setQuery(query,Conteudo.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterAdapter = new FilmeAdapter(options,this);
        recyclerView.setAdapter(filterAdapter);
    }

    public void countF() {
        String test = "Filme";
        Query query  = Utility.getCollectionReference()
                .whereEqualTo("tipo", test);
        AggregateQuery countQ = query.count();
        countQ.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                AggregateQuerySnapshot snapshot = task.getResult();
                String countF = String.valueOf(snapshot.getCount());
                count_filme.setText(countF);
            } else{
                System.out.println("COUNT  no");
            }
        });



    }

    public void countS() {
        String test = "Série";
        Query query  = Utility.getCollectionReference()
                .whereEqualTo("tipo", test);
        AggregateQuery countQ = query.count();
        countQ.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                AggregateQuerySnapshot snapshot = task.getResult();
                String countS = String.valueOf(snapshot.getCount());
                count_serie.setText(countS);
            } else{
                System.out.println("COUNT  no");
            }
        });



    }

    public void countJ() {
        String test = "Jogo";
        Query query  = Utility.getCollectionReference()
                .whereEqualTo("tipo", test);
        AggregateQuery countQ = query.count();
        countQ.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                AggregateQuerySnapshot snapshot = task.getResult();
                String countJ = String.valueOf(snapshot.getCount());
                count_jogo.setText(countJ);
            } else{
                System.out.println("COUNT  no");
            }
        });



    }

    public void countL() {
        String test = "Livro";
        Query query  = Utility.getCollectionReference()
                .whereEqualTo("tipo", test);
        AggregateQuery countQ = query.count();
        countQ.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                AggregateQuerySnapshot snapshot = task.getResult();
                String countL = String.valueOf(snapshot.getCount());
                count_livro.setText(countL);
            } else{
                System.out.println("COUNT  no");
            }
        });



    }

    void selectFiltro(){
        linear_count_filme.setOnClickListener(view ->
                startActivity(new Intent(LivroActivity.this, FilmeActivity.class)));

        linear_count_serie.setOnClickListener(view ->
                startActivity(new Intent(LivroActivity.this, SerieActivity.class)));

        linear_count_jogo.setOnClickListener(view ->
                startActivity(new Intent(LivroActivity.this, JogoActivity.class)));

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
        backButton = findViewById(R.id.btn_back);

        count_filme = findViewById(R.id.txt_filme_count);
        count_jogo = findViewById(R.id.txt_jogo_count);
        count_serie = findViewById(R.id.txt_serie_count);
        count_livro = findViewById(R.id.txt_livro_count);
        pageTitleTextView = findViewById(R.id.page_title);

        linear_count_filme = findViewById(R.id.linear_count_filme);
        linear_count_jogo = findViewById(R.id.linear_count_jogo);
        linear_count_serie = findViewById(R.id.linear_count_serie);
        linear_count_livro = findViewById(R.id.linear_count_livro);

    }
}
