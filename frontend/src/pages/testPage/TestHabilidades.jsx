import CuestionarioVocacional from "./CuestionarioVocacional";

const preguntas = [
    { categoria: "Pensamiento lógico", texto: "¿Se te facilita resolver problemas usando la lógica?" },
    { categoria: "Pensamiento lógico", texto: "¿Te gusta encontrar soluciones a situaciones complicadas?" },

    { categoria: "Comunicación", texto: "¿Se te facilita explicar tus ideas a otras personas?" },
    { categoria: "Comunicación", texto: "¿Te sientes cómodo hablando frente a un grupo?" },

    { categoria: "Creatividad", texto: "¿Te resulta fácil proponer ideas diferentes?" },
    { categoria: "Creatividad", texto: "¿Disfrutas crear proyectos o diseñar cosas nuevas?" },

    { categoria: "Organización", texto: "¿Te gusta planificar tus actividades?" },
    { categoria: "Organización", texto: "¿Se te facilita organizar información y documentos?" },

    { categoria: "Trabajo en equipo", texto: "¿Se te facilita colaborar con otras personas?" },
    { categoria: "Trabajo en equipo", texto: "¿Te gusta compartir responsabilidades en un grupo?" },

    { categoria: "Habilidades prácticas", texto: "¿Aprendes fácilmente realizando actividades prácticas?" },
    { categoria: "Habilidades prácticas", texto: "¿Te gusta trabajar con herramientas, materiales o equipos?" }
];

const TestHabilidades = () => {
    return (
        <CuestionarioVocacional
            titulo="Test de Habilidades y Aptitudes"
            descripcion="Reconoce las habilidades y actividades que consideras que se te facilitan."
            preguntas={preguntas}
            clave="sidcu_test_habilidades"
        />
    );
};

export default TestHabilidades;