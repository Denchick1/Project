package com.example.root.metr.models.view_model;

public class QueryWrecker {

    private String id;

    private String wrecker_type;

    private String wrecker_model;

    private String wrecker_mark;

    private String wreckers_carrying;

    private String number_auto;

    private boolean insured;

    private boolean crane_winch;

    private boolean rigid_coupling;

    private boolean fastening_elements;

    public QueryWrecker(String id, String wrecker_type, String wrecker_model, String wrecker_mark, String wreckers_carrying,
                        String number_auto, boolean insured, boolean crane_winch, boolean rigid_coupling, boolean fastening_elements) {
        this.id = id;
        this.wrecker_type = wrecker_type;
        this.wrecker_model = wrecker_model;
        this.wrecker_mark=wrecker_mark;
        this.wreckers_carrying = wreckers_carrying;
        this.number_auto = number_auto;
        this.insured = insured;
        this.crane_winch = crane_winch;
        this.rigid_coupling = rigid_coupling;
        this.fastening_elements = fastening_elements;
    }

    public String getId() {
        return id;
    }

    public String getWrecker_type() {
        return wrecker_type;
    }

    public String getWrecker_model() {
        return wrecker_model;
    }

    public String getWrecker_mark() {
        return wrecker_mark;
    }

    public String getWreckers_carrying() {
        return wreckers_carrying;
    }

    public String getNumber_auto() {
        return number_auto;
    }

    public boolean isInsured() {
        return insured;
    }

    public boolean isCrane_winch() {
        return crane_winch;
    }

    public boolean isRigid_coupling() {
        return rigid_coupling;
    }

    public boolean isFastening_elements() {
        return fastening_elements;
    }
}
