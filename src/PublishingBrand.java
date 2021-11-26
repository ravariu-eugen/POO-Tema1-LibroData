import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class PublishingBrand implements IPublishingArtifact{
    private final int ID;
    private final String name;
    private final ArrayList<Book> books = new ArrayList<>();
    public PublishingBrand(int ID, String name) {
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
     * @return un Map de forma (publishingBrandID, publishingBrand)
     */
    public static Map<Integer, PublishingBrand> getPublishingBrandMap(String path) {
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("Id###Name")) {
                Map<Integer, PublishingBrand> publishingBrandMapMap = new TreeMap<>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    publishingBrandMapMap.put(Integer.parseInt(token[0]),
                            new PublishingBrand(Integer.parseInt(token[0]), token[1]));
                }
                return publishingBrandMapMap;
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
        return "PublishingBrand{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public String Publish() {
        StringBuffer out = new StringBuffer("<xml>\n");
        out.append ("    <publishingBrand>\n");
        out.append("        <ID>").append(this.ID).append("</ID>\n");
        out.append("        <name>").append(this.name).append("</name>\n");
        out.append ("    </publishingBrand>\n");
        out.append ("    <books>\n");
        for(Book book:this.books) {
            out.append ("        <book>\n");
            out.append("            <title>").append(book.getName()).append("</title>\n");
            out.append("            <subtitle>").append(book.getSubtitle()).append("</subtitle>\n");
            out.append("            <isbn>").append(book.getISBN()).append("</isbn>\n");
            out.append("            <pageCount>").append(book.getPageCount()).append("</pageCount>\n");
            out.append("            <keywords>").append(book.getKeywords()).append("</keywords>\n");
            out.append("            <languageID>").append(book.getLanguageId()).append("</languageID>\n");
            out.append("            <createdOn>").append(book.getCreatedOn().toString()).append("</createdOn>\n");
            out.append("            <authors>").append(book.getAuthors().toString()).append("</authors>\n");
            out.append ("        </book>\n");
        }
        out.append ("    </books>\n");
        out.append ("</xml>\n");
        return out.toString();
    }
}

