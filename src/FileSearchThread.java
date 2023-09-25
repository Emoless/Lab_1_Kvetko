import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearchThread extends Thread {
    private String directoryPath;
    private List<String> foundFiles;
    private List<String> foundDirs;

    public FileSearchThread(String directoryPath) {
        this.directoryPath = directoryPath;
        this.foundFiles = new ArrayList<>();
        this.foundDirs = new ArrayList<>();
    }

    @Override
    public void run() {
        searchFiles();
    }

    public FilesAndDirs getFound() {
        return new FilesAndDirs(foundFiles, foundDirs);
    }

    private void searchFiles() {
        File f = new File(directoryPath);
        String[] l = f.list();
        if (l == null) {
            return;
        }

        for (String j : l) {
            File tmp = new File(directoryPath + "/" + j);
            if (tmp.isFile()) {
                foundFiles.add(j);
            } else {
                foundDirs.add(directoryPath + "/" + j);
            }
        }
    }
}
