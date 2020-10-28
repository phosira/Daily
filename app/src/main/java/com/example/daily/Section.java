package com.example.daily;

import java.util.List;

public class Section {
    String sectionName;
    List<String> sectionItems;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<String> getSectionItems() {
        return sectionItems;
    }

    public void setSectionItems(List<String> sectionItems) {
        this.sectionItems = sectionItems;
    }

    public Section(String sectionName, List<String> sectionItems) {
        this.sectionName = sectionName;
        this.sectionItems = sectionItems;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionName='" + sectionName + '\'' +
                ", sectionItems=" + sectionItems +
                '}';
    }
}
