package com.home.comandaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.home.comandaapp.model.Personas;

import java.util.ArrayList;
import java.util.List;

public class listar_personas extends AppCompatActivity {


    private List<Personas> listPerson = new ArrayList<Personas>();
    ArrayAdapter<Personas> arrayAdapterPersona;
    ListView lvListarPersonas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Personas personaSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_personas);

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

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {
        databaseReference.child("Personas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for(DataSnapshot objectSnapshot1: dataSnapshot.getChildren()){
                    Personas p = objectSnapshot1.getValue(Personas.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Personas>(listar_personas.this, android.R.layout.simple_list_item_1, listPerson);
                    lvListarPersonas.setAdapter(arrayAdapterPersona);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
