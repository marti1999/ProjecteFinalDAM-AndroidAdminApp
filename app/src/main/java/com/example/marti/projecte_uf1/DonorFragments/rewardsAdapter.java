package com.example.marti.projecte_uf1.DonorFragments;

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
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marti.projecte_uf1.Esdeveniment;
import com.example.marti.projecte_uf1.MainActivity;
import com.example.marti.projecte_uf1.NotificationActionReceiver;
import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.SQLiteManager;
import com.example.marti.projecte_uf1.model.Reward;
import com.example.marti.projecte_uf1.model.RewardInfoLang;

import java.util.ArrayList;

public class rewardsAdapter extends RecyclerView.Adapter<rewardsAdapter.MyViewHolder> {

    private ArrayList<Reward> list;
    private Context context;
    public static final String EXTRA_ID = "ID";
    SQLiteManager manager = new SQLiteManager(context);

    Esdeveniment esdG;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox interested;
        public TextView rewardTitle;
        public RelativeLayout relativeLayout;
        public LinearLayout linearLayout;
        public TextView rewardInfo;
        public ImageView rewardImage;
        public Button claimRewardBt;

        public MyViewHolder(View view) {
            super(view);
            rewardTitle = view.findViewById(R.id.RewardTitle);
            interested = view.findViewById(R.id.interested);
            relativeLayout = view.findViewById(R.id.esdeveniments_list);
            linearLayout = view.findViewById(R.id.rewardLayout);
            rewardInfo = view.findViewById(R.id.rewardInfo);
            rewardImage = view.findViewById(R.id.rewardImageExpand);
            claimRewardBt = view.findViewById(R.id.claimRewardButton);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public rewardsAdapter(ArrayList<Reward> rewardsList, Context context) {
        this.list = rewardsList;
        this.context = context;
    }

    @NonNull
    @Override
    public rewardsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rewards_list_item, viewGroup, false);

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
    public void onBindViewHolder(@NonNull final rewardsAdapter.MyViewHolder myViewHolder, final int i) {
        Reward reward = list.get(i);

        RewardInfoLang infoEng;
        for (RewardInfoLang item : reward.getRewardInfoLangs()){
            if (item.language.equals("En")) { //TODO: mirar si el equals En està igual a la base de dades
                infoEng = item;
            }
        }


        /*  //TODO: descomentar quan ja es tingui lo de dalt;

        System.out.print(reward.getTitol());
        Log.d("missatge: ", reward.getTitol());

        myViewHolder.rewardTitle.setText(reward.getTitol());
        myViewHolder.rewardInfo.setText("Type:   " + reward.getTipus() + "\nPlace:   " + reward.getLloc() + "\nDate:   " + reward.getData() + "\nPeople attending:   " + reward.getNumAssistens());

        myViewHolder.interested.setChecked(reward.isInteressa());
        myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup group = (ViewGroup) myViewHolder.relativeLayout;
                Esdeveniment esd = list.get(i);
                esdG = esd;
                if (myViewHolder.rewardInfo.getVisibility() == View.GONE) {



                    TransitionManager.beginDelayedTransition(group);
                    myViewHolder.rewardInfo.setVisibility(View.VISIBLE);
                    myViewHolder.rewardImage.animate().rotation(180).setDuration(400).start();


                } else {

                    myViewHolder.rewardImage.animate().rotation(0).setDuration(400).start();

                    myViewHolder.rewardInfo.setVisibility(View.GONE);



                }

//
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

        */
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
