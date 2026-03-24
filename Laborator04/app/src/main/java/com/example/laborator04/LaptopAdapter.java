package com.example.laborator04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class LaptopAdapter extends ArrayAdapter<DispozitivLaptop> {
    private Context context;
    private int resource;
    private List<DispozitivLaptop> laptopList;

    public LaptopAdapter(@NonNull Context context, int resource, @NonNull List<DispozitivLaptop> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.laptopList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        DispozitivLaptop laptop = laptopList.get(position);

        TextView tvModel = convertView.findViewById(R.id.tvModelItem);
        TextView tvDetails = convertView.findViewById(R.id.tvDetailsItem);
        TextView tvPrice = convertView.findViewById(R.id.tvPriceItem);

        if (laptop != null) {
            tvModel.setText(laptop.getModel());
            tvDetails.setText(laptop.getMemorieRAM() + " GB RAM | " + laptop.getTipEcran());
            tvPrice.setText(laptop.getPret() + " RON");
        }

        return convertView;
    }
}