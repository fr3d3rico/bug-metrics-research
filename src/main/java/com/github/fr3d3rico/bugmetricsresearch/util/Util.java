package com.github.fr3d3rico.bugmetricsresearch.util;

import com.github.fr3d3rico.bugmetricsresearch.config.Config;

public class Util {
	
	public static String extrairNomeProjeto(String nomeArquivoCompleto) {
		if( nomeArquivoCompleto != null ) {
			return nomeArquivoCompleto.substring(0, nomeArquivoCompleto.indexOf("/"));
		}
		return null;
	}
	
	public static String gerarComandoCalcularMetrics_VERSION_4(String nomeProjeto) {
		return "cmd /c und metrics -db "+ Config.REPOSITORY_DIR + nomeProjeto + "\\" + nomeProjeto +".udb";
	}
	
	public static String gerarComandosGit(String diretorioProjetoGit, String commit) {
		// return "cmd /c cd "+diretorioProjetoGit+" && git reset --hard master && git checkout "+commit+"~1";
		return "cmd /c cd "+diretorioProjetoGit+" && git checkout "+commit+"~1";
	}
	
	public static String getCaminhoArquivoProjeto(String nomeArquivo) {
		nomeArquivo = nomeArquivo.replaceAll("/", ";");
		String[] partes = nomeArquivo.split(";");
		nomeArquivo = "";
		for(int i = 0; i < partes.length; i++) {
			nomeArquivo += partes[i] + "\\";
		}
		nomeArquivo = nomeArquivo.substring(0, nomeArquivo.length() - 1);
		
		return nomeArquivo;
	}
	
}