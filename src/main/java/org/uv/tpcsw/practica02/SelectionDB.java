package org.uv.tpcsw.practica02;

import java.sql.Connection;
import java.util.List;

public abstract class SelectionDB<T, ID> {
    protected abstract List<T> findAll(Connection con);
    protected abstract T findByID(Connection con, ID id);
}
