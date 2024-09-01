package dao;

import conn.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CiudadDepartamentoDao {
    Conexion connBD = new Conexion();    

    public int probarConexion() {
        int cantidadCiudades = 0;
        String sql = "SELECT COUNT(*) AS total FROM ciudad;";

        try (Connection conexion = connBD.connMySQL();
                Statement statement = conexion.createStatement();
                ResultSet resultado = statement.executeQuery(sql)) {

            if (resultado.next()) {
                cantidadCiudades = resultado.getInt("total");
            }
        } catch (SQLException e) {
            Logger.getLogger(CiudadDepartamentoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return cantidadCiudades;
    }
    

    public static void main(String[] args) {
        CiudadDepartamentoDao dao = new CiudadDepartamentoDao();
        int cantidad = dao.probarConexion();
        System.out.println("Cantidad de ciudades en la base de datos: " + cantidad);
    }    
}
