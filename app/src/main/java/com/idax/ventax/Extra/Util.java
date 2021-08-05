package com.idax.ventax.Extra;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.idax.ventax.Activity.Launch.LaunchScreen;
import com.idax.ventax.Activity.Login.LoginActivity;
import com.idax.ventax.Activity.Login.LoginView;
import com.idax.ventax.Entity.Category;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.OrderProduct;
import com.idax.ventax.Entity.SocialMedia;
import com.idax.ventax.R;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.idax.ventax.Extra.Constansts.ACEPTED;
import static com.idax.ventax.Extra.Constansts.CANCELED;
import static com.idax.ventax.Extra.Constansts.FAQ_BASIC;
import static com.idax.ventax.Extra.Constansts.FAQ_BUSINESS;
import static com.idax.ventax.Extra.Constansts.FAQ_PREMIUM;
import static com.idax.ventax.Extra.Constansts.FINISHED;
import static com.idax.ventax.Extra.Constansts.ITEM_BASIC;
import static com.idax.ventax.Extra.Constansts.ITEM_BUSINESS;
import static com.idax.ventax.Extra.Constansts.ITEM_PREMIUM;
import static com.idax.ventax.Extra.Constansts.PENDING;
import static com.idax.ventax.Extra.Constansts.PRIORITY_BASIC;
import static com.idax.ventax.Extra.Constansts.PRIORITY_BUSINESS;
import static com.idax.ventax.Extra.Constansts.PRIORITY_PREMIUM;
import static com.idax.ventax.Extra.Constansts.PROCCESS;

public class Util {

    /***
     upload Company_image to /IMAGE/id_COMPANY/COMPANY.jpg
     upload Product_images to /IMAGE/id_COMPANY/id_PRODUCT.jpg
     */

    public static LoginView loginView;//DEPRECREATE -> ELIMINAR

    public static String throwError(Context context, int e) {
        String error = context.getString(R.string.no_error_error);
        switch (e) {
            case 0:
                return "No se encontró el dato";
            case 1:
                return "No tiene permisos para esta acción";
            case 2:
                return "Campo inválido";
        }
        return error;
    }

    public static String getCategory(Context context, int categoryId) {
        switch (categoryId) {
            case 0:
                return context.getString(R.string.food);
            case 1:
                return context.getString(R.string.electronic);
            case 2:
                return context.getString(R.string.health);
            case 3:
                return context.getString(R.string.style);
            case 4:
                return context.getString(R.string.desserts);
            case 5:
                return context.getString(R.string.sports);
            case 6:
                return context.getString(R.string.fashion);
            case 7:
                return context.getString(R.string.crafts);
            case 8:
                return context.getString(R.string.gifts);
            case 9:
                return context.getString(R.string.pets);
            case 10:
                return context.getString(R.string.clothes);
            case 11:
                return context.getString(R.string.art);
            case 12:
                return context.getString(R.string.accesory);
        }
        return context.getString(R.string.without_coincidance);
    }

