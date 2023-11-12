package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;

public class DetalheActivity extends AppCompatActivity {
    EditText et_titulo, et_genero, et_lancamento, et_avaliacao, et_comentario, et_tipo;
    ImageButton saveNoteBtn;
    TextView pageTitleTextView;
    String titulo, genero, lancamento, avaliacao, comentario,tipo,docId;
    boolean isEditMode = false;
    TextView deleteNoteTextViewBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        find();

        saveNoteBtn = findViewById(R.id.save_note_btn);

        //receive data
        titulo = getIntent().getStringExtra("titulo");
        genero = getIntent().getStringExtra("genero");
        lancamento = getIntent().getStringExtra("lancamento");
        avaliacao = getIntent().getStringExtra("avaliacao");
        comentario = getIntent().getStringExtra("comentario");
        tipo = getIntent().getStringExtra("tipo");
        docId = getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()){
            isEditMode = true;
        }
        et_titulo.setText(titulo);
        et_genero.setText(genero);
        et_lancamento.setText(lancamento);
        et_tipo.setText(tipo);
        et_avaliacao.setText(avaliacao);
        et_comentario.setText(comentario);
        if(isEditMode){
            pageTitleTextView.setText("Edite sua nota");
            deleteNoteTextViewBtn.setVisibility(View.VISIBLE);
        }

        saveNoteBtn.setOnClickListener( (v)-> saveNote());

       deleteNoteTextViewBtn.setOnClickListener((v)-> deleteNoteFromFirebase() );
    }

    void saveNote(){

        String conteudoTitulo = et_titulo.getText().toString();
        String conteudoGenero = et_genero.getText().toString();
        String conteudoLancamento = et_lancamento.getText().toString();
        String conteudoTipo = et_tipo.getText().toString();
        int conteudoAvaliacao = Integer.parseInt(et_avaliacao.getText().toString());
        String conteudoComentario = et_comentario.getText().toString();



        if(conteudoTitulo==null || conteudoTitulo.isEmpty() ){
            Toast.makeText(this, "O título é obrigatório", Toast.LENGTH_SHORT).show();
            return;
        } else if(conteudoAvaliacao>10){
            Toast.makeText(this, "10 é a nota máxima", Toast.LENGTH_SHORT).show();
            return;
        }else if(conteudoTipo==null || conteudoTitulo.isEmpty()) {
            Toast.makeText(this, "O tipo é obnrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        Conteudo conteudo = new Conteudo();
        conteudo.setTitulo(conteudoTitulo);
        conteudo.setGenero(conteudoGenero);
        conteudo.setTipo(conteudoTipo);
        conteudo.setLancamento(conteudoLancamento);
        conteudo.setAvaliacao(String.valueOf(conteudoAvaliacao));
        conteudo.setComentario(conteudoComentario);
        conteudo.setTimestamp(Timestamp.now());

        saveNoteToFirebase(conteudo);

    }

    void saveNoteToFirebase(Conteudo conteudo){
        DocumentReference documentReference;
        if(isEditMode){
            //update the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }else{
            //create new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }



        documentReference.set(conteudo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //note is added
                    Toast.makeText(DetalheActivity.this, "fooooi", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(DetalheActivity.this, "num foi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void deleteNoteFromFirebase() {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //note is added
                    Toast.makeText(DetalheActivity.this, "ejected", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(DetalheActivity.this, "num apagou", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void convertData(){

    }

    public void find(){
        et_titulo = findViewById(R.id.conteudo_titulo);
        et_genero = findViewById(R.id.conteudo_genero);
        et_lancamento = findViewById(R.id.conteudo_lancamento);
        et_avaliacao = findViewById(R.id.conteudo_avaliacao);
        et_comentario = findViewById(R.id.conteudo_comentarios);
        et_tipo = findViewById(R.id.conteudo_tipo);

        pageTitleTextView = findViewById(R.id.page_title);
        deleteNoteTextViewBtn  = findViewById(R.id.delete_note_text_view_btn);

    }

}