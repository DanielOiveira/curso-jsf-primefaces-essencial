package com.algaworks.erp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.model.RamoAtividade;
import com.algaworks.erp.model.TipoEmpresa;
import com.algaworks.erp.repository.EmpresaRepository;
import com.algaworks.erp.repository.RamoAtividadeRepository;
import com.algaworks.erp.service.CadastroEmpresaService;
import com.algaworks.erp.util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmpresaRepository empresaRepository;
	
	@Inject
	private RamoAtividadeRepository ramoAtividadeRepository;
	
	@Inject
	private CadastroEmpresaService cadastroEmpresaService;
	
	@Inject
	private FacesMessages messages;
	
	private List<Empresa> listaEmpresas;
	
	private String termoPesquisa;
	
	private Converter ramoAtividadeConverter;
	
	private Empresa empresa;
	
	
	public void prepararNovaEmpresa() {
		empresa = new Empresa();
	}
	
	public void salvar() {
		cadastroEmpresaService.salvar(empresa);
		
		if(jaHouvePesquisa()) {
			pesquisar();
		}else {
			todasEmpresas();
		}
		
		messages.info("Empresa salva com sucesso");
		
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:empresasDataTable", "frm:messages"));
	}
	
	public void todasEmpresas() {
		listaEmpresas = empresaRepository.todas();
	}
	
	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && !"".equals(termoPesquisa);
	}
	
	public List<RamoAtividade> completarRamoAtividade(String termo){
		List<RamoAtividade> listaRamoAtividades = ramoAtividadeRepository.pesquisar(termo);
		
		ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);
		
		return listaRamoAtividades;
	}
	
	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}
	
	public void pesquisar() {
		listaEmpresas = empresaRepository.pesquisar(termoPesquisa);
		
		if(listaEmpresas.isEmpty()) {
			messages.info("A consulta não retornou registros");
		}
	}
	
	public String getTermoPesquisa() {
		return termoPesquisa;
	}
	
	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}
	
	public TipoEmpresa[] getTiposEmpresa() {
		return TipoEmpresa.values();
	}
	
	public Converter getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public boolean isEmpresaSelecionada() {
		return empresa != null && empresa.getId() != null;
	}
}
