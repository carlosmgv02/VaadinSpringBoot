package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Contacts | Vaadin CRM")
@Route(value = "", layout= MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout {
    Grid<Contact> grid = new Grid<Contact>(Contact.class);
    TextField filterText = new TextField();
    ContactForm form;
    private CrmService service;

    public ListView(CrmService service) {
        this.service = service;
        addClassName("list-view"); //Para después poder darle estilo en el archivo css
        setSizeFull();

        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();

    }

    private void closeEditor() {
        form.setContact(null);;
        form.setVisible(false);
        removeClassName("editing");
    }

    /**
     * Configura el formulario
     */
    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2,grid); //Para que el grid ocupe el 2/3 del espacio
        content.setFlexGrow(1,form); //Para que el form ocupe el 1/3 del espacio
        content.addClassName("content");    //Para después poder darle estilo en el archivo css
        content.setSizeFull();  //Para que el contenido ocupe tod0 el espacio disponible

        return content;

    }

    private void configureForm() {
        form=new ContactForm(service.findAllCompanies(),service.findALlStatuses());
        form.setWidth("25em");
        form.addListener(ContactForm.SaveEvent.class,this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class,this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class,this::closeEvent);

    }
    private void closeEvent(ContactForm.CloseEvent evt){
        closeEditor();
        grid.asSingleSelect().clear();

    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateList());

        Button add_contactButton = new Button("Add contact");
        add_contactButton.addClickListener(e->addContact());    //Cuando se haga click en el botón se ejecutará el método addContact

        HorizontalLayout toolBar = new HorizontalLayout(filterText, add_contactButton);
        toolBar.addClassName("toolbar");

        return toolBar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");

        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event->editContact(event.getValue()));


    }

    private void editContact(Contact contact) {
        if(contact==null){
            closeEditor();
        }
        else{
            form.setContact(contact);;
            form.setVisible(true);
            addClassName("editing");
        }

    }
}
