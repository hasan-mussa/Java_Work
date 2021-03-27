package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import model.Module;

public class SelectModulesPane extends GridPane{

	private ListView<Module> unselectedT1Modules, unselectedT2Modules;
	private ObservableList<Module> uT1M, uT2M;
	private Button btnAddT1, btnRemoveT1, btnAddT2, btnRemoveT2, btnReset, btnSubmit;
	
	private ListView<Module> selectedYearLongModules;
	private ObservableList<Module> sYLM;
	
	private ListView<Module> selectedT1Modules, selectedT2Modules;
	private ObservableList<Module> sT1M, sT2M;
	
	private Label T1Creditsnumb;
	private Label T2Creditsnumb;
	
	public SelectModulesPane() {
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(20));

		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHalignment(HPos.LEFT);
		
		this.getColumnConstraints().addAll(column0);
		
		
		VBox uT1Box = new VBox();
		uT1Box.prefWidthProperty().bind(this.widthProperty());
		uT1Box.prefHeightProperty().bind(this.heightProperty());
		uT1M = FXCollections.observableArrayList();
		unselectedT1Modules = new ListView<>(uT1M);
		unselectedT1Modules.setMinSize(400, 150);
		Label lblt = new Label("Unselected Term 1 modules");
		lblt.setPadding(new Insets(20,20,15,0));
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setPadding(new Insets(20));
		hb.setAlignment(Pos.CENTER);
		Label lblterm1 = new Label("Term 1");
		btnAddT1 = new Button("Add");
		btnAddT1.setPrefSize(60, 20);
		btnAddT1.setAlignment(Pos.CENTER);
		btnRemoveT1 = new Button("Remove");
		btnRemoveT1.setPrefSize(60, 20);
		btnRemoveT1.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(lblterm1, btnAddT1, btnRemoveT1);
		uT1Box.getChildren().addAll(lblt, unselectedT1Modules, hb);
		this.add(uT1Box, 0, 0);

		
		VBox uT2Box = new VBox();
		uT2M = FXCollections.observableArrayList();
		unselectedT2Modules = new ListView<>(uT2M);
		unselectedT2Modules.setMinSize(400, 150);
		unselectedT2Modules.prefWidthProperty().bind(this.widthProperty());
		unselectedT2Modules.prefHeightProperty().bind(this.heightProperty());
		Label lblt2 = new Label("Unselected Term 2 modules");
		lblt2.setPadding(new Insets(20,20,15,0));
		HBox hb2 = new HBox();
		hb2.setSpacing(10);
		hb2.setPadding(new Insets(20));
		hb2.setAlignment(Pos.CENTER);
		Label lblterm2 = new Label("Term 2");
		btnAddT2 = new Button("Add");
		btnAddT2.setPrefSize(60, 20);
		btnAddT2.setAlignment(Pos.CENTER);
		btnRemoveT2 = new Button("Remove");
		btnRemoveT2.setPrefSize(60, 20);
		btnRemoveT2.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(lblterm2, btnAddT2, btnRemoveT2);
		uT2Box.getChildren().addAll(lblt2, unselectedT2Modules, hb2);
		this.add(uT2Box, 0, 1);
		
		VBox vST1Box = new VBox();
		sT1M = FXCollections.observableArrayList();
		selectedT1Modules = new ListView<>(sT1M);
		selectedT1Modules.setMinSize(400, 150);
		selectedT1Modules.prefWidthProperty().bind(this.widthProperty());
		selectedT1Modules.prefHeightProperty().bind(this.heightProperty().divide(2));
		Label ST1MHeading = new Label("Selected Term 1 modules");
		ST1MHeading.setPadding(new Insets(20,20,15,0));
		vST1Box.getChildren().addAll(ST1MHeading, selectedT1Modules);
		
		VBox vSYLBox = new VBox();
		sYLM = FXCollections.observableArrayList();
		selectedYearLongModules = new ListView<>(sYLM);
		selectedYearLongModules.setMinSize(400, 75);
		selectedYearLongModules.prefWidthProperty().bind(this.widthProperty());
		Label SYMLHeading = new Label("Selected Year Long Modules");
		SYMLHeading.setPadding(new Insets(20,20,15,0));
		vSYLBox.getChildren().addAll(SYMLHeading, selectedYearLongModules, vST1Box);
		this.add(vSYLBox, 1, 0);
	
