
import "./Comparador.css";
import { useState } from "react";
import { carreras as datosCarreras } from "../informacionCarrerasPage/CarrerasData";

const carreras = Object.entries(datosCarreras).map(([codigo, datos]) => ({
    codigo,
    ...datos
}));

const Comparador = () => {

    const [carreraA, setCarreraA] = useState("");
    const [carreraB, setCarreraB] = useState("");
    const [carreraC, setCarreraC] = useState("");

    const [mostrarResultado, setMostrarResultado] = useState(false);

    const comparar = (e) => {
        e.preventDefault();

        if (!carreraA || !carreraB) {
            alert("Selecciona al menos dos carreras para comparar.");
            return;
        }

        if (
            carreraA === carreraB ||
            carreraA === carreraC ||
            (carreraB === carreraC && carreraC !== "")
        ) {
            alert("Selecciona carreras diferentes.");
            return;
        }

        setMostrarResultado(true);
    };

    const restablecer = () => {
        setCarreraA("");
        setCarreraB("");
        setCarreraC("");
        setMostrarResultado(false);
    };

    const carrerasSeleccionadas = carreras.filter((carrera) =>
        [carreraA, carreraB, carreraC].includes(carrera.codigo)
    );

    return (
        <main className="comparador-main">

            <section className="comparador-intro">
                <h1>Comparador de Carreras</h1>

                <p>
                    Compara hasta tres carreras universitarias y descubre
                    sus diferencias en duración, costos, salarios,
                    ocupabilidad y campo laboral.
                </p>
            </section>

            <section className="comparador-controls">

                <form
                    className="comparador-form"
                    onSubmit={comparar}
                >

                    <div className="selector-row">

                        <div className="selector-group">
                            <label htmlFor="selectA">Carrera A</label>

                            <select
                                id="selectA"
                                value={carreraA}
                                onChange={(e) => {
                                    setCarreraA(e.target.value);
                                    setMostrarResultado(false);
                                }}
                            >
                                <option value="">Seleccionar carrera</option>

                                {carreras.map((carrera) => (
                                    <option
                                        key={carrera.codigo}
                                        value={carrera.codigo}
                                    >
                                        {carrera.nombre}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="selector-group">
                            <label htmlFor="selectB">Carrera B</label>

                            <select
                                id="selectB"
                                value={carreraB}
                                onChange={(e) => {
                                    setCarreraB(e.target.value);
                                    setMostrarResultado(false);
                                }}
                            >
                                <option value="">Seleccionar carrera</option>

                                {carreras.map((carrera) => (
                                    <option
                                        key={carrera.codigo}
                                        value={carrera.codigo}
                                    >
                                        {carrera.nombre}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="selector-group">
                            <label htmlFor="selectC">
                                Carrera C (opcional)
                            </label>

                            <select
                                id="selectC"
                                value={carreraC}
                                onChange={(e) => {
                                    setCarreraC(e.target.value);
                                    setMostrarResultado(false);
                                }}
                            >
                                <option value="">Seleccionar carrera</option>

                                {carreras.map((carrera) => (
                                    <option
                                        key={carrera.codigo}
                                        value={carrera.codigo}
                                    >
                                        {carrera.nombre}
                                    </option>
                                ))}
                            </select>
                        </div>

                    </div>

                    <div className="form-actions">

                        <button type="submit" className="comparar-button">
                            Comparar
                        </button>

                        <button
                            type="button"
                            className="reset-button"
                            onClick={restablecer}
                        >
                            Restablecer
                        </button>

                    </div>

                </form>

            </section>

            {mostrarResultado && (

                <section className="resultado-comparador">

                    <h2>Resultados de la comparación</h2>

                    <div className="resultado-lista">

                        {carrerasSeleccionadas.map((carrera) => (

                            <article
                                className="carrera-comparada"
                                key={carrera.codigo}
                            >

                                <h3>{carrera.nombre}</h3>

                                <p>
                                    <strong>Categoría:</strong> {carrera.categoria}
                                </p>

                                <p>
                                    <strong>Duración:</strong> {carrera.duracion}
                                </p>

                                <p>
                                    <strong>Costo aproximado:</strong> {carrera.costo}
                                </p>

                                <p>
                                    <strong>Salario promedio:</strong> {carrera.salario}
                                </p>

                                <p>
                                    <strong>Ocupabilidad:</strong> {carrera.ocupabilidad}
                                </p>

                                <div className="comparador-detalle">
                                    <h4>Descripción</h4>
                                    <p>{carrera.descripcion}</p>
                                </div>

                                <div className="comparador-detalle">
                                    <h4>Habilidades</h4>

                                    <ul>
                                        {carrera.habilidades.map((habilidad, index) => (
                                            <li key={index}>{habilidad}</li>
                                        ))}
                                    </ul>
                                </div>

                                <div className="comparador-detalle">
                                    <h4>Campo laboral</h4>
                                    <p>{carrera.campo}</p>
                                </div>

                                <div className="comparador-detalle">
                                    <h4>Universidades</h4>

                                    <ul>
                                        {carrera.universidades.map((universidad, index) => (
                                            <li key={index}>{universidad}</li>
                                        ))}
                                    </ul>
                                </div>

                            </article>

                        ))}

                    </div>

                </section>

            )}

        </main>
    );
};

export default Comparador;
