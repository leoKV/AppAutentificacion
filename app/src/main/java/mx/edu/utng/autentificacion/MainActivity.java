package mx.edu.utng.autentificacion;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Definir nuestros objetos View
    private EditText et_correo;
    private EditText et_pass;
    private Button btn_registro,btn_login;
    private ProgressDialog progressDialog;
    //Declaramos un objeto de firebaseAuth
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Instanciamos el objeto auth
        firebaseAuth= firebaseAuth.getInstance();
        //Referenciamos los views
        et_correo= (EditText) findViewById(R.id.et_correo);
        et_pass=(EditText) findViewById(R.id.et_pass);
        btn_registro= (Button) findViewById(R.id.btn_registro);
        btn_login= (Button)findViewById(R.id.btn_login);
        progressDialog= new ProgressDialog(this);
        //Iniciamos el evento de escucha para el botón
        btn_registro.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }
    //Metodo para registrar usuarios
    public void registrarUsuario(){
        //Obtener el email y contraseña desde las cajas de texto
        String email = et_correo.getText().toString().trim();
        String pass= et_pass.getText().toString().trim();
        //Verificar que las cajas de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Por favor ingrese un correo", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Por favor ingrese una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Procesando el registro en linea...");
        progressDialog.show();
        //creando el nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Verificando si es exitoso
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Se ha registrado el usuario con el correo: "+et_correo.getText(), Toast.LENGTH_LONG).show();
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){ //Si se presenta una colisión con el usuario

                            }else{
                                Toast.makeText(MainActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    public void loguearUsuario(){
        //Obtener el email y contraseña desde las cajas de texto
        String email = et_correo.getText().toString().trim();
        String pass= et_pass.getText().toString().trim();

        //Verificar que las cajas de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Por favor ingrese un correo", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Por favor ingrese una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Realizando Consulta en linea...");
        progressDialog.show();
        //Loguear Usuario
        firebaseAuth.signInWithEmailAndPassword(email,pass) //Se loguea con email y la contraseña
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Verificando si es exitoso
                        if(task.isSuccessful()){
                            int pos= email.indexOf("@");
                            String user= email.substring(0,pos);
                            Toast.makeText(MainActivity.this, "Bienvenido ", Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(getApplicationContext(),Bienvenida.class); //Enviamos al usuario a la actividad de bienvenida
                            intent.putExtra(Bienvenida.user,user); //Enviamos el usuario a la actividad de Bienvenida para mostrarlo en pantalla
                            startActivity(intent);//Iniciamos la actividad
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){ //Si se presenta una colisión con el usuario

                            }else{
                                Toast.makeText(MainActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(MainActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registro:
                //Invocamos el metodo para registrar usuarios
                registrarUsuario();
                break;
            case R.id.btn_login:
                //Invocamos el metodo para loguearse en la aplicación
                loguearUsuario();
                break;
        }
    }
}