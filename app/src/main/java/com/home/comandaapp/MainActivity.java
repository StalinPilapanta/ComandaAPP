    package com.home.comandaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

    public class MainActivity extends AppCompatActivity {

        TextView txCrear;
        EditText user, password;
        Button login;

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
            String userF = user.getText().toString();
            String passF = password.getText().toString();
            if(userF.equals("Stalin") && passF.equals("est.uisrael.2019")){
                //ntent vista2 =  new Intent(MainActivity.this,actividad2.class);
                //vista2.putExtra("datoUser",userF);
                Toast.makeText(getApplicationContext(), "Ingreso Exitoso",Toast.LENGTH_LONG).show();
                //startActivity(vista2);
            }else{
                Toast.makeText(getApplicationContext(), "Error al ingresar",Toast.LENGTH_SHORT).show();
            }
        }
}
