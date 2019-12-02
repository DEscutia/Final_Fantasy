package com.example.finalproyect.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.finalproyect.BDD.BD;
import com.example.finalproyect.Clases.Ruta;

import java.util.ArrayList;

public class DaoRutas {
    SQLiteDatabase _sqLiteDatabase;
    Context ctx;

    public DaoRutas(Context ctx){
        this.ctx = ctx;
        _sqLiteDatabase =
                new BD(ctx).getWritableDatabase();
    }

    public long insert(Ruta ruta){
        ContentValues contentValues
                = new ContentValues();

        String path = ruta.getRuta().toString();
        contentValues.put(BD.COLUMNS_NAME_RUTAS[1],
                path);
        contentValues.put(BD.COLUMNS_NAME_RUTAS[2],
                ruta.getTipo());
        contentValues.put(BD.COLUMNS_NAME_RUTAS[3],
                ruta.getIdTarea());

        return  _sqLiteDatabase.insert(BD.TABLE_NAME_RUTAS,
                null, contentValues);
    }

    public int eliminar (int id){

        String[] argumentos = {String.valueOf(id)};
        return _sqLiteDatabase.delete(BD.TABLE_NAME_RUTAS, "_id = ?", argumentos);

    }

    public ArrayList<Uri> buscarRutas(String[] id){
        ArrayList<Uri> rutas = new ArrayList<>();

        String[] columnasAConsultar = {BD.COLUMNS_NAME_RUTAS[1]};
        Cursor cursor = _sqLiteDatabase.query(BD.TABLE_NAME_RUTAS, columnasAConsultar, "idTarea = ?", id, null, null, null);

        if(id[0].equals("")){

            cursor = _sqLiteDatabase.query(BD.TABLE_NAME_RUTAS, columnasAConsultar, null, null, null, null, null);
        }

        if (cursor == null){
            return rutas;
        }

        if (!cursor.moveToFirst()) return rutas;

        do {

            Uri pathObtenidoDeBD = Uri.parse(cursor.getString(0));

            rutas.add(pathObtenidoDeBD);

        } while (cursor.moveToNext());

        cursor.close();
        return rutas;
    }

    public ArrayList<Ruta> buscarObjeto(String[] id){
        ArrayList<Ruta> rutas = new ArrayList<>();

        ////////////////
        String[] columnasAConsultar = {BD.COLUMNS_NAME_RUTAS[0],BD.COLUMNS_NAME_RUTAS[1],BD.COLUMNS_NAME_RUTAS[2],BD.COLUMNS_NAME_RUTAS[3]};
        Cursor cursor = _sqLiteDatabase.query(BD.TABLE_NAME_RUTAS, columnasAConsultar, "idTarea = ?", id, null, null, null);

        if(id[0].equals("")){

            cursor = _sqLiteDatabase.query(BD.TABLE_NAME_RUTAS, columnasAConsultar, null, null, null, null, null);
        }

        if (cursor == null){
            return rutas;
        }

        if (!cursor.moveToFirst()) return rutas;

        do {

            int idObtenidoDeBD = cursor.getInt(0);
            Uri pathObtenidoDeBD = Uri.parse(cursor.getString(1));
            int tipoObtenidoDeBD = cursor.getInt(2);
            int idTareaObtenidoDeBD = cursor.getInt(3);

            Ruta rutaObtenidoDeBD = new Ruta(idObtenidoDeBD, pathObtenidoDeBD, tipoObtenidoDeBD, idTareaObtenidoDeBD);
            rutas.add(rutaObtenidoDeBD);

        } while (cursor.moveToNext());

        cursor.close();
        return rutas;
    }
}
