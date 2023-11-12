package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ConteudoAdapter extends FirestoreRecyclerAdapter<Conteudo, ConteudoAdapter.ConteudoViewHolder> {
    Context context;


    public ConteudoAdapter(@NonNull FirestoreRecyclerOptions<Conteudo> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ConteudoViewHolder holder, int position, @NonNull Conteudo conteudo) {
        holder.tituloTextView.setText(conteudo.titulo);
        holder.avaliacaoTextView.setText(conteudo.avaliacao);
        holder.tipoTextView.setText(conteudo.tipo);
        holder.timestampTextView.setText(Utility.timestampToString(conteudo.timestamp));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,DetalheActivity.class);
            intent.putExtra("titulo",conteudo.titulo);
            intent.putExtra("genero",conteudo.genero);
            intent.putExtra("lancamento",conteudo.lancamento);
            intent.putExtra("avaliacao",conteudo.avaliacao);
            intent.putExtra("tipo",conteudo.tipo);
            intent.putExtra("comentario",conteudo.comentario);



            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public ConteudoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conteudo_item,parent, false);
        return new ConteudoViewHolder(view);
    }

    class ConteudoViewHolder extends RecyclerView.ViewHolder{

        TextView tituloTextView, avaliacaoTextView, timestampTextView, tipoTextView;

        public ConteudoViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.conteudo_titulo_view);
            avaliacaoTextView = itemView.findViewById(R.id.conteudo_nota_view);
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);
            tipoTextView = itemView.findViewById(R.id.conteudo_tipo_view);
        }
    }
}
