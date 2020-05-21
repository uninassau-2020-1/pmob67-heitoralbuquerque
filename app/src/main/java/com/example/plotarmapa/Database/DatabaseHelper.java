package com.example.plotarmapa.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table usuario(Email text primary key, Senha text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop  table if  exists usuario");
    }

    //Inserir no banco
    public boolean Insert(String email, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("Senha", senha);
        long ins = db.insert("Usuario", null, contentValues);

        if(ins ==- 1){
            return false;
        }
        else {
            return true;
        }
    }

    //Checar se e-mail existe
    public boolean checkEmail(String email){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery("Select * from Usuario where Email = ?", new String[] {email});
        if (cursor.getCount() > 0) {
            return false;
        } else
            return true;

    }

    public boolean  Autentication(String email, String senha){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("Select * from Usuario where Email=? and Senha=?", new String[]{email, senha});
        if (cursor.getCount() > 0 ){
            return   true;
        } else
            return   false;

    }


}
