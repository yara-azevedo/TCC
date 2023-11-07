package com.example.myapplication;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConteudoService {
    public interface OnConteudoLoadListener {
        void onConteudoLoad(List<Conteudo> conteudos);

        void onError(Exception e);
    }

    public static void obterConteudosPorTipo(String tipo, OnConteudoLoadListener listener) {
        CollectionReference conteudoCollection = FirebaseFirestore.getInstance().collection("conteudo");

        Query query = conteudoCollection.whereEqualTo("tipo", tipo);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Conteudo> conteudos = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Conteudo conteudo = document.toObject(Conteudo.class);
                        conteudos.add(conteudo);
                    }
                    listener.onConteudoLoad(conteudos);
                } else {
                    Exception e = task.getException();
                    listener.onError(e);
                }
            }
        });
    }
}
