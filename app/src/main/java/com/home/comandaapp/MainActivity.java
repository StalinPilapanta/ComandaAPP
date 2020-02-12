    package com.home.comandaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.home.comandaapp.model.Personas;

    public class MainActivity extends AppCompatActivity {

        TextView txCrear;
        EditText user, password;
        Button login;

        DatabaseReference mRootRefence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.edtUser);
        password = findViewById(R.id.edtPassword);
        login = findViewById(R.id.btnLogin);


        txCrear = findViewById(R.id.txtCrear);
        String text = "No tienes una cuenta, Crea una ahora!!";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Toast.makeText( MainActivity.this, "Crear",Toast.LENGTH_SHORT).show();
                Intent ingresarPersonas =  new Intent(MainActivity.this,ingresarPersonas.class);
                //ingresarPersonas.putExtra("datoUser",user);
                startActivity(ingresarPersonas);

            }
        };

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText( MainActivity.this, "Listar",Toast.LENGTH_SHORT).show();
                Intent listarPersona =  new Intent(MainActivity.this,listar_personas.class);
                startActivity(listarPersona);
            }
        };

        ss.setSpan(clickableSpan,22,38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan1,0,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txCrear.setText(ss);
        txCrear.setMovementMethod(LinkMovementMethod.getInstance());
    }

        public void Ingresar(View v){

            mRootRefence = FirebaseDatabase.getInstance().getReference();
            mRootRefence.child("Personas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                        mRootRefence.child("Personas").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Personas personas = snapshot.getValue(Personas.class);

                                String fusername = personas.getUsername();
                                String fpassword = personas.getPassword();

                                Log.e("Nombre username: ", ""+fusername);
                                Log.e("Nombre password: ", ""+fpassword);
                                //Log.e("Datos: ", ""+snapshot.getValue());

                                //ingreso(fusername, fpassword);
                                String userF = user.getText().toString();
                                String passF = password.getText().toString();

                                if(userF.equals(fusername) && passF.equals(fpassword)){
                                    Intent pedidos =  new Intent(MainActivity.this, com.home.comandaapp.pedidos.class);
                                    //vista2.putExtra("datoUser",userF);
                                    Toast.makeText(getApplicationContext(), "Ingreso Exitoso",Toast.LENGTH_SHORT).show();
                                    startActivity(pedidos);
                                    limpiarCajas();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Clave equivocada",Toast.LENGTH_SHORT).show();
                                    limpiarCajas();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public void ingreso(String fusername,String fpassword){

            String userF = user.getText().toString();
            String passF = password.getText().toString();

            if(userF.equals(fusername) && passF.equals(fpassword)){
                Intent pedidos =  new Intent(MainActivity.this, com.home.comandaapp.pedidos.class);
                //vista2.putExtra("datoUser",userF);
                Toast.makeText(getApplicationContext(), "Ingreso Exitoso",Toast.LENGTH_SHORT).show();
                startActivity(pedidos);
                limpiarCajas();
            }else{
                Toast.makeText(getApplicationContext(), "Clave equivocada",Toast.LENGTH_SHORT).show();
                limpiarCajas();
            }
        }

        public void limpiarCajas(){
            user.setText("");
            password.setText("");
        }
}
