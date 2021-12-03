package com.example.servicecompany.web.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VariantConfigTranslate {

    UUID id;
    String nameConfig;
    String nameConfigTranslated;
    Boolean isActivated;

    List<VariantValueConfigTranslate> variantValueConfigs = new ArrayList<>();


    public VariantConfigTranslate() {
    }



    public VariantConfigTranslate(VariantConfigTranslate variantConfigTranslate) {
        this.id = variantConfigTranslate.id;
        this.nameConfig = variantConfigTranslate.nameConfig;
        this.nameConfigTranslated = variantConfigTranslate.nameConfigTranslated;
        this.variantValueConfigs = variantConfigTranslate.variantValueConfigs;
        this.isActivated = variantConfigTranslate.isActivated;
    }

    public UUID getId() {
        return id;
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

    public List<VariantValueConfigTranslate> getVariantValueConfigs() {
        return variantValueConfigs;
    }

    public void setVariantValueConfigs(List<VariantValueConfigTranslate> variantValueConfigs) {
        this.variantValueConfigs = variantValueConfigs;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean activated) {
        isActivated = activated;
    }
}
