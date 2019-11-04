package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.backup.BackupDataInput;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_desc , et_precio;
    private Button btn_ingreso, btn_busq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.et_codigo = (EditText) findViewById(R.id.Codigo);
        this.et_desc = (EditText) findViewById(R.id.Descripcion);
        this.et_precio  = (EditText) findViewById(R.id.Precio);

        this.btn_ingreso= (Button) findViewById(R.id.Ingreso);
        this.btn_ingreso.setOnClickListener( {Registro();});
        this.btn_busq = (Button) findViewById(R.id.Busqueda);

    }
    public void Registro(){
        Conexion miobjetivo = new Conexion(this, "Administracion", null , 1);

        SQLiteDatabase BD = miobjetivo.getReadableDatabase();

        //CAPTURAMOS LOS VALORES INGRESADOS
        String cod = this.et_codigo.getText().toString();
        String des = this.et_desc.getText().toString().toUpperCase();
        String pr = this.et_precio.getText().toString();

        if(!cod.isEmpty() && !des.isEmpty() && !pr.isEmpty())//los 3 campos no nulos
        {
            ContentValues reg = new ContentValues();
            reg.put("Codigo",cod);
            reg.put("descrip",des);
            reg.put("precio",pr);
            BD.insert("articulos", null,reg);
            BD.close();
            this.Limpiar();
            Toast.makeText(this, "Datos Almacenados", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, " Ingrese Todos Los valores solicitados", Toast.LENGTH_SHORT).show();
        }
        private void Limpiar(){
            this.et_codigo.setText("");

        }

    }
}
