package br.com.apidigitalweb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageOptions;

import br.com.apidigitalweb.config.DriveQuickstart;
import br.com.apidigitalweb.config.exception.AuthorizationException;
import br.com.apidigitalweb.config.exception.FileException;
import br.com.apidigitalweb.config.security.UserSS;
import br.com.apidigitalweb.config.services.UserService;
import br.com.apidigitalweb.domin.pessoa.Empresa;
import br.com.apidigitalweb.domin.pessoa.Endereco;
import br.com.apidigitalweb.dto.pessoa.BasePessoaJuridicaDTO;
import br.com.apidigitalweb.openfaing.ReceitaWsFeignPessoaJuridica;
import br.com.apidigitalweb.openfaing.ViaCEPClient;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2

public class EmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private Empresa empresa;
	@Autowired
	ReceitaWsFeignPessoaJuridica feignPessoaJuridica;

	@Autowired
	private ViaCEPClient viaCEPClient;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	DriveQuickstart googleDriveManager;

	// firebase
	@Value("${spring.cloud.gcp.credentials.location}")
	private String credencial;

	@Value("${spring.cloud.gcp.config_projectId}")
	private String projectId;

	@Value("${spring.cloud.gcp.bucketName}")
	private String bucketName;

	public Empresa getEmpresa() {
		Empresa obj = Empresa.getEmpresa();
		try {
			System.err.println(obj.getImagem() + "." + obj.getExtension());

			obj.setImagemview(downloadFile(obj.getImagem() + "." + obj.getExtension()));
			System.out.println(obj.getImagemview());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return obj;
	}

	public Empresa newemtras(String cnpj) {
		cnpj = cnpj.replaceAll("\\p{Punct}", "");
		Empresa obj = new Empresa();
		try {
			obj = (Empresa) Empresa.getEmpresa().clone();
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BasePessoaJuridicaDTO t = null;
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			t = feignPessoaJuridica.buscaPorcnpj(cnpj);
			obj.setBairro(t.getBairro());
			obj.setCep(t.getCep());
			obj.setCnpj(t.getCnpj());
			obj.setComplemento(t.getComplemento());

			obj.setDescricao(Empresa.getEmpresa().getDescricao());
			obj.setEmail(Empresa.getEmpresa().getEmail());
			obj.setGia(Empresa.getEmpresa().getGia());
			obj.setIbge(Empresa.getEmpresa().getIbge());
			obj.setImagem(Empresa.getEmpresa().getImagem());
			obj.setInstagram(Empresa.getEmpresa().getInstagram());
			obj.setLocalidade(t.getMunicipio());
			obj.setLogradouro(t.getLogradouro());
			obj.setName(t.getNome());
			obj.setNumero(t.getNumero());
			obj.setSiafi(Empresa.getEmpresa().getSiafi());
			obj.setSite(Empresa.getEmpresa().getSite());
			obj.setImagemview(downloadFile(obj.getImagem() + "." + obj.getExtension()));
			obj.setTelefone(Empresa.getEmpresa().getTelefone());
			obj.setUf(t.getUf());
			obj.setExtension(Empresa.getEmpresa().getExtension());
			obj.setFantasia(t.getFantasia());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return obj;
	}

	public Empresa loadCnpj(String cnpj) {
		cnpj = cnpj.replaceAll("\\p{Punct}", "");
		Empresa obj = Empresa.getEmpresa();
		BasePessoaJuridicaDTO t = null;
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			t = feignPessoaJuridica.buscaPorcnpj(cnpj);
			obj.setBairro(t.getBairro());
			obj.setCep(t.getCep());
			obj.setCnpj(t.getCnpj());
			obj.setComplemento(t.getComplemento());

			obj.setDescricao(Empresa.getEmpresa().getDescricao());
			obj.setEmail(Empresa.getEmpresa().getEmail());
			obj.setGia(Empresa.getEmpresa().getGia());
			obj.setIbge(Empresa.getEmpresa().getIbge());
			obj.setImagem(Empresa.getEmpresa().getImagem());
			obj.setInstagram(Empresa.getEmpresa().getInstagram());
			obj.setLocalidade(t.getMunicipio());
			obj.setLogradouro(t.getLogradouro());
			obj.setName(t.getNome());
			obj.setNumero(t.getNumero());
			obj.setSiafi(Empresa.getEmpresa().getSiafi());
			obj.setSite(Empresa.getEmpresa().getSite());
			obj.setImagemview(downloadFile(obj.getImagem() + "." + obj.getExtension()));
			obj.setTelefone(Empresa.getEmpresa().getTelefone());
			obj.setUf(t.getUf());
			obj.setExtension(Empresa.getEmpresa().getExtension());
			obj.setFantasia(t.getFantasia());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return obj;
	}

	public Empresa update(Empresa obj) {
		try {
			Empresa.saveEmpresa(obj);
			log.info("atualização  da " + Empresa.class.getSimpleName() + " realizado com sucesso");
		} catch (Exception e) {
			log.error("erro de inserção da " + Empresa.class.getSimpleName() + ", descrição:" + e.getMessage());
		}
		return Empresa.getEmpresa();
	}

	public Endereco getendereco(String cep) {
		cep= cep.replaceAll("\\p{Punct}", "");
		Endereco e = viaCEPClient.buscaEnderecoPor(cep);
		return e;
	}

	// FileManager.java
	public String uploadFile(MultipartFile file, String fileName) throws GeneralSecurityException, IOException {

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Drive service = new Drive.Builder(HTTP_TRANSPORT, DriveQuickstart.JSON_FACTORY,
				DriveQuickstart.getCredentials(HTTP_TRANSPORT)).setApplicationName(DriveQuickstart.APPLICATION_NAME)
						.build();

		// Print the names and IDs for up to 10 files.
		FileList result = service.files().list().setPageSize(10).setFields("nextPageToken, files(id, name)").execute();
		List<com.google.api.services.drive.model.File> files = result.getFiles();
		if (files == null || files.isEmpty()) {
			System.out.println("No files found.");
		} else {
			System.out.println("Files:");
			for (com.google.api.services.drive.model.File file2 : files) {
				System.out.printf("%s (%s)\n", file.getName(), file2.getId());
			}
		}
		return null;
	}

	public String uploadFile(MultipartFile file) {

		Empresa obj = Empresa.getEmpresa();
		String s = obj.getImagem();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = s;

		String t = fileName;
		File file1 = null;
		try {
			file1 = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "credencial.json");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (fileName.equals("") || fileName == null) {
				fileName = file.getOriginalFilename(); // to get original file name
				fileName = UUID.randomUUID().toString(); // to generated random
				obj.setImagem(fileName);
				obj.setExtension(extension);
				fileName += "." + extension;
			} else {
				deleteimage(fileName + "." + obj.getExtension());
				obj.setExtension(extension);
				fileName += "." + extension;

			}
		} catch (Exception e) {
			fileName = file.getOriginalFilename(); // to get original file name
			fileName = UUID.randomUUID().toString(); // to generated random
			obj.setImagem(fileName);
			obj.setExtension(extension);
			fileName += "." + extension;
		}

		try {

			BlobId blobId = BlobId.of(bucketName, fileName);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
			Credentials credentials = null;

			credentials = GoogleCredentials.fromStream(new FileInputStream(file1));

			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

			/*
			 * t = storage.get(BlobId.of(bucketName, s)).getName(); System.out.println(s);
			 * System.out.println(t); if (s.equals(t)) {
			 * 
			 * deleteimage(t); fileName = t; } else obj.setImagem(fileName);
			 */
		} catch (Exception e) {
			obj.setImagem(fileName);
		}
		update(obj);

		return upload(file, fileName);

	}

	private String uploadFile(File file, String fileName, String media) throws IOException {

		File file1 = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "credencial.json");

		String content = new String(Files.readAllBytes(file1.toPath()));
		System.out.println(credencial);

		BlobId blobId = BlobId.of(bucketName, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(media).build();
		Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(file1));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		storage.create(blobInfo, Files.readAllBytes(file.toPath()));
		System.out.println(storage.get(BlobId.of(bucketName, fileName)));
		return downloadFile(fileName);// String.format("https://firebasestorage.googleapis.com/v0/b/digital-servicos.appspot.como/%s?alt=media",
										// URLEncoder.encode(fileName, StandardCharsets.UTF_8));
	}

	private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		}
		return tempFile;
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public String downloadFile(String fileName) {
		if (!fileName.equals("null.null")) {
			Resource res = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "/tmp");
			File ins = null;
			try {
				ins = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "credencial.json");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// ="cliente5ef7668b24f13062db255a24.png";

			URL signedUrl = null;

			InputStream in = null;
			try {
				in = new FileInputStream(ins);
			} catch (FileNotFoundException e) {
				return "";
			}

			Credentials credential = null;
			try {
				credential = GoogleCredentials.fromStream(in);
			} catch (IOException e) {
				return "";
			}

			Storage storage = StorageOptions.newBuilder().setCredentials(credential).setProjectId(projectId).build()
					.getService();

			try {

				signedUrl = storage.signUrl(BlobInfo.newBuilder(bucketName, fileName).build(), 1, TimeUnit.DAYS,
						SignUrlOption.signWith(ServiceAccountCredentials.fromStream(new FileInputStream(ins))));
			} catch (Exception e) {
				return "";
			}

			return signedUrl.toString();
		} else
			return null;
	}

	public void deleteimage(String filename) {
		File file1 = null;
		try {
			file1 = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "credencial.json");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BlobId blobId = BlobId.of(bucketName, filename);
		Credentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(file1));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		try {

			storage.delete(bucketName, filename);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String upload(MultipartFile multipartFile, String fileName) {
		Resource res = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "/tmp");
		File file = null;
		String TEMP_URL = "";
		try {
			String media = multipartFile.getContentType(); // values for file name.

			file = this.convertToFile(multipartFile, fileName); // to convert multipartFile to File
			TEMP_URL = this.uploadFile(file, fileName, media); // to get uploaded file link
			file.delete(); // to delete the copy of uploaded file stored in the project folder
		} catch (Exception e) {
			try {
				file.delete();
			} catch (Exception e1) {
			}
			throw new FileException("Erro gravar arquivo: " + e.getMessage());
			// return sendResponse("500", e, "Unsuccessfully Uploaded!");
		}
		return TEMP_URL;
	}

}
/*
 * public String uploadFile(MultipartFile file, String fileName) throws
 * GeneralSecurityException, IOException {
 * 
 * final NetHttpTransport HTTP_TRANSPORT =
 * GoogleNetHttpTransport.newTrustedTransport(); Drive service = new
 * Drive.Builder(HTTP_TRANSPORT, DriveQuickstart.JSON_FACTORY,
 * DriveQuickstart.getCredentials(HTTP_TRANSPORT)).setApplicationName(
 * DriveQuickstart.APPLICATION_NAME) .build();
 * 
 * // Print the names and IDs for up to 10 files. FileList result =
 * service.files().list().setPageSize(10).
 * setFields("nextPageToken, files(id, name)").execute();
 * List<com.google.api.services.drive.model.File> files = result.getFiles(); if
 * (files == null || files.isEmpty()) { System.out.println("No files found."); }
 * else { System.out.println("Files:"); for
 * (com.google.api.services.drive.model.File file2 : files) {
 * System.out.printf("%s (%s)\n", file.getName(), file2.getId()); } } return
 * null; }
 * 
 * public void uploadFile(MultipartFile file ) { String fileName = null; Empresa
 * obj = Empresa.getEmpresa(); String t = fileName; String s = obj.getImagem();
 * File file1 = null; try { file1 =
 * ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX +
 * "credencial.json"); } catch (FileNotFoundException e1) { // TODO
 * Auto-generated catch block e1.printStackTrace(); }
 * 
 * try { fileName = file.getOriginalFilename(); // to get original file name
 * fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
 * // to generated random string BlobId blobId = BlobId.of(bucketName, s);
 * BlobInfo blobInfo =
 * BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
 * Credentials credentials = null;
 * 
 * credentials = GoogleCredentials.fromStream(new FileInputStream(file1));
 * 
 * Storage storage =
 * StorageOptions.newBuilder().setCredentials(credentials).build().getService();
 * 
 * t = storage.get(BlobId.of(bucketName, s)).getName(); System.out.println(s);
 * System.out.println(t); if (s.equals(t)) {
 * 
 * deleteimage(t); fileName = t; } else obj.setImagem(fileName); } catch
 * (Exception e) { obj.setImagem(fileName); } Empresa.saveEmpresa(obj);
 * 
 * upload(file, fileName);
 * 
 * }
 * 
 * private String uploadFile(File file, String fileName, String media) throws
 * IOException {
 * 
 * File file1 = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX +
 * "credencial.json");
 * 
 * System.out.println(credencial);
 * 
 * BlobId blobId = BlobId.of(bucketName, fileName); BlobInfo blobInfo =
 * BlobInfo.newBuilder(blobId).setContentType(media).build(); Credentials
 * credentials = GoogleCredentials.fromStream(new FileInputStream(file1));
 * Storage storage =
 * StorageOptions.newBuilder().setCredentials(credentials).build().getService();
 * storage.create(blobInfo, Files.readAllBytes(file.toPath()));
 * System.out.println(storage.get(BlobId.of(bucketName, fileName))); return
 * downloadFile(fileName);// String.format(
 * "https://firebasestorage.googleapis.com/v0/b/digital-servicos.appspot.como/%s?alt=media",
 * // URLEncoder.encode(fileName, StandardCharsets.UTF_8)); }
 * 
 * private File convertToFile(MultipartFile multipartFile, String fileName)
 * throws IOException { File tempFile = new File(fileName); try
 * (FileOutputStream fos = new FileOutputStream(tempFile)) {
 * fos.write(multipartFile.getBytes()); fos.close(); } return tempFile; }
 * 
 * private String getExtension(String fileName) { return
 * fileName.substring(fileName.lastIndexOf(".")); }
 * 
 * public String downloadFile(String fileName) {
 * 
 * Resource res = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX
 * + "/tmp"); File ins = null; try { ins =
 * ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX +
 * "credencial.json"); } catch (FileNotFoundException e1) { // TODO
 * Auto-generated catch block e1.printStackTrace(); } URL signedUrl = null;
 * InputStream in = null; try { in = new FileInputStream(ins); } catch
 * (FileNotFoundException e) { return ""; }
 * 
 * Credentials credential = null; try { credential =
 * GoogleCredentials.fromStream(in); } catch (IOException e) { return ""; }
 * 
 * Storage storage =
 * StorageOptions.newBuilder().setCredentials(credential).setProjectId(projectId
 * ).build() .getService();
 * 
 * try {
 * 
 * signedUrl = storage.signUrl(BlobInfo.newBuilder(bucketName,
 * fileName).build(), 1, TimeUnit.DAYS,
 * SignUrlOption.signWith(ServiceAccountCredentials.fromStream(new
 * FileInputStream(ins)))); } catch (Exception e) { return ""; }
 * 
 * return signedUrl.toString(); }
 * 
 * public void deleteimage(String filename) { File file1 = null; try { file1 =
 * ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX +
 * "credencial.json"); } catch (FileNotFoundException e1) {
 * 
 * }
 * 
 * BlobId blobId = BlobId.of(bucketName, filename); Credentials credentials =
 * null; try { credentials = GoogleCredentials.fromStream(new
 * FileInputStream(file1)); } catch (IOException e1) {
 * 
 * } Storage storage =
 * StorageOptions.newBuilder().setCredentials(credentials).build().getService();
 * try { storage.delete(bucketName, filename); } catch (Exception e) { // TODO:
 * handle exception } }
 * 
 * public void upload(MultipartFile multipartFile, String fileName) { Resource
 * res = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX +
 * "/tmp"); File file = null; try { String media =
 * multipartFile.getContentType(); // values for file name.
 * 
 * file = this.convertToFile(multipartFile, fileName); // to convert
 * multipartFile to File String TEMP_URL = this.uploadFile(file, fileName,
 * media); // to get uploaded file link file.delete(); // to delete the copy of
 * uploaded file stored in the project folder } catch (Exception e) { try {
 * file.delete(); } catch (Exception e1) { } throw new
 * FileException("Erro gravar arquivo: " + e.getMessage()); // return
 * sendResponse("500", e, "Unsuccessfully Uploaded!"); }
 * 
 * }
 */
