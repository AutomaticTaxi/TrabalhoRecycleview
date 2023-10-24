package br.unigran.novorecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MeuAdapter extends RecyclerView.Adapter<MeuAdapter.MeuViewHolder> {
    private List<Pessoa> dados;
    public MeuAdapter(List dados){
        this.dados=dados;
    }
    @NonNull
    @Override
    public MeuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.line_recycle,parent,false);
        return new MeuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeuViewHolder holder, int position) {
        holder.nome.setText(dados.get(position).nome);
        holder.telefone.setText(dados.get(position).telefone);
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class MeuViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        TextView telefone;
        Button b;

        public MeuViewHolder(@NonNull View itemView) {
            super(itemView);

           nome= itemView.findViewById(R.id.idNome);
           telefone= itemView.findViewById(R.id.idTelefone);

          //implementação botão
           b=itemView.findViewById(R.id.button);
           b.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

               }
           });
        }
    }
}
