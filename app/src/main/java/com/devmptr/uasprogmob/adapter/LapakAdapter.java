package com.devmptr.uasprogmob.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devmptr.uasprogmob.PembayaranActivity;
import com.devmptr.uasprogmob.R;
import com.devmptr.uasprogmob.model.LapakModel;

import java.util.List;

public class LapakAdapter extends RecyclerView.Adapter<LapakAdapter.ViewHolder> {
    private List<LapakModel> modelLapak;
    private Context context;

    public LapakAdapter (List<LapakModel> lapak){
        modelLapak = lapak;
    }



    @NonNull
    @Override
    public LapakAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_lapak, parent, false);

        LapakAdapter.ViewHolder viewHolder = new LapakAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LapakAdapter.ViewHolder holder, int position) {
        final LapakModel lapak = modelLapak.get(position);

        holder.namaLapak.setText("Lapak "+lapak.getNamaLapak());
        holder.namaPemilik.setText(lapak.getNamaPemilik()+" | Area "+lapak.getPosisiLapak());

        holder.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PembayaranActivity.class).putExtra("lapak_id",lapak.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelLapak.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView namaLapak, namaPemilik;
        public Button btnBayar;

        public ViewHolder (View view) {
            super(view);

            namaLapak = (TextView) view.findViewById(R.id.nama_lapak);
            namaPemilik = (TextView) view.findViewById(R.id.nama_pemilik);
            btnBayar = (Button) view.findViewById(R.id.btn_bayar);
            context = view.getContext();
        }
    }
}
