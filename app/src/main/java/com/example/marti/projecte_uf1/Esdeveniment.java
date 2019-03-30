package com.example.marti.projecte_uf1;

public class Esdeveniment {

    public static final String ID = "id";
    public static final String TITOL = "titol";
    public static final String TIPUS = "tipus";
    public static final String LLOC = "lloc";
    public static final String DATA = "data";
    public static final String INTERESSA = "interessa";
    public static final String NUMASSISTENTS = "numAssistents";


    private int id;
    private String titol;
    private String tipus;
    private String lloc;
    private String data;
    private boolean interessa;
    private int numAssistens;

    public Esdeveniment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getLloc() {
        return lloc;
    }

    public void setLloc(String lloc) {
        this.lloc = lloc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isInteressa() {
        return interessa;
    }

    public void setInteressa(boolean interessa) {
        this.interessa = interessa;
    }

    public int getNumAssistens() {
        return numAssistens;
    }

    public void setNumAssistens(int numAssistens) {
        this.numAssistens = numAssistens;
    }

    @Override
    public String toString() {
        return "Esdeveniment{" +
                "id=" + id +
                ", titol='" + titol + '\'' +
                ", tipus='" + tipus + '\'' +
                ", lloc='" + lloc + '\'' +
                ", data='" + data + '\'' +
                ", interessa=" + interessa +
                ", numAssistens=" + numAssistens +
                '}';
    }
}
