package com.example.laborator08;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ListView listView = findViewById(R.id.lvImages);
        List<LaptopInfo> laptopList = new ArrayList<>();

        laptopList.add(new LaptopInfo(
            "https://s13emagst.akamaized.net/products/100391/100390199/images/res_c5fb81d6b9798a54c9c93bb2c8121877.png?width=720&height=720&hash=550CD0F6D1A470B33B58E4CD1E09937F",
            "Laptop Gaming Dell Alienware",
            "https://www.emag.ro/laptop-gaming-dell-alienware-16x-aurora-ac16251-cu-procesor-intelr-coretm-ultra-9-275hx-pana-la-5-4ghz-16-qhd-240hz-32gb-ddr5-ram-1tb-ssd-nvidiar-geforce-rtxtm-5070-8gb-gddr7-windows-11-pro-3y-onsite-/pd/DJNRLF3BM/?ref=sponsored_products_listing_f_c_1_1&recid=recads_1_6e23654b6b1153e63bc1e403b659692819c4db991e3125eefcc24a79a9f2f17d_1776713800&aid=625cb226-1e66-11f1-801c-06eaf0d4245d&oid=277999541&scenario_ID=1"));
        laptopList.add(new LaptopInfo(
            "https://s13emagst.akamaized.net/products/111116/111115893/images/res_364eaf9f7622ac2ab75ec75924a3f32a.png?width=720&height=720&hash=92D1A1A13177FD00B7A8A672FACB52D0",
            "Laptop Lenovo IdeaPad Slim 3",
            "https://www.emag.ro/laptop-lenovo-ideapad-slim-3-15ian8-cu-procesor-intelr-n100-pana-la-3-4ghz-15-6-fhd-8gb-lpddr5-128gb-ufs-intelr-uhd-graphics-windowsr-11-home-s-arctic-grey-82xb00hxrm/pd/DCK5N63BM/"));
        laptopList.add(new LaptopInfo(
            "https://s13emagst.akamaized.net/products/115787/115786875/images/res_6c64d6bf8dabc204fe8527c5e16e186d.jpg?width=720&height=720&hash=DA125BCFCA5A23221F2736B728F9D432",
            "Apple MacBook Neo 13",
            "https://www.emag.ro/apple-macbook-neo-13-retina-cu-procesor-apple-a18-pro-6-nuclee-cpu-5-nuclee-gpu-16-nuclee-neuralengine-8gb-ram-512gb-ssd-indigo-tastatura-internationala-manual-ro-mhfg4ro-a/pd/D9R4HM2BM/"));
        laptopList.add(new LaptopInfo(
            "https://s13emagst.akamaized.net/products/96842/96841557/images/res_d608a26d3e0efe808f725dc5a949ee4b.jpg?width=720&height=720&hash=F1E33A7BB50F9753EE49054EAF2529A0",
            "Laptop Blackview Acebook 6",
            "https://www.emag.ro/laptop-blackview-acebook-6-16gb-ddr4-256gb-ssd-intel-n150-3-6ghz-intel-uhd-graphics-win11-home-38wh-15-6-fhd-hdmi-2-0-bluetooth-5-0-usb-3-0-wlan-5g-gri-blackviewacebook616-256-grey/pd/DDCL5H3BM/?ref=sponsored_products_listing_f_c_1_3&recid=recads_1_6e23654b6b1153e63bc1e403b659692819c4db991e3125eefcc24a79a9f2f17d_1776713800&aid=a8bcbb6e-3c92-11f1-801c-06eaf0d4245d&oid=265487959&scenario_ID=1"));
        laptopList.add(new LaptopInfo(
            "https://s13emagst.akamaized.net/products/109153/109152834/images/res_e0f514f9b108e6af903927ec6a8cb00e.jpg?width=720&height=720&hash=48B52F28FA7C5E5D1800047DD1F9AF74",
            "Laptop Auusda",
            "https://www.emag.ro/laptop-auusda-cu-procesor-intel-alder-celeon-n5100-4c-4t-16gb-512go-ssd-15-6-fhd-windows-11-intel-uhd-graphics-roz-n5100-16-512-rose/pd/D7SG7R3BM/?ref=sponsored_products_listing_f_c_1_4&recid=recads_1_6e23654b6b1153e63bc1e403b659692819c4db991e3125eefcc24a79a9f2f17d_1776713800&aid=ab981a64-fdbf-11f0-801c-06eaf0d4245d&oid=295735628&scenario_ID=1"));

        LaptopAdapter adapter = new LaptopAdapter(this, laptopList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            LaptopInfo selected = laptopList.get(position);
            Intent intent = new Intent(ImageActivity.this, WebViewActivity.class);
            intent.putExtra("url", selected.getWebUrl());
            startActivity(intent);
        });
    }
}
