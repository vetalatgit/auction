package ua.cn.stu.auction2011.ImplService;

import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.IGenericService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.cn.stu.auction2011.config.Messages;
import ua.cn.stu.auction2011.domain.DomainObject;

public abstract class GenericServiceImpl<T extends DomainObject> implements IGenericService<T> {

	private static final String QUERY_SELECT_BY_ID_LIST = "SELECT x FROM %s x WHERE x.id IN (?1);"; //$NON-NLS-1$

	private static final String QUERY_SELECT_ALL = "SELECT x FROM %s x;"; //$NON-NLS-1$

	private static final String QUERY_COUNT_ALL = "SELECT COUNT(x) FROM %s x;"; //$NON-NLS-1$
	
	@PersistenceContext(unitName = "auct")
	private EntityManager em;

	private Class < T > entityClass;
	
	public String getStr(){
		return String.format(QUERY_COUNT_ALL,entityClass
				.getSimpleName());
	}
	/** Log object used for logging */
	//protected Log log = LogFactory.getLog(getClass());
	

	/**
	 * Constructor with fields
	 * 
	 * @param entityClass
	 *            Class that this dao will work with
	 */
	public GenericServiceImpl() {
		
	}
	
	public GenericServiceImpl(Class<T> entityClass) {
		super();
		this.entityClass = entityClass;
	}
	
	 public EntityManager getEntityManager(){
		 return this.em;
	 };
	    public void create(T entity) {
	        getEntityManager()
	            .persist(entity);
	    }

	    public void edit(T entity) {
	        getEntityManager()
	            .merge(entity);
	    }

	    public void remove(T entity) {
	        getEntityManager()
	            .remove(getEntityManager().merge(entity));
	    }

	   @SuppressWarnings("unchecked")
		public List<T> findAll() throws IllegalStateException {
	       /* @SuppressWarnings("rawtypes")
			/*javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
	                                                          .getCriteriaBuilder()
	                                                          .createQuery();
	        cq.select(cq.from(entityClass));*/

		   return getEntityManager().createQuery(QUERY_SELECT_ALL).getResultList();
	       /*return getEntityManager()
	                   .createQuery(cq)
	                   .getResultList();
	    */}
	
	/**
	 * <p>
	 * This template method executes query with performing all needed
	 * operations, like creating EntityManager, creating transaction,
	 * committing, or rolling it back.
	 * </p>
	 * <p>
	 * Methods sets parameters for the query as they appear in the parameters
	 * list, by number starting from 1. So, the first parameter in your named
	 * query should be referenced as <code>?1</code>, second as <code>?2</code>
	 * and so on.
	 * </p>
	 * <p>
	 * If <code>singleResult = true</code> and no result is found, then
	 * <code>null</code> is returned.
	 * </p>
	 * <p>
	 * Be aware, that when multiple results are returned, they are being
	 * dynamically casted to <code>REZ</code> class. It should be able to cast
	 * to {@link java.util.List}. When returning single result, <code>REZ</code>
	 * should be a single persistent entity class.
	 * </p>
	 * 
	 * @param <REZ>
	 *            Class of the result
	 * @param queryOrQueryName
	 *            Query string or NamedQuery name
	 * @param namedQuery
	 *            Specifies, whether query is named query
	 * @param singleResult
	 *            Specifies, whether single result should be returned
	 * @param parameters
	 *            Parameters. You can specify multiple parameters separated by
	 *            comma
	 * @return Result of the query
	 * @throws GeneralServiceException
	 *             If error occurs
	*/
	   @SuppressWarnings("unchecked")
	protected <REZ> REZ executeQuery(String queryOrQueryName,
				boolean namedQuery, boolean singleResult, Object... parameters) 
		throws IllegalArgumentException, GeneralServiceException {
			
			if (StringUtils.isBlank(queryOrQueryName)) {
				throw new IllegalArgumentException(
						"Query for executing cannot be null");
			}

		/*	if (log.isDebugEnabled()) {
				log.debug(String.format(
					"Executing query '%s' to return single result '%s' with params %s", //$NON-NLS-1$
						queryOrQueryName, singleResult, ArrayUtils
							.toString(parameters)));
			}*/
			try{
			REZ result;
			List<?> list;
			Query qw;
			if (namedQuery) {
				qw= getEntityManager().createNamedQuery(queryOrQueryName); 
			} else {
				qw= getEntityManager().createQuery(queryOrQueryName); 
			}
			if (parameters.length>0){
				for (int i=0; i<parameters.length; i++){
					qw.setParameter(i+1, parameters[i]);
				}
			}
			list = qw.getResultList();
			// Returning needed result
			if (singleResult) {
				if (CollectionUtils.isNotEmpty(list)) {
					result = (REZ) list.get(0);
				} else {
					result = null;
				}
			} else {
				result = (REZ) list;
			}

			return result;
			}
			catch (Exception e){
				return null;
				
			}
		}
	
