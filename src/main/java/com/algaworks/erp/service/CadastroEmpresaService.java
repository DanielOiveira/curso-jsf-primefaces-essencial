package com.algaworks.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.repository.EmpresaRepository;
import com.algaworks.erp.util.Transacional;

public class CadastroEmpresaService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmpresaRepository repository;
	
	@Transacional
	public void salvar(Empresa empresa) {
		repository.guardar(empresa);
	}
	
	@Transacional
	public void excluir(Empresa empresa) {
		repository.remover(empresa);
	}
	
}
