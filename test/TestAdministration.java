import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

public class TestAdministration {
    static { Administration.initAdministration();}
    @Test
    public void testGetBooksForPublishingID(){

    }
    @Test
    public void testGetLanguagesForPublishingRetailerID(){

    }
    @Test
    public void testGetCountriesForBookID(){

        Country[] countries = Administration.getCountriesForBookID(204);
        Arrays.sort(countries, Comparator.comparingInt(Country::getID));
        Country[] countriesTest = new Country[1];
        countriesTest[0] = new Country(246,"YT");
        Assertions.assertTrue(countries.length == countriesTest.length);
        //Assertions.assertTrue(match);

    }
    @Test
    public void testGetCountriesForBookIDNull(){

        Country[] countries = Administration.getCountriesForBookID(200);
        Assertions.assertTrue(countries == null);

    }
    @Test
    public void testGetCommonBooksForRetailerIDs(){

    }
    @Test
    public void testGetAllBooksForRetailerIDs(){

    }
}
