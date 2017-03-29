package edu.ayd.joyfukitchen.bean;

import java.util.Date;

/**
 * Created by 萝莉 on 2017/3/28.
 */
public class FoodNutrition {
    private Integer id;   //编号
    private Integer food_id;  //类型（外键Food_id）
    private String name;    //名称
    private Float water;
    private String water_unit;
    private Float energy;
    private String energy_unit;
    private Float protein;
    private String protein_unit;
    private Float fat;
    private String fat_unit;
    private Float carbohydrate;
    private String carbohydrate_unit;
    private Float df;
    private String df_unit;
    private Float cholesterol;
    private String cholesterol_unit;
    private Float vitamina;
    private String vitamina_unit;
    private Float carotene;
    private String carotene_unit;
    private Float retinol;
    private String retinol_unit;
    private Float vitaminb1;
    private String vitaminb1_unit;
    private Float vitaminb2;
    private String vitaminb2_unit;
    private Float niacin;
    private String niacin_unit;
    private Float vitaminc;
    private String vitaminc_unit;
    private Float vitamine;
    private String vitamine_unit;
    private Float ca;
    private String ca_unit;
    private Float p;
    private String p_unit;
    private Float k;
    private String k_unit;
    private Float na;
    private String na_unit;
    private Float mg;
    private String mg_unit;
    private Float fe;
    private String fe_unit;
    private Float zn;
    private String zn_unit;
    private Float cu;
    private String cu_unit;
    private Float mn;
    private String mn_unit;
    private Float se;
    private String se_unit;
    private Date createtime;

