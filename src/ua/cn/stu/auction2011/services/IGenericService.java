package ua.cn.stu.auction2011.services;


import ua.cn.stu.auction2011.exceptions.GeneralServiceException;

import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;
import javax.persistence.EntityManager;

import ua.cn.stu.auction2011.domain.DomainObject;


/**
 * This is a generic interface that exposes generic methods used mostly by all
 * services. This methods include CRUD operations with ability to get all
 * entity. If some service doesn't need to provide support for all such methods,
 * then it should not subclass this interface.
 * 
 * @author vetal
 * 
 * @param <T>
 *            Domain class to work with
 */
@Remote
public interface IGenericService<T extends DomainObject> {

	/**
	 * Deleting entity from database. Doing nothing if entity with passed id not
	 * found.
	 * 
	 * @param id
	 *            Unique id of the entity to delete
	 * @throws GeneralServiceException
	 *             If service fails to delete entity
	 * @throws IllegalArgumentException
	 *             If id is <code>null</code>
	 */
	public void delEntity(Long id) throws IllegalArgumentException,
			GeneralServiceException;
	public String getStr();

	/**
	 * Deleting persistent entity from database.
	 * 
	 * @param entity
	 *            Persistent entity to delete
	 * @throws GeneralServiceException
	 *             If service fails to delete entity
	 * @throws IllegalArgumentException
	 *             If entity is <code>null</code>
	 */
	public void delEntity(T entity) throws IllegalArgumentException,
		GeneralServiceException;

	/**
	 * Deleting all entities by their ids
	 * 
	 * @param ids
	 *            Collection of ids. If it is null or empty, then doing nothing
	 * @throws GeneralServiceException
	 *             If error occurs
	 * @throws IllegalArgumentException
	 *             If ids is <code>null</code>
	 */
	public void delAllEntities(Collection<Long> ids)
			throws IllegalArgumentException, GeneralServiceException;

	/**
	 * Getting entity by its unique identifier
	 * 
	 * @param id
	 *            Id of entity to find
	 * @return Entity of T type or <code>null</code> if not found or error
	 * @throws IllegalArgumentException
	 *             If id is <code>null</code>
	 */
	public T getEntityById(Long id) throws IllegalArgumentException;

	/**
	 * Getting all entities
	 * 
	 * @return List of all entities of T type or empty list if error
	 */
	public List<T> getAllEntites();
	
	public List<T> getEntites(int firstEntity,int batchSize);

	/**
	 * Save new object or update old one in the DB.
	 * 
	 * @param entity
	 *            object to store
	 * @return stored object
	 * @throws ServiceException
	 *             If domain or DB error occurs
	 * @throws IllegalArgumentException
	 *             If entity is <code>null</code>
	 * @throws GeneralServiceException 
	 */
	//@TransactionAttribute(value=TransactionAttributeType.SUPPORTS)
	public T save(T entity) throws IllegalArgumentException,
			GeneralServiceException;

	/**
	 * Count of all entities of current ItemType
	 * 
	 * @return number of all entities or 0 if error
	 */
	public long getAllEntitiesCount();

	/**
	 * Retrieves from database all entities ordered by field specified by
	 * <code>propertySortBy</code> parameter.
	 * 
	 * @param propertySortBy
	 *            Name of property of entity class to order by.
	 * @param asc
	 *            Order direction: ascending if <code>true</code> and descending
	 *            otherwise.
	 * @return List of entities or empty list if there are no entities or error
	 * @throws IllegalArgumentException
	 *             If propertySortBy is <code>null</code>, or empty
	 */
	public List<T> getAllSorted(String propertySortBy, boolean asc)
			throws IllegalArgumentException;

	/**
	 * Retrieves from database entities for IDs specified by <code>ids</code>
	 * parameter.
	 * 
	 * @param ids
	 *            List of IDs of entities to retrieve from database.
	 * @return List of entities or empty list. If list of IDs is null or empty
	 *         method will return empty list of entities.
	 * @throws IllegalArgumentException
	 *             If ids is <code>null</code>
	 */
	public List<T> getEntitiesByIds(List<Long> ids)
			throws IllegalArgumentException;
	
	public abstract EntityManager getEntityManager();
}