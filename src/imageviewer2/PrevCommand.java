package imageviewer2;

public class PrevCommand implements Command {
    
    private final ImageDisplay display;

    public PrevCommand(ImageDisplay display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.show(display.getCurrentImage().prev());
    }
    
}
