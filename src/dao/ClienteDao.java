package dao;

import com.mysql.cj.util.StringUtils;
import conn.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Cliente;
import model.Vehiculo;

/**
 *
 * @author farca
 */
public class ClienteDao {

    Conexion connBD = new Conexion();

    public int probarConexion() {
        int cantidadVehiculos = 0;
        String sql = "SELECT COUNT(*) AS total FROM clientes;";

        try (Connection conexion = connBD.connMySQL();
                Statement statement = conexion.createStatement();
                ResultSet resultado = statement.executeQuery(sql)) {

            if (resultado.next()) {
                cantidadVehiculos = resultado.getInt("total");
            }
        } catch (SQLException e) {
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return cantidadVehiculos;
    }
    
    public void agregar(Cliente c) {
        String sql = "INSERT INTO `clientes` (`id_ciudad`, `id_depto`, `ruc`, "
                + "`ci`, `nombre`, `apellido`, `direccion`, `telefono`, `celular`)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conexion = connBD.connMySQL();
                PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, c.getId_ciudad());
            statement.setString(2, c.getId_depto());
            statement.setString(3, c.getRuc());
            statement.setString(4, c.getCi());
            statement.setString(5, c.getNombre());
            statement.setString(6, c.getApellido());
            statement.setString(7, c.getDireccion());
            statement.setString(8, c.getTelefono());
            statement.setString(9, c.getCelular());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Se ha grabado con éxito");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizar(Cliente c) {
        String sql = "UPDATE `concesionario_test`.`clientes` "
                + "SET `id_ciudad` = ?,`id_depto` = ?, `ruc` = ?, `ci` = ?, `nombre` = ?, "
                + "`apellido` = ?, `direccion` = ?, `telefono` = ?, `celular` = ?"
                + "WHERE `id_cliente` = ?;";

        try (Connection conexion = connBD.connMySQL();
                PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Establecer los valores en la consulta
            statement.setString(1, c.getId_ciudad());
            statement.setString(2, c.getId_depto());
            statement.setString(3, c.getRuc());
            statement.setString(4, c.getCi());
            statement.setString(5, c.getNombre());
            statement.setString(6, c.getApellido());
            statement.setString(7, c.getDireccion());
            statement.setString(8, c.getTelefono());
            statement.setString(9, c.getCelular());
            statement.setInt(10, Integer.parseInt(c.getId_cliente()));

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Actualizado con éxito");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            // Maneja el caso en que la conversión de String a int o double falla
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, "Error en la conversión de tipo de dato", ex);
        }
    }
    
    public void guardar(Cliente c) {
        if (StringUtils.isEmptyOrWhitespaceOnly(c.getId_cliente())) {
            // Si no se proporciona un ID, genera uno nuevo
            c.setId_cliente(String.valueOf(obtenerUltimoID()));
            agregar(c);
        } else {
            // Verificar si el ID ya existe en la base de datos
            if (existeIdVehiculo(c.getId_cliente())) {
                actualizar(c);
            } else {
                agregar(c);
            }
        }
    }
    
    public void eliminarCliente(String id) {
        try {
            Statement statement = null;
            Connection conexion = connBD.connMySQL();
            //DELETE FROM `concesionario_test`.`vehiculos` WHERE (`id_vehiculo` = '1');
            String sql = "DELETE FROM clientes WHERE id_cliente = " + id;
            statement = conexion.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int obtenerUltimoID() {
        int ultimoID = 0;
        String query = "SELECT MAX(id_cliente) FROM clientes"; // Suponiendo que 'id_vehiculo' es el nombre de la columna ID en tu tabla.

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario_test", "root", "root1234");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                ultimoID = rs.getInt(1); // Obtén el último ID
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ultimoID + 1; // Devuelve el siguiente ID
    }

    private boolean existeIdVehiculo(String id_cliente) {
        // Realiza una consulta en la base de datos para verificar si el ID existe
        String sql = "SELECT COUNT(*) FROM clientes WHERE id_cliente = ?";
        try (Connection conexion = connBD.connMySQL();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, id_cliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //    public static void main(String[] args) {
//        VehiculoDao dao = new VehiculoDao();
//        int cantidad = dao.probarConexion();
//        System.out.println("Cantidad de vehículos en la base de datos: " + cantidad);
//    }   
}
