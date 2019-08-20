package com.github.fr3d3rico.bugmetricsresearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.github.fr3d3rico.bugmetricsresearch.config.Config;
import com.github.fr3d3rico.bugmetricsresearch.model.Bug;
import com.github.fr3d3rico.bugmetricsresearch.util.Util;

public class BugAcademicResearch {
	
	public static void main(String args[]) {
		
		final String NOME_PROJETO = "birt";

		
		List<Bug> listaBugs = new ArrayList<Bug>();
		
		try {
			
			//Mysql Connection
			Class.forName("com.mysql.jdbc.Driver");
			String connection = "jdbc:mysql://"+Config.DB_HOST_PORT+"/"+NOME_PROJETO+"?useUnicode=true&characterEncoding=UTF-8&user="+Config.DB_USER+"&password="+Config.DB_PASSWORD;
			Connection conn = DriverManager.getConnection(connection);
			
			ResultSet rs = conn.prepareStatement("select * from bug_and_files where commit is not null and bug_id is not null and bug_id in (314233)").executeQuery();

			if( rs != null ) {
				while( rs.next() ) {
					listaBugs.add(new Bug(rs.getLong("bug_id"), rs.getString("commit"), rs.getString("files"), NOME_PROJETO));
				}
				System.out.println(listaBugs.size());
			}
			
			if( rs != null ) {
				rs.close();
			}
			if( conn != null ) {
				conn.close();
			}
			
			
			
			
			//Para cada Bug faço:
			for(Bug b: listaBugs) {
				System.out.println();
				System.out.println(b.toString());
				
				
				
				//Deleta o arquivo do udb e csv
				//e copia o novo arquivo
				Process delFile = Runtime.getRuntime().exec("cmd /c del " + Config.REPOSITORY_DIR + NOME_PROJETO + "\\" + NOME_PROJETO + ".udb && del " + Config.REPOSITORY_DIR + NOME_PROJETO + "\\" + NOME_PROJETO + ".csv");
				delFile.waitFor();
				Process copyFile = Runtime.getRuntime().exec("cmd /c cd " + Config.REPOSITORY_DIR + NOME_PROJETO + "\\ && copy " + Config.REPOSITORY_DIR + "backup\\" + NOME_PROJETO + ".udb " + NOME_PROJETO + ".udb");
				copyFile.waitFor();
				
				
				
				//Git
				System.out.printf("\nGit(bug_id: %s and commit: %s)", b.getBug_id(), b.getCommit());
				Process pgit = Runtime.getRuntime().exec(Util.gerarComandosGit(Config.REPOSITORY_DIR + NOME_PROJETO+"\\", b.getCommit()));
				int wait = pgit.waitFor();
				if (wait == 0) {
					System.out.println("\nGit: Success!");
				}
				else {
					System.out.println("\nGit: Error!");
				}
				
				
				
				for(String nomeProjeto: b.getProjectName()) {
					
					//Adicionar arquivo no projecto Understand
					boolean algumProjetoAddedFile = false;
					System.out.printf("\nAdd File(proj: %s) \n", nomeProjeto);
					for(String nomeArquivo: b.getListaFiles()) {
						Process padd = Runtime.getRuntime().exec("cmd /c und -db "+Config.REPOSITORY_DIR + nomeProjeto + "\\" + nomeProjeto +".udb add \"" + Config.REPOSITORY_DIR + nomeProjeto + "\\" + Util.getCaminhoArquivoProjeto(nomeArquivo)+"\"");
						int exitValAdd = padd.waitFor();
						if (exitValAdd == 0) {
							System.out.println("Add File: Success! ("+nomeArquivo+")");
							algumProjetoAddedFile = true;
						}
						else {
							System.out.println("Add File: Error! ("+nomeArquivo+")");
						}
					}
					
					
					
					if(algumProjetoAddedFile) {
					
					
						//Analisar
						System.out.printf("\nAnalyze(proj: %s)", nomeProjeto);
						ProcessBuilder processBuilder = new ProcessBuilder();
						processBuilder.command("cmd", "/c", "und -db " + Config.REPOSITORY_DIR + nomeProjeto + "\\" + nomeProjeto +".udb", "analyze");
						Process panalisar = processBuilder.start();
						
						StringBuilder output = new StringBuilder();
						BufferedReader reader = new BufferedReader(new InputStreamReader(panalisar.getInputStream()));
						String line;
						while ((line = reader.readLine()) != null) {
							output.append(line + "\n");
						}
						int exitVal = panalisar.waitFor();
						if (exitVal == 0) {
							System.out.println("\nAnalyze: Success!");
						}
						else {
							System.out.println("\nAnalyze: Error!");
						}
						
						
						
						
						//metrica
						System.out.printf("\nMetrics(proj: %s)", nomeProjeto);
						Process pmetrics = Runtime.getRuntime().exec(Util.gerarComandoCalcularMetrics_VERSION_4(nomeProjeto));
						int wait2 = pmetrics.waitFor();
						if (wait2 == 0) {
							System.out.println("Metrics: Success!");
						}
						else {
							System.out.println("Metrics: Error!");
						}
						
						
						
						
						
						//Extrair dados e gerar arquivo
						System.out.printf("\nGerando Arquivo Saída(proj: %s)", nomeProjeto);
						BufferedReader br = null;
						BufferedWriter arquivo = null;
						
						try {
							br = new BufferedReader(new FileReader(Config.REPOSITORY_DIR + nomeProjeto+"\\"+nomeProjeto+".csv"));
							
							String linha = null;
							linha = br.readLine();
							
							arquivo = new BufferedWriter(new FileWriter(Config.REPORT_DIR + nomeProjeto+"\\report_bug_id_"+ b.getBug_id() +"_commit_id_"+ b.getCommit() +"_project_name_"+nomeProjeto+".csv"));
							arquivo.write(linha + "\n");
							
							while (linha != null) {
								linha = br.readLine();
								
								if( linha != null && linha.trim().length() > 0 ) {
									
									for(String nomeArquivo: b.getListaFiles()) {
										
										nomeArquivo = nomeArquivo.replaceAll("/", ";");
										
										String[] partes = nomeArquivo.split(";");
										nomeArquivo = partes[partes.length - 1];

										nomeArquivo = nomeArquivo.substring(0, nomeArquivo.length() - 1);
										
										if( linha.contains(nomeArquivo) ) {
											arquivo.write(linha + "\n");
										}
									}
									
								}
							}
							
							System.out.println("\nGerando Arquivo Saída: Success!");
						}
						catch(Exception e) {
							System.out.println(e);
						}
						finally {
							if( arquivo != null ) {
								arquivo.close();
							}
							if( br != null ) {
								br.close();
							}
						}
					}
				}
				
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	
	}

}
