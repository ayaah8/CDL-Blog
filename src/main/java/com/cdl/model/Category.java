package com.cdl.model;

public class Category {
    private int id;
    private String nameFr;
    private String nameEn;
    private String slug;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNameFr() { return nameFr; }
    public void setNameFr(String nameFr) { this.nameFr = nameFr; }
    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getName(String lang) { return "fr".equals(lang) ? nameFr : nameEn; }
}
