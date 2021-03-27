package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class ReserveModulesPane extends GridPane {
	
	private Accordion root;
	private ListView<Module> unselectedTerm1, unselectedTerm2, reservedTerm1, reservedTerm2;
	private ObservableList<Module> uT1M, uT2M, rT1, rT2;
	private Button btnAddT1, btnRemoveT1, btnConfirmT1, btnAddT2, btnRemoveT2, btnConfirmT2;

	public ReserveModulesPane() {
		this.setVgap(15);
		this.setHgap(20);
		this.setPadding(new Insets(20));
		this.setAlignment(Pos.TOP_CENTER);
		
		VBox t1VContainer = new VBox();
		t1VContainer.setSpacing(20);
		HBox t1HContainer = new HBox();
		t1HContainer.setSpacing(20);
		t1HContainer.setPadding(new Insets(50, 0, 0, 0));
		
		VBox lC1 = new VBox();
		uT1M = FXCollections.observableArrayList();
		unselectedTerm1 = new ListView<>(uT1M);
		unselectedTerm1.setMinSize(400, 400);
		unselectedTerm1.prefWidthProperty().bind(this.widthProperty());
		unselectedTerm1.prefHeightProperty().bind(this.heightProperty());
		unselectedTerm1.setPadding(new Insets(20));
		lC1.setPadding(new Insets(0, 0, 0, 50));
		Label uT1L = new Label("Unselected Term 1 modules");
		lC1.getChildren().addAll(uT1L, unselectedTerm1);
		
		VBox rC1 = new VBox();
		rT1 = FXCollections.observableArrayList();
		reservedTerm1 = new ListView<>(rT1);
		reservedTerm1.setMinSize(400, 400);
		reservedTerm1.prefWidthProperty().bind(this.widthProperty());
		reservedTerm1.prefHeightProperty().bind(this.heightProperty());
		reservedTerm1.setPadding(new Insets(20));
		rC1.setPadding(new Insets(0, 50, 0, 0));
		Label rT1L = new Label("Reserved Term 1 modules");
		rC1.getChildren().addAll(rT1L, reservedTerm1);
		
		t1HContainer.getChildren().addAll(lC1, rC1);
		
		HBox t1Controls = new HBox();
		t1Controls.setSpacing(20);
		t1Controls.setPadding(new Insets(20, 0, 50, 0));
		t1Controls.setAlignment(Pos.CENTER);
		Label t1L = new Label("Reserve 30 credits worth of term 1 modules");
		t1L.setAlignment(Pos.CENTER_LEFT);
		
		btnAddT1 = new Button("Add");
		btnAddT1.setPrefSize(60, 20);
		btnRemoveT1 = new Button("Remove");
		btnRemoveT1.setPrefSize(60, 20);
		btnConfirmT1 = new Button("Confirm");
		btnConfirmT1.setPrefSize(60, 20);
		t1Controls.getChildren().addAll(t1L, btnAddT1, btnRemoveT1, btnConfirmT1);
		
		t1VContainer.getChildren().addAll(t1HContainer, t1Controls);
		
		
		VBox t2VContainer = new VBox();
		t2VContainer.setSpacing(20);
		HBox t2HContainer = new HBox();
		t2HContainer.setSpacing(20);
		t2HContainer.setPadding(new Insets(50, 0, 0, 0));
		
		VBox lC2 = new VBox();
		uT2M = FXCollections.observableArrayList();
		unselectedTerm2 = new ListView<>(uT2M);
		unselectedTerm2.setMinSize(400, 400);
		unselectedTerm2.prefWidthProperty().bind(this.widthProperty());
		unselectedTerm2.prefHeightProperty().bind(this.heightProperty());
		unselectedTerm2.setPadding(new Insets(20));
		lC2.setPadding(new Insets(0, 0, 0, 50));
		Label uT2L = new Label("Unselected Term 2 modules");
		lC2.getChildren().addAll(uT2L, unselectedTerm2);
		
		VBox rC2 = new VBox();
		rT2 = FXCollections.observableArrayList();
		reservedTerm2 = new ListView<>(rT2);
		reservedTerm2.setMinSize(400, 400);
		reservedTerm2.prefWidthProperty().bind(this.widthProperty());
		reservedTerm2.prefHeightProperty().bind(this.heightProperty());
		reservedTerm2.setPadding(new Insets(20));
		rC2.setPadding(new Insets(0, 50, 0, 0));
		Label rT2L = new Label("Reserved Term 2 modules");
		rC2.getChildren().addAll(rT2L, reservedTerm2);
		
		t2HContainer.getChildren().addAll(lC2, rC2);
		
		HBox t2Controls = new HBox();
		t2Controls.setSpacing(20);
		t2Controls.setPadding(new Insets(20, 0, 50, 0));
		t2Controls.setAlignment(Pos.CENTER);
		Label t2L = new Label("Reserve 30 credits worth of term 2 modules");
		t2L.setAlignment(Pos.CENTER_LEFT);
		
		btnAddT2 = new Button("Add");
		btnAddT2.setPrefSize(60, 20);
		btnRemoveT2 = new Button("Remove");
		btnRemoveT2.setPrefSize(60, 20);
		btnConfirmT2 = new Button("Confirm");
		btnConfirmT2.setPrefSize(60, 20);
		t2Controls.getChildren().addAll(t2L, btnAddT2, btnRemoveT2, btnConfirmT2);
		
		t2VContainer.getChildren().addAll(t2HContainer, t2Controls);
		
		root = new Accordion();
		root.getPanes().addAll(new TitledPane("Term 1 Modules", t1VContainer), new TitledPane("Term 2 Modules", t2VContainer));
		root.setExpandedPane(root.getPanes().get(0));
		
		this.add(root, 0, 0);
	}
	
	public void changePane(int x) {
		root.setExpandedPane(root.getPanes().get(x));
	}
	
	// Populating list methods
	public void populateUnselectedTerm1Modules(Module modules) {
		unselectedTerm1.getItems().addAll(modules);
	}
	
	public void populateUnselectedTerm2Modules(Module modules) {
		unselectedTerm2.getItems().addAll(modules);
	}
	
	
	public ListView<Module> getUnselectedTerm1(){
		return unselectedTerm1;
	}
	
	public ListView<Module> getUnselectedTerm2(){
		return unselectedTerm2;
	}
	
	public ListView<Module> getReservedTerm1(){
		return reservedTerm1;
	}
	
	public ListView<Module> getReservedTerm2(){
		return reservedTerm2;
	}
	
	public void clearList(ListView<Module> m){
		m.getItems().clear();
	}
	
	//Button Handlers
	public void addBtnT1A1Handler(EventHandler<ActionEvent> handler) {
		btnAddT1.setOnAction(handler);
	}
	public void addBtnT2A2Handler(EventHandler<ActionEvent> handler) {
		btnAddT2.setOnAction(handler);
	}
	
	public void removeBtnT1A1Handler(EventHandler<ActionEvent> handler) {
		btnRemoveT1.setOnAction(handler);
	}
	public void removeBtnT2A2Handler(EventHandler<ActionEvent> handler) {
		btnRemoveT2.setOnAction(handler);
	}
	
	public void confirmBtnT1A1Handler(EventHandler<ActionEvent> handler) {
		btnConfirmT1.setOnAction(handler);
	}
	public void confirmBtnT2A2Handler(EventHandler<ActionEvent> handler) {
		btnConfirmT2.setOnAction(handler);
	}
	
}
