package model;

/**
 * The In_House class inherits the Part class's members.
 */
public class In_House extends Part {
    /**
     * Private integer machine ID that applies to only in-house parts.
     */
    private int machineId;

    /**
     * The In_House object has all the same parameters as a Part object except the machine ID.
     * First six arguments are passed to a super constructor.
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part stock
     * @param min part minimum inventory
     * @param max part maximum inventory
     * @param machineId in-house part machine ID
     */
    public In_House(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter method for returning the machine ID.
     * @return returns machine ID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter method for setting the machine ID.
     * @param machineId takes in machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
