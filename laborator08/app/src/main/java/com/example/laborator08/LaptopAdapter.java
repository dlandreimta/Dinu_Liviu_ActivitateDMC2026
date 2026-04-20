package com.example.laborator08;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaptopAdapter extends BaseAdapter {
    private Context context;
    private List<LaptopInfo> laptopList;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public LaptopAdapter(Context context, List<LaptopInfo> laptopList) {
        this.context = context;
        this.laptopList = laptopList;
    }

    @Override
    public int getCount() { return laptopList.size(); }
    @Override
    public Object getItem(int position) { return laptopList.get(position); }
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_laptop, parent, false);
        }

        LaptopInfo currentLaptop = laptopList.get(position);
        ImageView ivLaptop = convertView.findViewById(R.id.ivLaptop);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);

        tvDescription.setText(currentLaptop.getDescription());
        ivLaptop.setImageResource(android.R.drawable.ic_menu_report_image); // Placeholder

        executorService.execute(() -> {
            try {
                InputStream in = new URL(currentLaptop.getImageUrl()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                mainHandler.post(() -> ivLaptop.setImageBitmap(bitmap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return convertView;
    }
}
