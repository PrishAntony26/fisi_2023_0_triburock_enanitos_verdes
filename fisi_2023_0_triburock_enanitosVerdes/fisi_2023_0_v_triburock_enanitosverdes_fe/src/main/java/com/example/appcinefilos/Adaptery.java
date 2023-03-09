package com.example.appcinefilos;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import android.os.Build;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<MovieModelClass> mData;
    private List<MovieModelClass> originalMovies;
    private RecyclerItemClick itemClick;



    public Adaptery(Context mContext, List<MovieModelClass> mData,RecyclerItemClick itemClick) {
        this.mContext = mContext;
        this.mData = mData;
        this.originalMovies = new ArrayList<>();
        originalMovies.addAll(mData);
        this.itemClick=itemClick;
    }

    public void filter(String svSearch){
        //String svSearch="bl";
        if(svSearch.length()==0){
            mData.clear();
            mData.addAll(originalMovies);
        }else{
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
                mData.clear();
                List<MovieModelClass> collect=originalMovies.stream()
                        .filter(m -> m.getNombre().toLowerCase().contains(svSearch))
                        .collect(Collectors.toList());
                mData.addAll(collect);
            }else{
                System.out.println("BUSQUEDA NO COMPLETADA");
                mData.clear();
                for (MovieModelClass i : originalMovies){
                    if(i.getNombre().toLowerCase().contains(svSearch)){
                        mData.add(i);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
       //v= inflater.inflate(R.layout.movie_items, parent,false);
        v= inflater.inflate(R.layout.item_movie, parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MovieModelClass item = mData.get(position);
        holder.duracion.setText(mData.get(position).getDuracion()+"min");
        holder.genero.setText(mData.get(position).getGenero());
        holder.categoria.setText(mData.get(position).getCategoria());

        holder.name.setText(mData.get(position).getNombre());
        //https://image.tmdb.org/t/p/w500/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg

        Glide.with(mContext)
                .load(mData.get(position).getImagen())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView name;
        TextView genero;
        TextView duracion;
        TextView categoria;

        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

         /*   genero=itemView.findViewById(R.id.id_txt);
            duracion=itemView.findViewById(R.id.id_txt2);
            categoria=itemView.findViewById(R.id.id_txt3);
            name = itemView.findViewById(R.id.name_txt);
            img=itemView.findViewById(R.id.imageView);*/
            name = itemView.findViewById(R.id.name_txt_f);
            img=itemView.findViewById(R.id.imageView_f);
            duracion=itemView.findViewById(R.id.name_txt_2);
            categoria=itemView.findViewById(R.id.name_txt_3);
            genero=itemView.findViewById(R.id.name_txt_1);

        }
    }

    public interface RecyclerItemClick{
        void itemClick(MovieModelClass item);
    }

}
