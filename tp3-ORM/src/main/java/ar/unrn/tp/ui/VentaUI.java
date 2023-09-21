package ar.unrn.tp.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.exception.TarjetaInvalidaException;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.servicios.VentaServiceImplementacion;
import jakarta.persistence.EntityManagerFactory;

public class VentaUI extends JFrame {

	private JPanel contentPane;
	private JButton consultarPrecioButton;
	private JButton comprarButton;
	private ClienteService clientService = new ClienteServiceImplementacion();
	private VentaService ventaService = new VentaServiceImplementacion();
	private ProductoService producService = new ProductoServiceImplementacion();
	private DescuentoService descService = new DescuentoServiceImplementacion();
	private EntityManagerFactory emf;
	private JTable table;
	private JTable productosTable;
	private JTable tarjetasTable;

	public VentaUI(EntityManagerFactory emf, ClienteService clientService, VentaService ventaService,
			ProductoService producService, DescuentoService descService) {

		this.clientService = clientService;
		this.ventaService = ventaService;
		this.producService = producService;
		this.descService = descService;
		this.emf = emf;

		setTitle("Venta o Consulta");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 525, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		clientService.ClienteService(emf);

		producService.ProductoService(emf);
		descService.DescuentoService(emf);

		DefaultTableModel modeloProductos;

		String[] titulosProductos = { "Descripción", "Precio" };

		modeloProductos = new DefaultTableModel(new Object[][] {}, titulosProductos);

		producService.ProductoService(emf);
		List<Producto> productosDeBase = producService.listarProductos();

		clientService.ClienteService(emf);
		List<Tarjeta> tarjetas = clientService.listarTarjetas(1L);

		descService.DescuentoService(emf);
		List<Promocion> promociones = descService.listarDescuentos();

		for (Producto producto : productosDeBase) {
			modeloProductos.addRow(new Object[] { producto.descripcion(), producto.precioProducto() });
		}

//		
		DefaultTableModel modeloTarjetas;
		String[] titulosTajetas = { "Marca", "Digito" };
		modeloTarjetas = new DefaultTableModel(new Object[][] {}, titulosTajetas);

		for (Tarjeta tarjeta : tarjetas) {

			String num = tarjeta.nroTarjeta().toString();
			modeloTarjetas.addRow(new Object[] { tarjeta.marcaTarjeta(), num.substring(3) });
		}

		consultarPrecioButton = new JButton("Costo");
		consultarPrecioButton.setFont(new Font("Arial", Font.PLAIN, 12));
		consultarPrecioButton.addActionListener(new ActionListener() {
			float monto;

			public void actionPerformed(ActionEvent e) {
				List<Long> productos = new ArrayList<>();
				int[] seleccionados = productosTable.getSelectedRows();
				for (int i : seleccionados) {
					productos.add(productosDeBase.get(i).idProducto());
				}
				var tarjeta = tarjetas.get(tarjetasTable.getSelectedRow());

				try {
					ventaService.VentaService(emf);
					monto = ventaService.calcularMonto(productos, tarjeta.nroTarjeta(), 1L);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "El precio de la compra es de: " + monto);
			}

		});

		consultarPrecioButton.setBounds(279, 393, 89, 23);
		contentPane.add(consultarPrecioButton);

		comprarButton = new JButton("Comprar");
		comprarButton.setFont(new Font("Arial", Font.PLAIN, 12));
		comprarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventaService.VentaService(emf);
				List<Long> productos = new ArrayList<>();
				int[] seleccionados = productosTable.getSelectedRows();
				for (int i : seleccionados) {
					productos.add(productosDeBase.get(i).idProducto());
				}
				var tarjeta = tarjetas.get(tarjetasTable.getSelectedRow());

				float monto;

				try {
					ventaService.realizarVenta(1L, productos, tarjeta.nroTarjeta());
				} catch (DatoVacioException | CarritoVacioException | TarjetaInvalidaException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}

			}
		});

		comprarButton.setBounds(137, 393, 89, 23);
		contentPane.add(comprarButton);

		DefaultListModel descuentosListModel = new DefaultListModel();

		for (Promocion promocion : promociones) {
			descuentosListModel.addElement(promocion.marca());

		}
		JList descuentosList = new JList();
		descuentosList.setFont(new Font("Arial", Font.PLAIN, 12));

		descuentosList.setBounds(279, 235, 195, 130);
		contentPane.add(descuentosList);

		descuentosList.setModel(descuentosListModel);

		JLabel lblNewLabel = new JLabel("Descuentos\r\n Disponibles");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(290, 198, 143, 38);
		contentPane.add(lblNewLabel);

		JLabel lblTarjetas = new JLabel("Tarjetas");
		lblTarjetas.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTarjetas.setBounds(338, 33, 46, 14);
		contentPane.add(lblTarjetas);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(new Font("Arial", Font.PLAIN, 12));
		lblProductos.setBounds(108, 31, 71, 14);
		contentPane.add(lblProductos);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Descripci\u00F3n", "Precio" }));
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setBounds(38, 251, 111, -180);
		contentPane.add(table);

		productosTable = new JTable();
		productosTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		productosTable.setModel(modeloProductos);
		productosTable.setBounds(29, 68, 223, 215);
		contentPane.add(productosTable);

		tarjetasTable = new JTable();
		tarjetasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tarjetasTable.setModel(modeloTarjetas);
		tarjetasTable.setBounds(304, 68, 146, 119);
		contentPane.add(tarjetasTable);

	}

	public boolean cerrar(boolean cierre) {
		return cierre;
	}
}
