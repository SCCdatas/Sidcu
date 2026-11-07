import CuestionarioVocacional from "./CuestionarioVocacional";

const preguntas = [
    { categoria: "Matemáticas", texto: "¿Disfrutas resolver ejercicios y problemas matemáticos?" },
    { categoria: "Matemáticas", texto: "¿Te interesan los números, cálculos y estadísticas?" },

    { categoria: "Ciencias naturales", texto: "¿Te gusta aprender sobre los seres vivos y el cuerpo humano?" },
    { categoria: "Ciencias naturales", texto: "¿Te interesan la biología, la química o la física?" },

    { categoria: "Tecnología", texto: "¿Te interesa aprender programación o desarrollar aplicaciones?" },
    { categoria: "Tecnología", texto: "¿Te gusta conocer cómo funcionan los computadores y sistemas tecnológicos?" },

    { categoria: "Humanidades", texto: "¿Disfrutas leer, escribir y analizar textos?" },
    { categoria: "Humanidades", texto: "¿Te interesan la historia, los idiomas y la literatura?" },

    { categoria: "Ciencias sociales", texto: "¿Te interesa comprender el comportamiento de las personas y la sociedad?" },
    { categoria: "Ciencias sociales", texto: "¿Te gusta aprender sobre economía, política o cultura?" }
];

const TestAcademico = () => {
    return (
        <CuestionarioVocacional
            titulo="Test de Áreas Académicas"
            descripcion="Identifica las materias y campos de conocimiento que más despiertan tu interés."
            preguntas={preguntas}
            clave="sidcu_test_academico"
        />
    );
};

export default TestAcademico;