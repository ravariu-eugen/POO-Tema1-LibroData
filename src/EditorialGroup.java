import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class EditorialGroup implements IPublishingArtifact{

    private final int ID;
    private final String name;
    private final ArrayList<Book> books = new ArrayList<>();

    public EditorialGroup(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book){
        books.add(book);
    }
    public void addAuthor(Author author){
        books.addAll(author.getBooks());
    }

    /**
     *  Citeste datele dintr-un fiser si intoarce un map ce contine
     *  datele din acesta
     * @param path calea la un fisier de format "Id###Name"
     * @return un Map de forma (editorialGroupID, editorialGroup)
     */

    public static Map<Integer, EditorialGroup> getEditorialGroupMap(String path) {
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("Id###Name")) {
                // verificare format
                Map<Integer, EditorialGroup> editorialGroupMap = new TreeMap<>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    editorialGroupMap.put(Integer.parseInt(token[0]),
                            new EditorialGroup(Integer.parseInt(token[0]), token[1]));
                }
                return editorialGroupMap;
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
        return "EditorialGroup{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public String Publish() {
        String output = "<xml>\n";
        output += ("    <editorialGroup>\n");
        output += ("        <ID>" + this.ID + "</ID>\n");
        output += ("        <name>" + this.name + "</name>\n");
        output += ("    </editorialGroup>\n");
        output += ("    <books>\n");
        for(Book book:this.books) {
            output += "        <book>\n";
            output += ("            <title>" + book.getName() + "</title>\n");
            output += ("            <subtitle>" + book.getSubtitle() + "</subtitle>\n");
            output += ("            <isbn>" + book.getISBN() + "</isbn>\n");
            output += ("            <pageCount>" + book.getPageCount() + "</pageCount>\n");
            output += ("            <keywords>" + book.getKeywords() + "</keywords>\n");
            output += ("            <languageID>" + book.getLanguageId() + "</languageID>\n");
            output += ("            <createdOn>" + book.getCreatedOn().toString() + "</createdOn>\n");
            output += ("            <authors>" + book.getAuthors().toString() + "</authors>\n");
            output += ("        </book>\n");
        }
        output += ("    </books>\n");
        output += ("</xml>\n");
        return output;
    }
}
