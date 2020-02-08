package com.home.comandaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.home.comandaapp.model.Personas;

import java.util.UUID;

public class ingresarPersonas extends AppCompatActivity {

    EditText nombres,apellidos,cedula,email,username,password;
    Button guardar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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

        guardar = findViewById(R.id.btnGuardarPersonas);

        inicializarFirebase();
    }

    public void guardarDatos(View view){

        String name = nombres.getText().toString();
        String lasName = apellidos.getText().toString();
        String ci = cedula.getText().toString();
        String correo = email.getText().toString();
        String nick = username.getText().toString();
        String pwd = password.getText().toString();

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
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void limpiarCajas(){
        nombres.setText("");
        apellidos.setText("");
        cedula.setText("");
        email.setText("");
        username.setText("");
        password.setText("");
    }
}