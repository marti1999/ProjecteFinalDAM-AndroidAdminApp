package com.example.marti.projecte_uf1.RequestorFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.SQLiteManager;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Warehouse;
import com.example.marti.projecte_uf1.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public class clothAdapter extends RecyclerView.Adapter<clothAdapter.MyViewHolder> {

    private ArrayList<Warehouse> list;
    private Context context;
    public ApiMecAroundInterfaces mAPIService;
    public clothFragment fragment;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView warehouseAddress;
        public TextView warehouseAddress2;
        public Button btMap;
        public LinearLayout linearLayout;
        public ImageView warehouseImage;


        public MyViewHolder(View view) {
            super(view);
            warehouseAddress = view.findViewById(R.id.tvAddress);
            warehouseAddress2 = view.findViewById(R.id.tvAddress2);

            linearLayout = view.findViewById(R.id.warehouse_list);
            btMap = view.findViewById(R.id.btViewOnMap);

            warehouseImage = view.findViewById(R.id.warehouse_image);
        }

        @Override
        public void onClick(View v) {

        }
    }



    public clothAdapter(ArrayList<Warehouse> warehouseArrayList, Context context, clothFragment fragment) {
        this.list = warehouseArrayList;
        this.context = context;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public clothAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.warehouse_list_item, viewGroup, false);


        mAPIService = ApiUtils.getAPIService();

        return new MyViewHolder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull final clothAdapter.MyViewHolder myViewHolder, final int i) {
        final Warehouse warehouse = list.get(i);




        myViewHolder.warehouseAddress.setText(warehouse.street + ", " + warehouse.number);
        myViewHolder.warehouseAddress2.setText(warehouse.postalCode+ ",  " +warehouse.city);


        ColorGenerator generator = ColorGenerator.MATERIAL;
        String name = warehouse.city;
        String letter2 = String.valueOf(name.charAt(0));
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect(letter2, generator.getRandomColor(), 10);



        myViewHolder.warehouseImage.setImageDrawable(drawable);

        myViewHolder.btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Warehouse> list = new ArrayList<Warehouse>();
                list.add(warehouse);
               // Toast.makeText(context, "aqui se deberia ver el mapa TODO", Toast.LENGTH_SHORT).show();
                fragment.showWarehouseOnMap(warehouse);
            }
        });


    }





    @Override
    public int getItemCount() {
        return list.size();
    }


}