		VBox vST2Box = new VBox();
		sT2M = FXCollections.observableArrayList();
		selectedT2Modules = new ListView<>(sT2M);
		selectedT2Modules.setPrefSize(400, 150);
		selectedT2Modules.prefWidthProperty().bind(this.widthProperty());
		selectedT2Modules.prefHeightProperty().bind(this.heightProperty());
		Label ST2MHeading = new Label("Selected Term 2 modules");
		ST2MHeading.setPadding(new Insets(20,20,15,0));
		vST2Box.getChildren().addAll(ST2MHeading, selectedT2Modules);
		this.add(vST2Box, 1, 1);
		
		HBox T1Credit = new HBox();
		T1Credit.setAlignment(Pos.CENTER_RIGHT);
		T1Credit.setSpacing(20);
		Label T1Credits = new Label("Current term 1 credits:");
		T1Creditsnumb = new Label("0");
		T1Creditsnumb.setPrefSize(50, 25);
		T1Creditsnumb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0.75))));
		T1Creditsnumb.setPadding(new Insets(0, 0, 0, 10));
		T1Creditsnumb.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		T1Credit.getChildren().addAll(T1Credits, T1Creditsnumb);
		this.add(T1Credit, 0, 2);
		
		HBox T2Credit = new HBox();
		T2Credit.setAlignment(Pos.CENTER_RIGHT);
		T2Credit.setSpacing(20);
		Label T2Credits = new Label("Current term 2 credits:");
		T2Creditsnumb = new Label("0");
		T2Creditsnumb.setPrefSize(50, 25);
		T2Creditsnumb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0.75))));
		T2Creditsnumb.setPadding(new Insets(0, 0, 0, 10));
		T2Creditsnumb.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		T2Credit.getChildren().addAll(T2Credits, T2Creditsnumb);
		this.add(T2Credit, 1, 2);
		
		HBox ControlsL = new HBox();
		btnReset = new Button("Reset");
		btnReset.setPrefSize(70, 20);
		btnReset.setAlignment(Pos.CENTER);
		ControlsL.setSpacing(10);
		ControlsL.setAlignment(Pos.CENTER_RIGHT);
		ControlsL.getChildren().addAll(btnReset);
		this.add(ControlsL, 0, 3);
		
		HBox ControlsR = new HBox();
		btnSubmit = new Button("Submit");
		btnSubmit.setPrefSize(70, 20);
		btnSubmit.setAlignment(Pos.CENTER);
		ControlsR.setSpacing(10);
		ControlsR.setAlignment(Pos.CENTER_LEFT);
		ControlsR.getChildren().addAll(btnSubmit);
		this.add(ControlsR, 1, 3);
		
		unselectedT1Modules.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		unselectedT2Modules.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	public void populateModuleList1(Module modules) {
		unselectedT1Modules.getItems().addAll(modules);
	}
	
	public void populateModuleList2(Module modules) {
		unselectedT2Modules.getItems().addAll(modules);
	}
	
	public void populateModuleListYearLong(Module modules) {
		selectedYearLongModules.getItems().addAll(modules);
	}
	
	public void populateSelectedT1Modules(Module modules) {
		selectedT1Modules.getItems().addAll(modules);
	}
	
	public void populateSelectedT2Modules(Module modules) {
		selectedT2Modules.getItems().addAll(modules);
	}
	
	public ListView<Module> getSelectedYearLongModules(){
		return selectedYearLongModules;
	}
	
	public ListView<Module> getUnselectedT1Modules(){
		return unselectedT1Modules;
	}
	
	public ListView<Module> getSelectedT1Modules(){
		return selectedT1Modules;
	}
	
	public ListView<Module> getUnselectedT2Modules(){
		return unselectedT2Modules;
	}
	
	public ListView<Module> getSelectedT2Modules(){
		return selectedT2Modules;
	}
	
	public void clearList(ListView<Module> m){
		m.getItems().clear();
	}
	
	public Label getT1CreditNumb() {
		return T1Creditsnumb;
	}
	
	public void setT1CreditNumb(int x) {
		T1Creditsnumb.setText(String.valueOf(x));
	}
	
	public Label getT2CreditNumb() {
		return T2Creditsnumb;
	}
	
	public void setT2CreditNumb(int x) {
		T2Creditsnumb.setText(String.valueOf(x));
	}
	
	
	public void addBtnT1Handler(EventHandler<ActionEvent> handler) {
		btnAddT1.setOnAction(handler);
	}
	
	public void addBtnT2Handler(EventHandler<ActionEvent> handler) {
		btnAddT2.setOnAction(handler);
	}
	
	public void removeBtnT1Handler(EventHandler<ActionEvent> handler) {
		btnRemoveT1.setOnAction(handler);
	}
	
	public void removeBtnT2Handler(EventHandler<ActionEvent> handler) {
		btnRemoveT2.setOnAction(handler);
	}
	
	public void resetBtnHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}
	
	public void submitBtnHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}
}
