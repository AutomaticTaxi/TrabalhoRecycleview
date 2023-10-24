package br.unigran.novorecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Pessoa> pessoas;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pessoas=new ArrayList<>();
        pessoas.add(new Pessoa("ANdre","9999"));
        pessoas.add(new Pessoa("ANdre2","9999"));
        pessoas.add(new Pessoa("ANdre3","9999"));

        recyclerView=findViewById(R.id.idRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MeuAdapter(pessoas));

        pessoas.add(new Pessoa("ANdre4","9999"));




    }
}