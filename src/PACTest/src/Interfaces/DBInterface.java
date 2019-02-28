/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.sql.SQLException;

/**
 *
 * @author jacob
 */
public interface DBInterface {
    
    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void getConnection() throws SQLException, ClassNotFoundException;
}
