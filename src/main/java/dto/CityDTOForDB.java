package dto;

import entities.CityInfo;

public class CityDTOForDB {

    private String primærtNavn;
    private String name;
    private double geocoordinates;
    private String municipality;
    private int population;

    public CityDTOForDB(CityInfo city) {
        this.name = city.getName();
        this.geocoordinates = city.getGeocoordinates();
        this.municipality = city.getMunicipality();
        this.population = city.getPopulation();
    }


    public CityDTOForDB() {
    }



    public String getName() {
        return name;
    }

    public double getGeocoordinates() {
        return geocoordinates;
    }

    public String getMunicipality() {
        return municipality;
    }

    public int getPopulation() {
        return population;
    }

    public String getPrimærtNavn() {
        return primærtNavn;
    }

    public void setPrimærtNavn(String primærtNavn) {
        this.primærtNavn = primærtNavn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeocoordinates(double geocoordinates) {
        this.geocoordinates = geocoordinates;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setPopulation(int population) {
        this.population = population;
    }


}
