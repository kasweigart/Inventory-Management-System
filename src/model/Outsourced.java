package model;

/**
 * The Outsourced class inherits the Part class's members.
 */
public class Outsourced extends Part {
    /**
     * An outsourced object holds on one differing class member, the company name.
     */
    private String companyName;

    /**
     * The In_House object has all the same parameters as a Part object except the company name.
     * First six arguments are passed to a super constructor.
     * @param ID part id
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min part minimum inventory
     * @param max part maximum inventory
     * @param companyName outsourced part company name
     */
    public Outsourced(int ID, String name, double price, int stock, int min, int max, String companyName) {
        super(ID, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * Setter method to set the company name.
     * @param companyName takes in company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter method for returning the company name.
     * @return returns company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
