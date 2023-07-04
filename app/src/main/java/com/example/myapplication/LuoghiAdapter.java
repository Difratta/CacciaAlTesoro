package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LuoghiAdapter extends RecyclerView.Adapter<LuoghiAdapter.LuogoViewHolder> {

    private List<MarkerInfo> luoghi;

    public LuoghiAdapter(List<MarkerInfo> luoghi) {
        this.luoghi = luoghi;
    }

    @NonNull
    @Override
    public LuogoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.luogo_item_layout, parent, false);
        return new LuogoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LuogoViewHolder holder, int position) {
        MarkerInfo luogo = luoghi.get(position);
        holder.bind(luogo);
    }

    @Override
    public int getItemCount() {
        return luoghi.size();
    }

    public class LuogoViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeTextView;

        private TextView suggerimentoTextView;
        private TextView descrizioneTextView;


        public LuogoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nome_text_view);
            suggerimentoTextView = itemView.findViewById(R.id.suggerimento_text_view);
            descrizioneTextView = itemView.findViewById(R.id.descrizione_text_view);
        }

        public void bind(MarkerInfo luogo) {
            nomeTextView.setText(luogo.getNome());
            suggerimentoTextView.setText(luogo.getSuggerimento());
            if(luogo.getTrovato()) {
                descrizioneTextView.setText(luogo.getDescrizione());
            } else {
                descrizioneTextView.setText("Non trovato");
            }
        }
    }
}
