package com.idax.ventax.OrderProduct;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;
import static com.idax.ventax.Extra.Constansts.REQUEST_CODE_ORDER_ITEM;


public class RequestItem extends BroadcastReceiver {
    /**
     * ESTA CLASE RequestItem SIRVE PARA HACER EL PEDIDO SI ESTÁ FUERA DE HORARIO
     */

    /**
     * TODO: {
     *     1- Abrir dialog de producto y ordenar
     *     2- Hacer el pedido -> {
     *          2.1- Si hace el pedido dentro del horario laboral -> llevar a whats con un mensaje predeterminado donde solite el prodcuto e insertar en la DB
     *          el pedido
     *          2.2- Si hace el pedido fuera del horario laboral -> {
     *               2.2.1- Mandar un Bundle por webSocket a un celular-servidor con el nombre y número celular del cliente, además de los detalles del pedido
     *               2.2.2- Insertar en la DB el pedido del cliente
     *               2.2.3- Almacenar el pedido en el celular-servidor y crear un jobSchedule para que mande un mensaje automático al empresario a la hora de
     *               apertuda del siguiente día
     *               2.2.5- Cuando se haya mandado el mensaje desde el otro celular, recibir un mensaje en el celular que ordernó para avisarle que ya se realizó
     *               el pedido y que pronto se pondrá en contacto
     *               2.2.6- eliminar el objeto de pedido de la base de datos local
     *           }
     *     }
     *     3- crear un  activity o fragment para identificar el pedido pendiente(Cada vez que se entra a esta activity se hace una petición para tener esa
     *     vista siempre actualziada)
     *     4- El empresario finalizará el pedido manualmente y a ambos le saldrá finalizado
     * }
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Mandar mensaje de whats", Toast.LENGTH_SHORT).show();
    }

    public static void setRequestItem(Context context,long time){
        Intent requestItemIntent = new Intent(context, RequestItem.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_ORDER_ITEM, requestItemIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + , pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        Toast.makeText(context, "Pedido realizado", Toast.LENGTH_SHORT).show();
    }

    public static void cancelRequestItem(Context context){
        Intent requestItemIntent = new Intent(context, RequestItem.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_ORDER_ITEM, requestItemIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Pedido cancelado", Toast.LENGTH_SHORT).show();
    }
}
