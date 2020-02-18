package com.app.getonlinedeals.Features.DealsList;

import java.util.ArrayList;

public class DealsResponse {
    private ArrayList<Products> products = new ArrayList<>();

    public ArrayList<Products> getProducts() {
        return products;
    }

    public class Products {
        private String id;
        private String title;
        private Image image;
        private ArrayList<Variants> variants = new ArrayList<>();

        public ArrayList<Variants> getVariants() {
            return variants;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Image getImage() {
            return image;
        }
    }

    public class Variants {
        private String price;
        private String id;
        private String compare_at_price;
        private String title;
        private String inventory_quantity;
        private String image_id;

        public String getImage_id() {
            return image_id;
        }

        public String getInventory_quantity() {
            return inventory_quantity;
        }

        public String getTitle() {
            return title;
        }

        public String getId() {
            return id;
        }

        public String getPrice() {
            return price;
        }

        public String getCompare_at_price() {
            return compare_at_price;
        }
    }

    public class Image {
        private String src;
        private String id;

        public String getId() {
            return id;
        }

        public String getSrc() {
            return src;
        }
    }
}
