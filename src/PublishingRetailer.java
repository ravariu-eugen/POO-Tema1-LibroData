import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class PublishingRetailer {
    private final int ID;
    private final String name;
    private final ArrayList<IPublishingArtifact> publishingArtifacts = new ArrayList<>();
    private final ArrayList<Country> countries = new ArrayList<>();

    public PublishingRetailer(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<IPublishingArtifact> getPublishingArtifacts() {
        return this.publishingArtifacts;
    }

    public ArrayList<Country> getCountries() {
        return this.countries;
    }


    public void addPublishingArtifact(IPublishingArtifact artifact){
        this.publishingArtifacts.add(artifact);
    }
    public void addCountry(Country country){
        this.countries.add(country);
    }

    public static Map<Integer, PublishingRetailer> getPublishingRetailerMap(String path) {
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("Id###Name")) {
                Map<Integer, PublishingRetailer> publishingRetailerMap = new TreeMap<>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    publishingRetailerMap.put(Integer.parseInt(token[0]),
                            new PublishingRetailer(Integer.parseInt(token[0]), token[1]));
                }
                return publishingRetailerMap;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "PublishingRetailer{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
