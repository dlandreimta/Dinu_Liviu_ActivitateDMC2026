package com.example.laborator08;

public class LaptopInfo {
    private String imageUrl;
    private String description;
    private String webUrl;

    public LaptopInfo(String imageUrl, String description, String webUrl) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.webUrl = webUrl;
    }

    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public String getWebUrl() { return webUrl; }
}
