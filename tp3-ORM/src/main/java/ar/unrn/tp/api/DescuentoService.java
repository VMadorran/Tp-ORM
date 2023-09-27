package ar.unrn.tp.api;

import java.time.LocalDateTime;
import java.util.List;

import ar.unrn.tp.modelo.Promocion;
import jakarta.persistence.EntityManagerFactory;

public interface DescuentoService {

	void DescuentoService(EntityManagerFactory emf);

	void crearDescuentoSobreTotal(String marcaTarjeta, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			float porcentaje);

	void crearDescuento(String marcaProducto, LocalDateTime fechaDesde, LocalDateTime fechaHasta, float porcentaje);

	public List<Promocion> listarDescuentos();

}
