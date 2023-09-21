package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ProductoServiceImplementacion implements ProductoService {

	private EntityManagerFactory emf;

	@Override
	public void ProductoService(EntityManagerFactory emf) {
		// TODO Auto-generated method stub
		this.emf = emf;
	}

	@Override
	public void crearProducto(Long codigo, String descripcion, double precio, Long idCategoria, String marca) {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Categoria categoriaProducto = em.getReference(Categoria.class, idCategoria);
			Producto producto = new Producto(descripcion, codigo, categoriaProducto, precio, marca);

			em.persist(producto);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
//			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@Override
	public void modificarProducto(Long idProducto, String descripcion, double precio, String marca) {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Producto producto = em.getReference(Producto.class, idProducto);

			producto.modificarProducto(descripcion, precio, marca);
			em.persist(producto);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
//				throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@Override
	public List<Producto> listarProductos() {

		List<Producto> productos = new ArrayList<>();
		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			TypedQuery<Producto> productoTypedQuery = em.createQuery("select p from Producto p", Producto.class);
			productos.addAll(productoTypedQuery.getResultList());
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
//			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

		return productos;
	}

}
