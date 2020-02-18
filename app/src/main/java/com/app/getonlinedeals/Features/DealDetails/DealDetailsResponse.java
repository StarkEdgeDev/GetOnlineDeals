package com.app.getonlinedeals.Features.DealDetails;

import com.app.getonlinedeals.Features.DealsList.DealsResponse;

import java.util.ArrayList;

public class DealDetailsResponse {
    private Product product;

    public Product getProduct() {
        return product;
    }

    public class Product {
        private String id;
        private String title;
        private String body_html;
        private DealsResponse.Image image;
        private ArrayList<DealsResponse.Variants> variants = new ArrayList<>();
        private ArrayList<DealsResponse.Image> images = new ArrayList<>();

        public String getBody_html() {
            return body_html;
        }

        public ArrayList<DealsResponse.Image> getImages() {
            return images;
        }

        public ArrayList<DealsResponse.Variants> getVariants() {
            return variants;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public DealsResponse.Image getImage() {
            return image;
        }
    }
}
