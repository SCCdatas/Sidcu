import { useCallback, useEffect, useState } from "react";
import { getApiData } from "../../services/api";
import "./Analitica.css";

const fuentes = [
    { key: "carreras", ruta: "/api/carreras" },
    { key: "universidades", ruta: "/api/universidades" },
    { key: "habilidades", ruta: "/api/habilidades" },
    { key: "oferta", ruta: "/api/universidades-carreras" }
];

const consultarCatalogos = async () => {
    const respuestas = await Promise.all(
        fuentes.map(({ ruta }) => getApiData(ruta))
    );
    const catalogos = Object.fromEntries(
        fuentes.map(({ key }, indice) => [key, respuestas[indice]])
    );

    for (const { key } of fuentes) {
        if (!Array.isArray(catalogos[key])) {
            throw new Error(`El backend devolvió datos inválidos para ${key}.`);
        }
    }

    return catalogos;
};

const contarPor = (elementos, obtenerEtiqueta) => {
    const conteo = new Map();

    elementos.forEach((elemento) => {
        const etiqueta = obtenerEtiqueta(elemento)?.trim();
        if (etiqueta) {
            conteo.set(etiqueta, (conteo.get(etiqueta) || 0) + 1);
        }
    });

    return Array.from(conteo, ([etiqueta, cantidad]) => ({
        etiqueta,
        cantidad
    })).sort((a, b) => b.cantidad - a.cantidad || a.etiqueta.localeCompare(b.etiqueta));
};

const TarjetaIndicador = ({ etiqueta, valor, detalle }) => (
    <article className="analitica-indicador">
        <p>{etiqueta}</p>
        <strong>{valor}</strong>
        <span>{detalle}</span>
    </article>
);

const GraficoBarras = ({ titulo, pregunta, datos, vacio }) => {
    const maximo = Math.max(...datos.map((dato) => dato.cantidad), 0);

    return (
        <article className="analitica-grafico">
            <h2>{titulo}</h2>
            <p className="analitica-pregunta">{pregunta}</p>
            {datos.length > 0 ? (
                <ol className="analitica-barras">
                    {datos.map((dato) => (
                        <li key={dato.etiqueta}>
                            <div className="analitica-barra-encabezado">
                                <span title={dato.etiqueta}>{dato.etiqueta}</span>
                                <strong>{dato.cantidad}</strong>
                            </div>
                            <div
                                className="analitica-barra-fondo"
                                role="meter"
                                aria-label={`${dato.etiqueta}: ${dato.cantidad}`}
                                aria-valuemin={0}
                                aria-valuemax={maximo}
                                aria-valuenow={dato.cantidad}
                            >
                                <div
                                    className="analitica-barra-valor"
                                    style={{
                                        width: `${maximo ? (dato.cantidad / maximo) * 100 : 0}%`
                                    }}
                                />
                            </div>
                        </li>
                    ))}
                </ol>
            ) : (
                <p className="analitica-vacio">{vacio}</p>
            )}
        </article>
    );
};

