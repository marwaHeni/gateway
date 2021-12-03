package com.example.servicecompany.web.vm;

import java.util.UUID;

public class VariantValueConfigTranslate {

    UUID id;
    String nameConfig;
    String nameConfigTranslated;


    public VariantValueConfigTranslate() {
    }


    public VariantValueConfigTranslate(UUID id, String nameConfig, String nameConfigTranslated) {
        this.id = id;
        this.nameConfig = nameConfig;
        this.nameConfigTranslated = nameConfigTranslated;
    }


    public UUID getId() {
        return id;
    }

    public VariantValueConfigTranslate(VariantValueConfigTranslate variantValueConfigTranslate) {
        this.id = variantValueConfigTranslate.id;
        this.nameConfig = variantValueConfigTranslate.nameConfig;
        this.nameConfigTranslated = variantValueConfigTranslate.nameConfigTranslated;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public String getNameConfig() {
        return nameConfig;
    }

    public void setNameConfig(String nameConfig) {
        this.nameConfig = nameConfig;
    }

    public String getNameConfigTranslated() {
        return nameConfigTranslated;
    }

    public void setNameConfigTranslated(String nameConfigTranslated) {
        this.nameConfigTranslated = nameConfigTranslated;
    }
}
