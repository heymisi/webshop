package kmihaly.mywebshop.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringView(name = AboutUsView.VIEW_NAME)
public class AboutUsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "aboutUs";

    private String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    @PostConstruct
    void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();
        Label mainTitle = new Label("Who we are.");
        mainTitle.addStyleNames(ValoTheme.LABEL_H1, ValoTheme.LABEL_BOLD);
        Label text1 = new Label("We believe in a world where you have total freedom to be you, without judgement. To\n" +
                "experiment. To express yourself. To be brave and grab life as the extraordinary\n" +
                "adventure it is. So we make sure everyone has an equal chance to discover all the\n" +
                "amazing things they’re capable of – no matter who they are, where they’re from or\n" +
                "what looks they like to boss. We exist to give you the confidence to be whoever you\n" +
                "want to be.", ContentMode.PREFORMATTED);
        text1.setStyleName(ValoTheme.LABEL_H3);
        Image image1 = new Image("", new FileResource(new File(basePath + "/img/AboutUsImage1.png")));

        Label titleForText2 = new Label("Choice for all");
        titleForText2.addStyleNames(ValoTheme.LABEL_H2, ValoTheme.LABEL_BOLD);
        Label text2 = new Label("Our audience (AKA you) is wonderfully unique. And we do everything we can to help\n" +
                "you find your fit, offering our ASOS Brands in more than 30 sizes – and we're\n" +
                "committed to providing all sizes at the same price – so you can be confident we’ve\n" +
                "got the perfect thing for you. We’re also proud to partner with GLAAD, one of the \n" +
                "biggest voices in LGBTQ activism, on a gender-neutral collection to unite in \n" +
                "accelerating acceptance.", ContentMode.PREFORMATTED);
        text2.setStyleName(ValoTheme.LABEL_H3);
        Image image2 = new Image("", new FileResource(new File(basePath + "/img/AboutUsImage2.png")));
        addComponents(mainTitle, text1, image1, titleForText2, text2, image2);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
