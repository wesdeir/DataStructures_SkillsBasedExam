import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is a program that generates a report of vehicle tire inventory from a CSV file.
 * The program reads license plates and tire identifiers, grouping them by vehicle.
 */
public class Tires {
    /**
     * Reads autodata.csv that contains vehicle tire inventory data and generates a report
     * showing each license plate with its corresponding tire identifiers.
     *
     * @param args defaults to "autodata.csv" in the current directory.
     */
    public static void main(String[] args) {
        String filename = "autodata.csv";  // default filename
        // Map to store license plates and their associated tire IDs
        Map<String, Set<String>> carTires = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // skip header if its there (!= null)
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String plate = parts[0].trim();
                    String tireId = parts[1].trim();
                    
                    // Check if we already have a set for this license plate
                    if (!carTires.containsKey(plate)) {
                        carTires.put(plate, new HashSet<>());
                    }
                    carTires.get(plate).add(tireId);
                }
            }// end while
            // print out the report to the console
            for (Map.Entry<String, Set<String>> entry : carTires.entrySet()) {
                String plate = entry.getKey();
                Set<String> tires = entry.getValue();
                // we only print if we have all 4 tires
                if (tires.size() == 4) {
                    System.out.println(plate + " " + String.join(" ", tires));
                } else {
                    System.out.println(plate + "Found (" + tires.size() + ")" + " tires instead of 4");
                }
            }//end for
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }//end catch
    }//main
} // class