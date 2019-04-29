package com.example.marti.projecte_uf1.mutualFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.SQLiteManager;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Announcement;
import com.example.marti.projecte_uf1.remote.ApiUtils;

import java.util.ArrayList;

public class announcementsAdapter extends RecyclerView.Adapter<announcementsAdapter.MyViewHolder> {

    private ArrayList<Announcement> list;
    private Context context;
    public static final String EXTRA_ID = "ID";
    SQLiteManager manager = new SQLiteManager(context);
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    public ApiMecAroundInterfaces mAPIService;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView announcementTitle;
        public TextView announcementRecipient;
        public LinearLayout linearLayout;
        public LinearLayout linearLayoutHeader;
        public LinearLayout linearLayoutContent;
        public TextView announcementMessage;
        public ImageView announcementImage;


        public MyViewHolder(View view) {
            super(view);
            announcementTitle = view.findViewById(R.id.announcementTitle);
            announcementRecipient = view.findViewById(R.id.announcementRecipient);
            linearLayout = view.findViewById(R.id.announcementLayout);
            linearLayoutHeader = view.findViewById(R.id.announcementHeaderLaout);
            linearLayoutContent = view.findViewById(R.id.announcementLayoutContent);
            announcementMessage = view.findViewById(R.id.announcementInfo);
            announcementImage = view.findViewById(R.id.announcementImageExpand);
        }

        @Override
        public void onClick(View v) {

        }
    }


    //TODO: canviar tots els objectes reward per announcements

    public announcementsAdapter(ArrayList<Announcement> announcementsList, Context context) {
        this.list = announcementsList;
        this.context = context;
    }

    @NonNull
    @Override
    public announcementsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.announcement_list_item, viewGroup, false);


        mAPIService = ApiUtils.getAPIService();

        return new MyViewHolder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull final announcementsAdapter.MyViewHolder myViewHolder, final int i) {
        final Announcement announcement = list.get(i);




        myViewHolder.announcementTitle.setText(announcement.title);
        myViewHolder.announcementRecipient.setText(announcement.recipient);
        myViewHolder.announcementMessage.setText(announcement.message);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup group = (ViewGroup) myViewHolder.linearLayout;
                //Reward esd = list.get(i);
                //esdG = esd;
                if (myViewHolder.linearLayoutContent.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(group);
                    myViewHolder.linearLayoutContent.setVisibility(View.VISIBLE);
                    myViewHolder.announcementImage.animate().rotation(180).setDuration(400).start();


                } else {

                    myViewHolder.announcementImage.animate().rotation(0).setDuration(400).start();
                    myViewHolder.linearLayoutContent.setVisibility(View.GONE);

                }
            }
        });


    }





    @Override
    public int getItemCount() {
        return list.size();
    }


}
