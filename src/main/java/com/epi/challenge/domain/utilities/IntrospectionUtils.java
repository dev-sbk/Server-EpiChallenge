package com.epi.challenge.domain.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.persistence.EmbeddedId;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author Saber Ben Khalifa
 */
public final class IntrospectionUtils {

	public static final IntrospectionUtils SHARED_INSTANCE;

	static {
		SHARED_INSTANCE = new IntrospectionUtils();
		SHARED_INSTANCE.setFetchLazyAccess(true);
	}

	private static final Logger logger = Logger.getLogger(IntrospectionUtils.class.getName());
	private static final String CLASS_FIELD = "class";

	private Map<String, Object> processedObjects;

	@SuppressWarnings("rawtypes")
	private final Map<Class, Map<String, ClassFieldMetadata>> classPublicFields;
	private final Map<Class<?>, Class<?>> classAliases;

	private Pattern regexExclude;
	private Map<Class<?>, Map<Object, Long>> pseudoIds;
	private Map<Class<?>, String> entityClasses;

	private boolean fetchLazyAccess;
	@SuppressWarnings("unused")
	private EntityManager entityManager;

	private final Set<Class<? extends Annotation>> entityAnnotations;
	private final Set<Class<? extends Annotation>> idAnnotations;

	private final Map<Class<?>, ClassFieldMetadata> classIds;

	public IntrospectionUtils() {
		processedObjects = new HashMap<>();
		classPublicFields = new HashMap<>();
		classAliases = new HashMap<>();

		entityAnnotations = new HashSet<>();
		idAnnotations = new HashSet<>();

		entityAnnotations.add(Entity.class);
		idAnnotations.add(Id.class);
		idAnnotations.add(EmbeddedId.class);

		classIds = new HashMap<Class<?>, ClassFieldMetadata>();

		pseudoIds = new HashMap<Class<?>, Map<Object, Long>>();
	}

