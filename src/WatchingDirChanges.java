import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.zip.ZipOutputStream;

public class WatchingDirChanges {
    public static void main (String[] args) {
        Path path = Paths.get("temp");

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            WatchKey key = path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            startListening(watchService);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("done");
    }

    private static void startListening (WatchService watchService)
            throws InterruptedException, IOException {
        while (true) {
            WatchKey queuedKey = watchService.take();
            for (WatchEvent<?> watchEvent : queuedKey.pollEvents()) {
                System.out.printf("kind=%s, count=%d, context=%s Context type=%s%n ",
                        watchEvent.kind(),
                        watchEvent.count(), watchEvent.context(),
                        ((Path) watchEvent.context()).getClass());

                String sourceFile = "temp\\";
                FileOutputStream fos = new FileOutputStream("securezip\\temp.zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                File fileToZip = new File(sourceFile);

                ZipDirectory.zipFile(fileToZip, fileToZip.getName(), zipOut);
                zipOut.close();
                fos.close();


                if (!queuedKey.reset()) {
                    break;
                }

            }
        }
    }
}
