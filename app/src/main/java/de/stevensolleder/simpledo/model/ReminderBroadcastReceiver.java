package de.stevensolleder.simpledo.model;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import de.stevensolleder.simpledo.R;
import de.stevensolleder.simpledo.controller.Main;

public class ReminderBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(SimpleDo.getAppContext(), "main")
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle(SimpleDo.getAppContext().getResources().getString(R.string.app_name))
                .setContentText(intent.getStringExtra("content"))
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(SimpleDo.getAppContext(), 0, new Intent(SimpleDo.getAppContext(), Main.class), 0));

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(SimpleDo.getAppContext());
        notificationManagerCompat.notify(new IdentificationHelper().createUniqueId(), builder.build());
    }
}
