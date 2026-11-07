
import { useState } from "react";
import "./Actualizaciones.css";

const actualizacionesData = [
    {
        img: "/img/biblioteca.jpg",
        alt: "Nueva Simulación",
        texto: "Agregamos nuevas simulaciones"
    },
    {
        img: "/img/pelaos.jpg",
        alt: "Comparador",
        texto: "Implementación de Comparador"
    },
    {
        img: "/img/ia.jpg",
        alt: "IA en tests",
        texto: "Implementación de IA en los tests"
    },
    {
        img: "/img/inscripcion.jpg",
        alt: "Inscripción",
        texto: "Inscripciones Abiertas"
    },
    {
        img: "/img/test.jpg",
        alt: "Más novedades",
        texto: "Lanzamos nueva sección"
    }
];

const Actualizaciones = () => {
    const [currentIndex, setCurrentIndex] = useState(0);

    const anterior = () => {
        setCurrentIndex((actual) =>
            (actual - 1 + actualizacionesData.length) % actualizacionesData.length
        );
    };

    const siguiente = () => {
        setCurrentIndex((actual) =>
            (actual + 1) % actualizacionesData.length
        );
    };

    const actualizacionesVisibles = [];

    for (let i = 0; i < 3; i++) {
        actualizacionesVisibles.push(
            actualizacionesData[(currentIndex + i) % actualizacionesData.length]
        );
    }

    return (
        <section className="actualizaciones">
            <div className="actualizacionestitulo">
                <h2>
                    Novedades de <span>SIDCU</span>
                </h2>
            </div>

            <div className="actualizacionesarrow">
                <button className="arrow arrow-left" onClick={anterior}>
                    «
                </button>

                <div className="actualizacionescontenedor">
                    {actualizacionesVisibles.map((actualizacion, index) => (
                        <div className="actualizacion" key={index}>
                            <img
                                src={actualizacion.img}
                                alt={actualizacion.alt}
                            />
                            <p>{actualizacion.texto}</p>
                        </div>
                    ))}
                </div>

                <button className="arrow arrow-right" onClick={siguiente}>
                    »
                </button>
            </div>
        </section>
    );
};

export default Actualizaciones;
