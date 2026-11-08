import { useEffect, useState } from "react";
import { getApiData } from "../../services/api";
import "./Colaboradores.css";

const Colaboradores = () => {
    const [universidades, setUniversidades] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        let mounted = true;

        getApiData("/api/universidades")
            .then((data) => {
                if (!Array.isArray(data)) {
                    throw new Error("El backend devolvió un catálogo de universidades inválido.");
                }

                if (mounted) {
                    setUniversidades(data);
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
    }, []);

    return (
        <main>
            <section className="colaboradores-intro">
                <h1>Universidades registradas en SIDCU</h1>
                <p>
                    Consulta las instituciones educativas registradas en el catálogo de SIDCU.
                </p>
            </section>
            <section className="universidades-lista" aria-live="polite">
                {cargando && <p>Cargando universidades desde SIDCU...</p>}
                {error && <p role="alert">{error}</p>}
                {!cargando && !error && universidades.length === 0 && (
                    <p>No hay universidades registradas.</p>
                )}
                {!error && universidades.map((universidad) => (
                    <article className="universidad" key={universidad.id}>
                        <h2>{universidad.nombre}</h2>
                        <p>{universidad.ciudad || "Ubicación no disponible"}</p>
                        {universidad.tipo && <p>{universidad.tipo}</p>}
                    </article>
                ))}
            </section>
        </main>
    );
};

export default Colaboradores;