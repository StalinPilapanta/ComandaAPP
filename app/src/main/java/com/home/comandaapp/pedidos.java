package com.home.comandaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.home.comandaapp.model.Pedidos;
import com.home.comandaapp.model.Personas;

import java.util.ArrayList;
import java.util.List;

public class pedidos extends AppCompatActivity {

    private List<Pedidos> listPedidos = new ArrayList<Pedidos>();
    ArrayAdapter<Pedidos> arrayAdapterPersona;
    ListView lvpedidos;

    Pedidos pedidosSelected;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        lvpedidos = findViewById(R.id.lvpedidos);
        inicializarFirebase();
        listarPedidos();

        lvpedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pedidosSelected = (Pedidos) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Seleccionado",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarPedidos() {
        databaseReference.child("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPedidos.clear();
                for(DataSnapshot objectSnapshot1: dataSnapshot.getChildren()){
                    Pedidos p = objectSnapshot1.getValue(Pedidos.class);
                    listPedidos.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Pedidos>(pedidos.this, android.R.layout.simple_list_item_1, listPedidos);
                    lvpedidos.setAdapter(arrayAdapterPersona);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ingresarPedidos(View view){
        Intent actPedidos =  new Intent(pedidos.this,ingresarPedidos.class);
        startActivity(actPedidos);
        Toast.makeText( pedidos.this, "Ingresar pedido",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.icon_add){
            //guardarDatos();
            Toast.makeText( pedidos.this, "Agregado",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.icon_save){
           // actualizarDatos();
            Toast.makeText( pedidos.this, "Actualizar",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.icon_delete){
            eliminarDatos();
            Toast.makeText( pedidos.this, "Eliminar",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
    */

    public void eliminarDatos(){
        Personas p = new Personas();
        p.setUid(pedidosSelected.getUid());
        databaseReference.child("Pedidos").child(p.getUid()).removeValue();

    }




}
