package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ImgViewHolder> {

    private final String TAG = "LuoghiAdapter";
    private ArrayList<MarkerInfo> luoghi;

    public ImgAdapter(ArrayList<MarkerInfo> luoghi) {
        this.luoghi = luoghi;
    }

    @NonNull
    @Override
    public ImgAdapter.ImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imm_item_layout, parent, false);
        return new ImgAdapter.ImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgAdapter.ImgViewHolder holder, int position) {
        MarkerInfo luogo = luoghi.get(position);
        holder.bind(luogo);

    }
    @Override
    public int getItemCount() {
        if (luoghi == null) {
            Log.d(TAG, "getItemCount: luoghi è null");
            return 0;
        }
        return luoghi.size();
    }

    public class ImgViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeD;
        private ImageView imageD;


        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeD = itemView.findViewById(R.id.nomeD);
            imageD = itemView.findViewById(R.id.imageD);
        }
        public void bind(MarkerInfo luogo) {
            String numero = luogo.getNome().substring(luogo.getNome().lastIndexOf(" ") + 1);
            Log.d(TAG, "bind: " + numero);
            int num = Integer.parseInt(numero);
            if(luogo.getTrovato()) {
                String nome = "oggetto_" + (num);
                int resD = itemView.getResources().getIdentifier(nome, "drawable", itemView.getContext().getPackageName());
                nomeD.setText(luogo.getNome());
                imageD.setImageResource(resD);
            }
            else {
                nomeD.setText("?");
                imageD.setImageResource(R.drawable.resource_default);
            }
        }
    }
}
