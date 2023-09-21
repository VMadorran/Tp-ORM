package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.modelo.Producto;
import jakarta.persistence.EntityManagerFactory;

public interface ProductoService {

	void ProductoService(EntityManagerFactory emf);

	// validar que sea una categoría existente y que codigo no se repita
	void crearProducto(Long codigo, String descripcion, double precio, Long idCategoria, String marca);

	// validar que sea un producto existente
	void modificarProducto(Long idProducto, String descripcion, double precio, String marca);

	// Devuelve todos los productos
	List<Producto> listarProductos();
}