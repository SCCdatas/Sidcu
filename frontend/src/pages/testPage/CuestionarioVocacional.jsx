import { useState } from "react";
import { Link } from "react-router-dom";
import "./TestCuestionario.css";

const opciones = [
    { texto: "Nada", valor: 1 },
    { texto: "Poco", valor: 2 },
    { texto: "Bastante", valor: 3 },
    { texto: "Mucho", valor: 4 }
];

const CuestionarioVocacional = ({ titulo, descripcion, preguntas, clave }) => {
    const [respuestas, setRespuestas] = useState({});
    const [finalizado, setFinalizado] = useState(false);

    const responder = (indice, valor) => {
        setRespuestas({
            ...respuestas,
            [indice]: valor
        });
    };

    const calcularResultados = () => {
        const resultados = {};

        preguntas.forEach((pregunta, indice) => {
            const categoria = pregunta.categoria;

            if (!resultados[categoria]) {
                resultados[categoria] = 0;
            }

            resultados[categoria] += respuestas[indice];
        });

        const resultadosOrdenados = Object.entries(resultados)
            .sort((a, b) => b[1] - a[1]);

        const resultado = {
            titulo,
            resultados: resultadosOrdenados,
            fecha: new Date().toISOString()
        };

        localStorage.setItem(clave, JSON.stringify(resultado));
        setFinalizado(true);
    };

    const cantidadRespondida = Object.keys(respuestas).length;
    const porcentaje = (cantidadRespondida / preguntas.length) * 100;

    return (
        <main className="cuestionario-main">
            <section className="cuestionario">

                {!finalizado ? (
                    <>
                        <h1>{titulo}</h1>
                        <p>{descripcion}</p>

                        <div className="progreso-test">
                            <div
                                className="progreso-test-barra"
                                style={{ width: `${porcentaje}%` }}
                            ></div>
                        </div>

                        <p>
                            Preguntas respondidas: {cantidadRespondida} de {preguntas.length}
                        </p>

                        {preguntas.map((pregunta, indice) => (
                            <article className="pregunta-test" key={indice}>
                                <h3>
                                    {indice + 1}. {pregunta.texto}
                                </h3>

                                <div className="opciones-test">
                                    {opciones.map((opcion) => (
                                        <label
                                            className="opcion-test"
                                            key={opcion.valor}
                                        >
                                            <input
                                                type="radio"
                                                name={`pregunta-${indice}`}
                                                value={opcion.valor}
                                                checked={respuestas[indice] === opcion.valor}
                                                onChange={() =>
                                                    responder(indice, opcion.valor)
                                                }
                                            />

                                            {opcion.texto}
                                        </label>
                                    ))}
                                </div>
                            </article>
                        ))}

                        <button
                            className="test-btn"
                            disabled={cantidadRespondida !== preguntas.length}
                            onClick={calcularResultados}
                        >
                            Ver resultados
                        </button>

                        <Link to="/test" className="volver-tests">
                            Volver a los tests
                        </Link>
                    </>
                ) : (
                    <section className="resultado-test">
                        <h1>¡Test completado!</h1>
                        <p>Estos son los resultados de tus respuestas:</p>

                        {JSON.parse(localStorage.getItem(clave))
                            .resultados.map(([categoria, puntaje]) => (
                                <div key={categoria}>
                                    <h3>{categoria}</h3>
                                    <p>Puntaje: {puntaje}</p>
                                </div>
                            ))}

                        <p className="nota-test">
                            Este resultado es una orientación inicial basada en tus respuestas.
                            No representa un diagnóstico ni una evaluación profesional.
                        </p>

                        <Link to="/test" className="test-btn">
                            Volver a los tests
                        </Link>
                    </section>
                )}

            </section>
        </main>
    );
};

export default CuestionarioVocacional;