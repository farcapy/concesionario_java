package conn;

import dao.VehiculoDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    //Libreria MySQL
    public String driver = "com.mysql.cj.jdbc.Driver";
    //Nombre de la base de datos
    public String database = "concesionario_farca";
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
    public Statement st;
    public ResultSet re;

    public Connection connMySQL() {
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
            //System.out.println("Conectado a la base de datos");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public int probarConexion() {
        int cantidadVehiculos = 0;
        String sql = "SELECT COUNT(*) AS total FROM vehiculo;";

        try (Connection conexion = connMySQL();
                Statement statement = conexion.createStatement();
                ResultSet resultado = statement.executeQuery(sql)) {

            if (resultado.next()) {
                cantidadVehiculos = resultado.getInt("total");
            }
        } catch (SQLException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return cantidadVehiculos;
    }

    public static void main(String[] args) {
        VehiculoDao dao = new VehiculoDao();
        int cantidad = dao.probarConexion();
        System.out.println("Cantidad de vehículos en la base de datos: " + cantidad);
    }    
}
