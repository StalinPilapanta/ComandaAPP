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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.home.comandaapp.model.Personas;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ingresarPersonas extends AppCompatActivity {

    EditText nombres,apellidos,cedula,email,username,password;
    Button guardar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Personas> listPerson = new ArrayList<Personas>();
    ArrayAdapter<Personas> arrayAdapterPersona;
    ListView lvListarPersonas;

    Personas personaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_personas);

        nombres = findViewById(R.id.edtNombres);
        apellidos = findViewById(R.id.edtApellidos);
        cedula = findViewById(R.id.edtCedula);
        email = findViewById(R.id.edtEmail);
        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);

        guardar = findViewById(R.id.btnPedidos);

        inicializarFirebase();

        lvListarPersonas = findViewById(R.id.lvDatosPersonas);
        inicializarFirebase();
        listarDatos();

        lvListarPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelected = (Personas) parent.getItemAtPosition(position);
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
            Toast.makeText( ingresarPersonas.this, "Agregado",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.icon_save){
            Toast.makeText( ingresarPersonas.this, "Actualizar",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.icon_delete){
            Toast.makeText( ingresarPersonas.this, "Eliminar",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardarDatos(){

        String name = nombres.getText().toString();
        String lasName = apellidos.getText().toString();
        String ci = cedula.getText().toString();
        String correo = email.getText().toString();
        String nick = username.getText().toString();
        String pwd = password.getText().toString();


        if (name.equals("")||lasName.equals("")||ci.equals("")||correo.equals("")||nick.equals("")||pwd.equals("")){
            validacion();

        }else{

            Personas p = new Personas();
            p.setUid(UUID.randomUUID().toString());
            p.setNombre(name);
            p.setApellidos(lasName);
            p.setCedula(ci);
            p.setEmail(correo);
            p.setUsername(nick);
            p.setPassword(pwd);
            databaseReference.child("Personas").child(p.getUid()).setValue(p);
            Toast.makeText( ingresarPersonas.this, "Usuario Ingresado",Toast.LENGTH_SHORT).show();
            limpiarCajas();

            Intent actPrincipal =  new Intent(ingresarPersonas.this,MainActivity.class);
            startActivity(actPrincipal);
        }

    }

    private void validacion() {

        String name = nombres.getText().toString();
        String lasName = apellidos.getText().toString();
        String ci = cedula.getText().toString();
        String correo = email.getText().toString();
        String nick = username.getText().toString();
        String pwd = password.getText().toString();
        if (name.equals("")){
            nombres.setError("Required");
        }else if(lasName.equals("")){
            apellidos.setError("Required");
        }else if(ci.equals("")){
            cedula.setError("Required");
        }else if(correo.equals("")){
            email.setError("Required");
        }else if(nick.equals("")){
            username.setError("Required");
        }else if (pwd.equals("")){
            password.setError("Required");
        }
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }



    public void limpiarCajas(){
        nombres.setText("");
        apellidos.setText("");
        cedula.setText("");
        email.setText("");
        username.setText("");
        password.setText("");
    }

    private void listarDatos() {
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for(DataSnapshot objectSnapshot1: dataSnapshot.getChildren()){
                    Personas p = objectSnapshot1.getValue(Personas.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Personas>(ingresarPersonas.this, android.R.layout.simple_list_item_1, listPerson);
                    lvListarPersonas.setAdapter(arrayAdapterPersona);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
