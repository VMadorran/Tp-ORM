
package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.exception.TarjetaInvalidaException;
import ar.unrn.tp.modelo.Carrito;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ServicioWeb;
import ar.unrn.tp.modelo.ServicioWebImplementacion;
import ar.unrn.tp.modelo.Venta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class VentaServiceImplementacion implements VentaService {
	private EntityManagerFactory emf;
	private float monto;
	PromocionBancaria bancaria = null;
	ServicioWeb servicio = new ServicioWebImplementacion();

	public void VentaService(EntityManagerFactory emf) {
		// TODO Auto-generated method stub
		this.emf = emf;
	}

	@Override
	public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta)
			throws DatoVacioException, CarritoVacioException, TarjetaInvalidaException {

		if (idCliente == 0)
			throw new DatoVacioException("El cliente debe ser válido");

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();
			ArrayList<Producto> productosCompra = new ArrayList<>();

			for (Long idProducto : productos) {
				var producto = em.find(Producto.class, idProducto);
				productosCompra.add(producto);
			}

			Cliente cliente = em.find(Cliente.class, idCliente);

			var carrito = new Carrito(cliente, new ServicioWebImplementacion());

			carrito.agregarProductos(productosCompra);
			carrito.actualizarPromociones(this.promocionesMarca(), this.promocionBancaria());
			var venta = carrito.realizarCompra(idTarjeta);
			em.persist(venta);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

	}

	@Override
	public float calcularMonto(List<Long> productos, Long idTarjeta, Long idCliente)
			throws DatoVacioException, CarritoVacioException, TarjetaInvalidaException {

		if (idCliente == 0)
			throw new DatoVacioException("El cliente debe ser válido");

		ArrayList<Producto> productosCompra = new ArrayList<>();

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();

			for (Long idProducto : productos) {
				var producto = em.find(Producto.class, idProducto);
				productosCompra.add(producto);
			}

			var cliente = em.find(Cliente.class, idCliente);
			var carrito = new Carrito(cliente, servicio);

			carrito.agregarProductos(productosCompra);
			carrito.actualizarPromociones(this.promocionesMarca(), this.promocionBancaria());

			monto = (float) carrito.calcularMontoDeCompra(idTarjeta);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

		return monto;
	}

	private List<PromocionBancaria> promocionBancaria() {
		List<PromocionBancaria> bancarias = new ArrayList<>();

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			TypedQuery<PromocionBancaria> ventasTypedQuery = em.createQuery("select p from PromocionBancaria p",
					PromocionBancaria.class);
			bancarias.addAll(ventasTypedQuery.getResultList());

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
		return bancarias;
	}

	private List<PromocionMarca> promocionesMarca() {
		List<PromocionMarca> bancarias = new ArrayList<>();

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {

			tx.begin();

			TypedQuery<PromocionMarca> ventasTypedQuery = em.createQuery("select p from PromocionMarca p",
					PromocionMarca.class);
			bancarias.addAll(ventasTypedQuery.getResultList());

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

		return bancarias;
	}

	@Override
	public List<Venta> ventas() {
		List<Venta> ventas = new ArrayList<>();

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();

			TypedQuery<Venta> ventasTypedQuery = em.createQuery("select v from Venta v", Venta.class);
			ventas.addAll(ventasTypedQuery.getResultList());

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
//			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

		return ventas;
	}
}
