
import "./Carreras.css";
import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { getApiData } from "../../services/api";

const Carreras = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const busqueda = searchParams.get("buscar") || "";
    const [carreras, setCarreras] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        let mounted = true;

        getApiData("/api/carreras")
            .then((data) => {
                if (!Array.isArray(data)) {
                    throw new Error("El backend devolvió un catálogo de carreras inválido.");
                }

                if (mounted) {
                    setCarreras(data);
                    setError("");
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

    const carrerasFiltradas = carreras.filter((carrera) =>
        carrera.nombre.toLowerCase().includes(busqueda.toLowerCase())
    );

    const actualizarBusqueda = (valor) => {
        const nuevosParametros = new URLSearchParams(searchParams);
        if (valor) {
            nuevosParametros.set("buscar", valor);
        } else {
            nuevosParametros.delete("buscar");
        }
        setSearchParams(nuevosParametros, { replace: true });
    };

    return (
        <main>

            <section className="carreras-intro">
                <h1>Explora Carreras Universitarias en Colombia</h1>

                <p>
                    Encuentra información sobre programas académicos,
                    requisitos, duración y las mejores universidades
                    para cada carrera.
                </p>
            </section>

            <section className="busqueda-carrera">
                <input
                    type="text"
                    placeholder="Buscar una carrera..."
                    className="carrera-busqueda-input"
                    value={busqueda}
                    onChange={(e) => actualizarBusqueda(e.target.value)}
                />
            </section>

            <section className="carreras-lista" aria-live="polite">
                {cargando && <p>Cargando carreras desde SIDCU...</p>}
                {error && <p role="alert">{error}</p>}
                {!cargando && !error && carrerasFiltradas.length > 0
                    ? carrerasFiltradas.map((carrera) => (
                        <div className="carrera-card" key={carrera.id}>
                            <h3>{carrera.nombre}</h3>
                            <p>
                                {carrera.area}
                                <br />
                                <span>Nivel: {carrera.nivel}</span>
                            </p>
                            {carrera.habilidades?.length > 0 && (
                                <p>
                                    Habilidades: {carrera.habilidades
                                        .map((habilidad) => habilidad.nombre)
                                        .join(", ")}
                                </p>
                            )}
                            <a
                                href={`/informacion?id=${carrera.id}`}
                                className="ver-detalle"
                            >
                                Ver detalle
                            </a>
                        </div>
                    ))
                    : !cargando && !error && <p>No se encontraron carreras.</p>}
            </section>
        </main>
    );
};

export default Carreras;
