
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FiltrarCiudadesCB extends JFrame {

    private JComboBox<String> comboBoxDepartamentos;
    private JTable tableCiudades;
    private DefaultTableModel tableModel;

    private Connection conn;

    public FiltrarCiudadesCB() {
        setTitle("Filtro de Ciudades por Departamento");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar JComboBox y JTable
        comboBoxDepartamentos = new JComboBox<>();
        tableModel = new DefaultTableModel(new String[]{"ID Ciudad", "Nombre Ciudad"}, 0);
        tableCiudades = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableCiudades);

        // Layout de la ventana
        setLayout(new BorderLayout());
        add(comboBoxDepartamentos, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar departamentos en JComboBox
        cargarDepartamentos();

        // Listener para JComboBox
        comboBoxDepartamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCiudades();
            }
        });

        setVisible(true);
    }

    private void cargarDepartamentos() {
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario_test", "root", "root1234");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nombre FROM departamentos");

        while (rs.next()) {
            comboBoxDepartamentos.addItem(rs.getString("nombre"));
        }

        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar departamentos", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void cargarCiudades() {
        // Limpiar la tabla
        tableModel.setRowCount(0);

        String departamentoSeleccionado = (String) comboBoxDepartamentos.getSelectedItem();
        if (departamentoSeleccionado == null) {
            return;
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT ciudad.id_ciudad, ciudad.nombre FROM ciudad "
                    + "JOIN departamentos ON ciudad.id_depto = departamentos.id_depto "
                    + "WHERE departamentos.nombre = ?");
            pstmt.setString(1, departamentoSeleccionado);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idCiudad = rs.getInt("id_ciudad");
                String nombreCiudad = rs.getString("ciudad.nombre");  // Especifica la columna de la tabla 'ciudad'
                tableModel.addRow(new Object[]{idCiudad, nombreCiudad});
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar ciudades", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FiltrarCiudadesCB::new);
    }
}