const Analitica = () => {
    const [datos, setDatos] = useState(null);
    const [error, setError] = useState("");
    const [cargando, setCargando] = useState(true);

    const cargarDatos = useCallback(async () => {
        setCargando(true);
        setError("");

        try {
            setDatos(await consultarCatalogos());
        } catch (requestError) {
            setDatos(null);
            setError(requestError.message);
        } finally {
            setCargando(false);
        }
    }, []);

    useEffect(() => {
        let mounted = true;

        consultarCatalogos()
            .then((catalogos) => {
                if (mounted) {
                    setDatos(catalogos);
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

    const carrerasPorArea = datos
        ? contarPor(datos.carreras, (carrera) => carrera.area)
        : [];
    const carrerasPorNivel = datos
        ? contarPor(datos.carreras, (carrera) => carrera.nivel)
        : [];
    const universidadesPorOferta = datos
        ? datos.universidades
            .map((universidad) => ({
                etiqueta: universidad.nombre,
                cantidad: datos.oferta.filter(
                    (relacion) => relacion.universidadId === universidad.id
                ).length
            }))
            .sort((a, b) => b.cantidad - a.cantidad || a.etiqueta.localeCompare(b.etiqueta))
        : [];
    const habilidadesPorCarrera = datos
        ? contarPor(
            datos.carreras.flatMap((carrera) => carrera.habilidades || []),
            (habilidad) => habilidad.nombre
        )
        : [];
    const carrerasConMasOferta = datos
        ? contarPor(datos.oferta, (relacion) => relacion.nombreCarrera)
        : [];
    const areaPrincipal = carrerasPorArea[0];
    const nivelPrincipal = carrerasPorNivel[0];
    const universidadConMasOferta = universidadesPorOferta[0];
    const habilidadMasFrecuente = habilidadesPorCarrera[0];
    const carreraMasOfertada = carrerasConMasOferta[0];

    return (
        <main className="analitica-main">
            <header className="analitica-intro">
                <p className="analitica-eyebrow">Datos actuales de SIDCU</p>
                <h1>Analítica del catálogo académico</h1>
                <p>
                    Explora qué carreras hay en el catálogo, cómo se distribuyen
                    y qué universidades las ofrecen. Los gráficos se calculan
                    con los datos que responde la API conectada a la base de datos.
                </p>
                <button
                    className="analitica-actualizar"
                    type="button"
                    onClick={cargarDatos}
                    disabled={cargando}
                >
                    {cargando ? "Cargando datos..." : "Actualizar datos"}
                </button>
            </header>

            {cargando && (
                <p className="analitica-estado" role="status">
                    Consultando los catálogos de SIDCU...
                </p>
            )}

            {error && (
                <section className="analitica-error" role="alert">
                    <h2>No se pudieron cargar los datos</h2>
                    <p>{error}</p>
                    <p>
                        Verifica que el backend esté activo y que permita
                        solicitudes desde el frontend.
                    </p>
                </section>
            )}

            {datos && (
                <>
                    <section className="analitica-indicadores" aria-label="Resumen del catálogo">
                        <TarjetaIndicador
                            etiqueta="Carreras"
                            valor={datos.carreras.length}
                            detalle="programas en el catálogo"
                        />
                        <TarjetaIndicador
                            etiqueta="Universidades"
                            valor={datos.universidades.length}
                            detalle="instituciones registradas"
                        />
                        <TarjetaIndicador
                            etiqueta="Habilidades"
                            valor={datos.habilidades.length}
                            detalle="habilidades disponibles"
                        />
                        <TarjetaIndicador
                            etiqueta="Ofertas académicas"
                            valor={datos.oferta.length}
                            detalle="relaciones universidad-carrera"
                        />
                    </section>

                    <section className="analitica-hallazgos" aria-label="Respuestas rápidas">
                        <h2>¿Qué nos dicen los datos?</h2>
                        <ul>
                            <li>
                                {areaPrincipal
                                    ? `${areaPrincipal.etiqueta} es el área con más carreras (${areaPrincipal.cantidad}).`
                                    : "No hay carreras con área registrada."}
                            </li>
                            <li>
                                {nivelPrincipal
                                    ? `${nivelPrincipal.etiqueta} es el nivel académico más frecuente (${nivelPrincipal.cantidad} carreras).`
                                    : "No hay niveles académicos registrados."}
                            </li>
                            <li>
                                {universidadConMasOferta && universidadConMasOferta.cantidad > 0
                                    ? `${universidadConMasOferta.etiqueta} tiene más carreras asociadas (${universidadConMasOferta.cantidad}).`
                                    : "No hay carreras asociadas a universidades."}
                            </li>
                            <li>
                                {habilidadMasFrecuente
                                    ? `${habilidadMasFrecuente.etiqueta} es la habilidad más asociada (${habilidadMasFrecuente.cantidad} veces).`
                                    : "No hay habilidades asociadas a carreras."}
                            </li>
                            <li>
                                {carreraMasOfertada
                                    ? `${carreraMasOfertada.etiqueta} es la carrera ofrecida por más universidades (${carreraMasOfertada.cantidad}).`
                                    : "No hay relaciones de oferta académica registradas."}
                            </li>
                        </ul>
                        <p>
                            Los conteos describen los registros actuales; no representan
                            matrículas, demanda estudiantil ni tendencias históricas.
                        </p>
                    </section>

                    <section className="analitica-graficos" aria-label="Gráficos del catálogo">
                        <GraficoBarras
                            titulo="Carreras por área"
                            pregunta="¿En qué áreas se concentra la oferta de carreras?"
                            datos={carrerasPorArea}
                            vacio="No hay áreas registradas."
                        />
                        <GraficoBarras
                            titulo="Carreras por nivel académico"
                            pregunta="¿Qué nivel académico aparece con mayor frecuencia?"
                            datos={carrerasPorNivel}
                            vacio="No hay niveles académicos registrados."
                        />
                        <GraficoBarras
                            titulo="Carreras asociadas por universidad"
                            pregunta="¿Qué universidades tienen más carreras relacionadas en SIDCU?"
                            datos={universidadesPorOferta}
                            vacio="No hay universidades registradas."
                        />
                        <GraficoBarras
                            titulo="Habilidades asociadas a carreras"
                            pregunta="¿Qué habilidades se repiten con más frecuencia en el catálogo?"
                            datos={habilidadesPorCarrera}
                            vacio="No hay habilidades asociadas a las carreras."
                        />
                        <GraficoBarras
                            titulo="Universidades por carrera"
                            pregunta="¿Qué carreras aparecen en la oferta de más universidades?"
                            datos={carrerasConMasOferta}
                            vacio="No hay relaciones de oferta académica."
                        />
                    </section>

                    <p className="analitica-nota">
                        Los gráficos muestran agregaciones en el navegador de los
                        registros públicos de la API. No incluyen nombres,
                        documentos ni otros datos personales de estudiantes.
                    </p>
                </>
            )}
        </main>
    );
};

export default Analitica;
