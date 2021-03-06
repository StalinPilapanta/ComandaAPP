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
import android.widget.EditText;
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
import java.util.UUID;

public class ingresarPedidos extends AppCompatActivity {

    EditText numeroPedido, clienteP, cedulaP, menuP, mesaP,extrasP;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Pedidos> listPedidos= new ArrayList<Pedidos>();
    ArrayAdapter<Pedidos> arrayAdapterPedidos;
    ListView lvPedidos2;


    Pedidos PedidosSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_pedidos);

        numeroPedido = findViewById(R.id.edtNumeroPedido);
        clienteP = findViewById(R.id.edtClientep);
        cedulaP = findViewById(R.id.edtCedulaP);
        menuP = findViewById(R.id.edtMenuP);
        mesaP = findViewById(R.id.edtMesaP);
        extrasP = findViewById(R.id.edtExtrasP);

        inicializarFirebase();

        lvPedidos2 = findViewById(R.id.lvPedidos2);
        inicializarFirebase();
        listarPedidos();

        lvPedidos2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PedidosSelected = (Pedidos) parent.getItemAtPosition(position);

                ///Datos de las personas
                numeroPedido.setText(PedidosSelected.getNumerPedido());
                clienteP.setText(PedidosSelected.getNombre());
                cedulaP.setText(PedidosSelected.getCedula());
                menuP.setText(PedidosSelected.getMenu());
                mesaP.setText(PedidosSelected.getMesa());
                extrasP.setText(PedidosSelected.getExtras());
                Toast.makeText(getApplicationContext(), "Seleccionado",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.icon_add){
            guardarDatos();
            Toast.makeText( ingresarPedidos.this, "Agregado",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.icon_save){
            actualizarPedidos();
            Toast.makeText( ingresarPedidos.this, "Actualizar",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.icon_delete){
            eliminarPedidos();
            Toast.makeText( ingresarPedidos.this, "Eliminar",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardarDatos(){

        String nPedido = numeroPedido.getText().toString();
        String clientePedido = clienteP.getText().toString();
        String ciP = cedulaP.getText().toString();
        String menuPedido = menuP.getText().toString();
        String mesaPedido = mesaP.getText().toString();
        String exttasPedido = extrasP.getText().toString();


        if (nPedido.equals("")||clientePedido.equals("")||ciP.equals("")||menuPedido.equals("")||mesaPedido.equals("")||exttasPedido.equals("")){
            validacion();

        }else{

            Pedidos p = new Pedidos();
            p.setUid(UUID.randomUUID().toString());
            p.setNumerPedido(nPedido);
            p.setNombre(clientePedido);
            p.setCedula(ciP);
            p.setMenu(menuPedido);
            p.setMesa(mesaPedido);
            p.setExtras(exttasPedido);
            databaseReference.child("Pedidos").child(p.getUid()).setValue(p);
            Toast.makeText( ingresarPedidos.this, "Menu Ingresado",Toast.LENGTH_SHORT).show();
            limpiarCajas();

            Intent actIngresoPedidos =  new Intent(ingresarPedidos.this,pedidos.class);
            startActivity(actIngresoPedidos);
        }

    }

    public void actualizarPedidos(){
        Pedidos p = new Pedidos();
        p.setUid(PedidosSelected.getUid());
        p.setNumerPedido(numeroPedido.getText().toString().trim());
        p.setNombre(clienteP.getText().toString().trim());
        p.setCedula(cedulaP.getText().toString().trim());
        p.setMenu(menuP.getText().toString().trim());
        p.setMesa(mesaP.getText().toString().trim());
        p.setExtras(extrasP.getText().toString().trim());
        databaseReference.child("Pedidos").child(p.getUid()).setValue(p);
        limpiarCajas();
    }
    public void eliminarPedidos(){
        Personas p = new Personas();
        p.setUid(PedidosSelected.getUid());
        databaseReference.child("Pedidos").child(p.getUid()).removeValue();
        limpiarCajas();
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void limpiarCajas(){
        numeroPedido.setText("");
        clienteP.setText("");
        cedulaP.setText("");
        menuP.setText("");
        mesaP.setText("");
        extrasP.setText("");
    }

    private void validacion() {

        String name = numeroPedido.getText().toString();
        String lasName = clienteP.getText().toString();
        String ci = cedulaP.getText().toString();
        String correo = menuP.getText().toString();
        String nick = mesaP.getText().toString();
        String pwd = extrasP.getText().toString();
        if (name.equals("")){
            numeroPedido.setError("Required");
        }else if(lasName.equals("")){
            clienteP.setError("Required");
        }else if(ci.equals("")){
            cedulaP.setError("Required");
        }else if(correo.equals("")){
            menuP.setError("Required");
        }else if(nick.equals("")){
            mesaP.setError("Required");
        }else if (pwd.equals("")){
            extrasP.setError("Required");
        }
    }

    private void listarPedidos() {
        databaseReference.child("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPedidos.clear();
                for(DataSnapshot objectSnapshot1: dataSnapshot.getChildren()){
                    Pedidos p = objectSnapshot1.getValue(Pedidos.class);
                    listPedidos.add(p);

                    arrayAdapterPedidos = new ArrayAdapter<Pedidos>(ingresarPedidos.this, android.R.layout.simple_list_item_1, listPedidos);
                    lvPedidos2.setAdapter(arrayAdapterPedidos);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
