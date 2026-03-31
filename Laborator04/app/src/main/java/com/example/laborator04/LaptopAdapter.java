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
    private final Context context;
    private final int resource;

    static class ViewHolder {
        TextView tvModel, tvDetails, tvPrice;
    }

    public LaptopAdapter(@NonNull Context context, int resource, @NonNull List<DispozitivLaptop> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.tvModel = convertView.findViewById(R.id.tvModelItem);
            holder.tvDetails = convertView.findViewById(R.id.tvDetailsItem);
            holder.tvPrice = convertView.findViewById(R.id.tvPriceItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DispozitivLaptop laptop = getItem(position);

        if (laptop != null) {
            holder.tvModel.setText(laptop.getModel());

            String details = context.getString(R.string.laptop_details_format,
                    laptop.getMemorieRAM(),
                    laptop.getTipEcran());
            holder.tvDetails.setText(details);

            String price = context.getString(R.string.laptop_price_format,
                    laptop.getPret());
            holder.tvPrice.setText(price);
        }

        return convertView;
    }
}