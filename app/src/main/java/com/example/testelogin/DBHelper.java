package com.example.testelogin;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "contato1.db";
    private static final String TABLE_NAME = "contato";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "nome";
    private static final String COL_EMAIL = "email";
    private static final String COL_USER = "usuario";
    private static final String COL_PASS = "senha";
    SQLiteDatabase db;
    private static final String TABLE_CREATE="create table "+TABLE_NAME+
            "("+COL_ID+" integer primary key autoincrement, "+
            COL_NAME+" text not null, "+COL_EMAIL+" text not null, " +
            COL_USER+" text not null, "+COL_PASS+" text not null);";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
    public void insereContato(Contato c){
        db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_NAME, c.getNome());
            values.put(COL_EMAIL, c.getEmail());
            values.put(COL_USER,c.getUsuario());
            values.put(COL_PASS, c.getSenha());
            db.insertOrThrow(TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }
    public String buscarSenha(String usuario){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                COL_PASS, TABLE_NAME, COL_USER);
        String senha="não encontrado";
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(usuario)});
            try {
                if (cursor.moveToFirst()) {
                    senha = cursor.getString(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return senha;
    }
    public ArrayList<Contato> buscarContatos(){
        String[] coluns={ COL_ID, COL_NAME,COL_EMAIL,COL_USER, COL_PASS};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,
                coluns,null,null,null,
                null,"upper(nome)",null);
        ArrayList<Contato> listaContato = new ArrayList<Contato>();
        while(cursor.moveToNext()){
            Contato c = new Contato();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setEmail(cursor.getString(2));
            c.setUsuario(cursor.getString(3));
            c.setSenha(cursor.getString(4));
            listaContato.add(c);
        }
        return listaContato;
    }
    public long excluirContato(Contato contato) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(contato.getId())};
        retornoBD=db.delete(TABLE_NAME, COL_ID+"=?",args);
        return retornoBD;
    }
    public long atualizarContato(Contato c){
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME,c.getNome());
        values.put(COL_EMAIL,c.getEmail());
        values.put(COL_USER, c.getUsuario());
        values.put(COL_PASS,c.getSenha());
        String[] args = {String.valueOf(c.getId())};
        retornoBD=db.update(TABLE_NAME,values,"id=?",args);
        db.close();
        return retornoBD;
    }
}
