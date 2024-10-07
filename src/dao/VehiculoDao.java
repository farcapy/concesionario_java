package dao;

import com.mysql.cj.util.StringUtils;
import conn.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Vehiculo;

public class VehiculoDao {

    Conexion connBD = new Conexion();

    public int probarConexion() {
        int cantidadVehiculos = 0;
        String sql = "SELECT COUNT(*) AS total FROM vehiculo;";

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

    public void agregar(Vehiculo v) {
        String sql = "INSERT INTO `vehiculo` (`vehi_matricula`, `vehi_marca`, `vehi_modelo`, "
                + "`vehi_year`, `vehi_precio`, `vehi_color`, `vehi_tipo_motor`, `vehi_transmision`, `vehi_km`, `vehi_ubicacion`, `vehi_estado`, `vehi_descri`)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conexion = connBD.connMySQL();
                PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, v.getMatricula());
            statement.setString(2, v.getMarca());
            statement.setString(3, v.getModelo());
            statement.setInt(4, Integer.parseInt(v.getYear_fab()));
            statement.setDouble(5, Double.parseDouble(v.getPrecio()));
            statement.setString(6, v.getColor());
            statement.setString(7, v.getTipo_motor());
            statement.setString(8, v.getTransmision());
            statement.setInt(9, Integer.parseInt(v.getKm()));
            statement.setString(10, v.getUbicacion());
            statement.setString(11, v.getEstado());
            statement.setString(12, v.getDescripcion());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Se ha grabado con éxito");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar(Vehiculo v) {
        String sql = "UPDATE `concesionario_farca`.`vehiculo` "
                + "SET `vehi_matricula` = ?,`vehi_marca` = ?, `vehi_modelo` = ?, `vehi_year` = ?, `vehi_precio` = ?, "
                + "`vehi_color` = ?, `vehi_tipo_motor` = ?, `vehi_transmision` = ?, `vehi_km` = ?, "
                + "`vehi_ubicacion` = ?, `vehi_estado` = ?, `vehi_descri` = ? "
                + "WHERE `vehi_id` = ?;";

        try (Connection conexion = connBD.connMySQL();
                PreparedStatement statement = conexion.prepareStatement(sql)) {

            // Establecer los valores en la consulta
            statement.setString(1, v.getMatricula());
            statement.setString(2, v.getMarca());
            statement.setString(3, v.getModelo());
            statement.setInt(4, Integer.parseInt(v.getYear_fab())); // Convertir a int si es necesario
            statement.setDouble(5, Double.parseDouble(v.getPrecio())); // Convertir a double si es necesario
            statement.setString(6, v.getColor());
            statement.setString(7, v.getTipo_motor());
            statement.setString(8, v.getTransmision());
            statement.setInt(9, Integer.parseInt(v.getKm())); // Convertir a int si es necesario
            statement.setString(10, v.getUbicacion());
            statement.setString(11, v.getEstado());
            statement.setString(12, v.getDescripcion());
            statement.setInt(13, Integer.parseInt(v.getId_vehiculo())); // Convertir a int si es necesario

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

    public void eliminarVehiculo(String id) {
        try {
            Statement statement = null;
            Connection conexion = connBD.connMySQL();
            //DELETE FROM `concesionario_test`.`vehiculos` WHERE (`id_vehiculo` = '1');
            String sql = "DELETE FROM vehiculo WHERE vehi_id = " + id;
            statement = conexion.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Vehiculo> listarVehiculos() {
        List<Vehiculo> listado = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo;";

        try (Connection conexion = connBD.connMySQL();
                Statement statement = conexion.createStatement();
                ResultSet resultado = statement.executeQuery(sql)) {

            while (resultado.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setId_vehiculo(resultado.getString("vehi_id"));
                vehiculo.setMatricula(resultado.getString("vehi_matricula"));
                vehiculo.setMarca(resultado.getString("vehi_marca"));
                vehiculo.setModelo(resultado.getString("vehi_modelo"));
                vehiculo.setYear_fab(resultado.getString("vehi_year"));
                vehiculo.setPrecio(resultado.getString("vehi_precio"));
                vehiculo.setColor(resultado.getString("vehi_color"));
                vehiculo.setTipo_motor(resultado.getString("vehi_tipo_motor"));
                vehiculo.setTransmision(resultado.getString("vehi_transmision"));
                vehiculo.setKm(resultado.getString("vehi_km"));
                vehiculo.setUbicacion(resultado.getString("vehi_ubicacion"));
                vehiculo.setEstado(resultado.getString("vehi_estado"));
                vehiculo.setDescripcion(resultado.getString("vehi_descri"));
                listado.add(vehiculo);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehiculoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado;
    }

    public void guardar(Vehiculo v) {
        if (StringUtils.isEmptyOrWhitespaceOnly(v.getId_vehiculo())) {
            // Si no se proporciona un ID, genera uno nuevo
            v.setId_vehiculo(String.valueOf(obtenerUltimoID()));
            agregar(v);
        } else {
            // Verificar si el ID ya existe en la base de datos
            if (existeIdVehiculo(v.getId_vehiculo())) {
                actualizar(v);
            } else {
                agregar(v);
            }
        }
    }

    public int obtenerUltimoID() {
        int ultimoID = 0;
        String query = "SELECT MAX(vehi_id) FROM vehiculo"; // Suponiendo que 'id_vehiculo' es el nombre de la columna ID en tu tabla.

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario_farca", "root", "root1234");
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

    private boolean existeIdVehiculo(String id_vehiculo) {
        // Realiza una consulta en la base de datos para verificar si el ID existe
        String sql = "SELECT COUNT(*) FROM vehiculo WHERE vehi_id = ?";
        try (Connection conexion = connBD.connMySQL();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, id_vehiculo);
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
