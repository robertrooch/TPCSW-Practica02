
package org.uv.tpcsw.practica02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOEmpleado implements IDAOGeneral<Empleado, String>{

    @Override
    public boolean save(Empleado pojo) {
        ConexionBD conexion = ConexionBD.getInstance();
        TransaccionDB<Empleado> transaction = new TransaccionDB<Empleado>(pojo) {
            @Override
            public boolean execute(Connection con) {
                try {
                    String sql="insert into empleado (clave, nombre, direccion, telefono) values"
                            + " (?,?,?,?)";
                    
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, pojo.getClave());
                    pst.setString(2, pojo.getNombre());
                    pst.setString(3, pojo.getDireccion());
                    pst.setString(4, pojo.getTelefono());
                    pst.execute();
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(TPCSWPractica02.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        return conexion.execute(transaction);
    }

    @Override
    public boolean delete(String pojo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Empleado pojo, String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Empleado> findAll() {
        try {
            ConexionBD con  = ConexionBD.getInstance();
            String sql = "select * from empleado";
            ResultSet reg = con.select(sql);
            List<Empleado> lstEmpleado = new ArrayList<>();
            while(reg.next()){
                Empleado emp = new Empleado();
                emp.setClave(reg.getString("clave"));
                emp.setNombre(reg.getString("nombre"));
                emp.setDireccion(reg.getString("direccion"));
                emp.setTelefono(reg.getString("telefono"));
                lstEmpleado.add(emp);
            }
            return lstEmpleado;
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Empleado findById(String id) {
        try {
            ConexionBD con  = ConexionBD.getInstance();
            String sql = "select * from empleado where clave ='"+id+"'";
            ResultSet reg = con.select(sql);
            Empleado emp = new Empleado();
            while(reg.next()){
                emp.setClave(reg.getString("clave"));
                emp.setNombre(reg.getString("nombre"));
                emp.setDireccion(reg.getString("direccion"));
                emp.setTelefono(reg.getString("telefono"));
            }
            return emp;
        } catch (SQLException ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }}
    
}
