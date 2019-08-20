package com.github.fr3d3rico.bugmetricsresearch.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.fr3d3rico.bugmetricsresearch.util.Util;

public class Bug {

	private Long bug_id;
	private String commit;
	private String files;

	private List<String> listaFiles;
	private Set<String> projectName;

	public Bug(Long bug_id, String commit, String files) {
		super();
		this.bug_id = bug_id;
		this.commit = commit;
		this.files = files;

		// Extrair nomes dos arquivos
		// Extrair nome dos projetos
		if (files != null && files.length() > 0) {
			this.listaFiles = new ArrayList<String>();
			this.projectName = new HashSet<String>();

			String[] fileName = files.split("\\r?\\n");
			for (String nomeArquivo : fileName) {
				this.listaFiles.add(nomeArquivo);
				this.projectName.add(Util.extrairNomeProjeto(nomeArquivo));
			}
		}

	}
	
	public Bug(Long bug_id, String commit, String files, String nomeProjeto) {
		super();
		this.bug_id = bug_id;
		this.commit = commit;
		this.files = files;

		// Extrair nomes dos arquivos
		// Extrair nome dos projetos
		if (files != null && files.length() > 0) {
			this.listaFiles = new ArrayList<String>();
			this.projectName = new HashSet<String>();

			String[] fileName = files.split("\\r?\\n");
			for (String nomeArquivo : fileName) {
				this.listaFiles.add(nomeArquivo);
//				this.projectName.add(Util.extrairNomeProjeto(nomeArquivo));
				this.projectName.add(nomeProjeto);
			}
		}

	}

	public Long getBug_id() {
		return bug_id;
	}

	public void setBug_id(Long bug_id) {
		this.bug_id = bug_id;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(String commit) {
		this.commit = commit;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public List<String> getListaFiles() {
		return listaFiles;
	}

	public void setListaFiles(List<String> listaFiles) {
		this.listaFiles = listaFiles;
	}

	public Set<String> getProjectName() {
		return projectName;
	}

	public void setProjectName(Set<String> projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "Bug [bug_id=" + bug_id + ", commit=" + commit + ", files=" + files + ", listaFiles=" + listaFiles
				+ ", projectName=" + projectName + "]";
	}

}