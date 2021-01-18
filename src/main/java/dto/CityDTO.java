
package dto;

public class CityDTO {
    
    private static CityDTOForDB cityDB = new CityDTOForDB();
    
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
 //   private String primærtnavn;
    private Object[] kommuner; // kommune
    private Object egenskaber;
   // private int population;
    private Object[] visueltcenter; // visueltcenter

    public String getName() {
        return primærtnavn;
    }

    public static CityDTOForDB getCityDB() {
        return cityDB;
    }

    public Object[] getKommuner() {
        return kommuner;
    }

    public Object getEgenskaber() {
        return egenskaber;
    }

    public Object[] getVisueltcenter() {
        return visueltcenter;
    }
    
    

    @Override
    public String toString() {
        return "CityDTO{" + "prim\u00e6rtnavn=" + primærtnavn + ", kommuner=" + kommuner + ", egenskaber=" + egenskaber + ", visueltcenter=" + visueltcenter + '}';
    }

    
}
