package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.backup.BackupDataInput;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_desc , et_precio;
    private Button btn_ingreso, btn_busq,btn_elim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.et_codigo = (EditText) findViewById(R.id.Codigo);
        this.et_desc = (EditText) findViewById(R.id.Descripcion);
        this.et_precio  = (EditText) findViewById(R.id.Precio);

        this.btn_ingreso= (Button) findViewById(R.id.Ingreso);
        this.btn_ingreso.setOnClickListener({Registro();});

        this.btn_busq = (Button) findViewById(R.id.Busqueda);
        this.btn_busq.setOnClickListener();

        this.btn_elim = (Button) findViewById(R.id.eliminar);
        this.btn_elim.setOnClickListener();
    }
    public void Registro() {
        Conexion miobjetivo = new Conexion(this, "Administracion", null, 1);

        SQLiteDatabase BD = miobjetivo.getReadableDatabase();

        //CAPTURAMOS LOS VALORES INGRESADOS
        String cod = this.et_codigo.getText().toString();
        String des = this.et_desc.getText().toString().toUpperCase();
        String pr = this.et_precio.getText().toString();

        try {
            if (!cod.isEmpty() && !des.isEmpty() && !pr.isEmpty())//los 3 campos no nulos
            {
                ContentValues reg = new ContentValues();
                reg.put("Codigo", cod);
                reg.put("descrip", des);
                reg.put("precio", pr);
                BD.insert("articulos", null, reg);
                BD.close();
                this.limpiar();
                Toast.makeText(this, "Datos Almacenados", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, " Ingrese Todos Los valores solicitados", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } finally {
            BD.close();
 
        }
    }

        public void Busqueda() {
            String cod = this.et_codigo.getText().toString();
            Conexion miobjeto = new Conexion(this, "administracion", null,1);
            SQLiteDatabase BD = miobjeto.getWritableDatabase();
        try{
            if(!cod.isEmpty()){
                Cursor fil = BD.rawQuery("select descrip, precio from articulos where codigo ="+cod, null);
                if(fil.moveToFirst()){
                    this.et_desc.setText(fil.getString(0));
                    this.et_precio.setText(fil.getString(1));
                }
                else {
                    Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (SQLException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        finally {
            BD.close();
        }}
        public void eliminar(){
                String cod = this.et_codigo.getText().toString();
                Conexion miobjeto = new Conexion(this, "administracion", null,1);
                SQLiteDatabase BD = miobjeto.getWritableDatabase();
                try{
                    if(!cod.isEmpty()){
                       int cant = BD.delete("articulos","codigo ="+ cod, null);
                       this.limpiar();
                        if(cant == 1){
                            Toast.makeText(this, "articulo eliminado", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this, "Debes introducir codigo para eliminar", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (SQLException e){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                finally {
                    BD.close();
                }
            }

        private void limpiar(){
            this.et_codigo.setText("");
            this.et_precio.setText("");
            this.et_desc.setText("");
        }

    }

