package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javafx.animation.KeyValue.Type;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import model.Course;
import model.Delivery;
import model.Module;
import model.StudentProfile;
import view.ModuleSelectionRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateProfilePane;
import view.ModuleSelectionMenuBar;

public class ModuleSelectionController {

	//fields to be used throughout class
	private StudentProfile model;
	private ModuleSelectionRootPane view;
	
	private CreateProfilePane cpp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	private ModuleSelectionMenuBar msmb;
	private int creditT1;
	private int creditT2;
	private int reservedCreditT1;
	private int reservedCreditT2;
	private String temp = "";
	private int fullCredits;
	
	public ModuleSelectionController(StudentProfile model, ModuleSelectionRootPane view) throws FileNotFoundException {
		//initialise model and view fields
	    this.model = model;
		this.view = view;
		
		//initialise view subcontainer fields
		cpp = view.getCreateProfilePane();
		smp = view.getSelectModulesPane();
		msmb = view.getModuleSelectionMenuBar();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();

		//populate combobox in create profile pane with courses using the setupAndGetCourses method below
		cpp.populateCourseComboBox(setupAndGetCourses());
		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}

	
	//a helper method used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create profile pane
		cpp.addCreateProfileHandler(new CreateProfileHandler());
		smp.addBtnT1Handler(new addBtnT1Handler());
		smp.removeBtnT1Handler(new removeBtnT1Handler());
		smp.addBtnT2Handler(new addBtnT2Handler());
		smp.removeBtnT2Handler(new removeBtnT2Handler());
		smp.submitBtnHandler(new submitBtnHandler());
		smp.resetBtnHandler(new resetBtnHandler());
		rmp.addBtnT1A1Handler(new addBtnT1A1Handler());
		rmp.removeBtnT1A1Handler(new removeBtnT1A1Handler());
		rmp.addBtnT2A2Handler(new addBtnT2A2Handler());
		rmp.removeBtnT2A2Handler(new removeBtnT2A2Handler());
		rmp.confirmBtnT1A1Handler(new confirmBtnT1A1Handler());
		rmp.confirmBtnT2A2Handler(new confirmBtnT2A2Handler());
		osp.saveOverviewHandler(new saveHandler());
		msmb.addSaveHandler(new saveHandler());
		msmb.addLoadHandler(new loadHandler());
		//attach an event handler to the menu bar that closes the application
		msmb.addExitHandler(e -> System.exit(0));
		msmb.addAboutHandler(e -> alert(AlertType.INFORMATION, "About", null, "Completed Final Year Module Selection Tool" ));
	}
	
	private class CreateProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
					smp.clearList(smp.getUnselectedT1Modules());
					smp.clearList(smp.getUnselectedT2Modules());
					smp.clearList(smp.getSelectedT1Modules());
					smp.clearList(smp.getSelectedT2Modules());
					smp.clearList(smp.getSelectedYearLongModules());
					creditT1 = 0;
					creditT2 = 0;
					
					model = new StudentProfile();
					
					boolean isNumb;
					Long numb = (long) 0;
					try {
						Long.parseLong(cpp.getPnumberInput());
						isNumb = true;
						numb = Long.parseLong(cpp.getPnumberInput());
					} catch (NumberFormatException e2) {
						isNumb = false;
					}
					
					
					if(cpp.getPnumberInput().isEmpty()) {
						alert(AlertType.INFORMATION, "Alert", null, "Please enter your pNumber" );
					} else if (numb < 0) {
						alert(AlertType.INFORMATION, "Alert", null, "please enter whole positive numbers only for your pNumber" );
					} else if (isNumb == false) {
						alert(AlertType.INFORMATION, "Alert", null, "please enter numbers only for your pNumber" );
					} else if (cpp.getPnumberInput().length() > 8) {
						alert(AlertType.INFORMATION, "Alert", null, "please enter a pNumber with 8 numbers or less" );
					} else if(cpp.getName().getFirstName().isEmpty()) {
						alert(AlertType.INFORMATION, "Alert", null, "Please enter a your first name" );
					} else if (!cpp.getName().getFirstName().matches("^[a-zA-Z]*$")) {
						alert(AlertType.INFORMATION, "Alert", null, "please only enter alphabetic characters for your first name" );
					} else if(cpp.getName().getFirstName().length() > 50) {
						alert(AlertType.INFORMATION, "Alert", null, "Please enter a name with 50 characters or less" );
					} else if(cpp.getName().getFamilyName().isEmpty()) {
						alert(AlertType.INFORMATION, "Alert", null, "Please enter a your family name" );
					} else if(cpp.getName().getFamilyName().length() > 50) {
						alert(AlertType.INFORMATION, "Alert", null, "Please enter a name with 50 characters or less" );
					} else if (!cpp.getName().getFamilyName().matches("^[a-zA-Z]*$")) {
						alert(AlertType.INFORMATION, "Alert", null, "please only enter alphabetic characters for your family name" );
					} else if(cpp.getEmail().isEmpty()) {
						alert(AlertType.INFORMATION, "Alert", null, "Please enter a your email address" );
					} else if (cpp.getDate() == null) {
						alert(AlertType.INFORMATION, "Alert", null, "Please select or enter a date" );
					} else {
						
						model.setCourse(cpp.getSelectedCourse());
						model.setpNumber("P" + cpp.getPnumberInput());
						model.setEmail(cpp.getEmail());
						model.setStudentName(cpp.getName());
						model.getStudentName().setFirstName(model.getStudentName().getFirstName().substring(0, 1).toUpperCase() + model.getStudentName().getFirstName().substring(1, model.getStudentName().getFirstName().length()));
						model.getStudentName().setFamilyName(model.getStudentName().getFamilyName().substring(0, 1).toUpperCase() + model.getStudentName().getFamilyName().substring(1, model.getStudentName().getFamilyName().length()));
						model.setSubmissionDate(cpp.getDate());
						
							
							model.getCourse().getAllModulesOnCourse().forEach(m -> 
								{	
									if(m.getRunPlan() == Delivery.YEAR_LONG) {
										smp.populateModuleListYearLong(m);
										model.addSelectedModule(m);
										creditT1 += m.getCredits()/2;
										creditT2 += m.getCredits()/2;
									}
									
									if(m.isMandatory() && m.getRunPlan() == Delivery.TERM_1){
										smp.populateSelectedT1Modules(m);
										model.addSelectedModule(m);
										creditT1 += m.getCredits();
									}
									
									if(m.isMandatory() && m.getRunPlan() == Delivery.TERM_2){
										smp.populateSelectedT2Modules(m);
										model.addSelectedModule(m);
										creditT2 += m.getCredits();
									}
									
									if(m.getRunPlan() == Delivery.TERM_1 && !m.isMandatory()) {
										smp.populateModuleList1(m);	
									} else if (m.getRunPlan() == Delivery.TERM_2 && !m.isMandatory()){
										smp.populateModuleList2(m);	
									}
								}
							);
						smp.setT1CreditNumb(creditT1);
						smp.setT2CreditNumb(creditT2);
						view.changeTab(1);
					}
		}
	}

	private class addBtnT1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			}  else if(smp.getUnselectedT1Modules().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to add" );
			} else {
				smp.getSelectedT1Modules().getItems().add(smp.getUnselectedT1Modules().getSelectionModel().getSelectedItem());
				creditT1 += smp.getUnselectedT1Modules().getSelectionModel().getSelectedItem().getCredits();
				smp.getUnselectedT1Modules().getItems().remove(smp.getUnselectedT1Modules().getSelectionModel().getSelectedItem());
				smp.setT1CreditNumb(creditT1);
			}
		}
	}
	
	private class removeBtnT1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			}  else if (smp.getSelectedT1Modules().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to remove" );
			}
			else if(smp.getSelectedT1Modules().getSelectionModel().getSelectedItem().isMandatory()) {
				alert(AlertType.INFORMATION, "Alert", null, "This Module is mandatory and cannot be removed" );
			}else {
				smp.getUnselectedT1Modules().getItems().add(smp.getSelectedT1Modules().getSelectionModel().getSelectedItem());
				creditT1 -= smp.getSelectedT1Modules().getSelectionModel().getSelectedItem().getCredits();
				smp.getSelectedT1Modules().getItems().remove(smp.getSelectedT1Modules().getSelectionModel().getSelectedItem());
				smp.setT1CreditNumb(creditT1);
			}	
		}
	}
	
	private class addBtnT2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(smp.getUnselectedT2Modules().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to add" );
			} else {
				smp.getSelectedT2Modules().getItems().add(smp.getUnselectedT2Modules().getSelectionModel().getSelectedItem());
				creditT2 += smp.getUnselectedT2Modules().getSelectionModel().getSelectedItem().getCredits();
				smp.getUnselectedT2Modules().getItems().remove(smp.getUnselectedT2Modules().getSelectionModel().getSelectedItem());
				smp.setT2CreditNumb(creditT2);
			}
		}
	}
	
	private class removeBtnT2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			}  else if (smp.getSelectedT2Modules().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to remove" );
			} 
			else if(smp.getSelectedT2Modules().getSelectionModel().getSelectedItem().isMandatory()) {
				alert(AlertType.INFORMATION, "Alert", null, "This Module is mandatory and cannot be removed" );
			}else {
				smp.getUnselectedT2Modules().getItems().add(smp.getSelectedT2Modules().getSelectionModel().getSelectedItem());
				creditT2 -= smp.getSelectedT2Modules().getSelectionModel().getSelectedItem().getCredits();
				smp.getSelectedT2Modules().getItems().remove(smp.getSelectedT2Modules().getSelectionModel().getSelectedItem());
				smp.setT2CreditNumb(creditT2);
			}
		}
	}
	
	private class resetBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else {
			smp.clearList(smp.getUnselectedT1Modules());
			smp.clearList(smp.getUnselectedT2Modules());
			smp.clearList(smp.getSelectedT1Modules());
			smp.clearList(smp.getSelectedT2Modules());
			smp.clearList(smp.getSelectedYearLongModules());
			creditT1 = 0;
			creditT2 = 0;
			
			model.getCourse().getAllModulesOnCourse().forEach(m -> 
			{	
				if(m.getRunPlan() == Delivery.YEAR_LONG) {
					smp.populateModuleListYearLong(m);
					model.addSelectedModule(m);
					creditT1 += m.getCredits()/2;
					creditT2 += m.getCredits()/2;
				}
				
				if(m.isMandatory() && m.getRunPlan() == Delivery.TERM_1){
					smp.populateSelectedT1Modules(m);
					model.addSelectedModule(m);
					creditT1 += m.getCredits();
				}
				
				if(m.isMandatory() && m.getRunPlan() == Delivery.TERM_2){
					smp.populateSelectedT2Modules(m);
					model.addSelectedModule(m);
					creditT2 += m.getCredits();
				}
				
				if(m.getRunPlan() == Delivery.TERM_1 && !m.isMandatory()) {
					smp.populateModuleList1(m);	
				} else if (m.getRunPlan() == Delivery.TERM_2 && !m.isMandatory()){
					smp.populateModuleList2(m);	
				}
			}
		);
	smp.setT1CreditNumb(creditT1);
	smp.setT2CreditNumb(creditT2);
		}
		}
	}
	
	private class submitBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else {
			if(creditT1 > 60) {
				alert(AlertType.INFORMATION, "Alert", null, "You can only have a maximum of 60 credits for term 1" );
			} else if (creditT1 < 60) {
				alert(AlertType.INFORMATION, "Alert", null, "You need to pick enough modules to reach 60 credits total for term 1" );
			}
			
			if(creditT2 > 60) {
				alert(AlertType.INFORMATION, "Alert", null, "You can only have a maximum of 60 credits for term 2" );
			} else if (creditT2 < 60) {
				alert(AlertType.INFORMATION, "Alert", null, "You need to pick enough modules to reach 60 credits total for term 2" );
			}
			
			if(creditT1 == 60 && creditT2 == 60) {
				model.clearSelectedModules();
				smp.getSelectedYearLongModules().getItems().forEach(m -> {
					model.addSelectedModule(m);
				});
				
				smp.getSelectedT1Modules().getItems().forEach(m ->{
					model.addSelectedModule(m);
				});
				
				smp.getSelectedT2Modules().getItems().forEach(m ->{
					model.addSelectedModule(m);
				});
				
				rmp.clearList(rmp.getUnselectedTerm1());
				rmp.clearList(rmp.getReservedTerm1());
				smp.getUnselectedT1Modules().getItems().forEach(m ->{
					rmp.populateUnselectedTerm1Modules(m);
				});
				rmp.clearList(rmp.getUnselectedTerm2());
				rmp.clearList(rmp.getReservedTerm2());
				smp.getUnselectedT2Modules().getItems().forEach(m ->{
					rmp.populateUnselectedTerm2Modules(m);
				});
				
				
				view.changeTab(2);
				rmp.changePane(0);
			}
		}
		}
	}
	
	private class addBtnT1A1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(rmp.getUnselectedTerm1().getItems().isEmpty()) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select some optional modules first" );
				view.changeTab(1);
			} else if(rmp.getUnselectedTerm1().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to reserve" );
			} else if(rmp.getUnselectedTerm1().getItems().isEmpty()) {	
			}else {
				rmp.getReservedTerm1().getItems().add(rmp.getUnselectedTerm1().getSelectionModel().getSelectedItem());
				rmp.getUnselectedTerm1().getItems().remove(rmp.getUnselectedTerm1().getSelectionModel().getSelectedItem());
			}
		}
	}
	
	private class removeBtnT1A1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(rmp.getUnselectedTerm1().getItems().isEmpty()) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select some optional modules first" );
				view.changeTab(1);
			} else if (rmp.getReservedTerm1().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to remove" );
			} else {
				rmp.getUnselectedTerm1().getItems().add(rmp.getReservedTerm1().getSelectionModel().getSelectedItem());
				rmp.getReservedTerm1().getItems().remove(rmp.getReservedTerm1().getSelectionModel().getSelectedItem());
			}
		}
	}
	
	private class addBtnT2A2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(rmp.getUnselectedTerm2().getItems().isEmpty()) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select some optional modules first" );
				view.changeTab(1);
			} else if(rmp.getUnselectedTerm2().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to reserve" );
			} else if(rmp.getUnselectedTerm2().getItems().isEmpty()) {		
			}else {
				rmp.getReservedTerm2().getItems().add(rmp.getUnselectedTerm2().getSelectionModel().getSelectedItem());
				rmp.getUnselectedTerm2().getItems().remove(rmp.getUnselectedTerm2().getSelectionModel().getSelectedItem());
			}
		}
	}
	
	private class removeBtnT2A2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(rmp.getUnselectedTerm2().getItems().isEmpty()) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select some optional modules first" );
				view.changeTab(1);
			} else if (rmp.getReservedTerm2().getSelectionModel().getSelectedItem() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select a module to remove" );
			} else {
				rmp.getUnselectedTerm2().getItems().add(rmp.getReservedTerm2().getSelectionModel().getSelectedItem());
				rmp.getReservedTerm2().getItems().remove(rmp.getReservedTerm2().getSelectionModel().getSelectedItem());
			}
		}
	}
	
	private class confirmBtnT1A1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			reservedCreditT1 = 0;
			rmp.getReservedTerm1().getItems().forEach(m ->{
				reservedCreditT1 += m.getCredits();
			});
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(rmp.getUnselectedTerm1().getItems().isEmpty()) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select some optional modules first" );
				view.changeTab(1);
			} else if(reservedCreditT1 > 30) {
				alert(AlertType.INFORMATION, "Alert", null, "Please choose less modules up to 30 credits for this term." );
			} else if(reservedCreditT1 < 30) {
				alert(AlertType.INFORMATION, "Alert", null, "Please choose more modules up to 30 credits for this term." );
			}
			else if(reservedCreditT1 == 30) {
				rmp.changePane(1);
			}
		}
	}
	
	private class confirmBtnT2A2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			reservedCreditT2 = 0;
			rmp.getReservedTerm2().getItems().forEach(m ->{
				reservedCreditT2 += m.getCredits();
			});
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty()&& model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(rmp.getUnselectedTerm2().getItems().isEmpty()) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select some optional modules first" );
				view.changeTab(1);
			} else if(reservedCreditT1 != 30) {
				alert(AlertType.INFORMATION, "Alert", null, "Please reserve modules for Term 1 first." );
				rmp.changePane(0);
			} else if(reservedCreditT2 > 30) {
				alert(AlertType.INFORMATION, "Alert", null, "Please choose less modules up to 30 credits for this term." );
			} else if(reservedCreditT2 < 30) {
				alert(AlertType.INFORMATION, "Alert", null, "Please choose more modules up to 30 credits for this term." );
			}
			else if(reservedCreditT2 == 30) {
				osp.getStudentDetails().setText("");
				osp.getSelectedModules().setText("");
				osp.getReservedModules().setText("");
				
//				model.getAllReservedModules().clear();
				model.clearReservedModules();
				rmp.getReservedTerm1().getItems().forEach(m ->{
					model.addReservedModule(m);
				});
				rmp.getReservedTerm2().getItems().forEach(m ->{
					model.addReservedModule(m);
				});
				
				osp.getStudentDetails().setText("Name: " + model.getStudentName().getFullName() + 
						"\n" + "PNo: " + model.getpNumber() + "\n" + "Email: " + model.getEmail() + "\n" + 
						"Date: " + model.getSubmissionDate() + "\n" + "Course: " + model.getCourse());
				
				temp = "Selected modules\n==========\n";
				model.getAllSelectedModules().forEach(m -> {
					
					String mand;
					if(m.isMandatory() == true) {
						mand = "yes";
					} else {
						mand = "no";
					}
					temp += "Module code: " + m.getModuleCode() + ", Module name: " + m.getModuleName() + ",\n" +
							"Credits: " + m.getCredits() + ", Mandatory on your course? " + mand  + 
							", Delivery: " + m.getRunPlan() + "\n\n";
				});
				osp.getSelectedModules().setText(temp);
				
				temp = "Reserved modules\n==========\n";
				model.getAllReservedModules().forEach(m -> {
					
					String mand;
					if(m.isMandatory() == true) {
						mand = "yes";
					} else {
						mand = "no";
					}
					temp += "Module code: " + m.getModuleCode() + ", Module name: " + m.getModuleName() + ",\n" +
							"Credits: " + m.getCredits() + ", Mandatory on your course? " + mand  + 
							", Delivery: " + m.getRunPlan() + "\n\n";
				});
				osp.getReservedModules().setText(temp);
				view.changeTab(3);
			}
		}
	}
	
	private class saveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			fullCredits = 0;
			model.getAllSelectedModules().forEach(m ->{
				fullCredits += m.getCredits();
			});
			if(model.getpNumber().isEmpty() && model.getStudentName().getFullName().isEmpty() &&
					model.getEmail().isEmpty() && model.getSubmissionDate() == null) {
				alert(AlertType.INFORMATION, "Alert", null, "Please create a profile first." );
				view.changeTab(0);
			} else if(fullCredits != 120) {
				alert(AlertType.INFORMATION, "Alert", null, "Please select modules first." );
				view.changeTab(1);
			} else if(model.getAllReservedModules().isEmpty()) {
				alert(AlertType.INFORMATION, "Alert", null, "Please reserve modules first." );
				view.changeTab(2);
			} else {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getPath()));){
						oos.writeObject(model);
						oos.flush();
						oos.close();
					} catch (IOException e1) {
						System.err.println(e1);
						System.out.println("Error saving.");
					}            	
				}
			}
		}	
	}
	
	private class loadHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(null);
			if(file != null) {
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getPath()));) {
					model = (StudentProfile) ois.readObject();
				} catch (IOException e1) {
					System.out.println("Error loading.");
				} catch (ClassNotFoundException c) {
					System.out.println("Class Not found");
				}
				
				smp.getUnselectedT1Modules().getItems().clear();
				smp.getUnselectedT2Modules().getItems().clear();
				smp.getSelectedT1Modules().getItems().clear();
				smp.getSelectedT2Modules().getItems().clear();
				smp.getSelectedYearLongModules().getItems().clear();
				
				
				model.getAllSelectedModules().forEach(m -> {
					if(m.getRunPlan() == Delivery.YEAR_LONG) {
						smp.getSelectedYearLongModules().getItems().add(m);
						creditT1 += m.getCredits()/2;
						creditT2 += m.getCredits()/2;
					} else if(m.getRunPlan() == Delivery.TERM_1) {
						smp.getSelectedT1Modules().getItems().add(m);
						creditT1 += m.getCredits();
					} else if(m.getRunPlan() == Delivery.TERM_2) {
						smp.getSelectedT2Modules().getItems().add(m);
						creditT2 += m.getCredits();
					}
				});
				smp.setT1CreditNumb(creditT1);
				smp.setT2CreditNumb(creditT2);
				
				model.getCourse().getAllModulesOnCourse().forEach(m ->{
					if(!smp.getSelectedT1Modules().getItems().contains(m) && m.getRunPlan() == Delivery.TERM_1) {
						smp.getUnselectedT1Modules().getItems().add(m);
					} else if(!smp.getSelectedT2Modules().getItems().contains(m) && m.getRunPlan() == Delivery.TERM_2) {
						smp.getUnselectedT2Modules().getItems().add(m);
					}
				});
				
				rmp.getUnselectedTerm1().getItems().clear();
				rmp.getUnselectedTerm2().getItems().clear();
				rmp.getReservedTerm1().getItems().clear();
				rmp.getReservedTerm2().getItems().clear();
				
				reservedCreditT1 = 0;
				reservedCreditT2 = 0;
				model.getAllReservedModules().forEach(m ->{
					if(m.getRunPlan() == Delivery.TERM_1){
						rmp.getReservedTerm1().getItems().add(m);
						rmp.getUnselectedTerm1().getItems().remove(m);		
						reservedCreditT1 += m.getCredits();
					} else if(m.getRunPlan() == Delivery.TERM_2) {
						rmp.getReservedTerm2().getItems().add(m);
						rmp.getUnselectedTerm2().getItems().remove(m);
						reservedCreditT2 += m.getCredits();
					}
				});
				
				smp.getUnselectedT1Modules().getItems().forEach(m ->{
					if(!rmp.getReservedTerm1().getItems().contains(m)) {
						rmp.getUnselectedTerm1().getItems().add(m);
					} 
				});
				
				smp.getUnselectedT2Modules().getItems().forEach(m ->{
					if(!rmp.getReservedTerm2().getItems().contains(m)) {
						rmp.getUnselectedTerm2().getItems().add(m);
					}
				});
				
				osp.getStudentDetails().setText("Name: " + model.getStudentName().getFullName() + 
						"\n" + "PNo: " + model.getpNumber() + "\n" + "Email: " + model.getEmail() + "\n" + 
						"Date: " + model.getSubmissionDate() + "\n" + "Course: " + model.getCourse());

				temp = "Selected modules\n==========\n";
				model.getAllSelectedModules().forEach(m -> {
					
					String mand;
					if(m.isMandatory() == true) {
						mand = "yes";
					} else {
						mand = "no";
					}
					temp += "Module code: " + m.getModuleCode() + ", Module name: " + m.getModuleName() + ",\n" +
							"Credits: " + m.getCredits() + ", Mandatory on your course? " + mand  + 
							", Delivery: " + m.getRunPlan() + "\n\n";
				});
				osp.getSelectedModules().setText(temp);
				
				temp = "Reserved modules\n==========\n";
				model.getAllReservedModules().forEach(m -> {
					
					String mand;
					if(m.isMandatory() == true) {
						mand = "yes";
					} else {
						mand = "no";
					}
					temp += "Module code: " + m.getModuleCode() + ", Module name: " + m.getModuleName() + ",\n" +
							"Credits: " + m.getCredits() + ", Mandatory on your course? " + mand  + 
							", Delivery: " + m.getRunPlan() + "\n\n";
				});
				osp.getReservedModules().setText(temp);
				view.changeTab(3);
			}
		}	
	}
	
	
	private void alert(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	//generates all module and course data and returns courses within an array
	private Course[] setupAndGetCourses() throws FileNotFoundException {    
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Delivery.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Delivery.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Delivery.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Delivery.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Delivery.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModule(imat3423);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3902_CompSci);
		compSci.addModule(ctec3110);
		compSci.addModule(ctec3605);
		compSci.addModule(ctec3606);
		compSci.addModule(ctec3410);
		compSci.addModule(ctec3904);
		compSci.addModule(ctec3905);
		compSci.addModule(ctec3906);
		compSci.addModule(imat3410);
		compSci.addModule(imat3406);
		compSci.addModule(imat3611);
		compSci.addModule(imat3613);
		compSci.addModule(imat3614);
		compSci.addModule(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(imat3423);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3902_SoftEng);
		softEng.addModule(ctec3110);
		softEng.addModule(ctec3605);
		softEng.addModule(ctec3606);
		softEng.addModule(ctec3410);
		softEng.addModule(ctec3904);
		softEng.addModule(ctec3905);
		softEng.addModule(ctec3906);
		softEng.addModule(imat3410);
		softEng.addModule(imat3406);
		softEng.addModule(imat3611);
		softEng.addModule(imat3613);
		softEng.addModule(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
