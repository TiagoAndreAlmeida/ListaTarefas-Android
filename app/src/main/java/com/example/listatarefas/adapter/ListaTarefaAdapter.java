package com.example.listatarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatarefas.R;
import com.example.listatarefas.model.Tarefa;

import java.util.List;

public class ListaTarefaAdapter extends RecyclerView.Adapter<ListaTarefaAdapter.MyViewHolder> {

    private List<Tarefa> listaTarefa;

    public ListaTarefaAdapter(List<Tarefa> list) {
        this.listaTarefa = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_adapter , parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefa tarefa = listaTarefa.get(position);
        holder.textView.setText(tarefa.getNome());
    }

    @Override
    public int getItemCount() {
        return listaTarefa.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewAdapter);
        }
    }
}
