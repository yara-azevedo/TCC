package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fob;
    RecyclerView recyclerView;
    ImageButton btn_logoff, btn_search, btn_filter;
    ConteudoAdapter conteudoAdapter;
    TextView count_filme, count_serie, count_jogo, count_livro;
    String tipoDialogo, open;
    LinearLayout linear_count_filme, linear_count_jogo, linear_count_livro, linear_count_serie;

    public String tipoFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       find();
       countF(); countJ(); countL(); countS();
       //selectFiltro();

        fob.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, DetalheActivity.class)));
        btn_logoff.setOnClickListener((v)->logout() );
        btn_search.setOnClickListener( (v) -> startActivity(new Intent(MainActivity.this, PesquisaActivity.class) ));

        setupRecyclerView();
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

   /* void selectFiltro(){
        linear_count_filme.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, FilmeActivity.class)));

        linear_count_serie.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, SerieActivity.class)));

        linear_count_jogo.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, JogoActivity.class)));

        linear_count_livro.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LivroActivity.class)));
    }*/

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

    void setupRecyclerView(){
        Query query  = Utility.getCollectionReference().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Conteudo> options = new FirestoreRecyclerOptions.Builder<Conteudo>()
                .setQuery(query,Conteudo.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        conteudoAdapter = new ConteudoAdapter(options,this);
        recyclerView.setAdapter(conteudoAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        conteudoAdapter.startListening();
        countF(); countJ(); countL(); countS();
    }

    @Override
    protected void onStop() {
        super.onStop();
        conteudoAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        conteudoAdapter.notifyDataSetChanged();
        countF(); countJ(); countL(); countS();
    }

    public void find(){
        fob = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recyler_view);
        btn_logoff = findViewById(R.id.btn_logoff);
        btn_search = findViewById(R.id.btn_search);

        count_filme = findViewById(R.id.txt_filme_count);
        count_jogo = findViewById(R.id.txt_jogo_count);
        count_serie = findViewById(R.id.txt_serie_count);
        count_livro = findViewById(R.id.txt_livro_count);

        linear_count_filme = findViewById(R.id.linear_count_filme);
        linear_count_jogo = findViewById(R.id.linear_count_jogo);
        linear_count_serie = findViewById(R.id.linear_count_serie);
        linear_count_livro = findViewById(R.id.linear_count_livro);

    }

}