		protected int executeUpdateQuery(final String queryOrQueryName,
				final boolean namedQuery, final Object... parameters) 
		throws IllegalArgumentException, GeneralServiceException,PersistenceException{
			
			if (StringUtils.isBlank(queryOrQueryName)) {
				throw new IllegalArgumentException(
						"Query for executing cannot be null");
			}

		/*	if (log.isDebugEnabled()) {
				log.debug(String.format(
					"Executing update query '%s' with params %s", //$NON-NLS-1$
						queryOrQueryName, ArrayUtils
							.toString(parameters)));
			}*/
			try{
			Query query;
			if (namedQuery) {
					query=getEntityManager().createNamedQuery(queryOrQueryName);
					}else{
						query=getEntityManager().createQuery(queryOrQueryName);
					}
					if (parameters.length>0){
						for (int i=0; i<parameters.length; i++){
							query.setParameter(i+1, parameters[i]);
						}
					}
					return Integer.valueOf(query.executeUpdate());
			
			}
			catch (Exception e){
				throw new GeneralServiceException(e);
			}
			
		}
	/**
	 * Method that is called, before entity is being updated or added. Method
	 * must be overwritten in subclasses to make some checks.
	 * 
	 * @param entity
	 *            Entity that will be added or updated
	 * @throws GeneralServiceException
	 *             If error occurs
	 */
	 public long getAllEntitiesCount() {
			try {
				return executeQuery(String.format(QUERY_COUNT_ALL, entityClass
						.getSimpleName()), false, true);
			} catch (Exception e) {
			//log.error("Failed to get all entities count", e); //$NON-NLS-1$
				return 0;
			}
		}

		
	protected abstract void beforeEntityAddUpdate(T entity)
			throws GeneralServiceException;

	public T save(T entity) throws IllegalArgumentException, GeneralServiceException {
		if (entity == null) {
			throw new IllegalArgumentException(
					"Entity for saving cannot be null!");
		}

		T savedEntity = null;

		try {

			if (entity.getId() == null) {

				/*if (log.isDebugEnabled()) {
					log.debug("Saving new entity: " + entity); //$NON-NLS-1$
				}*/

				 getEntityManager().persist(entity);
				savedEntity = entity;
				
			/*	if (log.isDebugEnabled()) {
					log.debug("Saved entity: " + savedEntity); //$NON-NLS-1$
				}
				*/
			} else {

			/*	if (log.isDebugEnabled()) {
					log.debug("Updating entity: " + entity); //$NON-NLS-1$
				}*/

				savedEntity = getEntityManager().merge(entity);
			}

		} catch (Exception e) {
			if (entity.getId() == null) {
				throw new GeneralServiceException("GenericServiceImpl.ErrorFailedToAdd"); //$NON-NLS-1$
					//+ entity.getClass().getSimpleName(), e, this); //$NON-NLS-1$
			} else {
				throw new GeneralServiceException("GenericServiceImpl.ErrorFailedToUpdate") ;//$NON-NLS-1$
				//	+ entity.getClass().getSimpleName() + Messages.getString("GenericServiceImpl.WithId") + entity.getId(), e, this); //$NON-NLS-1$
		}
	}

	return savedEntity;
}

