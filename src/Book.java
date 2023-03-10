import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Book implements IPublishingArtifact{
    private int ID;
    private String name;
    private String subtitle;
    private String ISBN;
    private int pageCount;
    private String keywords;
    private int languageId;
    private Calendar createdOn;
    private final ArrayList<Author> authors = new ArrayList<>();

    public Book() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setID(String ID) {
        this.ID = Integer.parseInt(ID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public void setPageCount(String pageCount) {
        this.pageCount = Integer.parseInt(pageCount);
    }
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
    public void setLanguageId(String languageId) {
        this.languageId = Integer.parseInt(languageId);
    }
    public Calendar getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Calendar createdOn) {
        this.createdOn = createdOn;
    }
    public void setCreatedOn(String createdOn) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy hh:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(createdOn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.createdOn = new GregorianCalendar(){
            @Override
            public String toString() {

                Date date = this.getTime();
                SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                return ft.format(date);
            }
        };
        this.createdOn.setTime(d);
    }

    public void addAuthor(Author author){
        authors.add(author);
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }
    /**
     *  Citeste datele dintr-un fiser si intoarce un map ce contine
     *  datele din acesta
     * @param path calea la un fisier de format
     * "ID###Name###Subtitle###ISBN###PageCount###Keywords###LanguageId###CreatedOn"
     * @return un Map de forma (bookID, book)
     */
    public static Map<Integer, Book> getBookMap(String path) {

        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("ID###Name###Subtitle###ISBN###PageCount###Keywords###LanguageId###CreatedOn")) {
                // verificare format
                Map<Integer, Book> books = new TreeMap<Integer, Book>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    Book book = new Book();
                    book.setID(token[0]);
                    book.setName(token[1]);
                    book.setSubtitle(token[2]);
                    book.setISBN(token[3]);
                    book.setPageCount(token[4]);
                    book.setKeywords(token[5]);
                    book.setLanguageId(token[6]);
                    book.setCreatedOn(token[7]);
                    books.put(book.getID(), book);
                }
                return books;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ID == book.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                '}';
    }




    @Override
    public String Publish()  {
        StringBuffer out = new StringBuffer("<xml>\n");
        out.append("    <title>").      append(this.name).      append("</title>\n");
        out.append("    <subtitle>").   append(this.subtitle).  append("</subtitle>\n");
        out.append("    <isbn>").       append(this.ISBN).      append("</isbn>\n");
        out.append("    <pageCount>").  append(this.pageCount). append("</pageCount>\n");
        out.append("    <keywords>").   append(this.keywords).  append("</keywords>\n");
        out.append("    <languageID>"). append(this.languageId).append("</languageID>\n");
        out.append("    <createdOn>").  append(this.createdOn.toString()).append("</createdOn>\n");
        out.append("    <authors>").    append(this.authors).   append("</authors>\n");
        out.append("</xml>");
        return out.toString();
    }


}
