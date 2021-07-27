package br.com.apidigitalweb.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.apidigitalweb.domin.BaseEntity;
import net.bytebuddy.implementation.bind.annotation.Super;

public class CopyObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String stringTipo;
	private double doubleTipo;
	private int intTipo;
	private Date dataTipo;

	private BaseEntity baseEntity;
	private BaseEntity formulario;
	private BaseEntity banco;
	private Class classe;

	public CopyObject() {
	}

	public static BaseEntity copy(BaseEntity banco, BaseEntity newOBJ, Class classebanco, Class classeNewDto) throws NoSuchFieldException {

		Field listaCampo[] = classeNewDto.getDeclaredFields();
		Field listaCampoBanco[] = classebanco.getDeclaredFields();

		for (int i = 0; i < listaCampo.length; i++) {
			Field fld = listaCampo[i];
			fld.setAccessible(true);
			Field flsBanco = null;
			try {
				System.out.println(fld.getName());
                 for (Field field : listaCampoBanco) {

     				System.out.println(fld.getName() + "-" + field.getName()); 
					if(field.getName().equals(fld.getName())) {
						flsBanco=field;
					}
				}
				//flsBanco = classebanco.getDeclaredField(fld.getName());
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (flsBanco != null) {
				flsBanco.setAccessible(true);
				Object valor = null;
				try {
					valor = fld.get(newOBJ);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String id = fld.getName();// .getSimpleName();
				String tipoPrimario = fld.getType().getSimpleName();
				if (valor != null && !isBaseentity(fld, banco, newOBJ) & !id.equals("id")) {
					switch (tipoPrimario) {
					case "double":
						if ((double) valor != 0.0)
							try {
								flsBanco.setDouble(banco, (double) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;

					case "Integer":
						if ((Integer) valor != 0)
							try {
								flsBanco.setInt(banco, (Integer) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "int":
						if ((int) valor != 0)
							try {
								flsBanco.setInt(banco, (int) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Double":
						if ((double) valor != 0.0)
							try {
								double v = (double) valor;
								flsBanco.setDouble(banco, v);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Date":
						try {
							flsBanco.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "String":
						try {
							flsBanco.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;

					default:
						try {
							flsBanco.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}

			}
		}

		 
		Field listaCampoBancoSuper[] = classebanco.getSuperclass().getDeclaredFields();

		for (int i = 0; i < listaCampo.length; i++) {
			Field fld = listaCampo[i];
			fld.setAccessible(true);
			Field flsBanco = null; 
			
			 try {
			//	System.out.println(fld.getName());
               /* for (Field field : listaCampoBancoSuper) {
      				System.out.println(fld.getName() + "-" + field.getName()); 
					if(field.getName().equals(fld.getName())) {
						flsBanco=field;
					}
				}
				 */
				 flsBanco=findField(listaCampoBancoSuper, fld.getName());
			//	 flsBanco = classebanco.getField(fld.getName());
				 System.out.println(fld.getName() + "-" + flsBanco.getName()); 
			} catch (SecurityException e1) {
				 System.out.println(e1.getMessage());
			} 
			if (flsBanco != null) {
				flsBanco.setAccessible(true);
				Object valor = null;
				try {
					valor = fld.get(newOBJ);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String id = fld.getName();// .getSimpleName();
				String tipoPrimario = fld.getType().getSimpleName();
				if (valor != null && !isBaseentity(fld, banco, newOBJ) & !id.equals("id")) {
					switch (tipoPrimario) {
					case "double":
						if ((double) valor != 0.0)
							try {
								flsBanco.setDouble(banco, (double) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;

					case "Integer":
						if ((Integer) valor != 0)
							try {
								flsBanco.setInt(banco, (Integer) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "int":
						if ((int) valor != 0)
							try {
								flsBanco.setInt(banco, (int) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Double":
						if ((double) valor != 0.0)
							try {
								double v = (double) valor;
								flsBanco.setDouble(banco, v);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Date":
						try {
							flsBanco.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "String":
						try {
							flsBanco.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;

					default:
						try {
							flsBanco.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							 System.out.println(e.getMessage());
						}
						break;
					}
				}

			}
		}
		return newOBJ;
	}
private static Field findField(Field listaCampoBancoSuper[] ,String comp) {
	 
	 for (Field field : listaCampoBancoSuper) {
			System.out.println(comp + "-" + field.getName()); 
			if(field.getName().equals(comp)) {
				return field;
			}
		}
	 return null;
}
	public static BaseEntity complemenForm(BaseEntity banco1, BaseEntity formulario1, Class classe) {
		BaseEntity formulario = formulario1;
		if (banco1 == null)
			try {
				banco1 = (BaseEntity) classe.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		BaseEntity banco = banco1;

		String tipoPrimario;
		Object valor = null;
		List<Field> camposAtualizaveis = new LinkedList<>();
		Field listaCampo[] = classe.getDeclaredFields();

		for (int i = 0; i < listaCampo.length; i++) {
			Field fld = listaCampo[i];
			fld.setAccessible(true);
			try {
				valor = fld.get(formulario);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String id = fld.getName();// .getSimpleName();
			if (valor != null) {
				if (!isBaseentity(fld, banco, formulario) & !id.equals("id")) {
					tipoPrimario = fld.getType().getSimpleName();
					switch (tipoPrimario) {
					case "double":
						if ((double) valor != 0.0)
							try {
								fld.setDouble(banco, (double) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;

					case "Integer":
						if ((Integer) valor != 0)
							try {
								fld.setInt(banco, (Integer) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "int":
						if ((int) valor != 0)
							try {
								fld.setInt(banco, (int) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Double":
						if ((double) valor != 0.0)
							try {
								double v = (double) valor;
								fld.setDouble(banco, v);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Date":
						try {
							fld.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "String":
						try {
							fld.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;

					default:
						break;
					}
				}
			}
		}
		Field listaCampo1[] = classe.getSuperclass().getDeclaredFields();

		for (int i = 0; i < listaCampo1.length; i++) {
			Field fld = listaCampo1[i];
			fld.setAccessible(true);
			try {
				valor = fld.get(formulario);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String id = fld.getName();// .getSimpleName();
			if (valor != null) {
				if (!isBaseentity(fld, banco, formulario) & !id.equals("id")) {
					tipoPrimario = fld.getType().getSimpleName();
					switch (tipoPrimario) {
					case "double":
						if ((double) valor != 0.0)
							try {
								fld.setDouble(banco, (double) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;

					case "Integer":
						if ((Integer) valor != 0)
							try {
								fld.setInt(banco, (Integer) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "int":
						if ((int) valor != 0)
							try {
								fld.setInt(banco, (int) valor);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Double":
						if ((double) valor != 0.0)
							try {
								double v = (double) valor;
								fld.setDouble(banco, v);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					case "Date":
						try {
							fld.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "String":
						try {
							fld.set(banco, valor);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;

					default:
						break;
					}
				}
			}
		}

		return banco;

		// System.out.println("Interfaces = " + Arrays.asList(i));
	}

	public static boolean isBaseentity(Field fld, BaseEntity banco, BaseEntity formulario) {
		Class[] i = fld.getType().getInterfaces();
		BaseEntity baseEntityForm;
		// System.out.println(fld.getType().getSimpleName());// +"-"+"Interfaces = " +
		// Arrays.asList(i));

		// System.out.println( Arrays.asList(i) );

		for (int j = 0; j < i.length; j++) {
			Class class1 = i[j];

			if (class1.getSimpleName().equals("BaseEntity")) {
				System.out.println(fld.getType());
				fld.setAccessible(true);
				try {
					Object valor = fld.get(formulario);
					if (valor != null) {
						baseEntityForm = (BaseEntity) fld.get(formulario);
						if (baseEntityForm.getId() > 0) {
							// complemenForm(baseEntityForm, baseEntityForm, fld.getType());
							fld.set(banco, valor);
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}

		}
		return false;
	}

}
