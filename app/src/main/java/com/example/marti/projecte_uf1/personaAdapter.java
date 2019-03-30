package com.example.marti.projecte_uf1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

public class personaAdapter extends RecyclerView.Adapter<personaAdapter.MyViewHolder> {

    private ArrayList<Persona> list;
    private Context context;
    ImageView letter;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button sendMail;
        public TextView textViewName;
        public LinearLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            sendMail = view.findViewById(R.id.sendMail);
            relativeLayout = view.findViewById(R.id.perones_list);
            letter = (ImageView) itemView.findViewById(R.id.gmailitem_letter);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public personaAdapter(ArrayList<Persona> personesList, Context context) {
        this.list = personesList;
        this.context = context;
    }

    @NonNull
    @Override
    public personaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.persona_list_item, viewGroup, false);

        return new MyViewHolder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull personaAdapter.MyViewHolder myViewHolder, final int i) {
        Persona p = list.get(i);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        String name = p.getNom();
        String letter2 = String.valueOf(list.get(i).getNom().charAt(0));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter2, generator.getRandomColor());
        letter.setImageDrawable(drawable);

        myViewHolder.textViewName.setText(p.getNom());
        myViewHolder.sendMail.setText("Send Mail");
        myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona p = list.get(i);

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Contact information");
                alertDialog.setMessage("Name: " + p.getNom() + "\nAge: " + p.getEdat() + "\nPhone number: " + p.getTelefon() + "\nDescription: " + p.getDescripcio()    );
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CLose",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

        myViewHolder.sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona p = list.get(i);


                try {
                    Intent sendMailIntent = new Intent(Intent.ACTION_SENDTO);

                    sendMailIntent.setData(Uri.parse("mailto:" + p.getEmail()));
                    context.startActivity(sendMailIntent);
                } catch (Exception ex) {
                    Intent emailIntent = new Intent(
                            android.content.Intent.ACTION_SEND);
                    emailIntent.setAction(Intent.ACTION_SEND);
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                            new String[]{p.getEmail()});
                    emailIntent.putExtra(android.content.Intent.EXTRA_CC, "");
                    emailIntent.putExtra(android.content.Intent.EXTRA_BCC, "");
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            "");
                    //emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(Detail));
                    emailIntent.setType("text/html");


                    context.startActivity(Intent.createChooser(emailIntent,
                            "There is no default app to send emails, choose one: "));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
