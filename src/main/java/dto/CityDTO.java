package dto;

import dto.DTOForCherry.KommuneNameDTO;
import dto.DTOForCherry.EgenskaberDTO;

public class CityDTO {

    // Request data
    private String primærtnavn;

    public CityDTO(String primærtnavn) {
        this.primærtnavn = primærtnavn;
    }

    public String getPrimærtnavn() {
        return primærtnavn;
    }

    public void setPrimærtnavn(String primærtnavn) {
        this.primærtnavn = primærtnavn;
    }

    // Response data
    private KommuneNameDTO[] kommuner; // kommune
    private EgenskaberDTO egenskaber;
    private Object[] visueltcenter; // visueltcenter

    public String getName() {
        return primærtnavn;
    }

    public Object[] getVisueltcenter() {
        return visueltcenter;
    }

    public EgenskaberDTO getEgenskaber() {
        return egenskaber;
    }

    public String getVisueltcenterString() {
        String result = "";
        for (int i = 0; i < visueltcenter.length; i++) {
            if (i > 0) {
                result += ", ";
            }
            result += visueltcenter[i];
        }
        return result;
    }

    public String getKommuneName() {
        String result = "";

        if (kommuner[0].getNavn() != null) {
            // For loop in case there are more kommuner than 1.., like Hvidvore
            for (int i = 0; i < kommuner.length; i++) {
                if (i > 0) {
                    result += ", ";
                }
                result += kommuner[i].getNavn();
            }
        }
        return result;
    }

}
