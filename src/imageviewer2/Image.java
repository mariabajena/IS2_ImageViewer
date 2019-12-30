package imageviewer2;

public interface Image {
    public String name();
    public byte[] data();
    public Image next();
    public Image prev();
}
