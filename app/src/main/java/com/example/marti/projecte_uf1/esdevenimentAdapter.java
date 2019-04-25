package com.example.marti.projecte_uf1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class esdevenimentAdapter extends RecyclerView.Adapter<esdevenimentAdapter.MyViewHolder> {

    private ArrayList<Esdeveniment> list;
    private Context context;
    public static final String EXTRA_ID = "ID";
    SQLiteManager manager = new SQLiteManager(context);

    Esdeveniment esdG;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox interested;
        public TextView EsdevenimentName;
        public RelativeLayout relativeLayout;
        public TextView esdevenimentInfo;
        public ImageView esdevenimentImage;

        public MyViewHolder(View view) {
            super(view);
            EsdevenimentName = view.findViewById(R.id.EsdevenimentName);
            interested = view.findViewById(R.id.interested);
            relativeLayout = view.findViewById(R.id.esdeveniments_list);
            esdevenimentInfo = view.findViewById(R.id.eventInfo);
            esdevenimentImage = view.findViewById(R.id.eventImage);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public esdevenimentAdapter(ArrayList<Esdeveniment> esdevenimentsList, Context context) {
        this.list = esdevenimentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public esdevenimentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.esdeveniment_list_item, viewGroup, false);

        return new MyViewHolder(item);
    }

    // To animate view slide out from top to bottom
    public void slideToBottom(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, -view.getHeight(), 0);
        animate.setDuration(250);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    // To animate view slide out from bottom to top
    public void slideToTop(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }


    @Override
    public void onBindViewHolder(@NonNull final esdevenimentAdapter.MyViewHolder myViewHolder, final int i) {
        Esdeveniment esd = list.get(i);

        System.out.print(esd.getTitol());
        Log.d("missatge: ", esd.getTitol());

        myViewHolder.EsdevenimentName.setText(esd.getTitol());
        myViewHolder.esdevenimentInfo.setText("Type:   " + esd.getTipus() + "\nPlace:   " + esd.getLloc() + "\nDate:   " + esd.getData() + "\nPeople attending:   " + esd.getNumAssistens());

        myViewHolder.interested.setChecked(esd.isInteressa());
        myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup group = (ViewGroup) myViewHolder.relativeLayout;
                Esdeveniment esd = list.get(i);
                esdG = esd;
                if (myViewHolder.esdevenimentInfo.getVisibility() == View.GONE) {

                   // myViewHolder.rewardImage.setImageResource(R.drawable.uparrow);

                    // myViewHolder.rewardInfo.setText("Type:   " + esd.getTipus() + "\nPlace:   " + esd.getLloc() + "\nDate:   " + esd.getData() + "\nPeople attending:   " + esd.getNumAssistens());

//                    myViewHolder.rewardInfo.animate()
//                            .translationY(0)
//                            .alpha(1.0f)
//                            .setDuration(300)
//
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationStart(Animator animation) {
//                                    super.onAnimationStart(animation);
//                                    myViewHolder.rewardInfo.setVisibility(View.VISIBLE);
//                                }
//                            });

//                    Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
//                    myViewHolder.rewardInfo.setVisibility(View.VISIBLE);
//                    myViewHolder.rewardInfo.startAnimation(slideDown);

                    TransitionManager.beginDelayedTransition(group);
                    myViewHolder.esdevenimentInfo.setVisibility(View.VISIBLE);
                    myViewHolder.esdevenimentImage.animate().rotation(180).setDuration(400).start();
                   // myViewHolder.rewardImage.setRotation(180);
                    //                TransitionManager.beginDelayedTransition(group, new Rotate());


                    // slideToBottom(myViewHolder.rewardInfo);


                } else {
                    //  slideToTop(myViewHolder.rewardInfo);

                    //myViewHolder.rewardInfo.setVisibility(View.GONE);
                    myViewHolder.esdevenimentImage.animate().rotation(0).setDuration(400).start();
                   // TransitionManager.beginDelayedTransition(group);
                    myViewHolder.esdevenimentInfo.setVisibility(View.GONE);

                   // myViewHolder.rewardImage.setRotation(0);
//                    myViewHolder.rewardInfo.animate()
//                            .translationY(-myViewHolder.rewardInfo.getHeight())
//                            .alpha(0.0f)
//                            .setDuration(300)
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    myViewHolder.rewardInfo.setVisibility(View.GONE);
//                                }
//                            });

//                    Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
//                    myViewHolder.rewardInfo.setVisibility(View.GONE);
//                    myViewHolder.rewardInfo.startAnimation(slideUp);


                    //myViewHolder.rewardImage.setImageResource(R.drawable.downarrow);
//                    RotateAnimation rotate = new RotateAnimation(180, 0);
//                    rotate.setDuration(400);
//                    rotate.setInterpolator(new LinearInterpolator());
//
//                    myViewHolder.rewardImage.startAnimation(rotate);

                }

//                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                alertDialog.setTitle("Event information");
//                alertDialog.setMessage("Title:   " + esd.getTitol() + "\nType:   " + esd.getTipus() + "\nPlace:   " + esd.getLloc() + "\nDate:   " + esd.getData() + "\nPeople attending:   " + esd.getNumAssistens());
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
            }
        });

        myViewHolder.interested.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    esdG = list.get(i);
                    sendNotification(new View(context));

                } else {
                    esdG = list.get(i);

                    manager.updateEsdeveniment(String.valueOf(esdG.getId()), false);


                }
            }
        });

    }

    public void sendNotification(View view) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("fromNotification", "attending?");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent intentConfirm = new Intent(context, NotificationActionReceiver.class);
        intentConfirm.putExtra(EXTRA_ID, String.valueOf(esdG.getId()));
        intentConfirm.setAction("CONFIRM");
        intentConfirm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Intent intentCancel = new Intent(context, NotificationActionReceiver.class);
        intentCancel.setAction("CANCEL");
        intentCancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        PendingIntent pendingIntentConfirm = PendingIntent.getBroadcast(context, 0, intentConfirm, PendingIntent.FLAG_CANCEL_CURRENT);

        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, 1, intentCancel, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.feministicon)
                .setBadgeIconType(R.drawable.feministicon)
                .setContentTitle("Attending to an event")
                .setContentText("" + "Are you sure you want to attend to the event?\nLong click to show buttons")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationBuilder.addAction(R.drawable.ic_nav_menu, "Confirm", pendingIntentConfirm);
        notificationBuilder.addAction(R.drawable.ic_nav_menu, "Cancel", pendingIntentCancel);


        //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


        notificationManager.notify(11111 /* ID of notification */, notificationBuilder.build());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
