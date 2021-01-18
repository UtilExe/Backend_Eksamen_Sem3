
package dto;

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
 //   private String primærtnavn;
    private Object[] kommuner; // kommune
    private TestDTO egenskaber; 
    private int indbyggerantal;
   // private int population;
    private Object[] visueltcenter; // visueltcenter

    public String getName() {
        return primærtnavn;
    }
    
    public Object[] getKommuner() {
        return kommuner;
    }

    public Object[] getVisueltcenter() {
        return visueltcenter;
    }

    public int getEgenskaber() {
        return egenskaber.getIndbyggerantal();
    }

    
    
}