    public static List<Category> getCategoryList(Context context) {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(0, context.getString(R.string.food)));
        categoryList.add(new Category(1, context.getString(R.string.electronic)));
        categoryList.add(new Category(2, context.getString(R.string.health)));
        categoryList.add(new Category(3, context.getString(R.string.style)));
        categoryList.add(new Category(4, context.getString(R.string.desserts)));
        categoryList.add(new Category(5, context.getString(R.string.sports)));
        categoryList.add(new Category(6, context.getString(R.string.fashion)));
        categoryList.add(new Category(7, context.getString(R.string.crafts)));
        categoryList.add(new Category(8, context.getString(R.string.gifts)));
        categoryList.add(new Category(9, context.getString(R.string.pets)));
        categoryList.add(new Category(10, context.getString(R.string.clothes)));
        categoryList.add(new Category(11, context.getString(R.string.art)));
        categoryList.add(new Category(12, context.getString(R.string.accesory)));
        return categoryList;
    }

    public static String getAccountType(Context context, int categoryId) {
        switch (categoryId) {
            case 0:
                return context.getString(R.string.basic);
            case 1:
                return context.getString(R.string.premium);
            case 2:
                return context.getString(R.string.business);
        }
        return context.getString(R.string.without_coincidance);
    }

    public static int getNumOfItemsByAccountType(int accountType) {
        switch (accountType) {
            case PRIORITY_BASIC:
                return ITEM_BASIC;
            case PRIORITY_PREMIUM:
                return ITEM_PREMIUM;
            case PRIORITY_BUSINESS:
                return ITEM_BUSINESS;
            default:
                return 0;
        }
    }

    public static int getNumOfFAQsByAccountType(int accountType) {
        switch (accountType) {
            case 0:
                return FAQ_BASIC;
            case 1:
                return FAQ_PREMIUM;
            case 2:
                return FAQ_BUSINESS;
            default:
                return 0;
        }
    }

    public static String getDay(Context context, int dayId) {
        switch (dayId) {
            case 0:
                return context.getString(R.string.monday);
            case 1:
                return context.getString(R.string.tuesday);
            case 2:
                return context.getString(R.string.wednesday);
            case 3:
                return context.getString(R.string.thursday);
            case 4:
                return context.getString(R.string.friday);
            case 5:
                return context.getString(R.string.saturday);
            case 6:
                return context.getString(R.string.sunday);
        }
        return context.getString(R.string.without_coincidance);
    }

    public static class ConfigSocialMedia {
        //private Context context;
        private LinearLayout linearLayout;
        private Intent intent;
        private Activity activity;

        public ConfigSocialMedia(Activity activity/*, Context context*/, LinearLayout linearLayout) {
            this.activity = activity;
            //this.context = context;
            this.linearLayout = linearLayout;
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void fillSocialMedia(List<SocialMedia> socialMediaList) {
            for (final SocialMedia sm : socialMediaList) {
                ImageButton imageButton = new ImageButton(activity);
                imageButton.setLayoutParams(setLayoutParams());
                imageButton.setBackground(activity.getResources().getDrawable(getDrawable(sm.getSocialmedia_id())));
                linearLayout.addView(imageButton);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToPage(sm.getSocialmedia_id(), sm.getCode());
                    }
                });
            }
        }

        public static String getStringImage(Bitmap bmp) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }

        private int getDrawable(int socialMediaId) {
            if (socialMediaId == 0) {//facebook
                return R.drawable.socialmedia_facebook;
            } else if (socialMediaId == 1) {//instagram
                return R.drawable.socialmedia_instagram;
            } else if (socialMediaId == 2) {//twitter
                return R.drawable.socialmedia_twitter;
            } else if (socialMediaId == 3) {//web
                return R.drawable.socialmedia_web;
            } else if (socialMediaId == 4) {//whatsapp
                return R.drawable.socialmedia_whatsapp;
            }
            return 0;
        }

        private void goToPage(int socialMediaId, String urlId) {
            String socialMediaName = "";
            switch (socialMediaId) {
                case 0:
                    socialMediaName = "Facebook";
                    goToFacebook(urlId);
                    break;
                case 1:
                    socialMediaName = "Instagram";
                    goToInstagram(urlId);
                    break;
                case 2:
                    socialMediaName = "Twitter";
                    goToTwitter(urlId);
                    break;
                case 3:
                    socialMediaName = "Web";
                    goToWeb(urlId);
                    break;
                case 4:
                    socialMediaName = "WhatsApp";
                    goToWhatsApp(urlId);
                    break;
            }
        }

        private LinearLayout.LayoutParams setLayoutParams() {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
            layoutParams.setMargins(30, 5, 30, 5);
            return layoutParams;
        }

        private void goToWhatsApp(String urlId) {//goToWhatsApp
            String msj = "";
            intent = new Intent(Intent.ACTION_VIEW);
            String uri = "whatsapp://send?phone=" + urlId + "&text=" + msj;
            intent.setData(Uri.parse(uri));
            activity.startActivityForResult(intent, 745);
        }

        private void goToWeb(String urlId) {
            activity.startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse(urlId)), 986);
        }

        private void goToTwitter(String urlId) {
            Uri uri = Uri.parse("http://twitter.com/" + urlId);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.twitter.android");
            try {
                activity.startActivityForResult(likeIng, 745);
            } catch (ActivityNotFoundException e) {
                activity.startActivityForResult(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://twitter.com/" + urlId)), 745);
            }
        }

        private void goToInstagram(String urlId) {
            Uri uri = Uri.parse("http://instagram.com/_u/" + urlId);
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");
            try {
                activity.startActivityForResult(likeIng, 745);
            } catch (ActivityNotFoundException e) {
                activity.startActivityForResult(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/" + urlId)), 745);
            }
        }

        private void goToFacebook(String urlId) {
            String facebookUrl = "https://www.facebook.com/" + urlId;
            try {
                int versionCode = activity.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) {
                    Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                    activity.startActivityForResult(new Intent(Intent.ACTION_VIEW, uri), 745);
                } else {
                    Uri uri = Uri.parse("fb://page/" + urlId);
                    activity.startActivityForResult(new Intent(Intent.ACTION_VIEW, uri), 745);
                }
            } catch (PackageManager.NameNotFoundException e) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
            }
        }
    }


    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    private static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }

    public static String formatDecimal(String value) {
        DecimalFormat df = new DecimalFormat("$#,###,##0.00");
        return df.format(Double.valueOf(value));
    }

    public static String formatDecimal(double value) {
        DecimalFormat df = new DecimalFormat("$#,###,##0.00");
        return df.format(Double.valueOf(value));
    }

    public static String getStateOrder(Context context, int stateId) {
        switch (stateId) {
            case PENDING:
                return context.getString(R.string.pending);//0
            case ACEPTED:
                return context.getString(R.string.acepted);//1
            case PROCCESS:
                return context.getString(R.string.proccess);//2
            case FINISHED:
                return context.getString(R.string.finished);//3
            case CANCELED:
                return context.getString(R.string.canceled);//4
            default:
                return context.getString(R.string.nulo);
        }
    }

    public static String getStateDescription(Context context, int stateId) {
        switch (stateId) {
            case PENDING:
                return context.getString(R.string.pending_);//0
            case ACEPTED:
                return context.getString(R.string.acepted_);//1
            case PROCCESS:
                return context.getString(R.string.proccess_);//2
            case FINISHED:
                return context.getString(R.string.finished_);//3
            case CANCELED:
                return context.getString(R.string.canceled);//4
            default:
                return context.getString(R.string.nulo);
        }
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    //customNotification(getApplicationContext(), "Hola mundo", "Como estás", "channel", 2, true);
    public static void customNotification(Context context, String title, Object content, String channel_, int notificationId, boolean launch) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channel_, name, importance);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, LaunchScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_)
                .setSmallIcon(R.drawable.ventax_logo)
                .setContentTitle(title)
                .setContentText(getContent(content))
                .setSound(sound)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (launch) builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    private static String getContent(Object content) {
        if (content instanceof String) {
            return String.valueOf(content);
        } else if (content instanceof OrderModel) {
            StringBuilder message = new StringBuilder();
            for (OrderProduct op : ((OrderModel) content).getOrderProductList()) {
                message.append("(").append(op.getQty()).append(") - ").append(op.getProduct()).append("\n");
            }
            return message.toString();
        }
        return "";
    }
}
