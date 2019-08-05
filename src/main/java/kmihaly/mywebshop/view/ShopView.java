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

        itemsForWomen.setItems(daoItemService.searchByGenre(GenreType.WOMEN));
        itemsForWomen.addColumn(Item::getName).setCaption("név");
        itemsForWomen.addColumn(Item::getDescription).setCaption("leírás");
        itemsForWomen.addColumn(Item::getBrand).setCaption("márka");
        itemsForWomen.addColumn(Item::getPrice).setCaption("ár");
        itemsForWomen.addColumn(Item::getRate).setCaption("értékelés");
        itemsForWomen.setSizeFull();
        VerticalLayout sideBar = new VerticalLayout();
        VerticalLayout sideBar2 = new VerticalLayout();

        Label label = new Label("Filter");

        ComboBox<String> brandFilter = new ComboBox<>("Choose Brand");
        brandFilter.setItems("nike","adidas","converse");
        brandFilter.addValueChangeListener(event -> {
            if(event.getValue().equals("nike")){
                itemsForWomen.setItems(daoItemService.searchByGenreAndBrand(GenreType.WOMEN, "nike"));
            }else if(event.getValue().equals("adidas")){
                itemsForWomen.setItems(daoItemService.searchByGenreAndBrand(GenreType.WOMEN, "adidas"));
            }else{
                itemsForWomen.setItems(daoItemService.searchByGenreAndBrand(GenreType.WOMEN, "converse"));
            }

        });

//        NumberField price = new NumberField("price");
        Label price = new Label();
        Slider slider = new Slider("price");
        slider.setMax(300000.0);
        slider.setResolution(0);
        slider.setWidth("150");
        slider.addValueChangeListener((HasValue.ValueChangeEvent<Double> event) -> {
           Double val = event.getValue();
           price.setValue(val + " price");
        });

        sideBar.addComponent(label);
        sideBar.addComponent(brandFilter);
        sideBar.addComponent(slider);
        sideBar.addComponent(price);
        sideBar.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);


        HorizontalLayout layoutWomen = new HorizontalLayout();
        layoutWomen.addComponent(sideBar);
        layoutWomen.addComponent(itemsForWomen);
        layoutWomen.setExpandRatio(sideBar, 1);
        layoutWomen.setExpandRatio(itemsForWomen, 4);
        layoutWomen.setSizeFull();

        itemsForMen.setItems(daoItemService.searchByGenre(GenreType.MEN));
        itemsForMen.addColumn(Item::getName).setCaption("név");
        itemsForMen.addColumn(Item::getDescription).setCaption("leírás");
        itemsForMen.addColumn(Item::getBrand).setCaption("márka");
        itemsForMen.addColumn(Item::getPrice).setCaption("ár");
        itemsForMen.addColumn(Item::getRate).setCaption("értékelés");
        itemsForMen.setSizeFull();

        Label label2 = new Label("Filter");
        ComboBox<String> brandFilter2 = new ComboBox<>("Choose Brand");
        brandFilter2.setItems("nike","adidas","converse");
        brandFilter2.addValueChangeListener(event -> {
           if(event.getValue().equals("nike")){
               itemsForMen.setItems(daoItemService.searchByGenreAndBrand(GenreType.MEN, "nike"));
           }else if(event.getValue().equals("adidas")){
               itemsForMen.setItems(daoItemService.searchByGenreAndBrand(GenreType.MEN, "adidas"));
           }else {
               itemsForMen.setItems(daoItemService.searchByGenreAndBrand(GenreType.MEN, "converse"));
           }
        });
        Label price2 = new Label();
        Slider slider2 = new Slider("price");
        slider2.setMax(300000.0);
        slider2.setResolution(0);
        slider2.setWidth("150");
        slider2.addValueChangeListener((HasValue.ValueChangeEvent<Double> event) -> {
            Double val = event.getValue();
            price.setValue(val + " price");
        });
        sideBar2.addComponent(label2);
        sideBar2.addComponent(brandFilter2);
        sideBar2.addComponent(slider2);
        sideBar2.addComponent(price2);

        HorizontalLayout layoutMen = new HorizontalLayout();
        layoutMen.addComponent(sideBar2);
        layoutMen.addComponent(itemsForMen);
        layoutMen.setExpandRatio(sideBar2, 1);
        layoutMen.setExpandRatio(itemsForMen, 4);
        layoutMen.setSizeFull();


        TabSheet tabs = new TabSheet();
        Tab menTab = new Tab("MAN");
        Tab womenTab = new Tab("WOMEN");
        VerticalLayout tab1 = new VerticalLayout();
        VerticalLayout tab2 = new VerticalLayout();

        tabs.addTab(tab1, "MEN");
        tabs.addTab(tab2, "WOMEN");
        tabs.setSelectedTab(tab2);
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
        addComponent(tabs);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
