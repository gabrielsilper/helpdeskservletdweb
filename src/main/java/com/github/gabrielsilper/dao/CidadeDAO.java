package com.github.gabrielsilper.dao;

import com.github.gabrielsilper.database.ConnectionFactory;
import com.github.gabrielsilper.domain.model.Cidade;
import com.github.gabrielsilper.domain.model.Estado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadeDAO {
    public List<Cidade> listar() {
        List<Cidade> cidades = new ArrayList<>();

        String sql = """
                SELECT
                    c.nome as cidade,
                    e.nome as estado,
                    e.sigla as sigla
                FROM cidades c
                    INNER JOIN estados e ON c.id_estado = e.id""";

        try (Connection con = ConnectionFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estado est = new Estado();
                est.setNome(rs.getString("estado"));
                est.setSigla(rs.getString("sigla"));
                Cidade cid = new Cidade();
                cid.setNome(rs.getString("cidade"));
                cid.setEstado(est);
                cidades.add(cid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cidades;
    }
}
