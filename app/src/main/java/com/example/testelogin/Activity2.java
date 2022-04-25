package com.example.testelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    DBHelper helper = new DBHelper(this);
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private Button btnSalvar;
    private Contato contato;
    private Contato altContato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfSenha = findViewById(R.id.edtConfSenha);
        btnSalvar=findViewById(R.id.btnSalvar);

        Intent it=getIntent();
        altContato = (Contato) it.getSerializableExtra("chave_contato");
        contato = new Contato();

        if(altContato != null){
            btnSalvar.setText("ALTERAR");
            edtNome.setText(altContato.getNome());
            edtEmail.setText(altContato.getEmail());
            edtUsuario.setText(altContato.getUsuario());
            edtSenha.setText(altContato.getSenha());
            edtConfSenha.setText(altContato.getSenha());
            contato.setId(altContato.getId());
        }else{
            btnSalvar.setText("SALVAR");
        }
    }
    public void cadastrar(View view) {
        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String usuario = edtUsuario.getText().toString();
        String senha = edtSenha.getText().toString();
        String confSenha = edtConfSenha.getText().toString();

        if(!senha.equals(confSenha)){
            Toast toast = Toast.makeText(Activity2.this,
                    "Senha diferente da confirmação de senha!",
                    Toast.LENGTH_SHORT);
            toast.show();
            edtSenha.setText("");
            edtConfSenha.setText("");
        }
        else{
            contato.setNome(nome);
            contato.setEmail(email);
            contato.setUsuario(usuario);
            contato.setSenha(senha);
            if(btnSalvar.getText().toString().equals("SALVAR")) {
                helper.insereContato(contato);
                Toast toast = Toast.makeText(Activity2.this,
                        "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                    helper.atualizarContato(contato);
                    helper.close();
            }
            limpar();
            finish();
        }
    }
    public void limpar(){
        edtNome.setText("");
        edtEmail.setText("");
        edtUsuario.setText("");
        edtSenha.setText("");
        edtConfSenha.setText("");
    }
    public void cancelar(View view) {
        finish();
    }
}
