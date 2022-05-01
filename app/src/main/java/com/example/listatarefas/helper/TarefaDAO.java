package com.example.listatarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listatarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve, le;

    public TarefaDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNome());
        try {
            escreve.insert(DBHelper.TABELA_NAME, null, cv);
            Log.i("DB INFO", "Tarefa salva");
        } catch (Exception e) {
            Log.i("DB INFO", "erro salvar tarefa "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNome());
        try {
            String [] args = {tarefa.getId().toString()};
            escreve.update(DBHelper.TABELA_NAME, cv, "id=?", args);
            Log.i("DB INFO", "Tarefa ATUALIZADA");
        } catch (Exception e) {
            Log.i("DB INFO", "Error atualizar "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        Log.i("DB INFO", "TAREFA ID"+tarefa.getId().toString());
        try {
            String [] args = {tarefa.getId().toString()};
            escreve.delete(DBHelper.TABELA_NAME, "id=?", args);
            Log.i("DB INFO", "Tarefa ATUALIZADA");
        } catch (Exception e) {
            Log.i("DB INFO", "Error atualizar "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String SQL = "SELECT * FROM "+DBHelper.TABELA_NAME+";";
        Cursor c = le.rawQuery(SQL, null);

        while (c.moveToNext()) {
            Tarefa tarefa = new Tarefa();
            Integer id = c.getInt(c.getColumnIndexOrThrow("id") );
            String nome = c.getString(c.getColumnIndexOrThrow("nome"));

            tarefa.setId(id);
            tarefa.setNome(nome);

            Log.i("DB", tarefa.getNome());

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
