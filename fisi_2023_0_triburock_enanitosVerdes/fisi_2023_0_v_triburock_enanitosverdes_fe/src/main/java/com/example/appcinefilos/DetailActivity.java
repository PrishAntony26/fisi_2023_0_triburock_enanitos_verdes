package com.example.appcinefilos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity{
    private ImageView imgItemDetail;
    private TextView tvTituloDetail;
    private TextView tvDescripcionDetail;
    private  TextView tvGenero;
    private  TextView tvDuracion;
    private TextView tvEdad;

    private MovieModelClass itemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(getClass().getSimpleName());

        initViews();
        initValues();
    }

    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        tvTituloDetail = findViewById(R.id.tvTituloDetail);
        tvDescripcionDetail = findViewById(R.id.tvDescripcionDetail);
        tvGenero = findViewById(R.id.tvGenero);
        tvDuracion = findViewById(R.id.tvDuracion);
        tvEdad = findViewById(R.id.tvEdad);
    }

    @SuppressLint("ResourceType")
    private void initValues(){

        itemDetail = (MovieModelClass) getIntent().getExtras().getSerializable("itemDetail");

        Picasso.get().load(itemDetail.getImagen()).resize(1000,1250).into(imgItemDetail);

        tvTituloDetail.setText(itemDetail.getNombre());
        tvDescripcionDetail.setText(itemDetail.getSinopsis());
        tvGenero.setText("Género: "+itemDetail.getGenero());
        tvDuracion.setText("Duración: "+itemDetail.getDuracion() +" minutos");
        tvEdad.setText("Clasificación de edad: "+itemDetail.getClasificacion_edad()+" años");

    }
}
