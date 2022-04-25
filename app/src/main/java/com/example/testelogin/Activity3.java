package com.example.testelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {
    private TextView txtNome;
    private ListView listContatos;
    DBHelper helper;
    Contato contato;
    ArrayList<Contato> arrayListContato;
    ArrayAdapter<Contato> arrayAdapterContato;
    private int id1,id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        txtNome = findViewById(R.id.txtNome);
        Bundle args = getIntent().getExtras();
        String nome = args.getString("chave_usuario");
        txtNome.setText("Bem vindo "+nome);

        listContatos=findViewById(R.id.listContatos);
        registerForContextMenu(listContatos);

        listContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contato contatoEnviada = (Contato)
                        arrayAdapterContato.getItem(i);
                Intent it = new Intent(Activity3.this,Activity2.class);
                it.putExtra("chave_contato",contatoEnviada);
                startActivity(it);
            }
        });
        listContatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, int
                    position, long id){
                contato = arrayAdapterContato.getItem(position);
                return false;
            }
        });
    }
    public void preencheLista(){
        helper=new DBHelper(Activity3.this);
        arrayListContato = helper.buscarContatos();
        helper.close();
        if(listContatos!=null){
            arrayAdapterContato = new ArrayAdapter<Contato>(
                    Activity3.this,
                    android.R.layout.simple_list_item_1,
                    arrayListContato);
            listContatos.setAdapter(arrayAdapterContato);
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        preencheLista();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add(Menu.NONE, 1, 1,"Deleta Registro");
        MenuItem mSair = menu.add(Menu.NONE, 2, 2,"Cancela");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoBD=1;
                helper=new DBHelper(Activity3.this);
                retornoBD = helper.excluirContato(contato);
                helper.close();
                if(retornoBD==-1){
                    alert("Erro de exclusão!");
                }
                else{
                    alert("Registro excluído com sucesso!");
                }
                preencheLista();
                return false; }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}