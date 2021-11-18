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

        Country[] countries1 = Administration.getCountriesForBookID(204);
        Arrays.sort(countries1, Comparator.comparingInt(Country::getID));
        Country[] countriesTest1 = new Country[1];
        countriesTest1[0] = new Country(246,"YT");
        boolean match1;
        if (Arrays.equals(countries1, countriesTest1)) match1 = true;
        else match1 = false;
        Assertions.assertTrue(match1);

    }
    @Test
    public void testGetCommonBooksForRetailerIDs(){

    }
    @Test
    public void testGetAllBooksForRetailerIDs(){

    }
}
