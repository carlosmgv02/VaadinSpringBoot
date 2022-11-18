package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private SecurityService securityService;

    public MainLayout(SecurityService securityService){
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    /**
     * Crea el encabezado de la aplicación
     */
    private void createHeader() {
        H1 logo=new H1("Login page");
        logo.addClassNames("text-l","m-m");
        Button logOut = new Button("log out", e -> securityService.logOut());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,logOut);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0","px-m");
        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listView = new RouterLink("List", ListView.class);
        listView.setHighlightCondition(HighlightConditions.sameLocation()); //Para que se resalte el link cuando estemos en esa vista
        addToDrawer(new VerticalLayout(
                listView,
                new RouterLink("Dashboard", DashboardView.class)
        ));

    }
}