	public void delEntity(Long id) throws IllegalArgumentException, GeneralServiceException {
		if (id == null) {
			throw new IllegalArgumentException(Messages.getString("GenericServiceImpl.ErrorIdNull")); //$NON-NLS-1$
		}

		// Getting entity by id
		T savedEntity = getEntityById(id);

		if (savedEntity != null) {
			// Deleting entity
			delEntity(savedEntity);
			}
		}

		
		public void delEntity(T entity) throws IllegalArgumentException, GeneralServiceException {
			if (entity == null) {
				throw new IllegalArgumentException(Messages.getString("GenericServiceImpl.ErrorEntityNull")); //$NON-NLS-1$
			}

			/*if (entity.getId() == null) {
				log.warn("Trying to delete transient entity: " + entity); //$NON-NLS-1$
				return;
			}*/
			
			beforeEntityDelete(entity);

			try {
				// Deleting entity object
				if (!getEntityManager().contains(entity)) {
				/*	if (log.isDebugEnabled()) {
						log.debug("Attaching detached entity before deleting " //$NON-NLS-1$
								+ entity);
					}*/
					T attachedEntity = getEntityManager().merge(entity);
					getEntityManager().remove(attachedEntity);
				} else {
					getEntityManager().remove(entity);
				}
			} catch (Exception e) {
				// Catching all runtime exceptions that might occur when operating
			// with entity. Returning our service exception in such case
			//throw new GeneralServiceException(Messages.getString("GenericServiceImpl.ErrorFailToDelete") //$NON-NLS-1$
			////		+ entity.getClass().getSimpleName() + Messages.getString("GenericServiceImpl.WithId") //$NON-NLS-1$
				//	+ entity.getId(), e, this);
		}
	}
	
	/**
	 * Method that is called just before entity is deleted
	 * @param entity Entity to delete
	 * @throws GeneralServiceException If error occurs
	 */
	protected abstract void beforeEntityDelete(T entity) throws GeneralServiceException;

	public void delAllEntities(Collection<Long> ids) throws IllegalArgumentException, 
			 GeneralServiceException {
		
		if (ids == null) {
			throw new IllegalArgumentException(Messages.getString("GenericDaoJpa.ErrorCollectionEntitiesNullForDelete")); //$NON-NLS-1$
		}
		
		if (!ids.isEmpty()) {
			
			for (Long id : ids) {
				T savedEntity = getEntityById(id);

				if (savedEntity != null) {
					// Deleting entity
					delEntity(savedEntity);
				} else {
					// Doing nothing if entity is not found
				//	log.info("Entity with id " + id + " was not deleted, because it was not found"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
	}

	public List<T> getAllEntites() {
		try {
			// Getting all entities
			List<T> list = executeQuery(String.format(QUERY_SELECT_ALL, entityClass
					.getSimpleName()), false, false);
			if (list != null)
				return list;
			else
				return new ArrayList<T>();

		} catch (Exception e) {
			//log.error("Failed to get list of all entities", e); //$NON-NLS-1$
			return new ArrayList<T>();
		}
	}
	
	public List<T> getEntites(int firstEntity,int batchSize){
		try {
			 Query q = getEntityManager().createQuery(String.format(QUERY_SELECT_ALL, entityClass
						.getSimpleName()));
		       q.setMaxResults(batchSize);
		       q.setFirstResult(firstEntity);
			List<T> list= q.getResultList();
		       if (list != null)
					return list;
				else
					return new ArrayList<T>();
		} catch (Exception e) {
			//log.error("Failed to get list of all entities", e); //$NON-NLS-1$
			return new ArrayList<T>();
		}
	}

	
	 public T find(Object id) {
	        return getEntityManager()
	                   .find(entityClass, id);
	    }
	//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public T getEntityById(Long id) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException(
				Messages.getString("GenericDaoJpa.ErrorEntityIdNull")); //$NON-NLS-1$
		}
		/*if (log.isDebugEnabled()) {
			log.debug("Getting entity " + entityClass.getSimpleName() //$NON-NLS-1$
					+ " by id " + id); }
*/
		try {

			// Getting entity by id
			T savedEntity = getEntityManager().find(entityClass, id);

			// Checking if we ua.cn.stu.auction2011t the entity that is not NULL
			if ((savedEntity == null) || (savedEntity.getId() == null)) {
				StringBuilder mess = new StringBuilder("Entity by id "); //$NON-NLS-1$
				
				mess.append(id).append(" was not found"); //$NON-NLS-1$
				
				//log.warn(mess.toString());
				
				return null;
			}

			return savedEntity;
		} catch (Exception e) {
			//log.error("Failed to get entity by id " + id, e); //$NON-NLS-1$
			return null;
		}
	}

	
	public List<T> getAllSorted(String propertySortBy, boolean asc) throws IllegalArgumentException {
		if (StringUtils.isBlank(propertySortBy)) {
			throw new IllegalArgumentException(
					Messages.getString("GenericDaoJpa.ErrorSortByPropertyNull")); //$NON-NLS-1$
		}

		try {
			String query = String.format(QUERY_SELECT_ALL, entityClass
					.getSimpleName());

			String newQuery = addSortPropertyToQuery(query, propertySortBy, asc);

			return executeQuery(newQuery, false, false);
		} catch (Exception e) {
			//log.error(Messages.getString("GenericServiceImpl.ErrorGetEntitiesSorted"), e); //$NON-NLS-1$
			return new ArrayList<T>();
		}
	}
	
