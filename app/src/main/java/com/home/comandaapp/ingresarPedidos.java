package com.home.comandaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.home.comandaapp.model.Pedidos;
import com.home.comandaapp.model.Personas;

import java.util.UUID;

public class ingresarPedidos extends AppCompatActivity {

    EditText numeroPedido, clienteP, cedulaP, menuP, mesaP,extrasP;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


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
            //actualizarDatos();
            Toast.makeText( ingresarPedidos.this, "Actualizar",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.icon_delete){
            //eliminarDatos();
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
}
