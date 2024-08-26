package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class FormularioCiudadTest extends JFrame {
    //Libreria MySQL
    public String driver = "com.mysql.cj.jdbc.Driver";
    //Nombre de la base de datos
    public String database = "concesionario_test";
    //Host
    public String hostname = "localhost";
    //Puerto
    public String port = "3306";
    //Ruta de nuestra base de datos (desactivamos el uso de SSl con ?useSSL=false
    public String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    //Usuario
    public String username = "root";
    //Password
    public String password = "root1234";
    
    
    
    private JComboBox<String> comboBoxDepartamentos;
    private JComboBox<String> comboBoxCiudades;

    public FormularioCiudadTest() {
        
        // Configuración del JFrame
        setTitle("Formulario de Ciudad");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Inicializar JComboBox para Departamentos y Ciudades
        comboBoxDepartamentos = new JComboBox<>();
        comboBoxCiudades = new JComboBox<>();

        // Agregar JComboBox al JFrame
        add(new JLabel("Selecciona un Departamento:"));
        add(comboBoxDepartamentos);
        add(new JLabel("Selecciona una Ciudad:"));
        add(comboBoxCiudades);

        // Cargar los departamentos en el comboBox
        cargarDepartamentos();

        // Agregar un ActionListener para actualizar las ciudades cuando se seleccione un departamento
        comboBoxDepartamentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarCiudades((String) comboBoxDepartamentos.getSelectedItem());
            }
        });

        setVisible(true);
    }

    private void cargarDepartamentos() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre FROM departamentos");

            while (rs.next()) {
                comboBoxDepartamentos.addItem(rs.getString("nombre"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarCiudades(String departamentoSeleccionado) {
        comboBoxCiudades.removeAllItems(); // Limpiar el comboBox de ciudades

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT ciudad.nombre FROM ciudad " +
                "JOIN departamentos ON ciudad.id_depto = departamentos.id_depto " +
                "WHERE departamentos.nombre = ?"
            );
            stmt.setString(1, departamentoSeleccionado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comboBoxCiudades.addItem(rs.getString("nombre"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //new FormularioCiudadTest();
        JFrame frame = new JFrame("JComboBox Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Crear el JComboBox con opciones específicas
        String[] transmisiones = {"MANUAL", "AUTOMATICA", "SEMI_AUTOMATICA"};
        JComboBox<String> comboBox = new JComboBox<>(transmisiones);

        frame.getContentPane().add(comboBox);
        frame.setVisible(true);
    }
}

