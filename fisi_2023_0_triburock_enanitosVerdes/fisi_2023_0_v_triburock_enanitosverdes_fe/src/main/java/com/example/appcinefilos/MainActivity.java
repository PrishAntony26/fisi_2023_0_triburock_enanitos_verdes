package com.example.appcinefilos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, Adaptery.RecyclerItemClick{

   // private static String JSON_URL="https://api.themoviedb.org/3/movie/popular?api_key=f5532515a204560032351b22689be3fc";
    private static String JSON_URL="https://fisi-2022-2-triburock-libido.azurewebsites.net/obtenerpelicula";
    List<MovieModelClass> movieList;
    RecyclerView recyclerView;
    ImageView header;
    private SearchView svSearch;
    Adaptery adaptery;
    Button siguiente;


    private void initListener(){
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList= new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        svSearch = findViewById(R.id.svSearch);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));

        header = findViewById(R.id.imageView2);
        Glide.with(this)
                .load(R.drawable.portada_cinefilo)
                .into(header);

        GetData getData= new GetData();
        getData.execute();
        initListener();

      /*  siguiente=(Button) findViewById(R.id.siguiente);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Adquirir_membresia.class);
                startActivity(i);
            }
        });*/
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow,menu);
        return false;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.item1){
            Toast.makeText(this,"Cargando...", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
        }else if(id==R.id.item2){
            Toast.makeText(this,"Falta implementar 2", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Suscr_exitosa.class);
            startActivity(i);
        }else if(id==R.id.item3){
            Toast.makeText(this,"Adquirir membresía", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, Adquirir_membresia.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adaptery.filter(s);
        return false;
    }


    @Override
    public void itemClick(MovieModelClass item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("itemDetail",item);
        startActivity(intent);
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current="";
            try {
                URL url;
                HttpURLConnection urlConnection=null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is= urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data !=-1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if(urlConnection!= null){
                        urlConnection.disconnect();
                    }
                }
            }catch ( Exception e ){
                e.printStackTrace();
            }
            return   current;
        }

        @Override
        protected void onPostExecute(String s){
            try {
                JSONArray jsonObject=new JSONArray(s);
                System.out.println(jsonObject);
             //   JSONArray jsonArray = jsonObject.getJSONArray();

                for (int i=0; i < jsonObject.length();i++){
                    JSONObject jsonObject1= jsonObject.getJSONObject(i);
                    System.out.println(jsonObject1.getString("nombre"));
                    MovieModelClass model = new MovieModelClass();
                    //model.setId_pelicula(jsonObject1.getString("id_pelicula"));
                    model.setNombre(jsonObject1.getString("nombre"));
                    model.setImagen(jsonObject1.getString("imagen"));

                    if (jsonObject1.has("genero")) {
                        model.setGenero(jsonObject1.getString("genero"));
                    }else{
                        model.setGenero("sin genero");                    }
                    model.setSinopsis(jsonObject1.getString("sinopsis"));
                    model.setDuracion(jsonObject1.getString("duracion"));
                    model.setClasificacion_edad(jsonObject1.getString("clasificacion_edad"));
                    movieList.add(model);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);
        }

    }

    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList) {

         adaptery = new Adaptery(this, movieList,MainActivity.this);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptery);
    }
}