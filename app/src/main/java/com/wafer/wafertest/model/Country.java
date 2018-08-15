package com.wafer.wafertest.model;

public class Country {
    String name;
    String language;
    String currency;

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getCurrency() {
        return currency;
    }

    public Country(String name, String currency, String language){
        this.name = name;
        this.language = language;
        this.currency = currency;
    }
}

    //{
    // "name":"Afghanistan",
    // "currencies":[{
    //      "code":"AFN",
    //      "name":"Afghan afghani",
    //      "symbol":"؋"}],
    // "languages":[{
    //      "iso639_1":"ps",
    //      "iso639_2":"pus",
    //      "name":"Pashto",
    //      "nativeName":"پښتو"},
    // {"iso639_1":"uz","iso639_2":"uzb","name":"Uzbek","nativeName":"Oʻzbek"},
    // {"iso639_1":"tk","iso639_2":"tuk","name":"Turkmen","nativeName":"Türkmen"}
    // ],
