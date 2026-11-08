import { useState } from "react";
import { Link } from "react-router-dom";
import "./TestCuestionario.css";

const preguntas = [
    {
        texto: "¿Cómo se comunican el frontend, el backend y el tablero analítico?",
        opciones: [
            "Mediante solicitudes HTTP y respuestas JSON",
            "Conectándose todos directamente a la base de datos",
            "Compartiendo archivos de texto por correo",
            "Usando únicamente hojas de cálculo"
        ],
        respuesta: 0,
        explicacion: "Los proyectos intercambian información por HTTP y usan JSON como formato común."
    },
    {
        texto: "¿Cuál es la responsabilidad principal del backend en este proyecto?",
        opciones: [
            "Diseñar todas las gráficas del tablero",
            "Validar solicitudes, aplicar reglas y guardar datos",
            "Reemplazar los formularios del frontend",
            "Abrir directamente la base de datos en el navegador"
        ],
        respuesta: 1,
        explicacion: "El backend valida, aplica reglas de negocio, persiste en SQLite y expone la API."
    },
    {
        texto: "¿Por qué el navegador no debe escribir directamente en SQLite?",
        opciones: [
            "Porque SQLite no permite guardar texto",
            "Porque el frontend solo puede mostrar imágenes",
            "Para centralizar validaciones y proteger el acceso a los datos en el backend",
            "Para evitar que la API use JSON"
        ],
        respuesta: 2,
        explicacion: "La API centraliza el acceso y las reglas; el navegador solicita operaciones al backend."
    },
    {
        texto: "¿Cuál es el orden recomendado para construir los tres componentes?",
        opciones: [
            "Analítica, frontend y backend",
            "Frontend, analítica y backend",
            "Backend, frontend y analítica",
            "Los tres deben construirse en una sola carpeta"
        ],
        respuesta: 2,
        explicacion: "Se recomienda construir primero la API, luego el frontend y finalmente el tablero analítico."
    },
    {
        texto: "¿Qué recursos principales administra el CRUD de la API del proyecto?",
        opciones: [
            "Estudiantes, cursos y matrículas",
            "Productos, vendedores y facturas",
            "Libros, autores y bibliotecas",
            "Usuarios, publicaciones y comentarios"
        ],
        respuesta: 0,
        explicacion: "La API define operaciones CRUD para estudiantes, cursos y matrículas."
    },
    {
        texto: "¿Qué representa una matrícula en el modelo de datos descrito en la guía?",
        opciones: [
            "Una gráfica que compara cursos",
            "La relación entre un estudiante y un curso",
            "Una copia de seguridad de SQLite",
            "Una regla para calcular los KPI"
        ],
        respuesta: 1,
        explicacion: "La matrícula une un estudiante con un curso mediante llaves foráneas."
    },
    {
        texto: "¿Qué método HTTP se usa normalmente para registrar un nuevo recurso?",
        opciones: [
            "GET",
            "POST",
            "DELETE",
            "HEAD"
        ],
        respuesta: 1,
        explicacion: "En los endpoints descritos, POST crea registros nuevos."
    },
    {
        texto: "¿Qué método HTTP permite consultar o listar recursos?",
        opciones: [
            "GET",
            "POST",
            "PATCH",
            "DELETE"
        ],
        respuesta: 0,
        explicacion: "GET obtiene información sin crear, actualizar ni eliminar registros."
    },
    {
        texto: "¿Qué métodos se usan para actualizar y eliminar un recurso existente?",
        opciones: [
            "GET para actualizar y POST para eliminar",
            "PUT para actualizar y DELETE para eliminar",
            "POST para ambas operaciones",
            "JSON para actualizar y SQLite para eliminar"
        ],
        respuesta: 1,
        explicacion: "La tabla de endpoints asigna PUT a la actualización y DELETE a la eliminación."
    },
    {
        texto: "¿Qué endpoint consolidado consume el tablero para obtener sus indicadores y series?",
        opciones: [
            "/api/estudiantes",
            "/api/cursos",
            "/api/analitica/reporte",
            "/api/matriculas"
        ],
        respuesta: 2,
        explicacion: "La guía identifica /api/analitica/reporte como el endpoint que devuelve KPI y series."
    },
    {
        texto: "¿Qué debe presentar el tablero analítico según el producto final esperado?",
        opciones: [
            "Cinco KPI, cinco gráficas e historias",
            "Un formulario y una tabla de usuarios",
            "Una única gráfica sin explicación",
            "Solo el archivo SQLite"
        ],
        respuesta: 0,
        explicacion: "El producto final del componente analítico contempla cinco KPI, cinco gráficas e historias."
    },
    {
        texto: "¿Cómo se debe elegir una gráfica para un análisis?",
        opciones: [
            "Elegir siempre una gráfica de torta",
            "Usar la que mejor responda la pregunta analítica",
            "Escoger la que tenga más colores",
            "Usar la misma gráfica para todas las variables"
        ],
        respuesta: 1,
        explicacion: "La visualización debe responder la pregunta planteada; no se elige solo por decoración."
    },
    {
        texto: "¿Cuál es una interpretación responsable de los datos?",
        opciones: [
            "Afirmar causas aunque los datos solo muestren una relación",
            "Repetir los números sin explicar su significado",
            "Describir hallazgos respaldados por evidencia y no inventar causas",
            "Eliminar valores atípicos sin revisarlos"
        ],
        respuesta: 2,
        explicacion: "Las conclusiones deben estar respaldadas por evidencia y distinguir hechos de interpretaciones."
    },
    {
        texto: "Si el frontend muestra “Failed to fetch”, ¿qué recomienda revisar primero la guía?",
        opciones: [
            "Que la API esté iniciada y que el origen permitido de CORS sea correcto",
            "Que el tablero tenga cinco gráficas",
            "Que la base de datos se abra en el navegador",
            "Que se borren todas las matrículas"
        ],
        respuesta: 0,
        explicacion: "La guía relaciona ese error con una API apagada o con una configuración incorrecta de CORS."
    },
    {
        texto: "¿Qué ayuda a evitar errores de integridad al guardar y eliminar registros relacionados?",
        opciones: [
            "Ignorar las llaves foráneas y las reglas de unicidad",
            "Validar datos y respetar las relaciones antes de borrar registros",
            "Guardar todos los datos en el navegador",
            "Eliminar primero las tablas principales"
        ],
        respuesta: 1,
        explicacion: "Las restricciones de unicidad y las llaves foráneas protegen la integridad de los datos relacionados."
    }
];

