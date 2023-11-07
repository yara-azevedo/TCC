package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

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

       find();
       count();


        fob.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, DetalheActivity.class)));
        btn_logoff.setOnClickListener((v)->logout() );
        btn_search.setOnClickListener((v)->pesquisa() );
        btn_filter.setOnClickListener((v)->filtro() );
        setupRecyclerView();
    }

    public void filtro() {
        Toast.makeText(getApplicationContext(), "filtro", Toast.LENGTH_SHORT).show();
        alert();



    }

    public void alert(){
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

    }

    public void pesquisa() {
        RelativeLayout search_layout = findViewById(R.id.search_bar_layout);
        RelativeLayout title_layout = findViewById(R.id.title_bar_layout);

        EditText et_pesquisa = findViewById(R.id.et_pesquisa);
        ImageButton btn_pesquisaa = findViewById(R.id.btn_pesquisa);

        title_layout.setVisibility(View.INVISIBLE);
        search_layout.setVisibility(View.VISIBLE);
        btn_pesquisaa.setOnClickListener(view -> {
            String digitado = et_pesquisa.getText().toString();
            Toast.makeText(getApplicationContext(), digitado, Toast.LENGTH_SHORT).show();
            title_layout.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);

        });



    }

    public void count(){

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

