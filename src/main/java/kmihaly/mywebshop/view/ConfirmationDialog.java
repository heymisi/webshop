package kmihaly.mywebshop.view;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class ConfirmationDialog extends Dialog {
    private Label title;
    private Label question;
    private Button confirm;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setQuestion(String question) {
        this.question.setText(question);
    }

    public ConfirmationDialog(){
        createHeader();
        createContent();
        createFooter();

    }
    public ConfirmationDialog(Label title, Label question, Button confirm) {
        this();
        this.title = title;
        this.question = question;
        this.confirm = confirm;
    }

    public  void addConfirmationListener(ComponentEventListener listener){
        confirm.addClickListener( listener);
    }


    private void createHeader(){
        this.title = new Label();
        Button close = new Button();
        close.setIcon(VaadinIcon.CLOSE.create());
        close.addClickListener(buttonClickEvent -> close());

        com.vaadin.flow.component.orderedlayout.HorizontalLayout header = new com.vaadin.flow.component.orderedlayout.HorizontalLayout();
        header.add(this.title,close);
        header.setFlexGrow(1,this.title);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle().set("background-color","grey");
        add(header);
    }
    private void createContent() {
        question = new Label();

        VerticalLayout content = new VerticalLayout();
        content.add(question);
        content.setPadding(false);
        content.getStyle().set("background-color", "grey");
        add(content);
    }

    private void createFooter() {
        Button abort = new Button("Abort");
        abort.addClickListener(buttonClickEvent -> close());
        confirm = new Button("Confirm");
        confirm.addClickListener(buttonClickEvent -> close());

        com.vaadin.flow.component.orderedlayout.HorizontalLayout footer = new com.vaadin.flow.component.orderedlayout.HorizontalLayout();
        footer.add(abort, confirm);
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        footer.getStyle().set("background-color", "grey");
        add(footer);
    }
}
