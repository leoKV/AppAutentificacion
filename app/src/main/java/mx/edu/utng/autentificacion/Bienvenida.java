package mx.edu.utng.autentificacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Bienvenida extends AppCompatActivity {
public static final String user ="names";
TextView tv_bienvenida;
Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        tv_bienvenida =(TextView) findViewById(R.id.tv_bienvenida);
        String user = getIntent().getStringExtra("names");
        tv_bienvenida.setText("¡¡Bienvenido "+user+" !!");
        btn_logout = (Button) findViewById(R.id.btn_logout);

    }
    public void logout(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(Bienvenida.this, "Gracias por entrar a la aplicación", Toast.LENGTH_LONG).show();
    }
}