const TestProyectoIntegrador = () => {
    const [respuestas, setRespuestas] = useState({});
    const [finalizado, setFinalizado] = useState(false);

    const totalRespondidas = Object.keys(respuestas).length;
    const puntaje = preguntas.reduce(
        (total, pregunta, indice) =>
            total + (respuestas[indice] === pregunta.respuesta ? 1 : 0),
        0
    );

    const reiniciar = () => {
        setRespuestas({});
        setFinalizado(false);
    };

    return (
        <main className="cuestionario-main">
            <section className="cuestionario">
                {!finalizado ? (
                    <>
                        <h1>Proyecto Integrador CESDE</h1>
                        <p className="instruccion-test">
                            Responde las 15 preguntas sobre frontend, API, base de datos
                            y analítica. Debes responder todas antes de ver tu resultado.
                        </p>

                        <div className="progreso-test">
                            <progress
                                max={preguntas.length}
                                value={totalRespondidas}
                                aria-label="Progreso del cuestionario"
                            />
                            Preguntas respondidas: {totalRespondidas} de {preguntas.length}
                        </div>

                        {preguntas.map((pregunta, indice) => (
                            <fieldset className="pregunta-test" key={pregunta.texto}>
                                <legend>
                                    {indice + 1}. {pregunta.texto}
                                </legend>
                                <div className="opciones-test">
                                    {pregunta.opciones.map((opcion, opcionIndice) => (
                                        <label className="opcion-test" key={opcion}>
                                            <input
                                                type="radio"
                                                name={`pregunta-${indice}`}
                                                value={opcionIndice}
                                                checked={respuestas[indice] === opcionIndice}
                                                onChange={() =>
                                                    setRespuestas((actuales) => ({
                                                        ...actuales,
                                                        [indice]: opcionIndice
                                                    }))
                                                }
                                            />
                                            {opcion}
                                        </label>
                                    ))}
                                </div>
                            </fieldset>
                        ))}

                        <button
                            className="test-btn"
                            type="button"
                            disabled={totalRespondidas !== preguntas.length}
                            onClick={() => setFinalizado(true)}
                        >
                            Ver resultado
                        </button>
                        <Link to="/test" className="volver-tests">
                            Volver a los tests
                        </Link>
                    </>
                ) : (
                    <section className="resultado-test" aria-live="polite">
                        <h1>Resultado del proyecto integrador</h1>
                        <p>
                            Tu puntaje: <strong>{puntaje} de {preguntas.length}</strong>
                        </p>
                        <p>
                            {Math.round((puntaje / preguntas.length) * 100)}% de respuestas correctas
                        </p>

                        <h2>Revisión de respuestas</h2>
                        {preguntas.map((pregunta, indice) => {
                            const correcta = respuestas[indice] === pregunta.respuesta;

                            return (
                                <article className="revision-pregunta" key={pregunta.texto}>
                                    <h3>
                                        {indice + 1}. {correcta ? "Correcta" : "Por revisar"}
                                    </h3>
                                    <p>{pregunta.texto}</p>
                                    <p>
                                        Respuesta correcta: {pregunta.opciones[pregunta.respuesta]}
                                    </p>
                                    <p>{pregunta.explicacion}</p>
                                </article>
                            );
                        })}

                        <button className="test-btn" type="button" onClick={reiniciar}>
                            Intentar de nuevo
                        </button>
                        <Link to="/test" className="volver-tests">
                            Volver a los tests
                        </Link>
                    </section>
                )}
            </section>
        </main>
    );
};

export default TestProyectoIntegrador;
