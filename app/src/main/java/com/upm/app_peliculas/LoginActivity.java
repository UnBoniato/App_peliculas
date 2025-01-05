package com.upm.app_peliculas;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
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
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;



public class LoginActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "1";
    private static final int NOTIFICATION_PERMISSION_CODE = 101;

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

        //Buscar el boton y añadirle la funcionalidad
        Button accessButton = findViewById(R.id.access_button);
        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lanza una notificacion y va a la pantalla HOME
                notificationButton(view);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
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
    public void notificationButton(View view) {

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


}