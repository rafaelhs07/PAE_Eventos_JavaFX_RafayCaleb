package org.example.javafxmenuretos.dao;

import org.example.javafxmenuretos.Artesania;

import java.util.ArrayList;
import java.util.List;

public class ArtesaniaDAO {

    private final List<Artesania> artesanias;

    public ArtesaniaDAO() {
        artesanias = new ArrayList<>();

        artesanias.add(
                new Artesania(
                        "Jarrón de barro",
                        "Cerámica",
                        18.50,
                        10,
                        "jarra.png"
                )
        );

        artesanias.add(
                new Artesania(
                        "Bolso tejido",
                        "Textil",
                        25.00,
                        7,
                        "bolso.png"
                )
        );

        artesanias.add(
                new Artesania(
                        "Máscara decorativa",
                        "Madera",
                        15.75,
                        12,
                        "mascara.png"
                )
        );
    }

    public void agregarArtesania(Artesania artesania) {
        artesanias.add(artesania);
    }

    public List<Artesania> listarArtesanias() {
        return artesanias;
    }

    public void eliminarArtesania(Artesania artesania) {
        artesanias.remove(artesania);
    }
}