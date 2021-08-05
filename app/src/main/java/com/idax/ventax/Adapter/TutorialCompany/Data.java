package com.idax.ventax.Adapter.TutorialCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.idax.ventax.R;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.AFFILIATE;
import static com.idax.ventax.Extra.Constansts.NOT_AFFILIATE;

public class Data implements Serializable {

    private int affiliate;
    private int cover;
    private View view;
    private String title;
    private String text;
    private String extraText;

    public Data() {
    }

    public Data(int affiliate, int cover, View view, String title, String text, String extraText) {
        this.affiliate = affiliate;
        this.title = title;
        this.cover = cover;
        this.view = view;
        this.text = text;
        this.extraText = extraText;
    }

    public static List<Data> getDataList(Context context){
        List<Data> dataList = new ArrayList<>();
        dataList.add(new Data(AFFILIATE, R.drawable.ventax_logo, getView(context, 0),"Bienvenido a VAX",
                "VAX está enfocado para facilitar que los emprendedores de Mérida Yucatán puedan DAR A CONOCER sus productos de manera sencilla y digitalizado.\n\nNuestro objetivo es apoyar a nuevos emprendedores y puedan tener otra plataforma donde puedan mostrar dichos emprendimientos.",
                MessageFormat.format("Desarrollado por {0}", context.getString(R.string.IDAX))));
        dataList.add(new Data(AFFILIATE, R.drawable.ventax_logo, null,"",
                "Cada usuarios de VAX™ cuenta con un 'DIRECTORIO' de emprendedores con sus respectivos catálogos, los cuales podrá visualizar, agregar a un carrito de compras o solicitar.",
                ""));
        //dataList.add(new Data(AFFILIATE, R.drawable.ventax_logo, null,"",
        //        "",
        //        ""));
        dataList.add(new Data(NOT_AFFILIATE, 0, null,"NUESTROS NUEVOS EMPRENDEDORES",
                        MessageFormat.format("Gracias por formar parte de {0}, constantemente estaremos trabajando para crear un espacio lo más comodo para nuestros usuarios", context.getString(R.string.IDAX)),
                ""));
        dataList.add(new Data(NOT_AFFILIATE, 0, getView(context, 0),"Mi empresa",
                "En este primer apartado se mostrará información básica de su empresa y posteriormente podrá llevar un control gráfico de todos los pedidos que le han solicitado\n\nEl tipo de cuenta de cada persona sirve para definir un máximo de productos permitidos y un máximo de preguntas frecuentes.\nDe igual manera, la posición en la busqueda siempre van a salir primero las cuentas superiores.",
                MessageFormat.format("{0}:\nProductos máx: 10\n'FAQ' máx: 3\n\n{1}:\nProductos máx: 20\n'FAQ' máx: 8\n\n{2}:\nProductos máx: 40\n'FAQ' máx: 15\n", context.getString(R.string.basic),context.getString(R.string.premium),context.getString(R.string.business))));
        dataList.add(new Data(NOT_AFFILIATE, 0, getView(context, 0),"Pedidos",
                "Se despleagaran todos los pedidos de sus clientes y al seleccionar uno tendrá la opción de pasar al siguiente paso si así lo considera. Cada paso representa el estado en el que se encuentra el pedido de su cliente y en cada cambio de paso se le notificará a su cliente.",
                "Pasos:\nPendiente: Le llegó notificación de que alguien solicitó alguno de sus productos\n\nAceptado: Usted se percata de que tiene un nuevo pedido y lo aceptó\n\nEn proceso: Empezó a trabajar en el pedido para el cliente\n\nTerminado: Usted se comunica con su cliente para definir la entrega y el pago (tiene la opción de comunicarse con su cliente por WhatsApp)."));
        //
        dataList.add(new Data(NOT_AFFILIATE, 0, getView(context, 0),"Productos",
                "Cada usuario afiliado tiene la oportunidad de crear un nuevo 'producto', sin embargo tiene un límite el cual dependerá del tipo de cuenta con la que cuente\nHemos tratado de permitir un número considerable de productos máximo en la cuenta más básica para que no tengan la necesidad de conseguir la siguiente.",
                ""));
        dataList.add(new Data(NOT_AFFILIATE, 0, getView(context, R.layout.fragment_settings_socialmedia),"Configuraciones",
                "Redes Sociales\nHabilite la red social con la que cuente para que sus clientes y los usuarios puedan acceder facilmente a cualquiera de sus redes sociales",
                "Ejemplo: el Facebook ID de IDAX es 'idax.mx' (https://www.facebook.com/idax.mx)"));
        dataList.add(new Data(NOT_AFFILIATE, 0, getView(context, R.layout.fragment_settings_set_design),"Configuraciones",
                "Diseño\nLos colores que seleccione solo apareceran en su 'espacio', cada emprendedor tiene su propio espacio.",
                "Solo se admiten valores HEXADECIMALES (0123456789ABCDEF) para los colores"));
        dataList.add(new Data(NOT_AFFILIATE, 0, getView(context, R.layout.fragment_settings_schedule),"Configuraciones",
                "Horarios\nConfigura el horario en el que laboras para evitar notificaciones fuera de tu horario laboral. En cualquier momento lo puedes cambiar y se actualizará",
                "No se garantiza que no reciba notificaciones fuera del horario labora. Esta opción permite al usuario saber su horario y no evita que pueda hacer un pedido fuera de hora, sin embargo usted decide cuando aceptar (o no) el pedido."));
        dataList.add(new Data(NOT_AFFILIATE, 0, getView(context, 0),"FAQ",
                "En este apartado le aparecerán el máximo de preguntas que puede tener, pero solo se mostrará en su espacio las que estén marcadas con un punto verde",
                "Puede eliminar o editar una pregunta si llegó a su máximo."));
        return  dataList;
    }

    private static View getView(Context context, int view){
        if (view == 0) return null;
        return LayoutInflater.from(context).inflate(view, null, false);
    }

    public int getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(int affiliate) {
        this.affiliate = affiliate;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExtraText() {
        return extraText;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

}
