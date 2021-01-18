
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
    private TestDTO[] kommuner; // kommune
    private TestDTO egenskaber; 
    private Object[] visueltcenter; // visueltcenter

    public String getName() {
        return primærtnavn;
    }

    public Object[] getVisueltcenter() {
        return visueltcenter;
    } 

    public TestDTO getEgenskaber() {
        return egenskaber;
    }

    public TestDTO[] getKommuner() {
        return kommuner;
    }
    
    
    
}