	public void setFetchLazyAccess(boolean fetchLazyAccess) {
		this.fetchLazyAccess = fetchLazyAccess;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public <E> E newInstance(Class<E> c, Object... kvs) throws ClassIntrospectionUtilsException {
		try {
			E instance = c.newInstance();
			Map<String, ClassFieldMetadata> fields = classPublicFields.get(c);
			if (fields == null) {
				fields = scanClass(c);
				classPublicFields.put(c, fields);
			}

			for (int i = 0; i < kvs.length;) {
				String key = (String) kvs[i++];
				Object value = kvs[i++];

				ClassFieldMetadata field = fields.get(key);
				field.getSetter().invoke(instance, value);
			}

			return instance;

		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException
				| InvocationTargetException ex) {
			throw new ClassIntrospectionUtilsException(ex);
		}
	}

	public <E> E setFields(E e, Object... kvs) throws ClassIntrospectionUtilsException {
		try {
			@SuppressWarnings("unchecked")
			Class<E> c = (Class<E>) e.getClass();
			Map<String, ClassFieldMetadata> fields = classPublicFields.get(c);
			if (fields == null) {
				fields = scanClass(c);
				classPublicFields.put(c, fields);
			}

			for (int i = 0; i < kvs.length;) {
				String key = (String) kvs[i++];
				Object value = kvs[i++];

				ClassFieldMetadata field = fields.get(key);
				field.getSetter().invoke(e, value);
			}

			return e;

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new ClassIntrospectionUtilsException(ex);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List cloneObjects(Collection objects, int maxDepth, String regexExclude) {
		processedObjects.clear();
		List cloned = new ArrayList();
		if (!StringUtils.isEmpty(regexExclude)) {
			this.regexExclude = Pattern.compile(regexExclude);
		}

		for (Object obj : objects) {
			try {
				cloned.add(cloneObjectImpl(obj, maxDepth));
			} catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
				throw new RuntimeException(ex);
			}
		}

		return cloned;
	}

	public void addEntityAnnotation(Class<? extends Annotation> c) {
		entityAnnotations.add(c);
	}

	public void addIdAnnotation(Class<? extends Annotation> c) {
		idAnnotations.add(c);
	}

	/**
	 * Ajoute des classes comme des entités.
	 *
	 * Cette méthode doit être utilisée dans de très rares cas (e.g. pour des
	 * classes générées automatiquement).
	 *
	 * Utiliser de préférence ${@link ExcelEntity}
	 *
	 * @param classes
	 * @deprecated
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public void addClassesAsEntities(Class... classes) {
		if (classes == null || classes.length == 0) {
			return;
		}

		if (entityClasses == null) {
			entityClasses = new HashMap<>();
			pseudoIds = new HashMap<>();
		}

		for (Class c : classes) {
			entityClasses.put(c, null);
		}
	}

	/**
	 *
	 * @param c
	 * @param id
	 * @deprecated
	 * @see EntityUtils#addClassesAsEntities(java.lang.Class[])
	 */
	@SuppressWarnings("rawtypes")
	@Deprecated
	public void addClassAsEntity(Class c, String id) {
		if (entityClasses == null) {
			entityClasses = new HashMap<>();
			pseudoIds = new HashMap<>();
		}

		entityClasses.put(c, id);
	}

	public Object cloneObject(Object o, int maxDepth, String regexExclude) {
		processedObjects.clear();
		if (!StringUtils.isEmpty(regexExclude)) {
			this.regexExclude = Pattern.compile(regexExclude);
		}

		try {
			return cloneObjectImpl(o, maxDepth);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings("rawtypes")
	public Class getEntityClass(Class c) {
		Class entityClass = classAliases.get(c);
		if (entityClass == null && !classAliases.containsKey(c)) {
			Map<String, ClassFieldMetadata> fields = scanClass(c);
			entityClass = classAliases.get(c);
			classPublicFields.put(entityClass, fields);
		}

		return entityClass;
	}

	/**
	 *
	 * @param c
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, ClassFieldMetadata> getFieldsMetadata(Class c) {
		Class entityClass = classAliases.get(c);
		Map<String, ClassFieldMetadata> fields;
		if (entityClass == null && !classAliases.containsKey(c)) {
			// missing data. Tested in non synchronized mode
			synchronized (classAliases) {
				// redo the test again in synchronized mode to avoid double
				// work.
				entityClass = classAliases.get(c);
				if (entityClass == null && !classAliases.containsKey(c)) {
					fields = scanClass(c);
					entityClass = classAliases.get(c);
					classPublicFields.put(entityClass, fields);
				} else {
					fields = classPublicFields.get(entityClass);
				}
			}
		} else {
			fields = classPublicFields.get(entityClass);
		}

		return fields;
	}

	/**
	 * Retrieve the id field associated to the entity
	 *
	 * @param c
	 *            The class entity
	 * @return Returns the ClassFieldMetadata object or null if no id is
	 *         available
	 */
	public ClassFieldMetadata getFieldId(Class<?> c) {
		ClassFieldMetadata fieldId = classIds.get(c);
		if (fieldId == null && !classIds.containsKey(c)) {
			synchronized (classIds) {
				fieldId = classIds.get(c);
				if (fieldId == null && !classIds.containsKey(c)) {
					Map<String, ClassFieldMetadata> fieldsMetadata = getFieldsMetadata(c);
					for (Entry<String, ClassFieldMetadata> entry : fieldsMetadata.entrySet()) {
						ClassFieldMetadata fieldMetadata = entry.getValue();
						if (fieldMetadata.isIdField()) {
							fieldId = fieldMetadata;
							break;
						}
					}

					classIds.put(c, fieldId);
				}
			}
		}

		return fieldId;
	}

	@SuppressWarnings({ "rawtypes" })
	private Object cloneObjectImpl(Object o, int maxDepth)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		if (!isProcessed(o, false)) {
			Class c = classAliases.get(o.getClass());
			Object target = getObject(o, true, c);
			assert target != null;

			Map<String, ClassFieldMetadata> fields = classPublicFields.get(c);
			for (Entry<String, ClassFieldMetadata> entry : fields.entrySet()) {
				String key = entry.getKey();
				if (key == null) {
					continue;
				}

				if (regexExclude != null && regexExclude.matcher(key).matches()) {
					continue;
				}

				ClassFieldMetadata fieldMetadata = entry.getValue();
				Method setter = fieldMetadata.getSetter();

				Object value = fieldMetadata.getValue(o);
				if (value == null) {
					continue;
				}

				EntityRelationship relation = fieldMetadata.getRelation();
				if (relation != null) {
					switch (relation) {
					case oneToOne:
					case manyToOne:
						if (maxDepth > 0) {
							setter.invoke(target, cloneObjectImpl(value, maxDepth - 1));
						}

						break;

					case oneToMany:
						if (maxDepth > 0) {
							// value peut etre une liste ou un tableau
							if (value instanceof List) {
								List list = (List) value;
								List<Object> targetList = new ArrayList<>();
								for (Object el : list) {
									targetList.add(cloneObjectImpl(el, maxDepth - 1));
								}

								setter.invoke(target, targetList);
							} else if (value instanceof Object[]) {
								Object[] arr = (Object[]) value;
								Object[] targetedArr = (Object[]) Array.newInstance(value.getClass().getComponentType(),
										arr.length);
								int index = 0;
								for (Object el : arr) {
									targetedArr[index++] = cloneObjectImpl(el, maxDepth - 1);
								}

								setter.invoke(target, targetedArr);
							}
						}
						break;

					case manyToMany:
						throw new UnsupportedOperationException();
					}
				} else {
					setter.invoke(target, value);
				}
			}

			return target;
		} else {
			return getObject(o, false, null);
		}

	}

	public boolean isProcessed(Object o, boolean addIt) {
		if (o == null) {
			return true;
		}

		String id = generateId(o);

		boolean processed = processedObjects.containsKey(id);
		if (addIt && !processed) {
			processedObjects.put(id, o);
		}

		return processed;
	}

	public String generateId(Object o) {
		@SuppressWarnings("rawtypes")
		Class entityClass = getEntityClass(o.getClass());

		String id = entityClass.getName() + "-" + getObjectId(o).toString();

		return id;

	}

	public void clear() {
		processedObjects.clear();
	}

	@SuppressWarnings("rawtypes")
	private Object getObject(Object src, boolean addIt, Class targetClass) {
		String id = generateId(src);
		Object target = processedObjects.get(id);
		if (target != null) {
			return target;
		}

		if (addIt) {
			if (targetClass != null) {
				try {
					Class aliasClass = classAliases.get(targetClass);
					target = (aliasClass != null) ? aliasClass.newInstance() : targetClass.newInstance();
					processedObjects.put(id, target);
				} catch (IllegalAccessException | InstantiationException ex) {
				}
			} else {
				processedObjects.put(id, src);
				target = src;
			}
		}

		return target;
	}

	private static final String GET = "get";
	private static final String IS = "is";

	@SuppressWarnings("rawtypes")
	private Map<String, ClassFieldMetadata> scanClass(Class<?> c) {
		Map<String, ClassFieldMetadata> fields = new HashMap<>();

		Class<?> currentClass = c;
		if (entityClasses == null || !entityClasses.containsKey(currentClass)) {
			Set<Class> implClasses = new HashSet<>();
			for (; currentClass != null; currentClass = currentClass.getSuperclass()) {
				boolean annFound = false;
				for (Class<? extends Annotation> entityAnnClass : entityAnnotations) {
					Annotation entityAnn = currentClass.getAnnotation(entityAnnClass);
					if (entityAnn != null) {
						annFound = true;
						break;
					}
				}

				if (annFound) {
					break;
				}

				implClasses.add(currentClass);
			}

			if (currentClass != null && !implClasses.isEmpty()) {
				for (Class aliasClass : implClasses) {
					classAliases.put(aliasClass, currentClass);
				}
			}
		}

		if (currentClass == null) {
			currentClass = c;
		}

		Class aliasClass = currentClass;
		classAliases.put(aliasClass, aliasClass);

		for (; !currentClass.equals(Object.class); currentClass = currentClass.getSuperclass()) {
			Method[] methods = currentClass.getMethods();
			for (Method method : methods) {
				int modifiers = method.getModifiers();
				if (Modifier.isStatic(modifiers)) {
					continue;
				}

				String fieldName;
				Class<?> fieldClass;

				String methodName = method.getName();
				if (methodName.startsWith(GET)) {
					fieldName = methodName.substring(GET.length());
					fieldClass = method.getReturnType();
				} else if (methodName.startsWith(IS)) {
					fieldName = methodName.substring(IS.length());
					fieldClass = method.getReturnType();
				} else {
					continue;
				}

				StringBuilder builder = new StringBuilder(fieldName);
				builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
				fieldName = builder.toString();

				if (fields.containsKey(fieldName) || CLASS_FIELD.equals(fieldName) /* getClass */) {
					// method ->
					continue;
				}

				Field field = null;
				try {
					field = findField(currentClass, fieldName);
					assert fieldClass == null || field.getType().equals(fieldClass);
					if (fieldClass == null) {
						fieldClass = field.getType();
					}
				} catch (NoSuchFieldException ex) {
				}

				Method setter = null;
				try {
					if (fieldClass != null && fieldName != null
							&& (field == null || !Modifier.isFinal(field.getModifiers()))) {
						setter = findSetter(currentClass, fieldClass, fieldName);
					}
				} catch (NoSuchMethodException ex) {
					logger.log(Level.WARNING, "No set method for {0} field in class {1}",
							new Object[] { fieldName, c.getName() });
				}

				ClassFieldMetadata fieldMetadata = new ClassFieldMetadata(field, method, setter, currentClass);
				if (fieldMetadata.isIdField() && !fields.containsKey(null)) {
					fields.put(null, fieldMetadata);
				}

				fields.put(fieldName, fieldMetadata);
			}
		}

		// assert (fields.get(null) != null) : "no id found for class " +
		// c.getName() + " -> " + aliasClass.getName();
		return fields;
	}

	private static Field findField(Class<?> c, String fieldName) throws NoSuchFieldException {
		for (@SuppressWarnings("rawtypes")
		Class currentClass = c; currentClass != null; currentClass = currentClass.getSuperclass()) {
			try {
				Field field = currentClass.getDeclaredField(fieldName);
				return field;
			} catch (NoSuchFieldException ex) {
			}
		}

		throw new NoSuchFieldException(fieldName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Method findSetter(final Class<?> c, final Class<?> fieldClass, final String fieldName)
			throws NoSuchMethodException {
		StringBuilder builder = new StringBuilder("set").append(fieldName);
		builder.setCharAt(3, Character.toUpperCase(builder.charAt(3)));
		String setterMethodName = builder.toString();

		for (Class currentClass = c; currentClass != null; currentClass = currentClass.getSuperclass()) {
			try {
				Method method = currentClass.getDeclaredMethod(setterMethodName, fieldClass);
				return method;
			} catch (NoSuchMethodException ex) {
			}
		}

		throw new NoSuchMethodException(setterMethodName);
	}

	public Object getObjectId(Object o) {
		Class<? extends Object> c = o.getClass();
		Map<String, ClassFieldMetadata> fieldsMetadata = getFieldsMetadata(c);
		ClassFieldMetadata idFieldMetadata = fieldsMetadata.get(null);
		if (idFieldMetadata != null) {
			return idFieldMetadata.getValue(o);
		}

		Map<Object, Long> objectIds = pseudoIds.get(c);
		if (objectIds == null) {
			objectIds = new HashMap<>();
			pseudoIds.put(c, objectIds);
		}

		Long id = objectIds.get(o);
		if (id == null) {
			id = 1L + objectIds.size();
			objectIds.put(o, id);
		}

		return id;
	}

	public final class ClassFieldMetadata {

		private Field field;
		private Method getter;
		private Method setter;
		private EntityRelationship relation;
		private boolean idField;
		private FetchType fetchMode;
		private Class<?> entityClass;

		// Map<K,V>
		private Class<?> mapKeyClass;
		private Class<?> mapValueClass;
		private boolean isMap;

		// Collection<E>
		private Class<?> collectionClass;
		private boolean isCollection;

		// Basic type
		private Class<?> fieldClass;

		private Map<Collection<Class<Annotation>>, Annotation> classesAnns;
		private Map<Class<Annotation>, Annotation> classAnns;

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public ClassFieldMetadata(Field field, Method getter, Method setter, Class<?> entityClass) {
			this.field = field;
			this.getter = getter;
			this.setter = setter;
			this.entityClass = entityClass;

			classesAnns = new HashMap<>();
			classAnns = new HashMap<>();

			// recherche d'une annotation relationnelle sur un getter ou le
			// champ
			relation = mapRelationshipAnnotation(
					getPropertyAnnotation(OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class));

			Type genericType = (field != null) ? field.getGenericType() : getter.getGenericReturnType();
			if (genericType instanceof Class) {
				Class c = (Class) genericType;
				boolean isArray = c.isArray();
				if (c.isArray()) {
					c = c.getComponentType();
				}

				fieldClass = c;

				if (relation == null && (entityClasses != null && entityClasses.containsKey(c)
						|| getClassAnnotation(c, entityAnnotations.toArray(new Class[0])) != null)) {
					relation = isArray ? EntityRelationship.oneToMany : EntityRelationship.oneToOne;
					if (entityClasses != null) {
						idField = entityClasses.get(c) != null;
					}
				}
			} else if (genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericType;
				Type rawType = parameterizedType.getRawType();
				if ((rawType instanceof Class)) {
					Type[] subTypes = parameterizedType.getActualTypeArguments();
					if (Collection.class.isAssignableFrom((Class) rawType)) {
						if (subTypes != null && subTypes.length == 1) {
							isCollection = true;
							Type subType = subTypes[0];
							collectionClass = (Class) subType;
							if (relation == null && (subType instanceof Class)
									&& (entityClasses != null && entityClasses.containsKey((Class) subType)
											|| getClassAnnotation((Class) subType,
													entityAnnotations.toArray(new Class[0])) != null)) {
								relation = EntityRelationship.oneToMany;
							}
						}
					} else if (Map.class.isAssignableFrom((Class) rawType)) {
						isMap = true;
						if (subTypes != null && subTypes.length == 2) {
							mapKeyClass = (Class<?>) subTypes[0];
							mapValueClass = (Class<?>) subTypes[1];
							if (relation == null && mapKeyClass instanceof Class
									&& (entityClasses != null && entityClasses.containsKey(mapKeyClass)
											|| getClassAnnotation(mapKeyClass,
													entityAnnotations.toArray(new Class[0])) != null)) {
								relation = EntityRelationship.manyToMany;
							}
						}
					}
				}
			}

			Class<Annotation>[] toArray = idAnnotations.toArray(new Class[0]);

			idField = getPropertyAnnotation(toArray) != null;
		}

		private EntityRelationship mapRelationshipAnnotation(Annotation annotation) {
			if (annotation == null) {
				return null;
			}

			if (annotation instanceof OneToOne) {
				relation = EntityRelationship.oneToOne;
				fetchMode = ((OneToOne) annotation).fetch();
			} else if (annotation instanceof OneToMany) {
				relation = EntityRelationship.oneToMany;
				fetchMode = ((OneToMany) annotation).fetch();
			} else if (annotation instanceof ManyToMany) {
				relation = EntityRelationship.manyToMany;
				fetchMode = ((ManyToMany) annotation).fetch();
			} else if (annotation instanceof ManyToOne) {
				relation = EntityRelationship.manyToOne;
				fetchMode = ((ManyToOne) annotation).fetch();
			}

			return relation;
		}

		public FetchType getFetchMode() {
			return fetchMode;
		}

		public Field getField() {
			return field;
		}

		public Method getGetter() {
			return getter;
		}

		public EntityRelationship getRelation() {
			return relation;
		}

		public Object getValue(Object o) {
			try {
				if (!fetchLazyAccess && FetchType.LAZY.equals(fetchMode)) {
					return null;
				}

				if (getter != null) {
					return getter.invoke(o);
				}

				if (field != null) {
					return field.get(o);
				}

			} catch (IllegalAccessException | IllegalArgumentException ex) {
				throw new RuntimeException(ex);
			} catch (RuntimeException ex) {
				if (isLazyException(ex)) {
					return null;
				} else {
					throw new RuntimeException(ex);
				}
			} catch (InvocationTargetException ex) {
				if (isLazyException(ex)) {
					return null;
				} else {
					throw new RuntimeException(ex);
				}
			}
			throw new IllegalStateException("???");
		}

		public void setValue(Object obj, Object value)
				throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			if (setter != null) {
				setter.invoke(obj, value);
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public <A extends Annotation> A getPropertyAnnotation(Class<? extends A>... annotationClasses) {
			int length = annotationClasses.length;
			if (length == 0) {
				return null;
			}

			A annotation;

			Collection<Class<Annotation>> s = null;
			Class<Annotation> annClass = null;
			if (length > 1) {
				s = (List) Arrays.asList(annotationClasses);

				annotation = (A) classesAnns.get(s);

				if (annotation != null || classesAnns.containsKey(s)) {
					return annotation;
				}
			} else {
				annClass = (Class<Annotation>) annotationClasses[0];
				annotation = (A) classAnns.get(annClass);
				if (annotation != null || classAnns.containsKey(annClass)) {
					return annotation;
				}
			}

			for (Class<?> currentClass = entityClass; currentClass != null; currentClass = currentClass
					.getSuperclass()) {
				try {
					Method method = currentClass.getMethod(getter.getName());
					for (Class<? extends A> annotationClass : annotationClasses) {
						annotation = method.getAnnotation(annotationClass);
						if (annotation != null) {
							break;
						}
					}
				} catch (NoSuchMethodException e) {
				}
			}

			if ((annotation == null) && (field != null)) {
				for (Class<? extends A> annotationClass : annotationClasses) {
					annotation = field.getAnnotation(annotationClass);
					if (annotation != null) {
						break;
					}
				}
			}

			if (s != null) {
				classesAnns.put(s, annotation);
			} else if (annClass != null) {
				classAnns.put(annClass, annotation);
			}

			return annotation;
		}

		public boolean isMap() {
			return isMap;
		}

		public Class<?> getCollectionClass() {
			return collectionClass;
		}

		public boolean isCollection() {
			return isCollection;
		}

		public boolean isIdField() {
			return idField;
		}

		public Class<?> getValueClass() {
			if (isMap) {
				return mapValueClass;
			} else if (isCollection) {
				return collectionClass;
			} else {
				return fieldClass;
			}
		}

		public String getFieldName() {
			String result;

			if (field != null) {
				result = field.getName();
			} else {
				result = getter.getName();
				if (result.startsWith(GET)) {
					result = result.substring(GET.length());
				} else if (result.startsWith(IS)) {
					result = result.substring(IS.length());
				}

				StringBuilder builder = new StringBuilder(result);
				builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
				result = builder.toString();
			}

			return result;
		}

		public Method getSetter() {
			return setter;
		}

		/**
		 * Valid only for manyToMany or manyToOne relationship
		 *
		 * @return
		 */
		public Class<?> getMapKeyClass() {
			return mapKeyClass;
		}

		/**
		 * Valid only for manyToMany or manyToOne relationship
		 *
		 * @return
		 */
		public Class<?> getMapValueClass() {
			return mapValueClass;
		}

		@Override
		public String toString() {
			return "EntityFieldMetadata{" + "field=" + field + ", method=" + getter + ", relation=" + relation
					+ ", idField=" + idField + '}';
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 89 * hash + Objects.hashCode(this.field);
			hash = 89 * hash + Objects.hashCode(this.getter);
			hash = 89 * hash + Objects.hashCode(this.entityClass);
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final ClassFieldMetadata other = (ClassFieldMetadata) obj;
			if (!Objects.equals(this.field, other.field)) {
				return false;
			}
			if (!Objects.equals(this.getter, other.getter)) {
				return false;
			}
			if (!Objects.equals(this.entityClass, other.entityClass)) {
				return false;
			}
			return true;
		}
	}

	/*
	 * L'appel au getter d'un membre d'une entité échoue lors du chargement à la
	 * demande (lazy) après que la session soit fermée (sortie d'une ejb).
	 * 
	 * Dans ce cas, la valeur retournée par défaut est null;
	 */
	public static boolean isLazyException(final Throwable cause) {
		for (Throwable th = cause; th != null; th = th.getCause()) {
			String className = th.getClass().getName();
			className = className.toLowerCase();
			String message = th.getMessage();
			if (message != null) {
				message = message.toLowerCase();
			}

			logger.log(Level.WARNING, "InvocationTargetException {0}, {1}", new Object[] { className, message });
			if (className.contains("lazy") && className.contains("hibernate")
					&& (message == null || message.contains(" no session"))) {
				return true;
			}
		}
		return false;
	}

	public static enum EntityRelationship {

		oneToOne, oneToMany, manyToMany, manyToOne
	}

	public static IntrospectionUtils newInstance() {
		return new IntrospectionUtils();
	}

	public static class ClassIntrospectionUtilsException extends Exception {

		private static final long serialVersionUID = 8593756188978308936L;

		public ClassIntrospectionUtilsException() {
			super();
		}

		public ClassIntrospectionUtilsException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public ClassIntrospectionUtilsException(String arg0) {
			super(arg0);
		}

		public ClassIntrospectionUtilsException(Throwable arg0) {
			super(arg0);
		}
	}

	public static <A extends Annotation> A getClassAnnotation(Class<?> entityClass,
			@SuppressWarnings("unchecked") Class<A>... annClasses) {
		for (Class<?> currentClass = entityClass; currentClass != null; currentClass = currentClass.getSuperclass()) {
			for (Class<A> c : annClasses) {
				A annotation = currentClass.getAnnotation(c);
				if (annotation != null) {
					return annotation;
				}
			}
		}

		return null;
	}

	public static <A extends Annotation> Map<Class<?>, A> getHirearchicalAnnotation(Class<?> entityClass,
			Class<A> annClass, boolean bottomToTop) {
		Map<Class<?>, A> anns = new LinkedHashMap<>();
		for (Class<?> currentClass = entityClass; currentClass != null; currentClass = currentClass.getSuperclass()) {
			A annotation = currentClass.getAnnotation(annClass);
			anns.put(currentClass, annotation);
		}

		if (!bottomToTop) {
			List<Class<?>> keys = new ArrayList<>(anns.keySet());
			Collections.reverse(keys);
			Map<Class<?>, A> reversed = new LinkedHashMap<>();
			for (Class<?> c : keys) {
				reversed.put(c, anns.get(c));
			}

			anns = reversed;
		}

		return anns;
	}

	public Method getMethodWithAnnatotation(Class<?> entityClass, Class<? extends Annotation> annClass) {
		for (Class<?> currentClass = entityClass; !currentClass.equals(Object.class); currentClass = currentClass
				.getSuperclass()) {
			for (Method m : currentClass.getMethods()) {
				if (m.getAnnotation(annClass) != null) {
					return m;
				}
			}
		}

		return null;
	}
}
