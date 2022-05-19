import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress player1 = new GameProgress(100, 2, 5, 1500);
        GameProgress player2 = new GameProgress(90, 3, 7, 2500);
        GameProgress player3 = new GameProgress(150, 5, 15, 8000);

        saveGame("C://Users//stix_//Desktop//Games//savegames//save1.dat", player1);
        saveGame("C://Users//stix_//Desktop//Games//savegames//save2.dat", player2);
        saveGame("C://Users//stix_//Desktop//Games//savegames//save3.dat", player3);

        File file = new File("C://Users//stix_//Desktop//Games//savegames");

        zipFiles("C://Users//stix_//Desktop//Games//savegames//save.zip", file);

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, File file) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path, true));
             FileInputStream fis = new FileInputStream(path)) {
            for (File item : Objects.requireNonNull(file.listFiles())) {
                if (item.getAbsolutePath().endsWith(".dat")) {
                    ZipEntry entry = new ZipEntry(item.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    item.delete();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
