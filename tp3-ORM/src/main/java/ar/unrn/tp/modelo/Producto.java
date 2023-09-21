package ar.unrn.tp.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private Long codigo;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Categoria categoria;
	private String descripcion;
	private double precio;
	private String marca;

	public Producto(String descripcion, Long codigo, Categoria categoria, double precio, String marca)
			throws Exception {

		if (this.datoNulo(descripcion))
			throw new Exception("dni no puede ser vacio");
		if (this.datoNulo(categoria))
			throw new Exception("La categoria no puede estar vacia");
		if (this.datoNulo(precio))
			throw new Exception("El precio no debe ser vacio");

		this.marca = marca;
		this.descripcion = descripcion;
		this.categoria = categoria;
		this.codigo = codigo;
		this.precio = precio;
	}

	public Producto() {

	}

	public double precioProducto() {

		return this.precio;
	}

	public String descripcion() {

		return this.descripcion;
	}

	public String marcaProducto() {

		return this.marca;
	}

	public void modificarProducto(String descripcion, double precio, String marca) {
		this.descripcion = descripcion;
		this.precio = precio;
		this.marca = marca;
	}

	public Long codigoProducto() {

		return this.codigo;
	}

	public Long idProducto() {
		return this.id;
	}

	private boolean datoNulo(String dato) {

		return dato.equals("");
	}

	private boolean datoNulo(double dato) {

		return dato == 0;
	}

	private boolean datoNulo(Categoria dato) {
		return dato == null;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private Long getCodigo() {
		return codigo;
	}

	private void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	private Categoria getCategoria() {
		return categoria;
	}

	private void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	private String getDescripcion() {
		return descripcion;
	}

	private void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	private double getPrecio() {
		return precio;
	}

	private void setPrecio(double precio) {
		this.precio = precio;
	}

	private String getMarca() {
		return marca;
	}

	private void setMarca(String marca) {
		this.marca = marca;
	}

}
