import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class MyFileEncoder {
    public static void main(String[] args) throws IOException {
        File myzip = new File("D:\\temp\\asdf.io");
        byte[] data = FileUtils.readFileToByteArray(myzip);

        byte[] encodedString = Base64.getEncoder().encode(data);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);

        File myunzip = new File("securezip\\sczip.io");
        FileUtils.writeByteArrayToFile(myunzip,decodedBytes);


//
//        String actual = Files.readString(fileName);
//        System.out.println(actual);
    }
    private static String readAllBytes(String filePath)
    {
        String content = "";

        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return content;
    }
}
