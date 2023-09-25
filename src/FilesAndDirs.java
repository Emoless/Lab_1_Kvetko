import java.util.List;

public class FilesAndDirs {
    private final List<String> files;
    private final List<String> dirs;

    public FilesAndDirs(List<String> files, List<String> dirs) {
        this.files = files;
        this.dirs = dirs;
    }

    public List<String> getFiles() {
        return files;
    }

    public List<String> getDirs() {
        return dirs;
    }
}
