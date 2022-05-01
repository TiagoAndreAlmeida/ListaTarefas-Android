package com.example.listatarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listatarefas.R;
import com.example.listatarefas.helper.TarefaDAO;
import com.example.listatarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefa extends AppCompatActivity {

    private TextInputEditText textInputEditText;
    private Boolean atualizando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        textInputEditText = findViewById(R.id.editTextTarefa);

        Tarefa selecionada = (Tarefa) getIntent().getSerializableExtra("selecionada");

        if(selecionada != null) {
            textInputEditText.setText(selecionada.getNome());
            atualizando = true;
        } else {
            atualizando = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Integer menuId = item.getItemId();
        switch (menuId) {
            case R.id.salvar_action:
                if(atualizando) {
                    atualizarTarefa();
                } else {
                    criarNova();
                }

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void criarNova () {
        String tarefaNome = textInputEditText.getText().toString();
        Tarefa tarefa = new Tarefa(tarefaNome);
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefaDAO.salvar(tarefa);
        Toast.makeText(getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
    }

    void atualizarTarefa() {
        Tarefa selecionada = (Tarefa) getIntent().getSerializableExtra("selecionada");
        String tarefaNome = textInputEditText.getText().toString();
        Tarefa tarefa = new Tarefa(tarefaNome, selecionada.getId());
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        tarefaDAO.atualizar(tarefa);
        Toast.makeText(getApplicationContext(), "Atualizado", Toast.LENGTH_SHORT).show();
    }
}