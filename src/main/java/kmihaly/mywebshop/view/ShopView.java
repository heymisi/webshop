package kmihaly.mywebshop.view;

import com.vaadin.data.HasValue;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import kmihaly.mywebshop.domain.model.item.GenreType;
import kmihaly.mywebshop.domain.model.item.Item;
import kmihaly.mywebshop.service.DAOItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = ShopView.VIEW_NAME)
public class ShopView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "shop";

    @Autowired
    private DAOItemService daoItemService;

    private Grid<Item> itemsForMen = new Grid<>();
    private Grid<Item> itemsForWomen = new Grid<>();


    @PostConstruct
    void init() {

        setUpItems(itemsForMen,GenreType.MEN);
        setUpItems(itemsForWomen,GenreType.WOMEN);

        HorizontalLayout layoutMen = filter(itemsForMen,GenreType.MEN);
        HorizontalLayout layoutWomen = filter(itemsForWomen,GenreType.WOMEN);


        TabSheet tabs = new TabSheet();
        addComponent(tabs);
        Tab menTab = new Tab("MAN");
        Tab womenTab = new Tab("WOMEN");
        VerticalLayout tab1 = new VerticalLayout();
        VerticalLayout tab2 = new VerticalLayout();

        tabs.addTab(tab1, "MEN");
        tabs.addTab(tab2, "WOMEN");
        tab1.addComponents(layoutMen);
        tabs.addSelectedTabChangeListener((TabSheet.SelectedTabChangeListener) selectedTabChangeEvent -> {
            TabSheet tabSheet = selectedTabChangeEvent.getTabSheet();
            Layout tab = (Layout) tabSheet.getSelectedTab();
            String caption = tabSheet.getTab(tab).getCaption();
            tab.removeAllComponents();

            if (caption.equals("MEN")) {
                tab.addComponent(layoutMen);
            } else if (caption.equals("WOMEN")) {
                tab.addComponent(layoutWomen);
            }
        });
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private HorizontalLayout filter(Grid<Item> items, GenreType genreType){
        VerticalLayout sideBar = new VerticalLayout();
        Label label = new Label("Filter");
        ComboBox<String> brandFilter = new ComboBox<>("Choose Brand");
        brandFilter.setItems("nike","adidas","converse");

        brandFilter.addValueChangeListener(event -> {
            if(event.getValue().equals("nike")){
                items.setItems(daoItemService.searchByGenreAndBrand(genreType, "nike"));
            }else if(event.getValue().equals("adidas")){
                items.setItems(daoItemService.searchByGenreAndBrand(genreType, "adidas"));
            }else{
                items.setItems(daoItemService.searchByGenreAndBrand(genreType, "converse"));
            }
        });
        Label price = new Label();
        Slider slider = new Slider("price");
        slider.setMax(300000.0);
        slider.setResolution(0);
        slider.setWidth("150");
        slider.addValueChangeListener((HasValue.ValueChangeEvent<Double> event) -> {
            Double val = event.getValue();
            price.setValue(Math.round(val) + " price");
        });
        sideBar.addComponents(label,brandFilter,slider,price);

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.addComponent(sideBar);
        mainLayout.addComponent(items);
        mainLayout.setExpandRatio(sideBar, 1);
        mainLayout.setExpandRatio(items, 4);
        mainLayout.setSizeFull();

        return mainLayout;
    }


    public void setUpItems(Grid<Item> items,GenreType genreType){

        items.setItems(daoItemService.searchByGenre(genreType));
        items.addColumn(Item::getName).setCaption("név");
        items.addColumn(Item::getDescription).setCaption("leírás");
        items.addColumn(Item::getBrand).setCaption("márka");
        items.addColumn(Item::getPrice).setCaption("ár");
        items.addColumn(Item::getRate).setCaption("értékelés");
        items.setSizeFull();
    }
}
