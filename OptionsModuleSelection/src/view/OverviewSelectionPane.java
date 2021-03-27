package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class OverviewSelectionPane extends StackPane {

	private TextArea studentDetails, selectedModules, reservedModules;
	private Button save;
	
	public OverviewSelectionPane() {
		this.setAlignment(Pos.CENTER);
		StackPane sp = new StackPane();
		sp.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);		
		
		VBox Container = new VBox();
		Container.setAlignment(Pos.CENTER);
		
		HBox top = new HBox();
		top.setAlignment(Pos.CENTER);
		top.setPadding(new Insets(50));
		studentDetails = new TextArea();
		studentDetails.setText("Profile will appear here");
		studentDetails.setMinSize(850, 125);
		studentDetails.prefWidthProperty().bind(sp.widthProperty());
		studentDetails.setEditable(false);
		top.getChildren().addAll(studentDetails);
		
		HBox moddules = new HBox();
		moddules.setAlignment(Pos.CENTER);
		moddules.setPadding(new Insets(0, 50, 50, 50));
		moddules.setSpacing(50);
		selectedModules = new TextArea();
		selectedModules.setText("Selected modules will appear here");
		selectedModules.setMinSize(400, 300);
		selectedModules.prefWidthProperty().bind(sp.widthProperty());
		selectedModules.prefHeightProperty().bind(sp.heightProperty());
		selectedModules.setEditable(false);
		
		reservedModules = new TextArea();
		reservedModules.setText("Reserved modules will appear here");
		reservedModules.setMinSize(400, 300);
		reservedModules.prefWidthProperty().bind(sp.widthProperty());
		reservedModules.prefHeightProperty().bind(sp.heightProperty());
		reservedModules.setEditable(false);
		moddules.getChildren().addAll(selectedModules, reservedModules);
		
		HBox control = new HBox();
		control.setAlignment(Pos.CENTER);
		control.setPadding(new Insets(0,0,50,0));

		save = new Button("Save Overview");
		save.setPrefSize(100, 30);
		save.setAlignment(Pos.CENTER);
		control.getChildren().addAll(save);

		
		Container.getChildren().addAll(top, moddules, control);
		sp.getChildren().add(Container);
		
		this.getChildren().addAll(sp);
	}
	
	public TextArea getStudentDetails() {
		return studentDetails;
	}
	
	public TextArea getReservedModules() {
		return reservedModules;
	}
	
	public TextArea getSelectedModules() {
		return selectedModules;
	}
	
	public void saveOverviewHandler(EventHandler<ActionEvent> handler) {
		save.setOnAction(handler);
	}
}
