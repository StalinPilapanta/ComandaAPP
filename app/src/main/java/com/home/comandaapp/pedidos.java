package com.home.comandaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class pedidos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
    }

    public void ingresarPedidos(View view){
        Toast.makeText( pedidos.this, "Ingresar pedido",Toast.LENGTH_SHORT).show();

    }
}
