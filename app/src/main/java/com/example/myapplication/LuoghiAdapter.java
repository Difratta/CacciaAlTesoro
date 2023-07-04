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
        private TextView latitudineTextView;
        private TextView longitudineTextView;
        private TextView trovatoTextView;
        private TextView descrizioneTextView;


        public LuogoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nome_text_view);
            latitudineTextView = itemView.findViewById(R.id.latitudine_text_view);
            longitudineTextView = itemView.findViewById(R.id.longitudine_text_view);
            trovatoTextView = itemView.findViewById(R.id.trovato_text_view);
            descrizioneTextView = itemView.findViewById(R.id.descrizione_text_view);
        }

        public void bind(MarkerInfo luogo) {
            nomeTextView.setText(luogo.getNome());
            latitudineTextView.setText(String.valueOf(luogo.getLatitudine()));
            longitudineTextView.setText(String.valueOf(luogo.getLongitudine()));
            trovatoTextView.setText(String.valueOf(luogo.getTrovato()));
            descrizioneTextView.setText(luogo.getDescrizione());
        }
    }
}
