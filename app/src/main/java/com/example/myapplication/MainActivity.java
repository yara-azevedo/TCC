package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fob;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    ConteudoAdapter conteudoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fob = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recyler_view);
        menuBtn = findViewById(R.id.menu_btn);

        fob.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, DetalheActivity.class)));
        menuBtn.setOnClickListener((v)->showMenu() );
        setupRecyclerView();
    }
    void showMenu(){
        PopupMenu popupMenu  = new PopupMenu(MainActivity.this,menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getTitle()=="Logout"){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                return true;
            }
            return false;
        });

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
}