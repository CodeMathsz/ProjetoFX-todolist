package com.br.matheus.todolist.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import javafx.scene.control.TableView;

public class Tarefa implements Comparable<Tarefa>{// IMPLEMENTANDO POR CAUSA DO,Collections.sort(tarefas); QUE � PARA COMPARAR

	private long id;
	private LocalDate dataCriacaoTf;// DATA DE CRIA��O DA TAREFA
	private LocalDate dataLimiteTf;// PRAZO, DATA LIMITE PARA A TAREFA SER CONCLUIDA
	private LocalDate dataFinalizadaTf;// DATA PARA FINALIZAR A TAREFA
	private String descricaoDaTf;// COMPLEMENTOS, NOME, DATA
	private String comentarioTf;
	private StatusTarefa status;// ENUMERA��O
	private Importancia classifImportancia;
	
	
	
	// GETs E SETs
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getDataCriacaoTf() {
		return dataCriacaoTf;
	}
	public void setDataCriacaoTf(LocalDate dataCriacaoTf) {
		this.dataCriacaoTf = dataCriacaoTf;
	}
	public LocalDate getDataLimiteTf() {
		return dataLimiteTf;
	}
	public void setDataLimiteTf(LocalDate dataLimiteTf) {
		this.dataLimiteTf = dataLimiteTf;
	}
	public LocalDate getDataFinalizadaTf() {
		return dataFinalizadaTf;
	}
	public void setDataFinalizadaTf(LocalDate dataFinalizadaTf) {
		this.dataFinalizadaTf = dataFinalizadaTf;
	}
	public String getDescricaoDaTf() {
		return descricaoDaTf;
	}
	public void setDescricaoDaTf(String descricaoDaTf) {
		this.descricaoDaTf = descricaoDaTf;
	}
	public String getComentarioTf() {
		return comentarioTf;
	}
	public void setComentarioTf(String comentarioTf) {
		this.comentarioTf = comentarioTf;
	}
	public StatusTarefa getStatus() {
		return status;
	}
	public void setStatus(StatusTarefa status) {
		this.status = status;
	}
	public Importancia getClassifImportancia() {
		return classifImportancia;
	}
	public void setClassifImportancia(Importancia classifImportancia) {
		 this.classifImportancia = classifImportancia;
	}
	
	
	// M�TODO "PREPARA A TAREFA PARA SER GRAVADA"
	public String formatToSave() {
		// "CONTRUIR UMA STRING(StringBuilder)"
		StringBuilder builder = new StringBuilder();
		//FORMATADOR DE DATA, DEIXANDO NA ORDEM DE DIA/M�S/ANO
		DateTimeFormatter fmt =DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		// CONSTRUINDO A STRING ATRAV�S DO BUILDER
		builder.append(this.getId()+";");
		builder.append(this.getDataCriacaoTf().format(fmt)+";");//FORMATANDO A DATA COM, format(fmt)
		builder.append(this.getDataLimiteTf().format(fmt)+";");
		if(this.getDataFinalizadaTf() != null) {
			builder.append(this.getDataCriacaoTf().format(fmt));
		}
		builder.append(";");// TENDO A DATA OU N�O TER� ;, POR ISSO EST� FORA DO IF
		
		builder.append(this.getDescricaoDaTf()+";");
		builder.append(this.getStatus().ordinal()+";");//GUARDANDO PELA POSI��O DA ENUM
		builder.append(this.getClassifImportancia().ordinal()+";");
		builder.append(this.getComentarioTf()+"\n");//\n PARA A PR�XIMA TAREFA COME�AR NA LINHA DE BAIXO
		
		return builder.toString();
	}
	
	
	@Override
	public int compareTo(Tarefa o) {
		// NEGATIVO QUANDO FOR MENOR QUE UM OBJETO E QUANDO MAIOR RETORNA POSITIVO
		
		if(this.getDataLimiteTf().isBefore(o.getDataLimiteTf())) {// isBefore, ANTES, QUE DIZER QUE this.getDataLimiteTf() VEM ANTES DO o.getDataLimiteTf(, SENDO ASSIM � MENOR  
			return -1;
		}else if(this.getDataLimiteTf().isAfter(o.getDataLimiteTf())) {// isAfter, DEPOIS, QUE DIZER QUE this.getDataLimiteTf() EST� 'DEPOIS' DO o.getDataLimiteTf(, SENDO ASSIM � MAIOR
			return 1;
		}
		return this.getDescricaoDaTf().compareTo(o.getDescricaoDaTf());// SE AS DUAS FOREM IGUAL ELE VAI ORDENAR PELA DESCRI��O NA ORDEM ALFABETICA
	}
	
	
	
}




