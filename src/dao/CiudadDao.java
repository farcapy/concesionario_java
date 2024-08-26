package dao;

import conn.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Vehiculo;

public class CiudadDao {

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

    public Connection conectarMySQL() {
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
            System.out.println("Conectado a la base de datos");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public int probarConexion() {
        int cantidadVehiculos = 0;
        String sql = "SELECT COUNT(*) AS total FROM ciudad;";

        try (Connection conexion = conectarMySQL();
                Statement statement = conexion.createStatement();
                ResultSet resultado = statement.executeQuery(sql)) {

            if (resultado.next()) {
                cantidadVehiculos = resultado.getInt("total");
            }
        } catch (SQLException e) {
            Logger.getLogger(CiudadDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return cantidadVehiculos;
    }
    

    public static void main(String[] args) {
        CiudadDao dao = new CiudadDao();
        int cantidad = dao.probarConexion();
        System.out.println("Cantidad de ciudades en la base de datos: " + cantidad);
    }    
}
