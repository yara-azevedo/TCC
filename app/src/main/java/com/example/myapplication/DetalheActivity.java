package com.example.myapplication;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class DetalheActivity extends AppCompatActivity {
    EditText et_titulo, et_genero, et_lancamento, et_avaliacao, et_comentario, et_tipo;
    ImageButton saveBtn;
    TextView pageTitleTextView;
    String titulo, genero, lancamento, avaliacao, comentario,tipo,docId;
    boolean isEditMode = false;
    TextView deleteTextViewBtn, saveTextViewBtn;
    RadioGroup rd_group;
    RadioButton rb_filme, rb_serie,rb_livro, rb_jogo;
    RatingBar ratingBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        find();
        rating();
        radioTipo();


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
            deleteTextViewBtn.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(GONE);
            rd_group.setVisibility(GONE);
        }else{
            et_avaliacao.setVisibility(GONE);
            et_tipo.setVisibility(GONE);
        }

        saveTextViewBtn.setOnClickListener( (v)-> saveConteudo());

       deleteTextViewBtn.setOnClickListener((v)-> deleteConteudoFromFirebase() );
    }

    void saveConteudo(){

        String conteudoTitulo = et_titulo.getText().toString();
        String conteudoGenero = et_genero.getText().toString();
        String conteudoLancamento = et_lancamento.getText().toString();
        String conteudoTipo = et_tipo.getText().toString();
        Double conteudoAvaliacao = Double.parseDouble(et_avaliacao.getText().toString());
        String conteudoComentario = et_comentario.getText().toString();



        if(conteudoTitulo==null || conteudoTitulo.isEmpty() ){
            Toast.makeText(this, "O título é obrigatório", Toast.LENGTH_SHORT).show();
            return;
        } else if(conteudoAvaliacao>10){
            Toast.makeText(this, "10 é a nota máxima", Toast.LENGTH_SHORT).show();
            return;
        }else if(conteudoTipo==null || conteudoTitulo.isEmpty()) {
            Toast.makeText(this, "O tipo é obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        /*String t1 = "Jogo"; String t2 = "jogo"; String t3 = "Filme"; String t4 = "filme";
        String t6 = "Série"; String t5 = "série"; String t7 = "Livro"; String t8 = "livro";
        if(conteudoTipo!= t1 ||conteudoTipo!= t2 ||conteudoTipo!= t3 ||conteudoTipo!= t4 ||
                conteudoTipo!= t6 ||conteudoTipo!= t5 ||conteudoTipo!= t7 ||conteudoTipo!= t8 ){
            Toast.makeText(this, "tipo invalido", Toast.LENGTH_SHORT).show();
            return;
        }*/
        Conteudo conteudo = new Conteudo();
        conteudo.setTitulo(conteudoTitulo);
        conteudo.setGenero(conteudoGenero);
        conteudo.setTipo(conteudoTipo);
        conteudo.setLancamento(conteudoLancamento);
        conteudo.setAvaliacao(String.valueOf(conteudoAvaliacao));
        conteudo.setComentario(conteudoComentario);
        conteudo.setTimestamp(Timestamp.now());

        saveConteudoToFirebase(conteudo);

    }

    void saveConteudoToFirebase(Conteudo conteudo){
        String conteudoTipo = et_tipo.getText().toString();
        DocumentReference documentReference;
        if(isEditMode){
            //update
            documentReference = Utility.getCollectionReference().document(docId);

        }else{
            //create new
            documentReference = Utility.getCollectionReference().document();
        }



        documentReference.set(conteudo).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                //note is added
                Toast.makeText(DetalheActivity.this, "fooooi", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(DetalheActivity.this, "num foi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteConteudoFromFirebase() {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReference().document(docId);

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

    public void rating(){
        ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            String notaa = String.valueOf(ratingBar.getRating());
           // Toast.makeText(getApplicationContext(), notaa, Toast.LENGTH_SHORT).show();
            et_avaliacao.setText(notaa);
        });

    }

    public void radioTipo(){

        rb_serie.setOnClickListener(view -> {
            et_tipo.setText("Série");
        });
        rb_livro.setOnClickListener(view -> {
            et_tipo.setText("Livro");
        });
        rb_filme.setOnClickListener(view -> {
            et_tipo.setText("Filme");
        });
        rb_jogo.setOnClickListener(view -> {
            et_tipo.setText("Jogo");
        });
    }



    public void find(){
        et_titulo = findViewById(R.id.conteudo_titulo);
        et_genero = findViewById(R.id.conteudo_genero);
        et_lancamento = findViewById(R.id.conteudo_lancamento);
        et_avaliacao = findViewById(R.id.conteudo_avaliacao);
        et_comentario = findViewById(R.id.conteudo_comentarios);
        et_tipo = findViewById(R.id.conteudo_tipo);
        ratingBar = findViewById(R.id.ratingBar);

        pageTitleTextView = findViewById(R.id.page_title);
        deleteTextViewBtn  = findViewById(R.id.delete_text_view);
        saveTextViewBtn  = findViewById(R.id.salvar_text_view2);
        rd_group = findViewById(R.id.rd_group);
        rb_filme = findViewById(R.id.rb_filme);
        rb_serie = findViewById(R.id.rb_serie);
        rb_jogo = findViewById(R.id.rb_jogo);
        rb_livro = findViewById(R.id.rb_livro);

    }

}