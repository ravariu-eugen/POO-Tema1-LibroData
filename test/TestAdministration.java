import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TestAdministration {
    static { Administration.initAdministration();}
    @Test
    public void testGetBooksForPublishingID1(){

    }
    @Test
    public void testGetBooksForPublishingIDNull(){
        ArrayList<Book> books = Administration.getBooksForPublishingID(15);
        Assertions.assertNull(books);
    }
    @Test
    public void testGetLanguagesForPublishingRetailerID1(){
        Language[] languages = Administration.getLanguagesForPublishingRetailerID(1);
        int[] languageIDs = new int[languages.length];
        for(int i = 0; i < languages.length; i++)
            languageIDs[i] = languages[i].getID();
        int[] languageIDsTest = {1,2,3};
        Assertions.assertArrayEquals(languageIDsTest, languageIDs);
    }
    @Test
    public void testGetLanguagesForPublishingRetailerID2(){
        Language[] languages = Administration.getLanguagesForPublishingRetailerID(26);
        int[] languageIDs = new int[languages.length];
        for(int i = 0; i < languages.length; i++)
            languageIDs[i] = languages[i].getID();
        int[] languageIDsTest = {1,2};
        Assertions.assertArrayEquals(languageIDsTest, languageIDs);
    }
    @Test
    public void testGetLanguagesForPublishingRetailerID3(){
        Language[] languages = Administration.getLanguagesForPublishingRetailerID(28);
        int[] languageIDs = new int[languages.length];
        for(int i = 0; i < languages.length; i++)
            languageIDs[i] = languages[i].getID();
        int[] languageIDsTest = {1,2,3,5};
        Assertions.assertArrayEquals(languageIDsTest, languageIDs);
    }
    @Test
    public void testGetLanguagesForPublishingRetailerID4(){
        Language[] languages = Administration.getLanguagesForPublishingRetailerID(11);
        int[] languageIDs = new int[languages.length];
        for(int i = 0; i < languages.length; i++)
            languageIDs[i] = languages[i].getID();
        int[] languageIDsTest = {1,2,3,5};
        Assertions.assertArrayEquals(languageIDsTest, languageIDs);
    }
    @Test
    public void testGetLanguagesForPublishingRetailerIDNull(){
        Language[] languages = Administration.getLanguagesForPublishingRetailerID(18);
        Assertions.assertNull(languages);
    }
    @Test
    public void testGetCountriesForBookID1(){

        Country[] countries = Administration.getCountriesForBookID(204);
        int[] countryIDs = new int[countries.length];
        for(int i = 0; i < countries.length; i++)
            countryIDs[i] = countries[i].getID();
        int[] countryIdsTest = {246};
        Assertions.assertArrayEquals(countryIdsTest, countryIDs);

    }
    @Test
    public void testGetCountriesForBookID2(){

        Country[] countries = Administration.getCountriesForBookID(719);
        int[] countryIDs = new int[countries.length];
        for(int i = 0; i < countries.length; i++)
            countryIDs[i] = countries[i].getID();
        int[] countryIdsTest = {2, 64};
        Assertions.assertArrayEquals(countryIdsTest, countryIDs);

    }
    @Test
    public void testGetCountriesForBookID3(){

        Country[] countries = Administration.getCountriesForBookID(962);
        int[] countryIDs = new int[countries.length];
        for(int i = 0; i < countries.length; i++)
            countryIDs[i] = countries[i].getID();
        int[] countryIdsTest = {106, 119, 198};
        Assertions.assertArrayEquals(countryIdsTest, countryIDs);

    }
    @Test
    public void testGetCountriesForBookID4(){

        Country[] countries = Administration.getCountriesForBookID(1135);
        int[] countryIDs = new int[countries.length];
        for(int i = 0; i < countries.length; i++)
            countryIDs[i] = countries[i].getID();
        int[] countryIdsTest = {217, 246};
        Assertions.assertArrayEquals(countryIdsTest, countryIDs);

    }
    @Test
    public void testGetCountriesForBookIDNull(){

        Country[] countries = Administration.getCountriesForBookID(200);
        Assertions.assertNull(countries);

    }
    @Test
    public void testGetCommonBooksForRetailerIDs1(){

    }
    @Test
    public void testGetCommonBooksForRetailerIDsNull(){
        ArrayList<Book> books = Administration.getCommonBooksForRetailerIDs(3, 18);
        Assertions.assertNull(books);
    }
    @Test
    public void testGetAllBooksForRetailerIDs1(){

    }
    @Test
    public void testGetAllBooksForRetailerIDsNull(){
        ArrayList<Book> books = Administration.getAllBooksForRetailerIDs(3, 18);
        Assertions.assertNull(books);
    }
}
