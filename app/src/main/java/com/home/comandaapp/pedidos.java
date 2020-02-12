package com.home.comandaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.home.comandaapp.model.Pedidos;
import com.home.comandaapp.model.Personas;

import java.util.ArrayList;
import java.util.List;

public class pedidos extends AppCompatActivity {

    private List<Pedidos> listPerson = new ArrayList<Pedidos>();
    ArrayAdapter<Pedidos> arrayAdapterPersona;
    ListView lvpedidos;

    Pedidos pedidosSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
    }

    public void ingresarPedidos(View view){
        Intent actPedidos =  new Intent(pedidos.this,ingresarPedidos.class);
        startActivity(actPedidos);
        Toast.makeText( pedidos.this, "Ingresar pedido",Toast.LENGTH_SHORT).show();

    }

    /*
    public void actualizarDatos(){
        Personas p = new Personas();
        p.setUid(personaSelected.getUid());
        p.setNombre(nombres.getText().toString().trim());
        p.setApellidos(apellidos.getText().toString().trim());
        p.setCedula(cedula.getText().toString().trim());
        p.setEmail(email.getText().toString().trim());
        p.setUsername(username.getText().toString().trim());
        p.setPassword(password.getText().toString().trim());
        databaseReference.child("Personas").child(p.getUid()).setValue(p);
        limpiarCajas();
    }

    public void eliminarDatos(){
        Personas p = new Personas();
        p.setUid(personaSelected.getUid());
        databaseReference.child("Personas").child(p.getUid()).removeValue();
        limpiarCajas();
    }

     */
}
