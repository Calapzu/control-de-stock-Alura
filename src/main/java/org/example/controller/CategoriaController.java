package org.example.controller;

import org.example.dao.CategoriaDAO;
import org.example.factory.ConectionFactory;
import org.example.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;

    public CategoriaController() {
        var factory = new ConectionFactory();
        this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion());
    }

    public List<Categoria> listar(){
        //TODO
        return categoriaDAO.listar();
    }

    public List<?> cargaReporte(){
        //TODO
        return new ArrayList<>();
    }
}
