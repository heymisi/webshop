package kmihaly.mywebshop.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static com.vaadin.server.VaadinService.getCurrent;

public class UploadFile extends Upload{
    private Image image = new Image("");
    private String basePath = getCurrent().getBaseDirectory().getAbsolutePath();
    private String fileName = "";

    public UploadFile(String caption, String text) {


        class ImageReceiver implements Upload.Receiver, Upload.SucceededListener {

            public File file;


            public OutputStream receiveUpload(String filename,
                                              String mimeType) {

                FileOutputStream fos = null;
                try {
                    fileName = filename;
                    file = new File(basePath + "/img/" + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file<br/>",
                            e.getMessage(),
                            Notification.Type.ERROR_MESSAGE)
                            .show(MyUI.getCurrent().getPage());
                    return null;
                }
                return fos;
            }

            @Override
            public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
                image.setSource(new FileResource(file));
            }
        }
        ImageReceiver receiver = new ImageReceiver();

        setReceiver(receiver);
        setCaption(caption);
        setButtonCaption(text);
        setIcon(VaadinIcons.UPLOAD);
        setWidth("500");
        addStyleNames("mystyle");
        addSucceededListener(receiver);
    }

    public String getFileName(){
        return this.fileName;
    }
}
