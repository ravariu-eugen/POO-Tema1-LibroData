import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

    public static Country[] getCountryArray(String path) {
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("ID###CountryCode")) {
                ArrayList<Country> countries = new ArrayList<Country>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    countries.add(new Country(Integer.parseInt(token[0]), token[1]));
                }
                return countries.toArray(new Country[0]);
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
