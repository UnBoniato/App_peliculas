package com.upm.app_peliculas;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;



public class LoginActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "1";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private EditText usuarioInput, PasswordInput;
    private CheckBox LogInCheckBox;
    private Button accessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Verificar y solicitar permiso de notificaciones en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            101
                    );
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //Logica para guardar si el usuario ha marcado la casilla de mantenerse logueado
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Aqui se verifica si el usuario ya esta logueado
        if (sharedPreferences.getBoolean("IsLoggedIn", false)){
            goToHomeActivity();
        }

        //Buscar las vistas de la interfaz
        usuarioInput = findViewById(R.id.input_usuario);
        PasswordInput = findViewById(R.id.input_password);
        LogInCheckBox = findViewById(R.id.checkBox);
        accessButton = findViewById(R.id.access_button);

        //Configuracion del boton de acceso
        accessButton.setOnClickListener(v -> {
            String usuario = usuarioInput.getText().toString().trim();
            String password = PasswordInput.getText().toString().trim();

            if(validateCredentials(usuario, password)){
                if(LogInCheckBox.isChecked()) {
                    editor.putBoolean("IsLoggedIn", true);
                    editor.apply();
                }
                goToHomeActivity();
            }else{
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido para enviar notificaciones", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso denegado para notificaciones", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //crear las notificaciones
    public void sendNotification(View view) {

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("¡No te lo pierdas!")
                .setContentText("Disfruta de lo último en la sección de estrenos")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("¡No te lo pierdas!")
                        .bigText("¡Disfruta de lo último en la sección de estrenos o de las películas más populares actualmente!"))
                .setLargeIcon(largeIcon);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.notify(1, builder.build());
    }

    /*---------------METODOS PRIVADOS----------------*/


    //Como no se usa servidor ni backend de momento se usa un usuario y contraseña de prueba
    private boolean validateCredentials(String user, String password) {
        return user.equals("usuario1") && password.equals("12345");
    }
    private void goToHomeActivity() {

        sendNotification(new View(this));

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}