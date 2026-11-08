import { Link } from "react-router-dom";
import "./Test.css";

const tests = [
    {
        titulo: "Intereses Vocacionales",
        descripcion:
            "Descubre qué actividades, campos profesionales y áreas de conocimiento llaman más tu atención.",
        imagen: "/img/ingsis.jpg",
        ruta: "/intereses",
        preguntas: "24 preguntas",
    },
    {
        titulo: "Habilidades y Aptitudes",
        descripcion:
            "Identifica tus fortalezas, habilidades y actividades que disfrutas desarrollar.",
        imagen: "/img/aptitud.jpg",
        ruta: "/habilidades",
        preguntas: "12 preguntas",
    },
    {
        titulo: "Personalidad y Estilo de Trabajo",
        descripcion:
            "Descubre cómo prefieres trabajar, colaborar y desenvolverte en diferentes ambientes.",
        imagen: "/img/ia.jpg",
        ruta: "/personalidad",
        preguntas: "10 preguntas",
    },
    {
        titulo: "Motivaciones y Expectativas",
        descripcion:
            "Identifica qué es lo que más valoras y esperas de tu futuro profesional.",
        imagen: "/img/educacion.jpg",
        ruta: "/motivaciones",
        preguntas: "10 preguntas",
    },
    {
        titulo: "Áreas Académicas",
        descripcion:
            "Reconoce las materias y campos de conocimiento que más te interesan.",
        imagen: "/img/cienciasb.jpg",
        ruta: "/academico",
        preguntas: "10 preguntas",
    },
    {
        titulo: "Proyecto Integrador CESDE",
        descripcion:
            "Pon a prueba tus conocimientos sobre frontend, API REST, bases de datos y análisis de información.",
        imagen: "/img/ingsis.jpg",
        ruta: "/proyecto-integrador",
        preguntas: "15 preguntas",
    },
];

const Test = () => {
    return (
        <main className="tests-main">
            <section className="tests-intro">
                <h1>Descubre tu futuro profesional</h1>
                <p>
                    Realiza nuestros tests de orientación vocacional y conoce
                    las carreras que podrían relacionarse con tus intereses.
                </p>
            </section>

            <section className="tests-lista" aria-label="Tests vocacionales">
                {tests.map((test) => (
                    <article className="test-card" key={test.ruta}>
                        <img
                            src={test.imagen}
                            alt=""
                            className="test-img"
                        />

                        <h2>{test.titulo}</h2>

                        <p className="test-desc">
                            {test.descripcion}
                        </p>

                        <p className="test-cantidad">
                            {test.preguntas}
                        </p>

                        <div className="test-actions">
                            <Link className="test-btn" to={test.ruta}>
                                Iniciar test
                            </Link>
                        </div>
                    </article>
                ))}
            </section>
        </main>
    );
};

export default Test;