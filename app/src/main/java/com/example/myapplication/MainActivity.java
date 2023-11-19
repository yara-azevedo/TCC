package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fob;
    RecyclerView recyclerView;
    ImageButton btn_logoff, btn_search, btn_filter;
    ConteudoAdapter conteudoAdapter;
    TextView count_filme, count_serie, count_jogo, count_livro;
    String tipoDialogo;
    LinearLayout linear_count_filme, linear_count_jogo, linear_count_livro, linear_count_serie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       String count =" ";
       find();
       count();
       selectCount();

        fob.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, DetalheActivity.class)));
        btn_logoff.setOnClickListener((v)->logout() );
        btn_search.setOnClickListener( (v) -> startActivity(new Intent(MainActivity.this, PesquisaActivity.class) ));
        btn_filter.setOnClickListener((v)->filtro() );
        setupRecyclerView();
    }

    public void filtro() {
    }


    public void count() {

    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

    void setupRecyclerView(){
        Query query  = Utility.getCollectionReferenceForNotes().orderBy("timestamp",Query.Direction.DESCENDING);
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
    }

    public void find(){
        fob = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recyler_view);
        btn_logoff = findViewById(R.id.btn_logoff);
        btn_search = findViewById(R.id.btn_search);
        btn_filter = findViewById(R.id.btn_filter);

        count_filme = findViewById(R.id.txt_filme_count);
        count_jogo = findViewById(R.id.txt_jogo_count);
        count_serie = findViewById(R.id.txt_serie_count);
        count_livro = findViewById(R.id.txt_livro_count);

        linear_count_filme = findViewById(R.id.linear_count_filme);
        linear_count_jogo = findViewById(R.id.linear_count_jogo);
        linear_count_serie = findViewById(R.id.linear_count_serie);
        linear_count_livro = findViewById(R.id.linear_count_livro);


    }

    void selectCount(){

        linear_count_filme.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(), "count filme", Toast.LENGTH_SHORT).show());

                 count_filme.setText("87");

        linear_count_jogo.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(), "count jogo", Toast.LENGTH_SHORT).show());
                count_jogo.setText("2");

        linear_count_livro.setOnClickListener(view ->
                Toast.makeText(getApplicationContext(), "count livro", Toast.LENGTH_SHORT).show());
                 count_livro.setText("09");

        linear_count_serie.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "count serie", Toast.LENGTH_SHORT).show();
            count_serie.setText("85");
        });
    }

    /*  public void alert(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Filtro")
                .setItems(new CharSequence[]{"Filmes", "Jogos", "Livros", "Séries", "Tudo"}, (dialog1, i) -> {
                    switch (i) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "Filmes", Toast.LENGTH_SHORT).show();
                            tipoDialogo= "Filmes";
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Jogos", Toast.LENGTH_SHORT).show();
                            tipoDialogo= "Jogos";
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Livros", Toast.LENGTH_SHORT).show();
                            tipoDialogo= "Livros";
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), "Séries", Toast.LENGTH_SHORT).show();
                            tipoDialogo= "Séries";
                            break;
                        case 4:
                            Toast.makeText(getApplicationContext(), "Tudo", Toast.LENGTH_SHORT).show();
                            tipoDialogo= "Tudo";
                            break;
                    }
                });

        dialog.create().show();

    }*/

     /*void showMenu(){
        PopupMenu popupMenu  = new PopupMenu(MainActivity.this,btn_logoff);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getTitle()=="Logout"){
                logout();
                return true;
            }
            return false;
        });

    }*/
}

