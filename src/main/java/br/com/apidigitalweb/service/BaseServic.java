package br.com.apidigitalweb.service;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
import br.com.apidigitalweb.domin.BaseEntity;
import br.com.apidigitalweb.dto.BaseDto;
import br.com.apidigitalweb.dto.SampleDto;
import br.com.apidigitalweb.enuns.EnumCategoriaProduto;
import br.com.apidigitalweb.enuns.TipoPatrimonioEnum;
import br.com.apidigitalweb.enuns.UnidadeProdutoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@NoArgsConstructor
public class BaseServic<T extends BaseEntity> implements Serializable, BaseServiceInterface<T> {

	private static final long serialVersionUID = 1L;

	T obj;

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

	@Autowired
	JpaRepository<T, Long> repo;

	@SuppressWarnings("unchecked")
	public Class<T> getClasse() {
		Class<T> classe = null;
		try {
			Class<T> class1 = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];

			classe = class1;
		} catch (Exception e) {
		}
		return classe;
	}

//	determinado pelo controller 
	public List<?> listany() {
		return null;

	}

	public List<String> getnomes() {
		List<String> getnomes = new ArrayList<>();
		List<T> lista = getAll();
		lista.stream().forEach((x) -> {
			getnomes.add(x.getNome());
		});
		return getnomes;
	}

	public List<T> getAll() {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		List<T> lista = repo.findAll();
		try {

			for (T t : lista) {
				if (t.getImagem() != null)
					t.setImagemView(downloadFile(t.getImagem() + "." + t.getExtension()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

	public boolean confirmNome(String nome) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		return false;
	}

	public T findbynome(String nome) {

		try {
			obj = getClasse().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setNome(nome);
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("nome", match -> match.contains().ignoreCase()) // ### This is (probably?) the bit that's
																				// wrong - none of these made any
																				// difference
				// .withMatcher("categories.id", match -> match.contains())
				// .withMatcher("categories.name", match -> match.contains().ignoreCase())
				// .withMatcher("categories", match -> match.contains())
				// ###
				// .withIgnoreNullValues() // ignore unset properties when finding
				.withIgnorePaths("id"); // ignore primitives as they default to 0

		Example<T> example = Example.of(obj, matcher);
		obj = repo.findOne(example).get();
		return obj;
	}

	public List<BaseDto> getAllsample() {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		return repo.findAll().stream().map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())))
				.collect(Collectors.toList());
	}

	public List<SampleDto> getSampleDto() {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		return repo.findAll().stream().map(x -> new SampleDto(x, downloadFile(x.getImagem() + "." + x.getExtension())))
				.collect(Collectors.toList());
	}

	public Page<T> findallpage(String find, Pageable page) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			obj = getClasse().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setNome(find);
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();

		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("nome", match -> match.contains().ignoreCase()) // ### This is (probably?) the bit that's
																				// wrong - none of these made any
																				// difference
				// .withMatcher("categories.id", match -> match.contains())
				// .withMatcher("categories.name", match -> match.contains().ignoreCase())
				// .withMatcher("categories", match -> match.contains())
				// ###
				// .withIgnoreNullValues() // ignore unset properties when finding
				.withIgnorePaths("id"); // ignore primitives as they default to 0

		Example<T> example = Example.of(obj, matcher);
		Page<T> p = repo.findAll(example, page);

		return p;
	}

	public Page<BaseDto> findallpagedto(String find, Pageable page) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			obj = getClasse().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setNome(find);
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();

		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("nome", match -> match.contains().ignoreCase()).withIgnoreNullValues() // ignore unset
																									// properties when
																									// finding
				.withIgnorePaths("id"); // ignore primitives as they default to 0

		Example<T> example = Example.of(obj, matcher);
		Page<T> p = repo.findAll(example, page);
		Page<BaseDto> baseDtos = repo.findAll(example, page)
				.map(x -> new BaseDto(x, downloadFile(x.getImagem() + "." + x.getExtension())));
		return baseDtos;
	}

	public T fingbyid(Long id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			obj = repo.findById(id).get();
			obj.setImagemView(downloadFile(obj.getImagem() + "." + obj.getExtension()));
			return obj;
		} catch (Exception e) {
			// TODO: handle exception
		}
		obj=repo.findById(id).get();
		posfingbyid(obj);
		
		return obj;
	}
	
	public void posfingbyid(T obj) {
		
	}

	public List<T> fingbynome(String find) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		try {
			obj = getClasse().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setNome(find);
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();

		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("nome", match -> match.contains().ignoreCase())
				// ### This is (probably?) the bit that's wrong - none of these made any
				// difference
				// .withMatcher("categories.id", match -> match.contains())
				// .withMatcher("categories.name", match -> match.contains().ignoreCase())
				// .withMatcher("categories", match -> match.contains())
				// ###
				.withIgnoreNullValues() // ignore unset properties when finding
				.withIgnorePaths("id"); // ignore primitives as they default to 0

		Example<T> example = Example.of(obj, matcher);
		List<T> p = repo.findAll(example);

		return p;
	}

	public T newobj(T obj) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		prenew(obj);
		ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();

		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withMatcher("nome", match -> match.contains().ignoreCase())
				// ### This is (probably?) the bit that's wrong - none of these made any
				// difference
				// .withMatcher("categories.id", match -> match.contains())
				// .withMatcher("categories.name", match -> match.contains().ignoreCase())
				// .withMatcher("categories", match -> match.contains())
				// ###
				.withIgnoreNullValues() // ignore unset properties when finding
				.withIgnorePaths("id"); // ignore primitives as they default to 0

		Example<T> example = Example.of(obj, matcher);
		List<T> p = repo.findAll(example);
		if (p.size() > 0) {
			throw new AuthorizationException("Já existe um cadastro para o nome:" + obj.getNome());
		}

		obj = repo.save(obj);
		posNewObj( obj);
		return obj;
	}

	public void posNewObj(T obj) {

	}

	public void preSaveObj(T obj) {

	}

	public T saveobj(Long id, T obj) {
		this.obj = repo.findById(id).get();
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		preSaveObj(obj);
		obj.setId(id);
		// First find out the object to be updated from the database
		// Use the non-null value of the update object to overwrite the object to be
		// updated
		BeanUtils.copyProperties(obj, this.obj, getNullPropertyNames(obj)); // Perform update operation

		obj = repo.save(this.obj);
		return obj;
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

	public void prenew(T obj) {

	}

	public String uploadFile(MultipartFile file, Long id) {

		obj = repo.findById(id).get();
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
		repo.save(obj);

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

				signedUrl = storage.signUrl(BlobInfo.newBuilder(bucketName, fileName).build(), 360, TimeUnit.DAYS,
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

	public List<String> listaCategoriaProduto() {
		List<String> listaCategoriaProduto = new ArrayList<>();
		for (EnumCategoriaProduto string : EnumCategoriaProduto.values()) {
			listaCategoriaProduto.add(string.getDescricao());
		}
		return listaCategoriaProduto;
	}

	public List<String> listaUnidade() {
		List<String> listaUnidade = new ArrayList<>();
		for (UnidadeProdutoEnum string : UnidadeProdutoEnum.values()) {
			listaUnidade.add(string.getDescricao());
		}
		return listaUnidade;
	}

	public List<String> tipopatriomonio() {
		List<String> listaUnidade = new ArrayList<>();
		for (TipoPatrimonioEnum string : TipoPatrimonioEnum.values()) {
			listaUnidade.add(string.getDescricao());
		}
		return listaUnidade;
	}

	public void setImagem(BaseEntity obj) {

	}

	public static void copyProperties(Object src, Object trg, Iterable<String> props) {

		BeanWrapper srcWrap = PropertyAccessorFactory.forBeanPropertyAccess(src);
		BeanWrapper trgWrap = PropertyAccessorFactory.forBeanPropertyAccess(trg);

		props.forEach(p -> trgWrap.setPropertyValue(p, srcWrap.getPropertyValue(p)));

	}

	public static void copyProperties2(Object src, Object trg, Set<String> props) {
		String[] excludedProperties = Arrays.stream(BeanUtils.getPropertyDescriptors(src.getClass()))
				.map(PropertyDescriptor::getName).filter(name -> !props.contains(name)).toArray(String[]::new);

		BeanUtils.copyProperties(src, trg, excludedProperties);
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
