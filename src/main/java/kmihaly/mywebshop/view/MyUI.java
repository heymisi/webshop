package kmihaly.mywebshop.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@SpringViewDisplay
public class MyUI extends UI implements ViewDisplay {

    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest request) {

        HorizontalLayout navigationBar = new HorizontalLayout();

        navigationBar.addComponent(createNavigationButton("Main Page", mainPage.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Shop", Shop.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Sign Up", SignUp.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Bag", Bag.VIEW_NAME));
        //  navigationBar.setIcon(new ClassResource("webshop-header.jpg"));
        //  navigationBar.addComponent(new Image(null,new ClassResource("webshop-header.jpg")));
        navigationBar.setMargin(false);
        navigationBar.setSpacing(false);
        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        HorizontalLayout footer = new HorizontalLayout();
        footer.addComponent(new Label("footer"));
        footer.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        VerticalLayout mainLayout = new VerticalLayout(navigationBar, springViewDisplay,footer);
        setContent(mainLayout);

    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.MENU_ITEM);
        button.setWidth("200");
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }


    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }

}
