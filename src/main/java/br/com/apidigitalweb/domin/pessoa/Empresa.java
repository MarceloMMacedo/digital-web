package br.com.apidigitalweb.domin.pessoa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;

import br.com.apidigitalweb.config.DriveQuickstart;

import com.google.cloud.storage.StorageOptions;

import lombok.Data;

@Data

public class Empresa implements Cloneable {

	private String name;
	private String descricao;
	private String cnpj;
	private String site;
	private String instagram;

	private String telefone;

	private String streinstagramet;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String numero;
	private String uf;
	private String ibge;
	private String gia;
	private String ddd;
	private String siafi;
	private String imagem;

	private String imagemview;
	private String email;
	private String extension;
	private String fantasia;

	// firebase
	@Override
	public Object clone() throws CloneNotSupportedException {
		Empresa empresa = (Empresa) super.clone();
		return empresa;
	}

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

	public static Empresa getEmpresa() {

		File file = null;

		try {
			file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "app.properties");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Empresa empresa = new Empresa();

		Properties properties = new Properties();

		// Setamos o arquivo que vai ser lido
		FileInputStream fis;

		try {
			fis = new FileInputStream(file);
			properties.load(fis);
			Class classefonte = Empresa.class;

			Field fldfonte = null;
			Field fldDestino = null;
			Field[] fs = classefonte.getDeclaredFields();
			for (Field f : fs) {
				String string = f.getName();
				fldfonte = classefonte.getDeclaredField(string);
				fldfonte.setAccessible(true);
				String value = properties.getProperty(string);
				fldfonte.set(empresa, value);

			}

		} catch (IOException e) {

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get avatar
		URL signedUrl = null;

		InputStream in = null;

		try {
			file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "credencial.json");
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {

		}

		Credentials credential = null;
		try {
			credential = GoogleCredentials.fromStream(in);
		} catch (IOException e) {

		}

		try {
			/*
			 * String credencial = file.getAbsolutePath();
			 * 
			 * String projectId = "digital-solucoes";
			 * 
			 * String bucketName = "digital-servicos.appspot.com"; Storage storage =
			 * StorageOptions.newBuilder().setCredentials(credential).setProjectId(projectId
			 * ).build() .getService(); signedUrl =
			 * storage.signUrl(BlobInfo.newBuilder(bucketName, empresa.getImagem()).build(),
			 * 1, TimeUnit.DAYS,
			 * SignUrlOption.signWith(ServiceAccountCredentials.fromStream(new
			 * FileInputStream(file)))); System.out.println(signedUrl.toString());
			 * 
			 * empresa.setImagemview(signedUrl.toString());
			 */
		} catch (Exception e) {
		}
		return empresa;

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

	public static void saveEmpresa(Empresa empresa) {
		File file = null;
		try {
			file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "app.properties");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Properties properties = new Properties();

		Class classefonte = Empresa.class;
		Field fldfonte = null;
		Field[] fs = classefonte.getDeclaredFields();

		for (Field f : fs) {
			String string = f.getName();
			try {
				fldfonte = classefonte.getDeclaredField(string);

				fldfonte.setAccessible(true);
				String value = (String) fldfonte.get(empresa);
				properties.setProperty(string, value);

			} catch (Exception e) {

			}
		}
		try {
			// Criamos um objeto FileOutputStream
			FileOutputStream fos = new FileOutputStream(file);
			// grava os dados no arquivo
			properties.store(fos, "FILE EMPRESA PROPERTIES:");
			// fecha o arquivo
			fos.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		System.out.println(empresa);
	}

}
