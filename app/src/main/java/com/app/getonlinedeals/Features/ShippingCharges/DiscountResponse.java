package com.app.getonlinedeals.Features.ShippingCharges;

import java.util.ArrayList;

public class DiscountResponse {
    ArrayList<PriceRules> price_rules = new ArrayList<>();

    public ArrayList<PriceRules> getPriceRules() {
        return price_rules;
    }

    public static class PriceRules {
        private String title;
        private String id;
        private String value_type;
        private String value;
        private ArrayList<String> entitled_product_ids = new ArrayList<>();
        private ArrayList<String> prerequisite_product_ids = new ArrayList<>();
        private PrerequisiteToEntitlementQuantityRatio prerequisite_to_entitlement_quantity_ratio;

        public PrerequisiteToEntitlementQuantityRatio getPrerequisite_to_entitlement_quantity_ratio() {
            return prerequisite_to_entitlement_quantity_ratio;
        }

        public ArrayList<String> getPrerequisite_product_ids() {
            return prerequisite_product_ids;
        }

        public ArrayList<String> getEntitled_product_ids() {
            return entitled_product_ids;
        }

        public String getTitle() {
            return title;
        }

        public String getId() {
            return id;
        }

        public String getValue_type() {
            return value_type;
        }

        public String getValue() {
            return value;
        }
    }

    public class PrerequisiteToEntitlementQuantityRatio {
        private int prerequisite_quantity;
        private int entitled_quantity;

        public Integer getPrerequisite_quantity() {
            return prerequisite_quantity;
        }

        public Integer getEntitled_quantity() {
            return entitled_quantity;
        }
    }
}