    @Override
    public String toString() {
        return "FoodNutrition{" +
                "id=" + id +
                ", food_id=" + food_id +
                ", name='" + name + '\'' +
                ", water=" + water +
                ", water_unit='" + water_unit + '\'' +
                ", energy=" + energy +
                ", energy_unit='" + energy_unit + '\'' +
                ", protein=" + protein +
                ", protein_unit='" + protein_unit + '\'' +
                ", fat=" + fat +
                ", fat_unit='" + fat_unit + '\'' +
                ", carbohydrate=" + carbohydrate +
                ", carbohydrate_unit='" + carbohydrate_unit + '\'' +
                ", df=" + df +
                ", df_unit='" + df_unit + '\'' +
                ", cholesterol=" + cholesterol +
                ", cholesterol_unit='" + cholesterol_unit + '\'' +
                ", vitamina=" + vitamina +
                ", vitamina_unit='" + vitamina_unit + '\'' +
                ", carotene=" + carotene +
                ", carotene_unit='" + carotene_unit + '\'' +
                ", retinol=" + retinol +
                ", retinol_unit='" + retinol_unit + '\'' +
                ", vitaminb1=" + vitaminb1 +
                ", vitaminb1_unit='" + vitaminb1_unit + '\'' +
                ", vitaminb2=" + vitaminb2 +
                ", vitaminb2_unit='" + vitaminb2_unit + '\'' +
                ", niacin=" + niacin +
                ", niacin_unit='" + niacin_unit + '\'' +
                ", vitaminc=" + vitaminc +
                ", vitaminc_unit='" + vitaminc_unit + '\'' +
                ", vitamine=" + vitamine +
                ", vitamine_unit='" + vitamine_unit + '\'' +
                ", ca=" + ca +
                ", ca_unit='" + ca_unit + '\'' +
                ", p=" + p +
                ", p_unit='" + p_unit + '\'' +
                ", k=" + k +
                ", k_unit='" + k_unit + '\'' +
                ", na=" + na +
                ", na_unit='" + na_unit + '\'' +
                ", mg=" + mg +
                ", mg_unit='" + mg_unit + '\'' +
                ", fe=" + fe +
                ", fe_unit='" + fe_unit + '\'' +
                ", zn=" + zn +
                ", zn_unit='" + zn_unit + '\'' +
                ", cu=" + cu +
                ", cu_unit='" + cu_unit + '\'' +
                ", mn=" + mn +
                ", mn_unit='" + mn_unit + '\'' +
                ", se=" + se +
                ", se_unit='" + se_unit + '\'' +
                ", createtime=" + createtime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWater() {
        return water;
    }

    public void setWater(Float water) {
        this.water = water;
    }

    public String getWater_unit() {
        return water_unit;
    }

    public void setWater_unit(String water_unit) {
        this.water_unit = water_unit;
    }

    public Float getEnergy() {
        return energy;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public String getEnergy_unit() {
        return energy_unit;
    }

    public void setEnergy_unit(String energy_unit) {
        this.energy_unit = energy_unit;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public String getProtein_unit() {
        return protein_unit;
    }

    public void setProtein_unit(String protein_unit) {
        this.protein_unit = protein_unit;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public String getFat_unit() {
        return fat_unit;
    }

    public void setFat_unit(String fat_unit) {
        this.fat_unit = fat_unit;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getCarbohydrate_unit() {
        return carbohydrate_unit;
    }

    public void setCarbohydrate_unit(String carbohydrate_unit) {
        this.carbohydrate_unit = carbohydrate_unit;
    }

    public Float getDf() {
        return df;
    }

    public void setDf(Float df) {
        this.df = df;
    }

    public String getDf_unit() {
        return df_unit;
    }

    public void setDf_unit(String df_unit) {
        this.df_unit = df_unit;
    }

    public Float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCholesterol_unit() {
        return cholesterol_unit;
    }

    public void setCholesterol_unit(String cholesterol_unit) {
        this.cholesterol_unit = cholesterol_unit;
    }

    public Float getVitamina() {
        return vitamina;
    }

    public void setVitamina(Float vitamina) {
        this.vitamina = vitamina;
    }

    public String getVitamina_unit() {
        return vitamina_unit;
    }

    public void setVitamina_unit(String vitamina_unit) {
        this.vitamina_unit = vitamina_unit;
    }

    public Float getCarotene() {
        return carotene;
    }

    public void setCarotene(Float carotene) {
        this.carotene = carotene;
    }

    public String getCarotene_unit() {
        return carotene_unit;
    }

    public void setCarotene_unit(String carotene_unit) {
        this.carotene_unit = carotene_unit;
    }

    public Float getRetinol() {
        return retinol;
    }

    public void setRetinol(Float retinol) {
        this.retinol = retinol;
    }

    public String getRetinol_unit() {
        return retinol_unit;
    }

    public void setRetinol_unit(String retinol_unit) {
        this.retinol_unit = retinol_unit;
    }

    public Float getVitaminb1() {
        return vitaminb1;
    }

    public void setVitaminb1(Float vitaminb1) {
        this.vitaminb1 = vitaminb1;
    }

    public String getVitaminb1_unit() {
        return vitaminb1_unit;
    }

    public void setVitaminb1_unit(String vitaminb1_unit) {
        this.vitaminb1_unit = vitaminb1_unit;
    }

    public Float getVitaminb2() {
        return vitaminb2;
    }

    public void setVitaminb2(Float vitaminb2) {
        this.vitaminb2 = vitaminb2;
    }

    public String getVitaminb2_unit() {
        return vitaminb2_unit;
    }

    public void setVitaminb2_unit(String vitaminb2_unit) {
        this.vitaminb2_unit = vitaminb2_unit;
    }

    public Float getNiacin() {
        return niacin;
    }

    public void setNiacin(Float niacin) {
        this.niacin = niacin;
    }

    public String getNiacin_unit() {
        return niacin_unit;
    }

    public void setNiacin_unit(String niacin_unit) {
        this.niacin_unit = niacin_unit;
    }

    public Float getVitaminc() {
        return vitaminc;
    }

    public void setVitaminc(Float vitaminc) {
        this.vitaminc = vitaminc;
    }

    public String getVitaminc_unit() {
        return vitaminc_unit;
    }

    public void setVitaminc_unit(String vitaminc_unit) {
        this.vitaminc_unit = vitaminc_unit;
    }

    public Float getVitamine() {
        return vitamine;
    }

    public void setVitamine(Float vitamine) {
        this.vitamine = vitamine;
    }

    public String getVitamine_unit() {
        return vitamine_unit;
    }

    public void setVitamine_unit(String vitamine_unit) {
        this.vitamine_unit = vitamine_unit;
    }

    public Float getCa() {
        return ca;
    }

    public void setCa(Float ca) {
        this.ca = ca;
    }

    public String getCa_unit() {
        return ca_unit;
    }

    public void setCa_unit(String ca_unit) {
        this.ca_unit = ca_unit;
    }

    public Float getP() {
        return p;
    }

    public void setP(Float p) {
        this.p = p;
    }

    public String getP_unit() {
        return p_unit;
    }

    public void setP_unit(String p_unit) {
        this.p_unit = p_unit;
    }

    public Float getK() {
        return k;
    }

    public void setK(Float k) {
        this.k = k;
    }

    public String getK_unit() {
        return k_unit;
    }

    public void setK_unit(String k_unit) {
        this.k_unit = k_unit;
    }

    public Float getNa() {
        return na;
    }

    public void setNa(Float na) {
        this.na = na;
    }

    public String getNa_unit() {
        return na_unit;
    }

    public void setNa_unit(String na_unit) {
        this.na_unit = na_unit;
    }

    public Float getMg() {
        return mg;
    }

    public void setMg(Float mg) {
        this.mg = mg;
    }

    public String getMg_unit() {
        return mg_unit;
    }

    public void setMg_unit(String mg_unit) {
        this.mg_unit = mg_unit;
    }

    public Float getFe() {
        return fe;
    }

    public void setFe(Float fe) {
        this.fe = fe;
    }

    public String getFe_unit() {
        return fe_unit;
    }

    public void setFe_unit(String fe_unit) {
        this.fe_unit = fe_unit;
    }

    public Float getZn() {
        return zn;
    }

    public void setZn(Float zn) {
        this.zn = zn;
    }

    public String getZn_unit() {
        return zn_unit;
    }

    public void setZn_unit(String zn_unit) {
        this.zn_unit = zn_unit;
    }

    public Float getCu() {
        return cu;
    }

    public void setCu(Float cu) {
        this.cu = cu;
    }

    public String getCu_unit() {
        return cu_unit;
    }

    public void setCu_unit(String cu_unit) {
        this.cu_unit = cu_unit;
    }

    public Float getMn() {
        return mn;
    }

    public void setMn(Float mn) {
        this.mn = mn;
    }

    public String getMn_unit() {
        return mn_unit;
    }

    public void setMn_unit(String mn_unit) {
        this.mn_unit = mn_unit;
    }

    public Float getSe() {
        return se;
    }

    public void setSe(Float se) {
        this.se = se;
    }

    public String getSe_unit() {
        return se_unit;
    }

    public void setSe_unit(String se_unit) {
        this.se_unit = se_unit;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
