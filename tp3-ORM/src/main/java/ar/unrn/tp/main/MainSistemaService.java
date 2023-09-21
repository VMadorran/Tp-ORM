package ar.unrn.tp.main;

import java.util.List;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainSistemaService {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-mysql");
		// TODO Auto-generated method stub

//		CategoriaService catService = new CategoriaServiceImplementacion();
//		catService.categoriaService(consService);
//
//		ProductoService productService = new ProductoServiceImplementacion();
//		productService.productoService(consService);
//
//		DescuentoService descService = new DescuentoServiceImplementacion();
//		descService.descuentoService(consService);
//		catService.crearCategoria(1L, "Indumentaria");
//		ClienteService clientService = new ClienteServiceImplementacion();
//		clientService.clienteService(consService);
//		VentaService ventaService = new VentaServiceImplementacion();
//		ventaService.VentaService(emf);
//
//		productService.crearProducto(1L, "Remera corta", 1000, 1L, "Nope");
//		productService.crearProducto(3L, "Medias", 500, 1L, "Acme");
//
//		var fecha = new ProveedorDeFecha();
//
//		descService.crearDescuento("Acme", fecha.now().minusMonths(1), fecha.now().plusMonths(1), 0.05f);
//		descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2), 0.08f);

//		clientService.agregarTarjeta(6L, "123123", "Visa");
//		CategoriaService catService = new CategoriaServiceImplementacion();
//		catService.CategoriaService(emf);
//
//		ProductoService productService = new ProductoServiceImplementacion();
//		productService.ProductoService(emf);
//
//		DescuentoService descService = new DescuentoServiceImplementacion();
//		descService.DescuentoService(emf);
//
		ClienteService clientService = new ClienteServiceImplementacion();
		clientService.ClienteService(emf);
		List<Tarjeta> tarjetas = clientService.listarTarjetas(1L);
		emf.close();

		System.out.println(tarjetas.get(0).marcaTarjeta());
		System.out.println(tarjetas.get(1).marcaTarjeta());

//		catService.crearCategoria(1L, "Indumentaria");
//
//		productService.crearProducto(1L, "Remera corta", 1000, 1L, "Nope");
//		productService.crearProducto(3L, "Medias", 500, 1L, "Acme");
//
//		var fecha = new ProveedorDeFecha();
//		descService.crearDescuento("Acme", fecha.now().minusMonths(1), fecha.now().plusMonths(1), 0.05f);
//		descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2), 0.08f);
//
//		clientService.crearCliente("Jose", "Perez", "12345678", "jose@acdc.com");
//		clientService.agregarTarjeta(1L, "123123", "Visa");

//		var monto = ventaService.calcularMonto(List.of(1L, 2L), 123123L, 1L);
//		ventaService.realizarVenta(1L, List.of(1L, 2L), 123123L);
//		System.out.println(monto);

//		List<Producto> productos = productService.listarProductos();
//		System.out.println(productos.get(0).descripcion());
//		System.out.println(productos.get(1).descripcion());

	}

}
