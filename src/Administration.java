import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Administration {

    private static Map<Integer, Country> countries;
    private static Map<Integer, Language> languages;
    private static Map<Integer, Author> authors;
    private static Map<Integer, Book> books;
    private static Map<Integer, EditorialGroup> editorialGroups;
    private static Map<Integer, PublishingBrand> publishingBrands;
    private static Map<Integer, PublishingRetailer> publishingRetailers;
    // am ales sa stochez datele intr-un map intrucat valorile ID-urilor pot sa nu fie consecutive

    public static ArrayList<Book> getBooksForPublishingID(int publishingRetailerID){
        ArrayList<Book> books = new ArrayList<>();
        PublishingRetailer publishingRetailer = publishingRetailers.get(publishingRetailerID);
        if(publishingRetailer == null)
            return null;
        for(IPublishingArtifact artifact: publishingRetailer.getPublishingArtifacts()){
            if(artifact instanceof Book){
                books.add((Book) artifact);
            }else if(artifact instanceof EditorialGroup){
                books.addAll(((EditorialGroup) artifact).getBooks());
            }else if (artifact instanceof PublishingBrand){
                books.addAll(((PublishingBrand) artifact).getBooks());
            }
        }
        books.sort(Comparator.comparingInt(Book::getID));
        return books;
    }
    public static Language[] getLanguagesForPublishingRetailerID(int publishingRetailerID){
        ArrayList<Language> languages = new ArrayList<>();
        ArrayList<Book> books = getBooksForPublishingID(publishingRetailerID);
        if (books == null)
            return null;
        for(Book book:books) {
            int languageId = book.getLanguageId();
            Language language = Administration.languages.get(languageId);
            if (!languages.contains(language)) {
                languages.add(language);
            }
        }
        languages.sort(Comparator.comparingInt(Language::getID));
        return languages.toArray(new Language[0]);
    }
    public static Country[] getCountriesForBookID(int bookID){

        Book book = books.get(bookID);
        if(book == null)
            return null;
        Set<Country> countries = new HashSet<>();
        for(Map.Entry<Integer, PublishingRetailer> entry: publishingRetailers.entrySet()) {
            PublishingRetailer publishingRetailer = entry.getValue();
            ArrayList<Book> publishingRetailerBooks = getBooksForPublishingID(publishingRetailer.getID());
            if(publishingRetailerBooks.contains(book)){
                countries.addAll(publishingRetailer.getCountries());
            }
        }
        Country[] countries1 = countries.toArray(new Country[0]);
        Arrays.sort(countries1, Comparator.comparingInt(Country::getID));
        return countries1;
    }
    public static ArrayList<Book> getCommonBooksForRetailerIDs(int retailerID1, int retailerID2){

        ArrayList<Book> books1 = getBooksForPublishingID(retailerID1);
        if (books1 == null)
            return null;
        ArrayList<Book> books2 = getBooksForPublishingID(retailerID2);
        if (books2 == null)
            return null;
        ArrayList<Book> commonBooks = new ArrayList<>();
        int iter1=0, iter2=0;
        while (iter1 < books1.size() && iter2< books2.size()){
            int bookID1 = books1.get(iter1).getID();
            int bookID2 = books2.get(iter2).getID();
            if(bookID1 < bookID2)
                iter1++;
            else if(bookID1 > bookID2)
                iter2++;
            else {
                commonBooks.add(books1.get(iter1));
                iter1++;
                iter2++;
            }
        }

        return commonBooks;
    }
    public static ArrayList<Book> getAllBooksForRetailerIDs (int retailerID1, int retailerID2){

        ArrayList<Book> books1 = getBooksForPublishingID(retailerID1);
        if (books1 == null)
            return null;
        ArrayList<Book> books2 = getBooksForPublishingID(retailerID2);
        if (books2 == null)
            return null;
        ArrayList<Book> allBooks = new ArrayList<>();
        int iter1=0, iter2=0;
        while (iter1 < books1.size() && iter2< books2.size()){
            int bookID1 = books1.get(iter1).getID();
            int bookID2 = books2.get(iter2).getID();
            if(bookID1 < bookID2) {
                allBooks.add(books1.get(iter1));
                iter1++;
            } else if(bookID1 > bookID2) {
                allBooks.add(books2.get(iter2));
                iter2++;
            } else {
                allBooks.add(books1.get(iter1));
                iter1++;
                iter2++;
            }
        }
        while (iter1 < books1.size()){
            allBooks.add(books1.get(iter1));
            iter1++;
        }
        while (iter2 < books1.size()){
            allBooks.add(books2.get(iter1));
            iter1++;
        }
        return allBooks;
    }

    public static void connectBooksToAuthors (String path){
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("BookId###AuthorId")) {
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    int bookID = Integer.parseInt(token[0]);
                    int authorID = Integer.parseInt(token[1]);
                    Book book = books.get(bookID);
                    Author author = authors.get(authorID);
                    book.addAuthor(author);
                    author.addBook(book);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connectEditorialGroupsToAuthors (String path){
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("GroupId###AuthorId")) {
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    int editorialGroupID = Integer.parseInt(token[0]);
                    int authorID = Integer.parseInt(token[1]);
                    EditorialGroup editorialGroup = editorialGroups.get(editorialGroupID);
                    Author author = authors.get(authorID);
                    editorialGroup.addAuthor(author);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connectPublishingBrandsToAuthors (String path){
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("PublisherId###AuthorId")) {
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    int publishingBrandID = Integer.parseInt(token[0]);
                    int authorID = Integer.parseInt(token[1]);
                    PublishingBrand publishingBrand = publishingBrands.get(publishingBrandID);
                    Author author = authors.get(authorID);
                    publishingBrand.addAuthor(author);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connectPublishingRetailersToBooks (String path){
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("RetailerId###BookId")) {
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    int publishingRetailerID = Integer.parseInt(token[0]);
                    int bookID = Integer.parseInt(token[1]);
                    PublishingRetailer publishingRetailer = publishingRetailers.get(publishingRetailerID);
                    Book book = books.get(bookID);
                    publishingRetailer.addPublishingArtifact(book);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connectPublishingRetailersToEditorialGroups(String path){
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("RetailerId###GroupId")) {
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    int publishingRetailerID = Integer.parseInt(token[0]);
                    int editorialGroupID = Integer.parseInt(token[1]);
                    PublishingRetailer publishingRetailer = publishingRetailers.get(publishingRetailerID);
                    EditorialGroup editorialGroup = editorialGroups.get(editorialGroupID);
                    publishingRetailer.addPublishingArtifact(editorialGroup);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connectPublishingRetailersToPublishingBrands(String path){
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("RetailerId###PublisherId")) {
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    int publishingRetailerID = Integer.parseInt(token[0]);
                    int publishingBrandID = Integer.parseInt(token[1]);
                    PublishingRetailer publishingRetailer = publishingRetailers.get(publishingRetailerID);
                    PublishingBrand publishingBrand = publishingBrands.get(publishingBrandID);
                    publishingRetailer.addPublishingArtifact(publishingBrand);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void connectPublishingRetailersToCountries(String path){
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("RetailerId###CountryId")) {
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    int publishingRetailerID = Integer.parseInt(token[0]);
                    int countryID = Integer.parseInt(token[1]);
                    PublishingRetailer publishingRetailer = publishingRetailers.get(publishingRetailerID);
                    publishingRetailer.addCountry(countries.get(countryID));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void initAdministration(){
        String init = "./init/";
        countries = Country.getCountryArray(init + "countries.in");
        languages = Language.getLanguageMap(init + "languages.in");
        authors = Author.getAuthorMap(init + "authors.in");
        books = Book.getBookMap(init + "books.in");
        editorialGroups = EditorialGroup.getEditorialGroupMap(init + "editorial-groups.in");
        publishingBrands = PublishingBrand.getPublishingBrandMap(init + "publishing-brands.in");
        publishingRetailers = PublishingRetailer.getPublishingRetailerMap(init + "publishing-retailers.in");
        connectBooksToAuthors(init + "books-authors.in");
        connectEditorialGroupsToAuthors(init + "editorial-groups-books.in");
        connectPublishingBrandsToAuthors(init + "publishing-brands-books.in");
        connectPublishingRetailersToBooks(init + "publishing-retailers-books.in");
        connectPublishingRetailersToEditorialGroups(init + "publishing-retailers-editorial-groups.in");
        connectPublishingRetailersToPublishingBrands(init + "publishing-retailers-publishing-brands.in");
        connectPublishingRetailersToCountries(init + "publishing-retailers-countries.in");
    }

    public static void main(String[] args) {
        initAdministration();
        /*
        for(Map.Entry<Integer, PublishingRetailer> x: publishingRetailers.entrySet()){
            System.out.println(x.getValue().toString());
            ArrayList<Book> books = getBooksForPublishingID(x.getValue().getID());
            System.out.println(books.size());
            //for(Book book:books){
            //    System.out.println(book.getID() + " " + book.getName());
            //}
        }

        ArrayList<Book> books = getBooksForPublishingID(16);
        System.out.println("<<<<1>>>>");
        if (books != null) {
            for(Book book:books){
                System.out.println(book.getID() + " " + book.getName());
            }
        }
        books = getBooksForPublishingID(4);
        System.out.println("<<<<2>>>>");
        for(Book book:books){
            System.out.println(book.getID() + " " + book.getName());
        }
        ArrayList<Book> commonBooks = getCommonBooksForRetailerIDs(3, 4);
        System.out.println("<<<<1&2>>>>");
        for(Book book:commonBooks){
            System.out.println(book.getID() + " " + book.getName());
        }for (int i = 0; i < 50; i++) {
            System.out.println(i);
            Language[] temp = getLanguagesForPublishingRetailerID(i);
            if (temp != null) {
                for (Language x : temp) {
                    System.out.println(x.toString());
                }
            }
        }*/
        for(int i = 0; i < 1000; i++){
            Country[] countries1 = getCountriesForBookID(i);
            if(countries1 != null){
                System.out.println(i);
                for(Country x:countries1){
                    System.out.println(x.toString());
                }
            }
        }/*
        for(Map.Entry<Integer, PublishingRetailer> x: publishingRetailers.entrySet()){
            for(Map.Entry<Integer, PublishingRetailer> y: publishingRetailers.entrySet()){
                if(!x.equals(y)){
                    System.out.println("| " + x.getValue().getName() + " & " + y.getValue().getName() + " |");
                    books = getCommonBooksForRetailerIDs(x.getValue().getID(), y.getValue().getID());
                    for(Book book:books){

                        System.out.println(book.getID() + " " + book.getName());
                    }
                }
            }
        }
        */

    }
}
