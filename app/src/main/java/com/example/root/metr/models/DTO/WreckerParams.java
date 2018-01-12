package com.example.root.metr.models.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import polanski.option.Option;

public class WreckerParams {

    @SerializedName("switch")
    public List<Switch> mswitch;
    @SerializedName("spinner")
    public List<Spinner> spinner;

    public List<Switch> getMswitch() {
        return Option.ofObj(mswitch).orDefault(ArrayList::new);
    }

    public List<Spinner> getSpinner() {
        return Option.ofObj(spinner).orDefault(ArrayList::new);
    }

    public static class Switch {
        @SerializedName("name")
        public String name;
        @SerializedName("label")
        public String label;

        public String getName() {
            return Option.ofObj(name).orDefault(() -> "");
        }

        public String getLabel() {
            return Option.ofObj(label).orDefault(() -> "");
        }
    }

    public static class Items {
        @SerializedName("name")
        public String name;
        @SerializedName("value")
        public String value;

        public String getName() {
            return Option.ofObj(name).orDefault(() -> "");
        }

        public String getValue() {
            return Option.ofObj(value).orDefault(() -> "");
        }
    }

    public static class Spinner {
        @SerializedName("name")
        public String name;
        @SerializedName("label")
        public String label;
        @SerializedName("items")
        public List<Items> items;

        public String getName() {
            return Option.ofObj(name).orDefault(() -> "");
        }

        public String getLabel() {
            return Option.ofObj(label).orDefault(() -> "");
        }

        public List<Items> getItems() {
            return Option.ofObj(items).orDefault(ArrayList::new);
        }

        public List<String> getNamesItemSpinner(){
            return rx.Observable.from(getItems()).concatMap(items1 -> rx.Observable.just(items1.getName()))
                    .toList().toBlocking().first();
        }
    }
}
