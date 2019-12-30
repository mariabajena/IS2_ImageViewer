package imageviewer2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileImageLoader implements ImageLoader {
    private static final String[] ImageExtensions = {".jpg", ".jpeg", ".png"};
    private File root;
    private final File[] files;

    public FileImageLoader(String root) {
        this(new File(root));
    }

    public FileImageLoader(File root) {
        this.root = root;
        this.files = root.listFiles(imageFiles());
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private FilenameFilter imageFiles() {
        return new FilenameFilter() {
            @Override
            public boolean accept(File file, String filename) {
                for (String extension : ImageExtensions) 
                    if (filename.endsWith(extension)) return true;
                return false;
            }
        };
    }

    private Image imageAt(int i) {
        return new Image() {
            @Override
            public String name() {
                return files[i].getName();
            }

            @Override
            public byte[] data() {
                try {
                    return load(files[i]);
                } catch (IOException ex) {
                    return new byte[0];
                }
            }

            @Override
            public Image next() {
                return imageAt((i+1) % files.length);
            }

            @Override
            public Image prev() {
                return imageAt((i-1+files.length) % files.length);
            }

        };
    }
    
    private byte[] load(File file) throws IOException {
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (InputStream is = inputStreamOf(file); OutputStream os = outputStream(bos)) {
            while (true) {
                int length = is.read(buffer);
                os.write(buffer, 0, length);
                if (length == buffer.length) continue;
                os.flush();
                return bos.toByteArray();
            }            
        }
    }

    private InputStream inputStreamOf(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    private OutputStream outputStream(ByteArrayOutputStream bos) {
        return new BufferedOutputStream(bos);
    }
}
