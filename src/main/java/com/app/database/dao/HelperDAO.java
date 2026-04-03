package com.app.database.dao;


import com.app.database.model.Emprestimo;

import java.sql.SQLException;
import java.util.List;

public interface HelperDAO<T> {

    void insert(T t) throws SQLException;
    T selectById(Long id) throws SQLException ;
    void deleteById(Long id) throws SQLException ;
    void update(T t) throws SQLException ;
    List<T> selectAll()throws SQLException ;

}
