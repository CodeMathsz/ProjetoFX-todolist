package com.br.matheus.todolist.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.br.matheus.todolist.io.TarefaIO;
import com.br.matheus.todolist.model.Importancia;
import com.br.matheus.todolist.model.StatusTarefa;
import com.br.matheus.todolist.model.Tarefa;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class IndexController implements Initializable {

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

	@FXML
	private Tarefa tarefa;
	@FXML
	private ChoiceBox<Importancia> choiceImportancia;

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
	public void initialize(URL location, ResourceBundle resources) {
		choiceImportancia.setItems(FXCollections.observableArrayList(Importancia.values()));

	}

	@FXML
	public void btnSaveClick() {

		// VALIDA��O DE CAMPOS
		if (dateLimitBox.getValue() == null) {
			dateLimitBox.setStyle("-fx-background-color:red");
			dateLimitBox.setPromptText("Informe a Data Limite");
			// dateLimitBox.requestFocus();// PARA DATEPICKER FICAR SELECIONADO DEPOIS DA
			// MSG
		} else if (boxNameTf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a descri��o da tarefa", "Campo da Descri��o da Tarefa Vazio",
					0);
			boxNameTf.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

		} else if (boxComent.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe os coment�rios da tarefa", "campo de coment�rios vazio", 0);
			boxComent.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG

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
		dateLimitBox.requestFocus();// FICAR SELECIONADO DEPOIS DA MSG
	}
}
