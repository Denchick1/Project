package com.example.root.metr.models.DTO;

import com.annimon.stream.internal.Params;

import java.util.List;

public class WreckerLast {


    private int id;
    private String number_auto;
    private int wrecker_type_id;
    private int wrecker_car_id;
    private int status;
    private String wrecker_type;
    private String brand;
    private String model;
    private String url_avatar;
    private Params_element_view params_element_view;
    private List<Params_value> params_value;
    private List<Params> params;

    public static class Switch {
        private String name;
        private String label;
    }

    public static class Items {
        private String name;
        private String value;
    }

    public static class Spinner {
        private String name;
        private String label;
        private List<Items> items;
    }

    public static class Params_element_view {
        private List<Switch> mswitch;
        private List<Spinner> spinner;
    }

    public static class Params_value {
        private String name;
        private String label;
        private String value;
        private String value_label;
        private String full_label;
    }
}
