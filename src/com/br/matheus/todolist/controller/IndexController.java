package com.br.matheus.todolist.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.br.matheus.todolist.io.TarefaIO;
import com.br.matheus.todolist.model.Importancia;
import com.br.matheus.todolist.model.StatusTarefa;
import com.br.matheus.todolist.model.Tarefa;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class IndexController implements Initializable, ChangeListener<Tarefa>{

	@FXML
	private MenuBar menu;

	@FXML
	private DatePicker dateLimitBox;

	@FXML
	private TextField boxNameTf, statusTf;

	@FXML
	private TextArea boxComent;

	@FXML
	private Button btnSave, btnList, btnCalendar, btnClean, btnDump;// Dump = JOGAR FORA

	// VARI�VEL PARA GUARDAR A TAREFA
	private Tarefa tarefa;
	// VARI�VEL PARA GUARDAR A LISTA DE TAREFAS
	private List<Tarefa> tarefas;
	
	@FXML
	private ChoiceBox<Importancia> choiceImportancia;
	@FXML
	private TableView<Tarefa> tvTarefa;
	@FXML
	private TableColumn<Tarefa, LocalDate> tcData;
	@FXML
	private TableColumn<Tarefa, String> tcTarefa;
	@FXML
	private TableColumn<Tarefa, StatusTarefa> tcStatus;
	@FXML
	private TableColumn<Tarefa, Importancia> tcImportancia;
	
	@FXML
	public void btnCalendarClick() {

	}

	@FXML
	public void btnCleanClick() {
		clearCamp();
	}

	@FXML
	public void btnDumpClick() {

	}

	@FXML
	public void btnListClick() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {// FAZENDO NO initialize "POR QUE O COMPORTAMENTO � UMA VEZ S�"
		choiceImportancia.setItems(FXCollections.observableArrayList(Importancia.values()));
		// DEFINE OS PAR�METROS PARA AS COLUNAS DO TableView
		tcData.setCellValueFactory(new PropertyValueFactory<>("dataLimiteTf"));// M�TODO DEFINE COMO � FABRICADO O VALOR DA CELULA
								   // VARI�VEL AN�NIMA 		  ACESSANDO o getDataLimite
		tcTarefa.setCellValueFactory(new PropertyValueFactory<>("descricaoDaTf"));
		
		tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		tcImportancia.setCellValueFactory(new PropertyValueFactory<>("classifImportancia"));
					//	FOI POSSIVEL DA UM NEW NA 'INTERFACE', POIS ELE CRIOU UMA CLASSE AN�NIMA, ENT�O N�O SERIA NA INTERFACE E SIM NESSA CLASSE AN�NIMA QUE ELE CRIOU
		
		// FORMATANDO A DATA NA COLUNA
		tcData.setCellFactory(call -> {
			
				//	CRIANDO UMA MODIFICA��O DA TableCell AN�NIMA	
				return new TableCell<Tarefa, LocalDate>() {// ABRINDO A CHAVE, DEVOLVENDO UMA INST�NCIA DA TableCell MODIFICADA
					
					@Override	
					protected void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);// EST� CHAMANDO O updateItem DA SUPER CLASSE
						// FORAMTANDO A CELULA, TEXTO
						DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
						
						if(!empty) {
							setText(item.format(fmt));
						}else {
							setText("");
						}
						
					}
				};
		
		});
		
		//	EVENTO DE SELE��O DE UM ITEM NA TableView
		tvTarefa.getSelectionModel().selectedItemProperty().addListener(this);
		
		
		
		carregarTarefas();
	}

	private void carregarTarefas() {
		try {
			// BUSCANDO AS TAREFAS NO BANCO DE DADOS E GUARDANDO NA VARI�VEL TAREFAS
			tarefas = TarefaIO.readTarefas();
			// CONVERTENDO A LISTA PARA ObservableList e ASSOCIANDO AO TableView
			tvTarefa.setItems(FXCollections.observableArrayList(tarefas));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erro ao buscar as tarefas", "Erro", 0);
			e.printStackTrace();
		}
	}
	
	@FXML
	public void btnSaveClick() {

		// VALIDA��O DE CAMPOS
		if (dateLimitBox.getValue() == null) {
			dateLimitBox.setStyle("-fx-background-color:red");
			dateLimitBox.setPromptText("Informe a Data Limite*");
			// dateLimitBox.requestFocus();// PARA DATEPICKER FICAR SELECIONADO DEPOIS DA
			// MSG
		} else if (boxNameTf.getText().isEmpty()) {
			boxNameTf.setStyle("-fx-border-color: red");
			boxNameTf.setPromptText("Informe o nome da Tarefa*");
			//JOptionPane.showMessageDialog(null, "Informe a descri��o da tarefa", "Campo da Descri��o da Tarefa Vazio",0);
			//boxNameTf.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

		} else if (boxComent.getText().isEmpty()) {
			boxComent.setStyle("-fx-background-color:red");
			boxComent.setPromptText("Adicione um coment�rio*");
			//JOptionPane.showMessageDialog(null, "Informe os coment�rios da tarefa", "campo de coment�rios vazio", 0);
			//boxComent.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

		} else if (boxNameTf.getLength() <= 5) {
			// VERIFICANDO SE O TAMANHO DO NOME DA TAREFA � VALIDO
			JOptionPane.showMessageDialog(null, "Nome da Tarefa muito curto, min(5 caracteres)",
					"Nome da Terefa inv�lido", 0);
			boxNameTf.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

		} else if (choiceImportancia.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma op��o, n�vel de improt�ncia",
					"Campo vazio,n�vel de import�ncia", 0);
			choiceImportancia.requestFocus();
		} else {
			// VENDO SE A DATA N�O � UMA DATA PASSADA, ANTERIOR A ATUAL
			if (dateLimitBox.getValue().isBefore(LocalDate.now())) {

				JOptionPane.showMessageDialog(null, "Data Limite inv�lida", "Data inv�lida", 0);
				dateLimitBox.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG
			} else {
				// INSTACIANDO A TAREFA
				tarefa = new Tarefa();
				// POPULAR TAREFA
				tarefa.setDataCriacaoTf(LocalDate.now());
				tarefa.setStatus(StatusTarefa.ABERTA);// CLASSIFICANDO O STATUS DA TAREFA COMO ABERTA
				tarefa.setDataLimiteTf(dateLimitBox.getValue());
				tarefa.setDescricaoDaTf(boxNameTf.getText());
				tarefa.setComentarioTf(boxComent.getText());
				tarefa.setClassifImportancia(choiceImportancia.getValue());
				// SALVAR NO BANCO DE DADOS
				try {// N�O TRATAMOS NA CLASSE TAREFAIO, USAMOS O THROWS, ENT�O ESTAMOS TRATANDO AQUI

					TarefaIO.insert(tarefa);
					// LIMPAR OS CAMPOS DO FORMUL�RIO
					clearCamp();
				} catch (FileNotFoundException e) {
					
					JOptionPane.showMessageDialog(null, "Arquivo n�o encontrado:"+e.getMessage(),"Erro",0);// MSG, PENSANDO NO �SUARIO
					e.printStackTrace();// MSG PENSANDO NO DEV
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Erro de I/O:"+e.getMessage(),"Erro",0);
					e.printStackTrace();
				}

				

			}
		}
	}

	private void clearCamp() {
		tarefa = null;// 'MATANDO' O OBJ TAREFA PARA A PROXIMA TAREFA
		boxNameTf.setText(null);
		dateLimitBox.setValue(null);
		statusTf.setText(null);
		boxComent.setText(null);
		choiceImportancia.setValue(null);// LIMPANDO O "VALOR"
		btnList.setDisable(true);
		btnCalendar.setDisable(true);
		btnDump.setDisable(true);
		dateLimitBox.setDisable(false);
		tvTarefa.getSelectionModel().clearSelection();
		dateLimitBox.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG
	}

	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {
		
		// PASSAR A REFER�NCIA DA VARI�VEL LOCAL PARA A TAREFA GLOBAL
		tarefa = newValue;
		
		// SE HOUVER UMA TAREFA SELECIONADA
		if (tarefa != null) {
			// LEVANDO AS INFORMA��ES DA TAREFA PARA O FORMULARIO
			
			
			
			statusTf.setText(tarefa.getStatus()+"");
			boxNameTf.setText(tarefa.getDescricaoDaTf());
			boxComent.setText(tarefa.getComentarioTf());
			choiceImportancia.setValue(tarefa.getClassifImportancia());
			dateLimitBox.setValue(tarefa.getDataLimiteTf());
			dateLimitBox.setDisable(true);
			btnList.setDisable(false);
			btnCalendar.setDisable(false);
			btnDump.setDisable(false);
			
		}
		
	}
}