	/**
	 * Appending sort information to query and returning new query
	 * 
	 * @param query
	 *            Query to append with sort information
	 * @param propertySortBy
	 *            Property to sort by
	 * @param asc
	 *            Specifies sort direction
	 * @return new query with added sort
	 */
	protected String addSortPropertyToQuery(String query, String propertySortBy,
			boolean asc) {
		StringBuilder sb = new StringBuilder();
		sb.append(query);
		sb.append(" "); //$NON-NLS-1$
		sb.append("order by "); //$NON-NLS-1$
		sb.append(propertySortBy);

		if (asc) {
			sb.append(" asc"); //$NON-NLS-1$
		} else {
			sb.append(" desc"); //$NON-NLS-1$
		}

		String newQuery = sb.toString();
		return newQuery;
	}

	public List<T> getEntitiesByIds(List<Long> ids) throws IllegalArgumentException {
		
		if (ids == null) {
			//throw new IllegalArgumentException(
				//	Messages.getString("GenericDaoJpa.ErrorListOfIdNull")); //$NON-NLS-1$
		}

		if (ids.isEmpty()) {
			return new ArrayList<T>();
		}

		try {
			return executeQuery(String.format(QUERY_SELECT_BY_ID_LIST,
					entityClass.getSimpleName()), false, false, ids);
		} catch (Exception e) {
			//log.error(Messages.getString("GenericServiceImpl.ErrorGetEntitiesByIds"), e); //$NON-NLS-1$
			return new ArrayList<T>();
		}
	}
	
	
	public String storeProcCall(final String queryString, final Object... params) 
		throws IllegalArgumentException, GeneralServiceException, PersistenceException {
		if (StringUtils.isBlank(queryString)) {
			throw new IllegalArgumentException(
					"Query for executing cannot be null");
		}
		String s;
		try{
					Query	query=getEntityManager().createNativeQuery(queryString);
					if (params.length>0){
						for (int i=0; i<params.length; i++){
							query.setParameter(i+1, params[i]);
						}
					}
					try{
					s=(String) query.getSingleResult(); 
					} catch (javax.persistence.PersistenceException e) {
						s = e.getCause().getCause().getMessage();
				    }
			
					if (s!=null)
				return s;
			else return null;
		}catch (Exception e){
			throw new GeneralServiceException(e.getMessage());
		}
}
	}
