package com.example.c114b_dinu_liviu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EvenimentAdapter extends ArrayAdapter<Eveniment> {
    public EvenimentAdapter(@NonNull Context context, int resource, @NonNull List<Eveniment> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.item_eveniment, parent, false);
        }

        Eveniment eveniment = getItem(position);
        if (eveniment != null) {
            android.widget.TextView denumireTextView = convertView.findViewById(R.id.dldentextView);
            android.widget.TextView locatieTextView = convertView.findViewById(R.id.dlloctextView);
            android.widget.TextView descriereTextView = convertView.findViewById(R.id.dldesctextView);
            android.widget.TextView dataTextView = convertView.findViewById(R.id.dldatatextView);

            denumireTextView.setText(getContext().getString(R.string.item_eveniment_denumire, eveniment.getDenumire()));
            locatieTextView.setText(getContext().getString(R.string.item_eveniment_locatie, eveniment.getLocatie()));
            descriereTextView.setText(getContext().getString(R.string.item_eveniment_descriere, eveniment.getDescriere()));
            if (dataTextView != null) {
                dataTextView.setText(getContext().getString(R.string.item_eveniment_data, eveniment.getData()));
            }
        }

        return convertView;
    }
}
