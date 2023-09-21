package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	private Long codigoCategoria;

	public Categoria(String nombre, Long codigoCategoria) {
		this.nombre = nombre;
		this.codigoCategoria = codigoCategoria;
	}

	public Categoria() {

	}

	public String infoCategoria() {
		return this.nombre + this.codigoCategoria;
	}

}
