package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venta {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "fecha_y_hora")
	private LocalDateTime fechaYHora;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_venta")
	private List<ProductoVendido> productosVendidos = new ArrayList<>();
	private Long dniCliente;
	private double precioFinal;
	private double descuentoBancario;

	public Venta(LocalDateTime fechaYHora, List<ProductoVendido> productosVendidos, Long dniCliente, double precioFinal,
			double descuentoBancario) {

		this.fechaYHora = fechaYHora;
		this.productosVendidos = productosVendidos;
		this.dniCliente = dniCliente;
		this.precioFinal = precioFinal;
		this.descuentoBancario = descuentoBancario;
	}

	public Venta() {

	}

	public Long clienteComprador() {
		return this.dniCliente;
	}

	public double precioFinal() {
		return this.precioFinal;
	}

	public List<ProductoVendido> productos() {
		return this.productosVendidos;
	}

	@Override
	public String toString() {
		String venta = "id=" + id + ", fechaYHora=" + fechaYHora + " dniCliente=" + dniCliente + ", precioFinal="
				+ precioFinal + ", descuentoBancario=" + descuentoBancario + "Cantidad de productos:"
				+ this.productosVendidos.size();

		return venta;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private LocalDateTime getFechaYHora() {
		return fechaYHora;
	}

	private void setFechaYHora(LocalDateTime fechaYHora) {
		this.fechaYHora = fechaYHora;
	}

	private List<ProductoVendido> getProductosVendidos() {
		return productosVendidos;
	}

	private void setProductosVendidos(List<ProductoVendido> productosVendidos) {
		this.productosVendidos = productosVendidos;
	}

	private Long getDniCliente() {
		return dniCliente;
	}

	private void setDniCliente(Long dniCliente) {
		this.dniCliente = dniCliente;
	}

	private double getPrecioFinal() {
		return precioFinal;
	}

	private void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	private double getDescuentoBancario() {
		return descuentoBancario;
	}

	private void setDescuentoBancario(double descuentoBancario) {
		this.descuentoBancario = descuentoBancario;
	}

}
