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
        holder.titleTextView.setText(conteudo.title);
        holder.contentTextView.setText(conteudo.content);
        holder.timestampTextView.setText(Utility.timestampToString(conteudo.timestamp));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,DetalheActivity.class);
            intent.putExtra("title",conteudo.title);
            intent.putExtra("content",conteudo.content);
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

        TextView titleTextView,contentTextView,timestampTextView;

        public ConteudoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_content_text_view);
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);
        }
    }
}
