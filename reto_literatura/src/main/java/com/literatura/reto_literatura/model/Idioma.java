package com.literatura.reto_literatura.model;

public enum Idioma {
    en("[en]","Ingles"),
    es("[es]","Español"),
    fr("[fr]","Frances"),
    pt("[pt]","Portugues");
    private String idiomaAPI;
    private String idiomaEspanol;
    Idioma(String idiomaAPI, String idiomaEspanol){
        this.idiomaAPI = idiomaAPI;
        this.idiomaEspanol = idiomaEspanol;
    }
    public static Idioma fromString(String texto){
        for (Idioma idioma : Idioma.values()){
            if(idioma.idiomaAPI.equalsIgnoreCase(texto)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Error de idioma, no se encontro el idioma: " + texto);
    }
    public static Idioma fromEspanol(String texto){
        for(Idioma idioma : Idioma.values()){
            if(idioma.idiomaEspanol.equalsIgnoreCase(texto)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Error de idioma: " + texto);
    }
    public String getIdiomaAPI(){return idiomaAPI;}
    public String getIdiomaEspanol(){return idiomaEspanol;}
}