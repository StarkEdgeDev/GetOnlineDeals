package com.app.getonlinedeals.Features.ShippingAddress;

import java.util.ArrayList;

public class CountriesListModel {
    private ArrayList<Countries> countries = new ArrayList<>();

    public ArrayList<Countries> getCountries() {
        return countries;
    }

    public class Countries {
        private String id;
        private String name;
        private String tax;
        private String tax_name;
        private String code;
        private ArrayList<Provinces> provinces = new ArrayList<>();

        public String getCode() {
            return code;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTax() {
            return tax;
        }

        public String getTax_name() {
            return tax_name;
        }

        public ArrayList<Provinces> getProvinces() {
            return provinces;
        }
    }

    public class Provinces {
        private String id;
        private String country_id;
        private String name;
        private String code;
        private String tax;
        private String tax_name;
        private String tax_type;
        private String shipping_zone_id;
        private String tax_percentage;

        public String getId() {
            return id;
        }

        public String getCountry_id() {
            return country_id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getTax() {
            return tax;
        }

        public String getTax_name() {
            return tax_name;
        }

        public String getTax_type() {
            return tax_type;
        }

        public String getShipping_zone_id() {
            return shipping_zone_id;
        }

        public String getTax_percentage() {
            return tax_percentage;
        }
    }
}
