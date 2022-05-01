package com.example.listatarefas.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listatarefas.R;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatarefas.adapter.ListaTarefaAdapter;
import com.example.listatarefas.databinding.ActivityMainBinding;
import com.example.listatarefas.helper.DBHelper;
import com.example.listatarefas.helper.RecyclerItemClickListener;
import com.example.listatarefas.helper.TarefaDAO;
import com.example.listatarefas.model.Tarefa;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private List<Tarefa> tarefas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        View view = findViewById(R.id.constrainLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefa.class);
                Tarefa tarefaSelecionada = tarefas.get(position);

                intent.putExtra("selecionada", tarefaSelecionada);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Tarefa tarefa = tarefas.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja apagar "+tarefa.getNome()+ " ?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                        Boolean deleteou = tarefaDAO.deletar(tarefa);
                        if(deleteou) {
                            Toast.makeText(getApplicationContext(), "Deletado com sucesso", Toast.LENGTH_SHORT).show();
                            carregarTarefas();
                        } else {
                            Toast.makeText(getApplicationContext(), "Deu ruim", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não", null);
                dialog.show();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



        dbHelper = new DBHelper(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTarefas();
    }

    public void carregarTarefas() {
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefas = tarefaDAO.listar();

        ListaTarefaAdapter adapter = new ListaTarefaAdapter(tarefas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefa.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}