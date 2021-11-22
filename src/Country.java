import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Country {
    private int ID;
    private String countryCode;

    public Country(int ID, String countryCode) {
        this.ID = ID;
        this.countryCode = countryCode;
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     *  Citeste datele dintr-un fiser si intoarce un map ce contine
     *  datele din acesta
     * @param path calea la un fisier de format "ID###CountryCode"
     * @return un Map de forma (countryID, country)
     */
    public static Map<Integer, Country> getCountryArray(String path) {
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("ID###CountryCode")) {
                Map<Integer, Country> countries = new TreeMap<>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    countries.put(Integer.parseInt(token[0]), new Country(Integer.parseInt(token[0]), token[1]));
                }
                return countries;
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
        return "Country{" +
                "ID=" + ID +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
