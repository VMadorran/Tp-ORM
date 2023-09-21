package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.exception.TarjetaInvalidaException;
import ar.unrn.tp.modelo.Venta;
import jakarta.persistence.EntityManagerFactory;

public interface VentaService {

	// Crea una venta. El monto se calcula aplicando los descuentos a la fecha
	// validaciones:
	// - debe ser un cliente existente
	// - la lista de productos no debe estar vacía
	// - La tarjeta debe pertenecer al cliente
	void VentaService(EntityManagerFactory emf);

	void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta)
			throws DatoVacioException, CarritoVacioException, TarjetaInvalidaException;

	// Devuelve el monto total aplicando los descuentos al día de la fecha
	// validar que no llegue una lista vacía y la tarjeta exista
	float calcularMonto(List<Long> productos, Long idTarjeta, Long idCliente) throws Exception, DatoVacioException;

	// Devuelve todas las ventas realizadas
	List<Venta> ventas();
}
