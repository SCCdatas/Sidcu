import CuestionarioVocacional from "./CuestionarioVocacional";

const preguntas = [
    { categoria: "Trabajo individual", texto: "¿Prefieres realizar tus actividades de manera independiente?" },
    { categoria: "Trabajo individual", texto: "¿Te concentras mejor cuando trabajas por tu cuenta?" },

    { categoria: "Trabajo en equipo", texto: "¿Disfrutas compartir ideas y tareas con otras personas?" },
    { categoria: "Trabajo en equipo", texto: "¿Te gusta participar en proyectos grupales?" },

    { categoria: "Organización", texto: "¿Prefieres tener instrucciones claras antes de comenzar una actividad?" },
    { categoria: "Organización", texto: "¿Te gusta planificar tus actividades con anticipación?" },

    { categoria: "Flexibilidad", texto: "¿Te adaptas fácilmente cuando cambian tus planes?" },
    { categoria: "Flexibilidad", texto: "¿Disfrutas probar diferentes formas de hacer una actividad?" },

    { categoria: "Aprendizaje práctico", texto: "¿Aprendes mejor cuando puedes practicar lo que estás estudiando?" },
    { categoria: "Aprendizaje práctico", texto: "¿Prefieres aprender mediante proyectos y experiencias?" }
];

const TestPersonalidad = () => {
    return (
        <CuestionarioVocacional
            titulo="Test de Personalidad y Estilo de Trabajo"
            descripcion="Explora cómo prefieres trabajar, aprender y colaborar con otras personas."
            preguntas={preguntas}
            clave="sidcu_test_personalidad"
        />
    );
};

export default TestPersonalidad;