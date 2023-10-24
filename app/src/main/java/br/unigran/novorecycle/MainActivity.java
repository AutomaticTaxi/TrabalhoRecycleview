package br.unigran.novorecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button BtEditar;
    private EditText Nome;
    private EditText Telefone;
    private Button BtAdd;
    private List<Pessoa> pessoas;
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        vincularXML();
        BtEditar.setVisibility(View.INVISIBLE);

        pessoas=new ArrayList<>();
        FireBaseLer();
        Click_na_Lista clickNaLista = new Click_na_Lista() {
            @Override
            public void onClickEditar(Pessoa pessoa) {
                String Nome_da_pessoa_clicada = pessoa.nome.toString();
                BtEditar.setVisibility(View.VISIBLE);
                BtAdd.setVisibility(View.INVISIBLE);
                Nome.setText(pessoa.nome.toString());
                Telefone.setText(pessoa.telefone.toString());
                BtEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Nome.getText().toString().isEmpty()==false){
                            Toast.makeText(MainActivity.this, "Atualizado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            Pessoa pessoaatualizado = new Pessoa();
                            pessoaatualizado.nome = Nome.getText().toString();
                            pessoaatualizado.telefone = Telefone.getText().toString();
                            FireBaseUpdate(Nome_da_pessoa_clicada,pessoaatualizado);
                            pessoas.clear();
                            FireBaseLer();

                            limpar_campos();
                            BtEditar.setVisibility(View.INVISIBLE);
                            BtAdd.setVisibility(View.VISIBLE);
                        }
                    }
                });


            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MeuAdapter(pessoas,clickNaLista));
        acoes();

    }
    public void acoes(){
        BtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"TesteBt",Toast.LENGTH_LONG).show();
                Pessoa p = new Pessoa();
                p.nome = Nome.getText().toString();
                p.telefone = Telefone.getText().toString();
                FireBaseSalvar(p);
                limpar_campos();
            }
        });
    }


    public void FireBaseSalvar(Pessoa pessoa){
        DatabaseReference pessoaBD =databaseReference.child("Pessoa");
        pessoaBD.push().setValue(pessoa);

    }
    public void FireBaseLer(){
        DatabaseReference PessoaBD =  databaseReference.child("Pessoa");
        PessoaBD.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue()!= null) {
                    Log.i("FireBase", snapshot.getValue().toString());
                    pessoas.clear();

                    for (DataSnapshot dados : snapshot.getChildren()) {
                        // Log.i("FireBase",dados.child("01").getValue().toString());


                        pessoas.add(dados.getValue(Pessoa.class));
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FireBase",error.toString());
            }
        });
    }
    public void FireBaseUpdate(String nome,Pessoa pessoa ){
        String Nome_da_cabelereiara_Antigo = nome;
        DatabaseReference PessoaRef = databaseReference.child("Pessoa");

        Query query = PessoaRef.orderByChild("nome").equalTo(Nome_da_cabelereiara_Antigo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String chaveDoObjetoEncontrado = data.getKey();

                    DatabaseReference PessoaRefToUpdate = PessoaRef.child(chaveDoObjetoEncontrado);

                    PessoaRefToUpdate.setValue(pessoa);


                    break;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Lidar com erros, se necessário
            }
        });


    }
    public void vincularXML(){
        Nome = findViewById(R.id.edNome);
        Telefone = findViewById(R.id.editTextPhone);
        BtAdd = findViewById(R.id.BtSalvar);
        recyclerView=findViewById(R.id.idRecycle);
        BtEditar = findViewById(R.id.button3);

    }
    public void limpar_campos(){
        Nome.setText("");
        Telefone.setText("");

    }
}