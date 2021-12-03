package com.br.matheus.todolist.model;

public enum StatusTarefa {
	// CRIANDO ENUMERA��O DOS STATUS POSSIVEIS DA TAREFA, TEM QUE SER TUDO MAI�SCULO E COM AS REGRAS DE VARI�VEIS
	ABERTA("Aberta"), CONCLUIDA("Conclu�da"), ADIADA("Adiada");

	String descricaoEnum;
	
	private StatusTarefa(String desc) {
		this.descricaoEnum = desc;
		
	}
	@Override
	public String toString() {
		
		return descricaoEnum;
	}
}
