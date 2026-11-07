import CuestionarioVocacional from "./CuestionarioVocacional";

const preguntas = [
    { categoria: "Ayudar a otros", texto: "¿Te gustaría tener una profesión en la que puedas ayudar a las personas?" },
    { categoria: "Ayudar a otros", texto: "¿Te motiva contribuir al bienestar de tu comunidad?" },

    { categoria: "Estabilidad", texto: "¿Consideras importante tener estabilidad laboral en el futuro?" },
    { categoria: "Estabilidad", texto: "¿Te interesa una profesión que ofrezca oportunidades de crecimiento?" },

    { categoria: "Creatividad", texto: "¿Te gustaría desarrollar ideas y proyectos originales?" },
    { categoria: "Creatividad", texto: "¿Te motiva encontrar nuevas formas de hacer las cosas?" },

    { categoria: "Liderazgo", texto: "¿Te gustaría dirigir proyectos o equipos de trabajo?" },
    { categoria: "Liderazgo", texto: "¿Te motiva asumir responsabilidades y tomar decisiones?" },

    { categoria: "Investigación", texto: "¿Te gustaría descubrir información y aprender cosas nuevas constantemente?" },
    { categoria: "Investigación", texto: "¿Te interesa investigar y encontrar respuestas a diferentes preguntas?" }
];

const TestMotivaciones = () => {
    return (
        <CuestionarioVocacional
            titulo="Test de Motivaciones y Expectativas"
            descripcion="Descubre qué aspectos valoras y qué esperas de tu futuro profesional."
            preguntas={preguntas}
            clave="sidcu_test_motivaciones"
        />
    );
};

export default TestMotivaciones;