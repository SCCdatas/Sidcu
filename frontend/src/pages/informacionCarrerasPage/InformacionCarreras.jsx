import { useEffect, useState } from "react";
import "./InformacionCarreras.css";
import { carreras } from "./CarrerasData";
import { getApiData } from "../../services/api";

const CarreraDelCatalogo = ({ id }) => {
    const [carrera, setCarrera] = useState(null);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        let mounted = true;

        getApiData(`/api/carreras/${encodeURIComponent(id)}`)
            .then((data) => {
                if (!data || typeof data.nombre !== "string") {
                    throw new Error("El backend devolvió una carrera inválida.");
                }

                if (mounted) {
                    setCarrera(data);
                }
            })
            .catch((requestError) => {
                if (mounted) {
                    setError(requestError.message);
                }
            })
            .finally(() => {
                if (mounted) {
                    setCargando(false);
                }
            });

        return () => {
            mounted = false;
        };
    }, [id]);

    if (cargando) {
        return <main className="info-main"><p>Cargando carrera...</p></main>;
    }

    if (error) {
        return <main className="info-main"><p role="alert">{error}</p></main>;
    }

    return (
        <main className="info-main">
            <div className="info-container">
                <div className="info-texto">
                    <h1>{carrera.nombre}</h1>
                    <span className="categoria">{carrera.area}</span>
                    <div className="info-grid">
                        <div className="info-card">
                            <h3>Nivel académico</h3>
                            <p>{carrera.nivel}</p>
                        </div>
                    </div>
                    <section className="habilidades">
                        <h2>Habilidades asociadas</h2>
                        {carrera.habilidades?.length > 0 ? (
                            <div id="habilidadesLista">
                                {carrera.habilidades.map((habilidad) => (
                                    <span className="habilidad" key={habilidad.id}>
                                        {habilidad.nombre}
                                    </span>
                                ))}
                            </div>
                        ) : (
                            <p>No hay habilidades registradas para esta carrera.</p>
                        )}
                    </section>
                </div>
            </div>
        </main>
    );
};

const InformacionCarrera = () => {

    const parametros = new URLSearchParams(window.location.search);
    const id = parametros.get("id");
    const codigo = parametros.get("carrera");

    if (id) {
        return <CarreraDelCatalogo id={id} />;
    }

    const carrera = carreras[codigo];

    if (!carrera) {
        return (
            <main className="info-main">
                <h1>No se encontró la carrera</h1>
                <p>Selecciona una carrera para consultar su información.</p>
            </main>
        );
    }

    return (
        <main className="info-main">

            <div className="info-container">

                <img
                    src={carrera.imagen}
                    alt={carrera.nombre}
                />

                <div className="info-texto">

                    <h1>{carrera.nombre}</h1>

                    <span className="categoria">
                        {carrera.categoria}
                    </span>

                    <p>{carrera.descripcion}</p>

                    <div className="info-grid">

                        <div className="info-card">
                            <h3>Duración</h3>
                            <p>{carrera.duracion}</p>
                        </div>

                        <div className="info-card">
                            <h3>Costo aproximado</h3>
                            <p>{carrera.costo}</p>
                        </div>

                        <div className="info-card">
                            <h3>Salario promedio</h3>
                            <p>{carrera.salario}</p>
                        </div>

                        <div className="info-card">
                            <h3>Ocupabilidad</h3>
                            <p>{carrera.ocupabilidad}</p>
                        </div>

                    </div>

                    <section className="habilidades">
                        <h2>Habilidades necesarias</h2>

                        <div id="habilidadesLista">
                            {carrera.habilidades.map((habilidad, index) => (
                                <span className="habilidad" key={index}>
                                    {habilidad}
                                </span>
                            ))}
                        </div>
                    </section>

                    <section className="campo-laboral">
                        <h2>Campo laboral</h2>
                        <p>{carrera.campo}</p>
                    </section>

                    <section className="universidades">
                        <h2>Universidades donde puedes estudiar</h2>

                        <ul id="universidadesLista">
                            {carrera.universidades.map((universidad, index) => (
                                <li key={index}>{universidad}</li>
                            ))}
                        </ul>
                    </section>

                </div>
            </div>

        </main>
    );
};

export default InformacionCarrera;
